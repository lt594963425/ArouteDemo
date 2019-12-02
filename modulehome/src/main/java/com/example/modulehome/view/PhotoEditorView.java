package com.example.modulehome.view;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


import com.android.utils.LogUtil;
import com.example.modulehome.R;
import com.example.modulehome.utils.SysUtils;
import com.luck.picture.lib.photoview.PhotoView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * Created by LiuTao on 2019/11/5 0005 下午 16:38
 * Date: 2019/11/5 0005
 * functiona:
 */

public class PhotoEditorView extends PhotoView implements LifecycleObserver {
    private static final String TAG = "PhotoEditorView";
    public Context mContext;
    public Region mRegion;
    public int mBitmapWidth;


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


    private MODE mMode = MODE.GRAPH_MODE;

    private GRAPH_TYPE mCurrentGraphType = GRAPH_TYPE.OVAL;
    private ArrayList<DrawGraphBean> mGraphPath = new ArrayList<>();

    /**
     * 是否可编辑
     */
    private boolean mIsEditable = false;


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


    public PhotoEditorView(Context context) {

        super(context);
        init(context);
        autoBindLifecycle(context);
    }

    public PhotoEditorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
        autoBindLifecycle(context);
    }

    public PhotoEditorView setCallBack(DoodleCallback callBack) {
        this.mCallBack = callBack;
        return this;
    }

    private void init(Context context) {
        this.mContext = context;
        setMode(mMode);
        initDraw();

    }


    /**
     * 设置原始的截图
     *
     * @param
     */
