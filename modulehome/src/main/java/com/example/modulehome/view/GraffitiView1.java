package com.example.modulehome.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.android.utils.FileUtils;
import com.android.utils.LogUtil;
import com.example.modulehome.R;

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
public class GraffitiView1 extends View {
    private Paint mPaint = new Paint();
    private List<Path> mPathList = new ArrayList<>(); // 保存涂鸦轨迹的集合
    private TouchGestureDetector mTouchGestureDetector; // 触摸手势监听
    private float mLastX, mLastY;
    private Path mCurrentPath; // 当前的涂鸦轨迹
    public Bitmap originalBitmap;
    public Bitmap new1Bitmap;
    public Canvas mCanvase;
    public DrawGraphBean mDrawGraphBean;

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
    }

    public GraffitiView1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public GraffitiView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    int bitW;
    int bitH;

    private void initView() {
        originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo).copy(Bitmap.Config.ARGB_8888, true);
        new1Bitmap = Bitmap.createBitmap(originalBitmap);
        mCanvase = new Canvas(new1Bitmap);
        mCanvase.drawColor(Color.TRANSPARENT);
        bitW = new1Bitmap.getWidth();
        bitH = new1Bitmap.getHeight();
        // 设置画笔
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mTouchGestureDetector = new TouchGestureDetector(getContext(), new TouchGestureDetector.OnTouchGestureListener() {
            @Override
            public void onScrollBegin(MotionEvent e) {
                if (e.getX() <= bitW && e.getY() <= bitH) {
                    mCurrentPath = new Path(); // 新的涂鸦
                    mPathList.add(mCurrentPath); // 添加的集合中
                    mCurrentPath.moveTo(e.getX(), e.getY());
                    mDrawGraphBean = new DrawGraphBean(e.getX(), e.getY(), e.getX(), e.getY());
                    mLastX = e.getX();
                    mLastY = e.getY();
                    invalidate(); // 刷新
                }

            }

            @Override
            public void onScrollEnd(MotionEvent e) {
                mDrawGraphBean = new DrawGraphBean(mLastX, mLastY, e.getX(), e.getY());
                mCurrentPath.quadTo(
                        mLastX,
                        mLastY,
                        (e.getX() + mLastX) / 2,
                        (e.getY() + mLastY) / 2); // 使用贝塞尔曲线 让涂鸦轨迹更圆滑
                mCurrentPath = null; // 轨迹结束
                mDrawGraphBean.setEndX(e.getX());
                mDrawGraphBean.setEndY(e.getY());

            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                if (e2.getX() <= bitW && e2.getY() <= bitH) {
                    mCurrentPath.quadTo(
                            mLastX,
                            mLastY,
                            (e2.getX() + mLastX) / 2,
                            (e2.getY() + mLastY) / 2); // 使用贝塞尔曲线 让涂鸦轨迹更圆滑
                    mDrawGraphBean.setEndX(e2.getX());
                    mDrawGraphBean.setEndY(e2.getY());
                    mLastX = e2.getX();
                    mLastY = e2.getY();
                    invalidate(); // 刷新
                    return true;
                }
                return false;
            }
        });
    }

    //测量
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(new1Bitmap.getWidth(), new1Bitmap.getHeight());

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getX() <= bitW && event.getY() <= bitH) {
            boolean consumed = mTouchGestureDetector.onTouchEvent(event);
            if (consumed) {
                return true;
            }
        }

        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(new1Bitmap, 0, 0, mPaint);
        if (mDrawGraphBean!=null){
            canvas.drawOval(new RectF(mDrawGraphBean.startX, mDrawGraphBean.startY, mDrawGraphBean.endX, mDrawGraphBean.endY), mPaint);
        }
        for (Path path : mPathList) {
            canvas.drawPath(path, mPaint);
        }
        for (Path path : mPathList) {
            mCanvase.drawPath(path, mPaint);
        }
        if (mDrawGraphBean!=null){
            mCanvase.drawOval(new RectF(mDrawGraphBean.startX, mDrawGraphBean.startY, mDrawGraphBean.endX, mDrawGraphBean.endY), mPaint);
        }

    }

    public void saveBitmap(TextView textView) {
        String sdcard = Environment.getExternalStorageDirectory().getPath() + "/ACanVas/";
        String name = System.currentTimeMillis() + ".jpg";
        textView.setText(sdcard);
        FileUtils.saveBitmap(sdcard, name, new1Bitmap);
    }
}
