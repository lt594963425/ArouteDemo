package com.example.modulehome.view;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.android.utils.LogUtil;
import com.example.modulehome.R;
import com.example.modulehome.utils.SysUtils;
import com.luck.picture.lib.photoview.PhotoView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiuTao on 2019/11/12 0012 下午 15:42
 * functiona:
 */
public class GraFFPhotoView extends PhotoView {
    private static final String TAG = "PhotoEditorView";
    public Context mContext;

    public interface DoodleCallback {
        void onDrawStart();

        void onDrawing();

        void onDrawComplete();

        void onRevertStateChanged(boolean canRevert);
    }

    private DoodleCallback mCallBack;

    private int mViewWidth, mViewHeight;

    private float mValidRadius = SysUtils.convertDpToPixel(getContext(), 2);

    /**
     * 判断手指移动距离，是否画了有效图形的区域
     */
    private float mGraphValidRange = SysUtils.convertDpToPixel(getContext(), 6);

    /**
     * 点击选择图形，扩大的相应的有效范围
     */
    private float mGraphValidClickRange = SysUtils.convertDpToPixel(getContext(), 8);
    /**
     * 可拖动的点的半径
     */
    private float mDotRadius = SysUtils.convertDpToPixel(getContext(), 8);

    /**
     * 暂时的涂鸦画笔
     */
    private Paint mTempPaint;


    /**
     * 暂时的图形实例，用来move时实时画路径
     */
    private DrawGraphBean mTempGraphBean;


    /**
     * 画笔的粗细
     */
    private int mPaintWidth = SysUtils.convertDpToPixel(getContext(), 3);

    /**
     * 框住图形的path的画笔
     */
    private Paint mGraphRectPaint;
    private Paint mDotPaint;


    private Bitmap mOriginBitmap;

    private MODE mMode = MODE.GRAPH_MODE;

    private GRAPH_TYPE mCurrentGraphType = GRAPH_TYPE.OVAL;
    private ArrayList<DrawGraphBean> mGraphPath = new ArrayList<>();

    /**
     * 是否可编辑
     */
    private boolean mIsEditable = true;


    /**
     * 图形的当前操作模式
     */
    private MODE mGraphMode = MODE.GRAPH_MODE;
    /**
     * 当前选中的图形
     */
    private DrawGraphBean mCurrentGraphBean;
    /**
     * 是否点击到图形了
     */
    private boolean mIsClickOnGraph = false;
    /**
     * 是否可以继续画图
     */
    private boolean mIsDrawGraph = true;

    private float mStartX, mStartY;
    private float mMoveX, mMoveY;

    /**
     * 区分点击和滑动
     */
    private float mDelaX, mDelaY;


    public GraFFPhotoView(Context context) {
        super(context);
        setDrawingCacheEnabled(true);
        initDraw();
    }

    public GraFFPhotoView(Context context, AttributeSet attr) {
        super(context, attr);
        setDrawingCacheEnabled(true);
        initDraw();
    }

