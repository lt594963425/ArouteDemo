package com.example.modulehome.view;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
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
import android.widget.Toast;

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
public class QQStepView extends View {
    private int outColor = Color.RED;
    private int innerColor = Color.YELLOW;
    private int borderWidth = 20;
    private int textColor = Color.GREEN;
    private int textSize = 40;
    //画笔
    private Paint outColorPaint, innerColorPaint, mTextPaint, personColorPaint;
    //总步数
    private int maxStep = 6000;
    private int currentStep = 2500;
    public Paint mPersonPaint;
    public GestureDetector mDetector;
    public RectF mRectF2;
    public RectF mTextRectF;
    // 2.创建一个检测器

    public QQStepView(Context context) {
        this(context, null);
    }

    public QQStepView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QQStepView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.QQStepView);
        outColor = typedArray.getColor(R.styleable.QQStepView_outColor, outColor);
        innerColor = typedArray.getColor(R.styleable.QQStepView_innerColor, innerColor);
        borderWidth = (int) typedArray.getDimension(R.styleable.QQStepView_borderWidth, borderWidth);
        textColor = typedArray.getColor(R.styleable.QQStepView_textColor, textColor);
        textSize = (int) typedArray.getDimension(R.styleable.QQStepView_textSize, textSize);
        typedArray.recycle();
        //初始化外圆画笔
        outColorPaint = new Paint();
        outColorPaint.setStyle(Paint.Style.STROKE);//空心
        outColorPaint.setColor(outColor);
        outColorPaint.setStrokeWidth(borderWidth);
        outColorPaint.setDither(true);
//        outColorPaint.setStrokeCap(Paint.Cap.ROUND);//末端圆形
        outColorPaint.setAntiAlias(true);
        outColorPaint.setPathEffect(new DashPathEffect(new float[]{4f, 4f}, 0));
        //初始化内弧形画笔
        innerColorPaint = new Paint();
        innerColorPaint.setStyle(Paint.Style.STROKE);//空心
        innerColorPaint.setColor(innerColor);
        innerColorPaint.setDither(true);
        innerColorPaint.setStrokeWidth(borderWidth);
//        innerColorPaint.setStrokeCap(Paint.Cap.ROUND);//末端圆形
        innerColorPaint.setAntiAlias(true);
        innerColorPaint.setPathEffect(new DashPathEffect(new float[]{4f, 4f}, 0));
        //初始化文字画笔
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setDither(true);
        mTextPaint.setColor(textColor);
        mTextPaint.setStrokeWidth(10);
        mTextPaint.setTextSize(textSize);

        //初始化小人画笔
        personColorPaint = new Paint();
        personColorPaint.setDither(true);
        personColorPaint.setAntiAlias(true);
        personColorPaint.setColor(Color.BLUE);
        personColorPaint.setStrokeWidth(6);
        personColorPaint.setStyle(Paint.Style.FILL);

        mPersonPaint = new Paint();
        mPersonPaint.setAntiAlias(true);
        mPersonPaint.setColor(Color.BLUE);
        mPersonPaint.setStrokeWidth(6);
        mPersonPaint.setStyle(Paint.Style.STROKE);
        personColorPaint.setDither(true);
// 1.创建一个监听回调
        GestureDetector.SimpleOnGestureListener listener = new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                ToastUtils.showToast("双击666");
                return super.onDoubleTap(e);
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                ToastUtils.showToast("单击");
                return super.onSingleTapConfirmed(e);
            }

            @Override
            public boolean onDown(MotionEvent e) {
                ToastUtils.showToast("onDown");
                return super.onDown(e);

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                ToastUtils.showToast("onFling");
                return super.onFling(e1, e2, velocityX, velocityY);
            }

            @Override
            public void onLongPress(MotionEvent e) {
                ToastUtils.showToast("onLongPress");
                super.onLongPress(e);
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                ToastUtils.showToast("onScroll");
                return super.onScroll(e1, e2, distanceX, distanceY);
            }

            @Override
            public void onShowPress(MotionEvent e) {

                super.onShowPress(e);
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                ToastUtils.showToast("onSingleTapUp");
                return super.onSingleTapUp(e);
            }

        };
        mDetector = new GestureDetector(getContext(), listener);


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
        int redus = getWidth() / 2 - borderWidth * 2 - UIUtils.dip2Px(20);

        RectF rectF = new RectF(center - redus, center - redus, center + redus, center + redus);
        //画内圆弧
        canvas.drawArc(rectF, 135, 270, false, innerColorPaint);
        // 绘制外圆弧
        float percent = (float) currentStep / maxStep;
        canvas.drawArc(rectF, 135, percent * 270, false, outColorPaint);
        //绘制文字
        //测量文字的宽度
        float textWith = mTextPaint.measureText(currentStep + "步");

        float textHgight = (mTextPaint.descent() + mTextPaint.ascent()) / 2;
