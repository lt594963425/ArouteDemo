package com.example.modulehome.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.TextView;

import com.android.utils.LogUtil;

/**
 * Created by ${LiuTao}.
 * User: Administrator
 * Name: ArouteDemo
 * functiona:
 * Date: 2019/6/24 0024
 * Time: 上午 9:49
 */
public class ClipRectDemo extends View {

    Paint mPaint;
    Paint mPaint2;
    Path mPath;
    Integer[] mCircleColors = new Integer[]{Color.RED, Color.GREEN, Color.BLUE, Color.GREEN, Color.YELLOW, Color.CYAN};
    //初始化的角度
    private float mCurrentRotationAngle = 315;
    private float mCircleRadius;//小圆的半径
    private float mRotationRadius;//绕着旋转的动画的半径
    private int cententX, cententY;
    // 屏幕对角线的一半
    private float mDiagonalDist;

    public ClipRectDemo(Context context) {
        super(context, null);
        init();
    }

    public ClipRectDemo(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        init();
    }

    public ClipRectDemo(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(6);
        mPaint.setTextSize(25);
        mPaint.setTextAlign(Paint.Align.RIGHT);
        mPaint.setColor(Color.BLUE);

        mPaint2 = new Paint();
        mPaint2.setAntiAlias(true);
        mPaint2.setStrokeWidth(6);
        mPaint2.setTextSize(25);
        mPaint2.setTextAlign(Paint.Align.RIGHT);
        mPaint2.setColor(Color.BLUE);

        mPath = new Path();



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

    public void setCurrentRotationAngle(float currentRotationAngle) {
        mCurrentRotationAngle = currentRotationAngle;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mRotationRadius = getMeasuredWidth() / 4;
        // 每个小圆的半径 = 大圆半径的 1/8
        mCircleRadius = mRotationRadius / 8;
        cententX = getWidth() / 2;
        cententY = getHeight() / 2;
        mDiagonalDist = (float) Math.sqrt(cententX * cententX + cententY * cententY);
        //绘制白色背景
        canvas.drawColor(Color.WHITE);
        //画6个圆
        //没份的角度
        double precentAngle = 2 * Math.PI / mCircleColors.length;
      //  LogUtil.e("角度", "每份的角度;"+precentAngle );
        mPaint.setColor(mCircleColors[0]);

        for (int i = 0; i < mCircleColors.length; i++) {
            mPaint.setColor(mCircleColors[i]);
            //当前的角度=初始化的角度+旋转的角度
            double currentAngle = precentAngle * i + mCurrentRotationAngle;
            float cx = (float) (cententX + mRotationRadius * Math.cos(currentAngle));
            float cy = (float) (cententY + mRotationRadius * Math.sin(currentAngle));
            //LogUtil.e("角度", currentAngle +"，大圆："+ mRotationRadius+"，半径："+mCircleRadius);
            canvas.drawCircle(cx, cy, mCircleRadius, mPaint);
        }


//        canvas.save();
//        drawScene(canvas);
//        canvas.restore();

//        mPaint.setColor(Color.BLUE);
//        canvas.clipRect(100, 100, 350, 600, Region.Op.INTERSECT);//设置显示范围
//
//        canvas.drawColor(Color.RED);
//
//        canvas.drawCircle((350 + 100) / 2, 350, radius, mPaint);

//        //DIFFERENCE,以第一次剪裁的为基础，在此基础上擦除第二次剪裁的部分(裁出第一次操作，并擦除掉第二次操作)
//        canvas.save();
//        canvas.translate(560, 0);
//        canvas.clipRect(0, 0, 300, 300);
//        canvas.clipRect(200, 200, 500, 500, Region.Op.DIFFERENCE);
//        drawScene(canvas);
//        canvas.restore();


//        //INTERSECT,交叉,保留第一次剪裁和第二次剪裁重叠的部分
//        canvas.save();
//        canvas.translate(560, 0);
//        canvas.clipRect(0, 0, 300, 300);
//        canvas.clipRect(200, 200, 500, 500, Region.Op.INTERSECT);
//        drawScene(canvas);
//        canvas.restore();

//        //REPLACE,替换,用第二次剪裁替换第一次剪裁(相当于只做了第二次剪裁,丢弃第一次剪裁)
//        //分别放开下面三种操作有助于理解
//        canvas.save();
//        canvas.translate(560, 0);
//        //1.第一种操作
//        canvas.clipRect(0, 0, 300, 300);
//        canvas.clipRect(200, 200, 500, 500, Region.Op.REPLACE);
//        //2.第二次操作
////        canvas.clipRect(200, 200, 500, 500);
////        canvas.clipRect(0, 0, 300, 300, Region.Op.REPLACE);
//        //3.第三种操作
////        canvas.clipRect(0, 0, 300, 300);
//        drawScene(canvas);
//        canvas.restore();

//        //REVERSE_DIFFERENCE,以第二次剪裁的为基础，在此基础上擦除第一次剪裁的部分(裁出第二次操作，并擦除掉第一次操作)
//        canvas.save();
//        canvas.translate(560, 0);
//        canvas.clipRect(0, 0, 300, 300);
//        canvas.clipRect(200, 200, 500, 500, Region.Op.REVERSE_DIFFERENCE );
//        drawScene(canvas);
//        canvas.restore();

//        //UNION,合并,即保留第一次剪裁和第二次剪裁的并集
//        canvas.save();
//        canvas.translate(560, 0);
//        canvas.clipRect(0, 0, 300, 300);
//        canvas.clipRect(200, 200, 500, 500, Region.Op.UNION);
//        drawScene(canvas);
//        canvas.restore();

//        //XOR,异或操作，做出第一次剪裁，并且做出第二次剪裁，在此基础上擦除掉重叠的部分(留下两次操作不重叠的部分)
//        canvas.save();
//        canvas.translate(560, 0);
//        canvas.clipRect(0, 0, 300, 300);
//        canvas.clipRect(200, 200, 500, 500, Region.Op.XOR);
//        drawScene(canvas);
//        canvas.restore();
        PointF point1 = new PointF();
        PointF point2 =new PointF();


        LoveTypeEvaluator evaluator = new LoveTypeEvaluator(point1, point2);
        //会调用自定义路径属性动画evaluate方法
        ValueAnimator bezierAnimator = ObjectAnimator.ofObject(evaluator, point1, point2);


    }


    private void drawScene(Canvas canvas) {
        canvas.clipRect(0, 0, 500, 500);

        canvas.drawColor(Color.WHITE);

        mPaint.setColor(Color.BLUE);
        canvas.drawRect(0, 0, 300, 300, mPaint);

        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(40);
        canvas.drawText("A", 140, 140, mPaint);

        mPaint.setColor(Color.GREEN);
        canvas.drawRect(200, 200, 500, 500, mPaint);

        mPaint.setColor(Color.WHITE);
        canvas.drawText("B", 350, 350, mPaint);

        mPaint.setColor(Color.RED);
        canvas.drawRect(200, 200, 300, 300, mPaint);

    }


}