//    public void setOriginBitmap(@NonNull Bitmap originBitmap) {
//        mOriginBitmap = originBitmap;
//        initOriginBitmap();
//        postInvalidate();
//    }
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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mViewWidth <= 0 || mViewHeight <= 0) {
            return;
        }

        // 再画图形
        if (!isClearPath) {
            drawGraphs(canvas);
        }
        isClearPath = false;
    }

    private boolean isClearPath = false;

    /**
     * 清除所有
     */
    public void deletePath() {
        isClearPath = true;
        mIsDrawGraph = true;
        mGraphPath.clear();
        mCurrentGraphBean = null;
        mIsClickOnGraph = false;
        mGraphMode = MODE.NONE;
        postInvalidate();
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
                        canvas.drawRect(graphBean.startX , graphBean.startY, graphBean.endX, graphBean.endY, mGraphRectPaint);
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
     * 保存
     *
     * @param path
     */
    public void saveBitmapCanvas(final String path) {
        ArrayList<DrawGraphBean> newGraphPath = mGraphPath;
        try {
            Drawable drawable = getDrawable();
            final Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            int h = bitmap.getHeight();
            int w = bitmap.getWidth();
            LogUtil.e("PhotoView", w + "------------------------------------" + h);
            Canvas canvas = new Canvas(bitmap);
            for (int i = 0; i < newGraphPath.size(); i++) {
                DrawGraphBean graphBean = newGraphPath.get(i);
                if (graphBean.isPass) {
                    if (graphBean.isPass) {
                        // 椭圆
                        graphBean.paint.setStyle(Paint.Style.STROKE);
                        graphBean.paint.setStrokeWidth(mPaintWidth * w / mViewWidth);
                        canvas.drawOval(new RectF(graphBean.startX * w / mViewWidth, graphBean.startY * w / mViewWidth, graphBean.endX * w / mViewWidth, graphBean.endY * w / mViewWidth), graphBean.paint);
                    }
                }
            }
            LogUtil.e("路径", path);
            deletePath();
            Executors.newCachedThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    LogUtil.e("路径", "-----------" + Thread.currentThread().getName());
                    FileOutputStream fos;
                    try {
                        File file = new File(path);
                        File pfIle = file.getParentFile();
                        if (pfIle != null && !pfIle.exists()) {
                            pfIle.mkdirs();
                        }
                        if (file.exists()) {
                            file.delete();
                        }
                        fos = new FileOutputStream(file);
                        //保存图片的设置，压缩图片
                        if (bitmap != null) {
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 99, fos);
                        }
                        fos.flush();
                        fos.close();//关闭流


                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearBitmap(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
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
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w > 0 && h > 0) {
            mViewWidth = w;
            mViewHeight = h;
//            1080###-------------------------------###720
            LogUtil.e("PhotoView", mViewWidth + "###-------------------------------###" + mViewHeight);
            mRegion = new Region();
            mRegion.set(0, 0, mViewWidth, mViewHeight);

        }
    }

//    private void initOriginBitmap() {
//        if (mOriginBitmap != null && mViewHeight > 0 && mViewWidth > 0) {
////            mOriginBitmap = Bitmap.createBitmap(mOriginBitmap, 0, 0, mViewWidth, mViewHeight);
////            Log.d(TAG, "onSizeChanged:w:" + mViewWidth + "//h:" + mViewHeight);
//        }
//    }

    private void setModePaint(MODE mode) {
        mTempPaint = new Paint();
        mTempPaint.setAntiAlias(true);
        // 绘画图形时，总是红色画笔
        mTempPaint.setColor(ContextCompat.getColor(getContext(), R.color.red));
        mTempPaint.setStrokeWidth(mPaintWidth);
        mTempPaint.setStrokeCap(Paint.Cap.ROUND);
        mTempPaint.setStrokeJoin(Paint.Join.ROUND);
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mIsEditable) {
            mMoveX = event.getX();
            mMoveY = event.getY();
            if (mRegion.contains((int) mMoveX, (int) mMoveY)) {
                LogUtil.e("PhotoView", mMoveX + "###------###" + mMoveY);
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
                    return motionActionUpCancel();
                }
            } else {
                return motionActionUpCancel();
            }

        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    private boolean motionActionUpCancel() {
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

    /**
     * 按下时，初始化绘图参数
     */
    private void touchDownNormalPath() {
        setModePaint(mMode);
        // 创建一个对象
        mTempGraphBean = new DrawGraphBean(mStartX, mStartY, mStartX, mStartY, mCurrentGraphType, mTempPaint);

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


    public void setEditable(boolean editable) {
        mIsEditable = editable;
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

        if (mCurrentGraphBean != null && mGraphPath.size() > 0) {
            float dx = mMoveX - mCurrentGraphBean.clickPoint.x;
            float dy = mMoveY - mCurrentGraphBean.clickPoint.y;
            // 如果是拖拽模式
            changeGraphRect(dx, dy);
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

                float startoffsetX = mCurrentGraphBean.startPoint.x + offsetX;
                float startoffsetY = mCurrentGraphBean.startPoint.y + offsetY;

                float endoffsetX = mCurrentGraphBean.endPoint.x + offsetX;
                float endoffsetY = mCurrentGraphBean.endPoint.y + offsetY;
                if (mRegion.contains((int) startoffsetX, (int) startoffsetY)
                        && mRegion.contains((int) endoffsetX, (int) endoffsetY)) {
                    if (mGraphMode == MODE.DRAG) {
                        mCurrentGraphBean.startX = startoffsetX;
                        mCurrentGraphBean.startY = startoffsetY;
                        mCurrentGraphBean.endX = endoffsetX;
                        mCurrentGraphBean.endY = endoffsetY;
                    } else if (mGraphMode == MODE.DRAG_START) {
                        // 如果是拖动起始点
                        // 只需要变化起始点的坐标即可
                        mCurrentGraphBean.startX = startoffsetX;
                        mCurrentGraphBean.startY = startoffsetY;
                        Log.d(TAG, "拖动起始点");
                    } else if (mGraphMode == MODE.DRAG_END) {
                        // 如果是拖动终点
                        // 只需要变化终点的坐标即可

                        mCurrentGraphBean.endX = endoffsetX;
                        mCurrentGraphBean.endY = endoffsetY;

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
     * 计算两点对应的角度
     *
     * @return float
     */
    public float getRotation(float startX, float startY, float endX, float endY) {
        float deltaX = startX - endX;
        float deltaY = startY - endY;
        // 计算坐标点相对于圆点所对应的弧度
        double radians = Math.atan2(deltaY, deltaX);
        // 把弧度转换成角度
        return (float) Math.toDegrees(radians);
    }


    public enum MODE {
        NONE, GRAPH_MODE, DRAG, DRAG_START, DRAG_END
    }


    public void setMode(MODE mode) {
        // 设置模式前，先把焦点给clear一下
        clearGraphFocus();
        this.mMode = mode;
        if (mCallBack != null) {
            mCallBack.onRevertStateChanged(getCurrentPathSize(mMode));
        }
    }

    public enum GRAPH_TYPE {
        OVAL
    }

    /**
     * 记录画笔和画图的路径，主要用来撤销画图的操作
     */
    class DrawPathBean {
        public Path path;
        public Paint paint;
        public MODE mode;

        DrawPathBean(Path path, Paint paint, MODE mode) {
            this.paint = paint;
            this.path = path;
            this.mode = mode;
        }
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
//        if (mOriginBitmap != null && !mOriginBitmap.isRecycled()) {
//            mOriginBitmap.recycle();
//            mOriginBitmap = null;
//        }

        mCallBack = null;
    }

    private void autoBindLifecycle(Context context) {
        if (context == null) {
            return;
        }
        if (context instanceof AppCompatActivity) {
            // 宿主是activity
            AppCompatActivity activity = (AppCompatActivity) context;
            ((AppCompatActivity) activity).getLifecycle().addObserver(this);
            return;
        }
        // 宿主是fragment
        if (context instanceof LifecycleOwner) {
            ((LifecycleOwner) context).getLifecycle().addObserver(this);
            return;
        }
    }


    /**
     * 撤销操作
     *
     * @return 撤销后剩余可以撤销的步骤
     */
    public int revertPath() {
        // 撤销只针对当前模式的撤销，不是所有步骤的撤销
        int size = 0;
        if (mCurrentGraphBean != null) {
            // 如果当前选中了某个图形,但是此处的rectList不能删光，需要保留一条最初的数据
            size = mCurrentGraphBean.rectFList.size();
            // 图行相关需要特殊处理，当撤销到最初始状态时，再按撤销，直接删除该图形！
            if (size > 1) {
                mCurrentGraphBean.rectFList.remove(size - 1);
                int currentSize = size - 1;
                // 移除之后，需要给起点和终点重新赋值
                if (currentSize > 0) {
                    RectF rectF = mCurrentGraphBean.rectFList.get(currentSize - 1);
                    mCurrentGraphBean.startX = rectF.left;
                    mCurrentGraphBean.startY = rectF.top;
                    mCurrentGraphBean.endX = rectF.right;
                    mCurrentGraphBean.endY = rectF.bottom;
                    mCurrentGraphBean.startPoint.x = rectF.left;
                    mCurrentGraphBean.startPoint.y = rectF.top;
                    mCurrentGraphBean.endPoint.x = rectF.right;
                    mCurrentGraphBean.endPoint.y = rectF.bottom;

                }
            } else {
                deleteCurrentGraph();
            }
            if (mCallBack != null) {
                // 只有删除了图形，这个选中图形才真正撤销完成，所以失去焦点，就撤销完了
                if (!mIsClickOnGraph) {
                    // 还有一点需要注意的是，可能是在其他涂鸦或者马赛克模式下操作的图形
                    mCallBack.onRevertStateChanged(getCurrentPathSize(mMode));
                } else {
                    mCallBack.onRevertStateChanged(mIsClickOnGraph);
                }
            }
            size -= 1;
        }
        postInvalidate();
        if (mGraphPath.size() == 0) {
            mIsDrawGraph = true;
        }
        return size;
    }

    public int revertPath2() {
        // 撤销只针对当前模式的撤销，不是所有步骤的撤销
        int size = 0;
        if (mCurrentGraphBean != null) {
            // 如果当前选中了某个图形,但是此处的rectList不能删光，需要保留一条最初的数据
            size = mCurrentGraphBean.rectFList.size();
            // 图行相关需要特殊处理，当撤销到最初始状态时，再按撤销，直接删除该图形！
            if (size > 1) {
                mCurrentGraphBean.rectFList.remove(size - 1);
                int currentSize = size - 1;
                // 移除之后，需要给起点和终点重新赋值
                if (currentSize > 0) {
                    RectF rectF = mCurrentGraphBean.rectFList.get(currentSize - 1);
                    mCurrentGraphBean.startX = rectF.left;
                    mCurrentGraphBean.startY = rectF.top;
                    mCurrentGraphBean.endX = rectF.right;
                    mCurrentGraphBean.endY = rectF.bottom;
                    mCurrentGraphBean.startPoint.x = rectF.left;
                    mCurrentGraphBean.startPoint.y = rectF.top;
                    mCurrentGraphBean.endPoint.x = rectF.right;
                    mCurrentGraphBean.endPoint.y = rectF.bottom;

                }
            } else {
                deleteCurrentGraph2();
            }
            if (mCallBack != null) {
                // 只有删除了图形，这个选中图形才真正撤销完成，所以失去焦点，就撤销完了
                if (!mIsClickOnGraph) {
                    // 还有一点需要注意的是，可能是在其他涂鸦或者马赛克模式下操作的图形
                    mCallBack.onRevertStateChanged(getCurrentPathSize(mMode));
                } else {
                    mCallBack.onRevertStateChanged(mIsClickOnGraph);
                }
            }
            size -= 1;
        }
        postInvalidate();
        if (mGraphPath.size() == 0) {
            mIsDrawGraph = true;
        }
        return size;
    }

    /**
     * 删除选中的某个图形
     */
    public void deleteCurrentGraph() {
        if (mIsClickOnGraph && mCurrentGraphBean != null && mGraphPath.size() > 0) {
            mGraphPath.remove(mCurrentGraphBean);
            mCurrentGraphBean = null;
            mIsClickOnGraph = false;
            mGraphMode = MODE.NONE;
        }
        postInvalidate();
    }

    public void deleteCurrentGraph2() {
        if (mCurrentGraphBean != null && mGraphPath.size() > 0) {
            mGraphPath.remove(mCurrentGraphBean);
            mCurrentGraphBean = null;
            mIsClickOnGraph = false;
            mGraphMode = MODE.NONE;
        }
        mGraphPath = new ArrayList<>();
        postInvalidate();
    }

    /**
     * 是否标记了缺陷
     *
     * @return
     */
    public boolean isDrawGraf() {
        return !mIsDrawGraph;
    }
}
