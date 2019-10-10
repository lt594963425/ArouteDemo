package com.example.modulehome.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.android.utils.LogUtil;
import com.android.utils.UIUtils;
import com.example.modulehome.R;

/**
 * Created by ${LiuTao}.
 * User: Administrator
 * Name: ArouteDemo
 * functiona:
 * Date: 2019/6/24 0024
 * Time: 上午 9:49
 */
public class TipsView extends View {

    Paint mPaint;
    Paint mPaint2;
    Path mPath;
    private float mRotationRadius;//绕着旋转的动画的半径


    public TipsView(Context context) {
        super(context, null);
        init();
    }

    public TipsView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        init();
    }

    public TipsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(6);
        mPaint.setTextSize(25);
        mPaint.setTextAlign(Paint.Align.RIGHT);
        mPaint.setColor(getResources().getColor(R.color.red_5f));

        mPaint2 = new Paint();
        mPaint2.setAntiAlias(true);
        mPaint2.setStrokeWidth(6);
        mPaint2.setTextSize(25);
        mPaint2.setStyle(Paint.Style.STROKE);
        mPaint2.setTextAlign(Paint.Align.RIGHT);
        mPaint2.setColor(Color.WHITE);

        mPath = new Path();


    }

    //测量
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //确保是正方形
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(w > h ? h : w, w > h ? h : w);

    }


    @Override
    protected void onDraw(Canvas canvas) {

        //绘制红色大圆

        mRotationRadius = getMeasuredWidth() / 2;

        //画圆
        canvas.drawCircle(mRotationRadius, mRotationRadius, mRotationRadius, mPaint);
        //绘制区域
        RectF mRectF2 = new RectF(getMeasuredWidth() / 7, getMeasuredWidth() / 7, getMeasuredWidth() - getMeasuredWidth() / 7, getMeasuredWidth() - getMeasuredWidth() / 7);
        //起始点 中点，结束点
        Bitmap bitmap = BitmapFactory.decodeResource(UIUtils.getResources(), R.drawable.gantan);
        // 指定图片绘制区域(左上角的四分之一)
        Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        // 绘制图片
        canvas.drawBitmap(bitmap, src, mRectF2, mPaint2);


    }


}
