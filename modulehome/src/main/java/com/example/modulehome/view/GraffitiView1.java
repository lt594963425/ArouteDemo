package com.example.modulehome.view;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.utils.FileUtils;
import com.android.utils.ToastUtils;
import com.example.modulehome.R;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by GraffitiView1.
 * User: Administrator
 * Name: ArouteDemo
 * functiona:
 * Date: 2019/10/23 0023
 * Time: 上午 9:27
 */
@SuppressLint("AppCompatCustomView")
public class GraffitiView1 extends FrameLayout implements LifecycleObserver, View.OnTouchListener {
    private Paint mPaint = new Paint();
    private Paint mTextPaint = new Paint();
    private TouchGestureDetector mTouchGestureDetector; // 触摸手势监听
    private float mLastX, mLastY;

    private static final int CLICKRANGE = 5;
    int startX = 0;
    int startY = 0;
    int startTouchViewLeft = 0;
    int startTouchViewTop = 0;
    private View touchView, clickView;

    public DrawGraphBean mDrawGraphBean;
    private List<PathItem> mPathItems = new ArrayList<>(); // 保存涂鸦轨迹的集合
    private List<DrawGraphBean> mDrawGraphBeans = new ArrayList<>(); // 保存图形的集合
    private PathItem mCurrentPathItem; // 当前的涂鸦轨迹
    private PathItem mSelectedPathItem; // 选中的涂鸦轨迹
    public Bitmap mOriginalBitmap;


    class DrawGraphBean {
        public float startX, startY, endX, endY;

        public DrawGraphBean(float startX, float startY, float endX, float endY) {
            this.startX = startX;
            this.startY = startY;
            this.endX = endX;
            this.endY = endY;
        }

        public float getStartX() {
            return startX;
        }

        public void setStartX(float startX) {
            this.startX = startX;
        }

        public float getStartY() {
            return startY;
        }

        public void setStartY(float startY) {
            this.startY = startY;
        }

        public float getEndX() {
            return endX;
        }

        public void setEndX(float endX) {
            this.endX = endX;
        }

        public float getEndY() {
            return endY;
        }

        public void setEndY(float endY) {
            this.endY = endY;
        }
    }

    public GraffitiView1(Context context) {
        super(context);

        initView();
        autoBindLifecycle(context);
    }

