package com.example.modulehome.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.android.utils.LogUtil;
import com.android.utils.ToastUtils;
import com.android.utils.UIUtils;
import com.example.modulehome.R;

/**
 * Created by ${LiuTao}.
 * User: Administrator
 * Name: ArouteDemo
 * functiona: 仿QQ计步器
 * Date: 2019/6/20 0020
 * Time: 下午 16:52
 */
public class CameraCusView extends View {
    private int outColor = Color.RED;
    private int innerColor = Color.YELLOW;
    private int borderWidth = 20;
    private int textColor = Color.GREEN;
    private int textSize = 40;
    //画笔
    public Paint mPersonPaint;
    public Camera mCamera;

    // 2.创建一个检测器

    public CameraCusView(Context context) {
        this(context, null);
    }

    public CameraCusView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CameraCusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        mPersonPaint = new Paint();
        mPersonPaint.setAntiAlias(true);
        mPersonPaint.setColor(Color.BLUE);
        mPersonPaint.setStrokeWidth(6);
        mPersonPaint.setStyle(Paint.Style.STROKE);
        mCamera = new Camera();
        //相机往后面挪
        mCamera.setLocation(0,0,-100);
    }

    //测量
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //确保是正方形 ,趣小编
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(w > h ? h : w, w > h ? h : w);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int center = getWidth() / 2;
        canvas.save();
        mCamera.save();
        mCamera.rotateX(40);
        //把画布中心挪到原点
        canvas.translate(center,center);
        mCamera.applyToCanvas(canvas);
        //把画布中心挪回来
        canvas.translate(-center,-center);
        mCamera.restore();
        RectF mRectF2 = new RectF(0+50, 0+50, getWidth()-50, getWidth()-50);

        //起始点 中点，结束点
        Bitmap bitmap = BitmapFactory.decodeResource(UIUtils.getResources(), R.drawable.logo);
        // 指定图片绘制区域(左上角的四分之一)
        Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        // 绘制图片
        canvas.drawBitmap(bitmap, src, mRectF2, mPersonPaint);
        canvas.restore();

    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

    }
}