    public GraFFPhotoView(Context context, AttributeSet attr, int defStyle) {
        super(context, attr, defStyle);
        setDrawingCacheEnabled(true);
        initDraw();
    }
    private void initDraw() {
        mGraphRectPaint = new Paint();
        mGraphRectPaint.setAntiAlias(true);
        mGraphRectPaint.setColor(Color.RED);
        mGraphRectPaint.setStyle(Paint.Style.STROKE);
        mGraphRectPaint.setStrokeWidth(1);
        mGraphRectPaint.setStrokeCap(Paint.Cap.ROUND);
        mGraphRectPaint.setStrokeJoin(Paint.Join.ROUND);
        // 3.5实线，2.5空白
        mGraphRectPaint.setPathEffect(new DashPathEffect(new float[]{SysUtils.convertDpToPixel(getContext(), 3.5f), SysUtils.convertDpToPixel(getContext(), 2.5f)}, 0));

        mDotPaint = new Paint();
        mDotPaint.setAntiAlias(true);
        mDotPaint.setColor(Color.RED);
        mDotPaint.setStyle(Paint.Style.FILL);
        mDotPaint.setStrokeCap(Paint.Cap.ROUND);
        mDotPaint.setStrokeJoin(Paint.Join.ROUND);

    }
    private boolean isClearPath = false;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 再画图形
        if (!isClearPath) {
            drawGraphs(canvas);
        }
        isClearPath = false;
    }

    public void setEditable(boolean editable) {
        mIsEditable = editable;
    }

    /**
     * 画所有图形
     */
    private void drawGraphs(Canvas canvas) {
        if (mGraphPath.size() > 0) {
            for (int i = 0; i < mGraphPath.size(); i++) {
                DrawGraphBean graphBean = mGraphPath.get(i);
                if (graphBean != null && graphBean.isPass) {
                    drawGraph(canvas, graphBean);
                    // 给最后一个画个框(直线除外）
                    if (mIsClickOnGraph && i == mGraphPath.size() - 1) {
                        canvas.drawRect(graphBean.startX, graphBean.startY, graphBean.endX, graphBean.endY, mGraphRectPaint);
                        // 再给起点终点画个圆
                        canvas.drawCircle(graphBean.startX, graphBean.startY, mDotRadius, mDotPaint);
                        canvas.drawCircle(graphBean.endX, graphBean.endY, mDotRadius, mDotPaint);

                    }
                }
            }
        }
        if (mTempGraphBean != null) {
            drawGraph(canvas, mTempGraphBean);
        }
    }
    /**
     * 画某个图形
     *
     * @param canvas    canvas
     * @param graphBean graphBean
     */
    private void drawGraph(Canvas canvas, DrawGraphBean graphBean) {
        if (graphBean.isPass) {
            // 椭圆
            graphBean.paint.setStyle(Paint.Style.STROKE);
            canvas.drawOval(new RectF(graphBean.startX, graphBean.startY, graphBean.endX, graphBean.endY), graphBean.paint);

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mIsEditable) {
            float downX = event.getX();
            float downY = event.getY();
            float[] tempF = {downX, downY};
            Matrix tempMatrix = new Matrix();
            getAttacher().getImageMatrix().invert(tempMatrix);
            tempMatrix.mapPoints(tempF, new float[]{downX, downY});
            mMoveX = tempF[0];
            mMoveY = tempF[1];

            int action = event.getAction();
            if (action == MotionEvent.ACTION_DOWN) {
                Log.d(TAG, "ACTION_DOWN");
                mStartX = mMoveX;
                mStartY = mMoveY;
                mDelaX = 0;
                mDelaY = 0;
                // 正常的画图操作
                if (!mIsClickOnGraph && mIsDrawGraph) {
                    touchDownNormalPath();
                } else if (mCurrentGraphBean != null && mGraphPath.size() > 0) {
                    touchDownInitGraphOperate();
                }
                if (mCallBack != null) {
                    mCallBack.onDrawStart();
                }
                return true;
            } else if (action == MotionEvent.ACTION_MOVE) {
                mDelaX += Math.abs(mMoveX - mStartX);
                mDelaY += Math.abs(mMoveY - mStartY);
                if (!mIsClickOnGraph && mIsDrawGraph) {
                    touchMoveNormalDraw();
                } else if (mCurrentGraphBean != null && mGraphPath.size() > 0) {
                    touchMoveGraphOperate();
                }

                if (mCallBack != null) {
                    mCallBack.onDrawing();
                }
                postInvalidate();
                return true;
            } else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
                Log.d(TAG, "ACTION_UP");
                if (mDelaX < mValidRadius && mDelaY < mValidRadius) {
                    // 是点击事件
                    judgeGraphClick();
                    mTempPaint = null;

                    mTempGraphBean = null;
                    postInvalidate();
                    if (mCallBack != null) {
                        mCallBack.onDrawComplete();
                        mCallBack.onRevertStateChanged(getCurrentPathSize(mMode));
                    }
                    return false;
                }
                // 非点击，正常Up
                if (!mIsClickOnGraph) {
                    // 如果是图形，画完之后，可以立即编辑当前图形
                    if (mTempGraphBean != null && mGraphPath.size() == 0) {
                        mGraphPath.add(mTempGraphBean);
                    }
                    if (mGraphPath.size() > 0 && mIsDrawGraph) {
                        mCurrentGraphBean = mGraphPath.get(mGraphPath.size() - 1);
                        mIsClickOnGraph = true;
                        mGraphMode = MODE.DRAG;
                    }
                    mTempPaint = null;
                    mTempGraphBean = null;
                }

                if (mIsClickOnGraph && mCurrentGraphBean != null) {
                    mCurrentGraphBean.startPoint.x = mCurrentGraphBean.startX;
                    mCurrentGraphBean.startPoint.y = mCurrentGraphBean.startY;
                    mCurrentGraphBean.endPoint.x = mCurrentGraphBean.endX;
                    mCurrentGraphBean.endPoint.y = mCurrentGraphBean.endY;
                    mGraphMode = MODE.DRAG;
                } else {
                    mGraphMode = MODE.NONE;
                    mCurrentGraphBean = null;
                }

                if (mCallBack != null) {
                    mCallBack.onDrawComplete();
                    mCallBack.onRevertStateChanged(getCurrentPathSize(mMode));
                }
                if (mGraphPath.size() > 0) {
                    mIsDrawGraph = false;
                } else {
                    mIsDrawGraph = true;
                }
                postInvalidate();
                return true;
            }
        }
        return super.onTouchEvent(event);
    }
    public enum MODE {
        NONE, GRAPH_MODE, DRAG, DRAG_START, DRAG_END
    }
    public enum GRAPH_TYPE {
        OVAL
    }

    /**
     * 获取指定模式下，是否可撤销
     *
     * @param mode mode
     * @return boolean
     */
    public boolean getCurrentPathSize(MODE mode) {
        boolean result = false;
        if (mIsClickOnGraph && mCurrentGraphBean != null) {
            result = true;
        }
        return result;
    }
    /**
     * 清楚正在操作的图形的焦点
     */
    public void clearGraphFocus() {
        mCurrentGraphBean = null;
        mIsClickOnGraph = false;
        mGraphMode = MODE.NONE;
        postInvalidate();
    }

    /**
     * 记录图形绘制的实例类
     */
    class DrawGraphBean {
        // 这四个点是实时变化的，用来绘制图形的四个点
        public float startX, startY, endX, endY;
        public GRAPH_TYPE type;
        public Paint paint;
        public PointF clickPoint = new PointF();
        // 两个点的变量，用于平移缩放的操作，只有在UP后，才会同步四个点的值
        PointF startPoint = new PointF();
        PointF endPoint = new PointF();
        // 是否是符合要求的图形
        boolean isPass = false;
        // 用于撤销移动缩放的操作
        List<RectF> rectFList = new ArrayList<>();

        DrawGraphBean(float startX, float startY, float endx, float endY, GRAPH_TYPE type, Paint paint) {
            this.startX = startX;
            this.startY = startY;
            this.endX = endx;
            this.endY = endY;
            this.type = type;
            this.paint = paint;
            this.startPoint.x = startX;
            this.startPoint.y = startY;
            this.endPoint.x = endx;
            this.endPoint.y = endY;
            rectFList.add(new RectF(startX, startY, endx, endY));
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void clear() {
        if (mOriginBitmap != null && !mOriginBitmap.isRecycled()) {
            mOriginBitmap.recycle();
            mOriginBitmap = null;
        }

        mCallBack = null;
    }
    /**
     * 按下时，初始化绘图参数
     */
    private void touchDownNormalPath() {
        setModePaint(mMode);
        // 创建一个对象
        mTempGraphBean = new DrawGraphBean(mStartX, mStartY, mStartX, mStartY, mCurrentGraphType, mTempPaint);

    }

    private void setModePaint(MODE mode) {
        mTempPaint = new Paint();
        mTempPaint.setAntiAlias(true);
        // 绘画图形时，总是红色画笔
        mTempPaint.setColor(ContextCompat.getColor(getContext(), R.color.red));
        mTempPaint.setStrokeWidth(mPaintWidth);
        mTempPaint.setStrokeCap(Paint.Cap.ROUND);
        mTempPaint.setStrokeJoin(Paint.Join.ROUND);
    }

    /**
     * 按下时，初始化正在操作的图形
     */
    private void touchDownInitGraphOperate() {
        // 此时，在操作某个图形
        mCurrentGraphBean.rectFList.add(new RectF(mCurrentGraphBean.startPoint.x, mCurrentGraphBean.startPoint.y,
                mCurrentGraphBean.endPoint.x, mCurrentGraphBean.endPoint.y));
        mCurrentGraphBean.clickPoint.set(mMoveX, mMoveY);
        // 判断是否点击到了起点或者终点
        RectF startDotRect = new RectF(mCurrentGraphBean.startX - mDotRadius, mCurrentGraphBean.startY - mDotRadius,
                mCurrentGraphBean.startX + mDotRadius, mCurrentGraphBean.startY + mDotRadius);
        RectF endDotRect = new RectF(mCurrentGraphBean.endX - mDotRadius, mCurrentGraphBean.endY - mDotRadius,
                mCurrentGraphBean.endX + mDotRadius, mCurrentGraphBean.endY + mDotRadius);
        if (startDotRect.contains(mMoveX, mMoveY)) {
            Log.d(TAG, "点击到起始点了");
            mGraphMode = MODE.DRAG_START;
        } else if (endDotRect.contains(mMoveX, mMoveY)) {
            Log.d(TAG, "点击到终点了");
            mGraphMode = MODE.DRAG_END;
        } else {
            mGraphMode = MODE.DRAG;
        }
    }

    /**
     * 移动时，绘制路径或者图形
     */
    private void touchMoveNormalDraw() {

        // 只操作暂时的图形对象
        if (mTempGraphBean != null) {
            // 只有移动了足够距离，才算合格的图形
            if (mDelaX > mGraphValidRange || mDelaY > mGraphValidRange) {
                mTempGraphBean.isPass = true;
                mTempGraphBean.endX = mMoveX;
                mTempGraphBean.endY = mMoveY;
                mTempGraphBean.endPoint.x = mMoveX;
                mTempGraphBean.endPoint.y = mMoveY;
                // 此时的rectList应该只有一条数据
                if (mTempGraphBean.rectFList.size() == 1) {
                    mTempGraphBean.rectFList.get(0).right = mMoveX;
                    mTempGraphBean.rectFList.get(0).bottom = mMoveY;
                }

            }
        }

    }

    /**
     * 移动图形，包括缩放等
     * 边界
     */
    private void touchMoveGraphOperate() {
        RectF tempRectF = new RectF(0, 0, SysUtils.getScreenWidth(getContext()), mOriginBitmap.getHeight());
        if (mCurrentGraphBean != null && mGraphPath.size() > 0) {
            LogUtil.e("拖动", mCurrentGraphBean.startPoint.x + "---" + mMoveX + "--------" + mMoveY + "-----" + mOriginBitmap.getHeight());
            if (mCurrentGraphBean != null && mGraphPath.size() > 0) {
                float dx = mMoveX - mCurrentGraphBean.clickPoint.x;
                float dy = mMoveY - mCurrentGraphBean.clickPoint.y;
                // 如果是拖拽模式
                changeGraphRect(dx, dy);
            }


        }
    }

    /**
     * 拖拽缩放图形的操作
     *
     * @param offsetX x偏移量
     * @param offsetY y偏移量
     */
    private void changeGraphRect(float offsetX, float offsetY) {
        if (mCurrentGraphBean != null && mGraphPath.size() > 0) {
            int rectSize = mCurrentGraphBean.rectFList.size();
            if (rectSize > 0) {
                RectF tempRectF = mCurrentGraphBean.rectFList.get((rectSize - 1));
                if (mGraphMode == MODE.DRAG) {
                    mCurrentGraphBean.startX = mCurrentGraphBean.startPoint.x + offsetX;
                    mCurrentGraphBean.startY = mCurrentGraphBean.startPoint.y + offsetY;
                    mCurrentGraphBean.endX = mCurrentGraphBean.endPoint.x + offsetX;
                    mCurrentGraphBean.endY = mCurrentGraphBean.endPoint.y + offsetY;
                } else if (mGraphMode == MODE.DRAG_START) {
                    // 如果是拖动起始点
                    // 只需要变化起始点的坐标即可
                    mCurrentGraphBean.startX = mCurrentGraphBean.startPoint.x + offsetX;
                    mCurrentGraphBean.startY = mCurrentGraphBean.startPoint.y + offsetY;
                    Log.d(TAG, "拖动起始点");
                } else if (mGraphMode == MODE.DRAG_END) {
                    // 如果是拖动终点
                    // 只需要变化终点的坐标即可

                    mCurrentGraphBean.endX = mCurrentGraphBean.endPoint.x + offsetX;
                    mCurrentGraphBean.endY = mCurrentGraphBean.endPoint.y + offsetY;

                    Log.d(TAG, "拖动终点");
                }
                // 更新围绕的rect
                tempRectF.left = mCurrentGraphBean.startX;
                tempRectF.top = mCurrentGraphBean.startY;
                tempRectF.right = mCurrentGraphBean.endX;
                tempRectF.bottom = mCurrentGraphBean.endY;

                Log.d(TAG, "拖动图形rect");
            }
        }
    }


    /**
     * 判断是否点击到图形，点击到的话，将点击的图形显示在最上层
     * 改成拖动模式，并设置是否点击图形为true
     */
    private void judgeGraphClick() {
        // 倒过来循环图形列表
        int clickIndex = -1;
        // 点击时，默认没选中
        mIsClickOnGraph = false;

        mGraphMode = MODE.NONE;
        // 重要！：当mCurrentGraphBean不为空时，在Down时，会new 一个记录操作的rect，典型的场景就是
        // 两个图形，第一个选中，此时点击第二个，就会多new 一个rect，因此在mCurrentGraphBean不是null，则需要移除这个多余的操作记录
        if (mCurrentGraphBean != null) {
            if (mCurrentGraphBean.rectFList.size() > 1) {
                mCurrentGraphBean.rectFList.remove(mCurrentGraphBean.rectFList.size() - 1);
            }
        }
        for (int i = mGraphPath.size() - 1; i > -1; i--) {
            DrawGraphBean graphBean = mGraphPath.get(i);
            if (graphBean != null) {
                RectF rectF = null;
                // 通过rect的contains判断，rect需要左上右下从小到大才能正确判断

                rectF = new RectF(Math.min(graphBean.startX, graphBean.endX) - mGraphValidClickRange,
                        Math.min(graphBean.startY, graphBean.endY) - mGraphValidClickRange,
                        Math.max(graphBean.startX, graphBean.endX) + mGraphValidClickRange,
                        Math.max(graphBean.startY, graphBean.endY) + mGraphValidClickRange);

                // 通过rect来判断
                // 看看点击的点是否在这个框框里(这个框框的判定很奇怪，需要坐标从小到大）
                if (rectF != null && rectF.contains(mMoveX, mMoveY)) {
                    // 点击到图形了，说明接下来会进行图形操作，给mCurrentGraphBean赋新值
                    mCurrentGraphBean = graphBean;
                    Log.d(TAG, "点击到图形啦！" + rectF);
                    mIsClickOnGraph = true;
                    mGraphMode = MODE.DRAG;
                    clickIndex = i;
                    // 直接跳出当前循环
                    break;
                }
            } else {
                mCurrentGraphBean = null;
                mIsClickOnGraph = false;
                mGraphMode = MODE.NONE;
            }
        }
        if (mGraphPath.size() <= 0) {
            mCurrentGraphBean = null;
            mIsClickOnGraph = false;
            mGraphMode = MODE.NONE;
        }

        // 把在操作的图形添加到栈底
        if (mIsClickOnGraph && mCurrentGraphBean != null && clickIndex > -1 && clickIndex < mGraphPath.size()) {
            mGraphPath.remove(clickIndex);
            mGraphPath.add(mCurrentGraphBean);
        } else {
            mCurrentGraphBean = null;
            mIsClickOnGraph = false;
            mGraphMode = MODE.NONE;
        }
    }

}