//        LogUtil.e("属性", center + ",,," + textHgight);
        mTextRectF = new RectF(center - textWith / 2, center + textHgight, center + textWith / 2, center - textHgight);
//        canvas.drawRect(mTextRectF, mPersonPaint);

        canvas.drawText(currentStep + "步", center - textWith / 2, center - textHgight, mTextPaint);

        int birmapCenter = center / 2 + UIUtils.dip2Px(20);
        int w = redus / 5;

        mRectF2 = new RectF(center - w, birmapCenter - w, center + w, birmapCenter + w);

        //起始点 中点，结束点
        Bitmap bitmap = BitmapFactory.decodeResource(UIUtils.getResources(), R.drawable.wark);
        // 指定图片绘制区域(左上角的四分之一)
        Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        // 绘制图片
        canvas.drawBitmap(bitmap, src, mRectF2, mPersonPaint);

        double rad = 2 * Math.PI / 360;  //1度对应的弧度 2π/360
        float trangleRedusC = redus + borderWidth - 5;
        float trangleX = (float) (center + trangleRedusC * Math.cos((135 + percent * 270) * rad));
        float trangleY = (float) (center + trangleRedusC * Math.sin((135 + percent * 270) * rad));

        float trangleRedus = redus + borderWidth + 15;
        float trangleAX = (float) (center + trangleRedus * Math.cos((135 + percent * 270 + 4) * rad));
        float trangleAY = (float) (center + trangleRedus * Math.sin((135 + percent * 270 + 4) * rad));

        float trangleBX = (float) (center + trangleRedus * Math.cos((135 + percent * 270 - 4) * rad));
        float trangleBY = (float) (center + trangleRedus * Math.sin((135 + percent * 270 - 4) * rad));
        Paint p = new Paint();
        p.setColor(Color.RED);
        Path mPath = new Path();
        mPath.moveTo(trangleX, trangleY);
        mPath.lineTo(trangleAX, trangleAY);
        mPath.lineTo(trangleBX, trangleBY);
        mPath.close();
        canvas.drawPath(mPath, p);


        Interpolator[] mInterpolator = new Interpolator[]{new AccelerateDecelerateInterpolator(), new AccelerateInterpolator(),
                new DecelerateInterpolator(), new LinearInterpolator()};

    }

    public void setMaxStep(int maxStep) {
        this.maxStep = maxStep;
    }

    public void setCurrentStep(int step) {
        this.currentStep = step;
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 5500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float stepValue = (Float) animation.getAnimatedValue();
                float step = stepValue;
                QQStepView.this.currentStep = (int) step;
                invalidate();
            }

        });
        valueAnimator.setDuration(4000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.start();

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();
        // ▼ 注意这里使用的是 getAction()，先埋一个小尾巴。
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 手指按下
                if (mRectF2.contains(x, y)) {
                    ToastUtils.showToast("点击图片");
                }

                if (mTextRectF.contains(x, y)) {
                    ToastUtils.showToast("点击文字");
                }

                break;
            case MotionEvent.ACTION_MOVE:
                // 手指移动
                LogUtil.e("onTouchEvent", "------手指移动-----");
                break;
            case MotionEvent.ACTION_UP:
                // 手指抬起
                LogUtil.e("onTouchEvent", "------手指抬起-----");
                break;
            case MotionEvent.ACTION_CANCEL:
                // 事件被拦截
                LogUtil.e("onTouchEvent", "------事件被拦截-----");
                break;
            case MotionEvent.ACTION_OUTSIDE:
                // 超出区域
                LogUtil.e("onTouchEvent", "------超出区域-----");
                break;
        }
        return super.onTouchEvent(event);
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

    }
}