    public GraffitiView1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
        autoBindLifecycle(context);
    }


    int bitW;
    int bitH;


    private void initView() {
        this.setOnTouchListener(this);
        //初始化文字画笔
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setDither(true);
        mTextPaint.setColor(Color.GREEN);
        mTextPaint.setStrokeWidth(10);
        mTextPaint.setTextSize(40);

        mTouchGestureDetector = new TouchGestureDetector(getContext(), new TouchGestureDetector.OnTouchGestureListener() {

            RectF mRectF = new RectF();

            @Override
            public void onScrollBegin(MotionEvent e) {
                if (e.getX() <= bitW && e.getY() <= bitH) {
                    if (mSelectedPathItem == null) {
                        mCurrentPathItem = new PathItem(); // 新的涂鸦
                        mPathItems.add(mCurrentPathItem); // 添加的集合中
                        mCurrentPathItem.mPath.moveTo(e.getX(), e.getY());
                        mDrawGraphBean = new DrawGraphBean(e.getX(), e.getY(), e.getX(), e.getY());
                        mLastX = e.getX();
                        mLastY = e.getY();
                    }

                    invalidate(); // 刷新

                }

            }

            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);

                if (hasView((int) e.getX(), (int) e.getY())) {
                    startTouchViewLeft = touchView.getLeft();
                    startTouchViewTop = touchView.getTop();
                }else {
                    touchView = null;
                    if (clickView != null) {
                        ((PictureTagView) clickView).setStatus(PictureTagView.Status.Normal);
                        clickView = null;
                    }

                }

            }
            @Override
            public boolean onSingleTapUp(MotionEvent e) { //单击
                boolean found = false;
                for (PathItem path : mPathItems) { // 绘制涂鸦轨迹
                    path.mPath.computeBounds(mRectF, true); // 计算涂鸦轨迹的矩形范围
                    mRectF.offset(path.mX, path.mY); // 加上偏移
                    if (mRectF.contains(e.getX(), e.getY())) { // 判断是否点中涂鸦轨迹的矩形范围内
                        found = true;
                        mSelectedPathItem = path;
                        break;
                    }
                }
                if (!found) { // 没有点中任何涂鸦
                    mSelectedPathItem = null;
                }
                startX = (int) e.getX();
                startY = (int) e.getY();
                //则判定为单击
                if (touchView != null) {
                    //当前点击的view进入编辑状态
                    ((PictureTagView) touchView).setStatus(PictureTagView.Status.Edit);
                    clickView = touchView;
                }
                touchView = null;

                addItem((int) e.getX(), (int) e.getY());
                invalidate();
                return true;

            }

            @Override
            public void onUpOrCancel(MotionEvent e) {
                super.onUpOrCancel(e);
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                if (e2.getX() <= bitW && e2.getY() <= bitH) {
                    if (mSelectedPathItem == null) { // 没有选中的涂鸦
                        mCurrentPathItem.mPath.quadTo(
                                mLastX,
                                mLastY,
                                (e2.getX() + mLastX) / 2,
                                (e2.getY() + mLastY) / 2); // 使用贝塞尔曲线 让涂鸦轨迹更圆滑
                        mDrawGraphBean.setEndX(e2.getX());
                        mDrawGraphBean.setEndY(e2.getY());
                        mLastX = e2.getX();
                        mLastY = e2.getY();
                    } else { // 移动选中的涂鸦
                        mSelectedPathItem.mX = mSelectedPathItem.mX - distanceX;
                        mSelectedPathItem.mY = mSelectedPathItem.mY - distanceY;
                    }
                    moveView((int) e2.getX(),
                            (int) e2.getY());
                    invalidate(); // 刷新
                    return true;
                }
                return false;
            }


            @Override
            public void onScrollEnd(MotionEvent e) {
//                mDrawGraphBean = new DrawGraphBean(mLastX, mLastY, e.getX(), e.getY());
                if (mSelectedPathItem == null) {
                    mCurrentPathItem.mPath.quadTo(
                            mLastX,
                            mLastY,
                            (e.getX() + mLastX) / 2,
                            (e.getY() + mLastY) / 2); // 使用贝塞尔曲线 让涂鸦轨迹更圆滑
                    mCurrentPathItem = null; // 轨迹结束
                }
                if (mDrawGraphBean != null) {
                    mDrawGraphBeans.add(mDrawGraphBean);
                }
                invalidate(); // 刷新

            }


        });
    }

    //测量
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w > 0 && h > 0) {
            bitW = w;
            bitH = h;
            mOriginalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
            mOriginalBitmap = Bitmap.createScaledBitmap(mOriginalBitmap, bitW, bitH, true);
            // 设置画笔
            mPaint.setColor(Color.RED);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(5);
            mPaint.setAntiAlias(true);
            mPaint.setStrokeCap(Paint.Cap.ROUND);
            postInvalidate();
        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getX() <= bitW && event.getY() <= bitH) {
            boolean consumed = mTouchGestureDetector.onTouchEvent(event);
            if (consumed) {
                return true;
            }
        }
        return false;
    }


    @Override
    protected void onDraw(Canvas canvas) {
//
        if (mOriginalBitmap != null) {
            canvas.drawBitmap(mOriginalBitmap, 0, 0, mPaint);

        }

        for (int i = 0; i < mDrawGraphBeans.size(); i++) {
            DrawGraphBean drawGraphBean = mDrawGraphBeans.get(i);

            float centerX = (drawGraphBean.endX + drawGraphBean.startX) / 2;
            float centerY = (drawGraphBean.endY + drawGraphBean.startY) / 2;
            float redous = (drawGraphBean.endX - drawGraphBean.startX) / 2;
//            canvas.drawOval(new RectF(drawGraphBean.startX, drawGraphBean.startY, drawGraphBean.endX, drawGraphBean.endY), mPaint);
            canvas.drawCircle(centerX, centerY, redous, mPaint);
        }
        if (mDrawGraphBean != null) {

//            canvas.drawOval(new RectF(mDrawGraphBean.startX, mDrawGraphBean.startY, mDrawGraphBean.endX, mDrawGraphBean.endY), mPaint);
            float centerX = (mDrawGraphBean.endX + mDrawGraphBean.startX) / 2;
            float centerY = (mDrawGraphBean.endY + mDrawGraphBean.startY) / 2;
            float redous = (mDrawGraphBean.endX - mDrawGraphBean.startX) / 2;
            canvas.drawCircle(centerX, centerY, redous, mPaint);
        }

        drawText(canvas);

        super.onDraw(canvas);
    }

    public void saveBitmap(TextView textView) {
        Bitmap bitmap = mOriginalBitmap.copy(mOriginalBitmap.getConfig(), true);
        Canvas canvas = new Canvas(bitmap);
        for (int i = 0; i < mDrawGraphBeans.size(); i++) {
            DrawGraphBean drawGraphBean = mDrawGraphBeans.get(i);
            float centerX = (drawGraphBean.endX + drawGraphBean.startX) / 2;
            float centerY = (drawGraphBean.endY + drawGraphBean.startY) / 2;
            float redous = (drawGraphBean.endX - drawGraphBean.startX) / 2;
//            canvas.drawOval(new RectF(drawGraphBean.startX, drawGraphBean.startY, drawGraphBean.endX, drawGraphBean.endY), mPaint);
            canvas.drawCircle(centerX, centerY, redous, mPaint);
        }
        // 判断手机设备是否有SD卡
        boolean isHasSDCard = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
        if (isHasSDCard) {
            // SD卡根目录

            String sdcard = Environment.getExternalStorageDirectory().getPath() + "/ACanVas/";
            String name = System.currentTimeMillis() + ".jpg";
            textView.setText(sdcard + name);
            FileUtils.saveBitmap(sdcard, name, bitmap);
        } else {
            ToastUtils.showToast("创建文件失败!");
        }

    }


    /**
     * 画文字
     *
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        String text = "你好啊";
        //绘制文字
        //测量文字的宽度
        float textWith = mTextPaint.measureText(text);

        float textHgight = (mTextPaint.descent() + mTextPaint.ascent()) / 2;
//        LogUtil.e("属性", center + ",,," + textHgight);
        RectF rectF = new RectF(0, bitH - textHgight, textWith, bitH);
        canvas.drawText(text, bitW / 2, bitH - textHgight - 20, mTextPaint);

    }

    /**
     * 封装涂鸦轨迹对象
     */
    private static class PathItem {
        Path mPath = new Path(); // 涂鸦轨迹
        float mX, mY; // 轨迹偏移值
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

    public void viewSaveToImage(View view) {
        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        view.setDrawingCacheBackgroundColor(Color.WHITE);

        // 把一个View转换成图片
        Bitmap cachebmp = loadBitmapFromView(view);

        // 添加水印
        Bitmap bitmap = Bitmap.createBitmap(createWatermarkBitmap(cachebmp, "你好啊qaq"));

        FileOutputStream fos;
        try {
            // 判断手机设备是否有SD卡
            boolean isHasSDCard = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
            if (isHasSDCard) {
                // SD卡根目录
                String sdRoot = Environment.getExternalStorageDirectory().getPath() + "/ACanVas/";
                File file = new File(sdRoot, "test.PNG");
                fos = new FileOutputStream(file);
            } else
                throw new Exception("创建文件失败!");

            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);

            fos.flush();
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        view.destroyDrawingCache();
    }

    private Bitmap loadBitmapFromView(View v) {
        int w = v.getWidth();
        int h = v.getHeight();

        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);

        c.drawColor(Color.WHITE);
        /** 如果不设置canvas画布为白色，则生成透明 */

        v.layout(0, 0, w, h);
        v.draw(c);

        return bmp;
    }

    // 为图片target添加水印
    private Bitmap createWatermarkBitmap(Bitmap target, String str) {
        int w = target.getWidth();
        int h = target.getHeight();

        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);

        Paint p = new Paint();

        // 水印的颜色
        p.setColor(Color.RED);

        // 水印的字体大小
        p.setTextSize(16);

        p.setAntiAlias(true);// 去锯齿

        canvas.drawBitmap(target, 0, 0, p);

        // 在中间位置开始添加水印
        canvas.drawText(str, w / 2, h / 2, p);

        canvas.save();
        canvas.restore();

        return bmp;
    }

    private void addItem(int x, int y) {
        View view = null;
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        if (x > getWidth() * 0.5) {
            params.leftMargin = x - PictureTagView.getViewWidth();
            view = new PictureTagView(getContext(), PictureTagView.Direction.Right);
        } else {
            params.leftMargin = x;
            view = new PictureTagView(getContext(), PictureTagView.Direction.Left);
        }

        params.topMargin = y;
        //上下位置在视图内
        if (params.topMargin < 0) params.topMargin = 0;
        else if ((params.topMargin + PictureTagView.getViewHeight()) > getHeight()) params.topMargin = getHeight() - PictureTagView.getViewHeight();

        this.addView(view, params);
    }

    private boolean hasView(int x, int y) {
        //循环获取子view，判断xy是否在子view上，即判断是否按住了子view
        for (int index = 0; index < this.getChildCount(); index++) {
            View view = this.getChildAt(index);
            int left = (int) view.getX();
            int top = (int) view.getY();
            int right = view.getRight();
            int bottom = view.getBottom();
            Rect rect = new Rect(left, top, right, bottom);
            boolean contains = rect.contains(x, y);
            //如果是与子view重叠则返回真,表示已经有了view不需要添加新view了
            if (contains) {
                touchView = view;
                touchView.bringToFront();
                return true;
            }
        }
        touchView = null;
        return false;
    }

    private void moveView(int x, int y) {
        if (touchView == null) return;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.leftMargin = x - startX + startTouchViewLeft;
        params.topMargin = y - startY + startTouchViewTop;
        //限制子控件移动必须在视图范围内
        if (params.leftMargin < 0 || (params.leftMargin + touchView.getWidth()) > getWidth()) params.leftMargin = touchView.getLeft();
        if (params.topMargin < 0 || (params.topMargin + touchView.getHeight()) > getHeight()) params.topMargin = touchView.getTop();
        touchView.setLayoutParams(params);
    }
}
