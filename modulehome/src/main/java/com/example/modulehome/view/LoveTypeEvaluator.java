package com.example.modulehome.view;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * Created by ${LiuTao}.
 * User: Administrator
 * Name: ArouteDemo
 * functiona:
 * Date: 2019/6/25 0025
 * Time: 下午 16:06
 */
public class LoveTypeEvaluator implements TypeEvaluator<PointF> {
    private  PointF point1;
    private  PointF point2;

    public LoveTypeEvaluator(PointF point1, PointF point2) {
        this.point1=point1;
        this.point2=point2;
    }
    //计算三阶贝塞尔曲线
    @Override
    public PointF evaluate(float t, PointF point0, PointF point3) {
        PointF pointF=new PointF();
        pointF.x = point0.x*(1-t)*(1-t)*(1-t)
                + 3*point1.x*t*(1-t)*(1-t)
                + 3*point2.x*t*t*(1-t)
                + point3.x*t*t*t;

        pointF.y = point0.y*(1-t)*(1-t)*(1-t)
                + 3*point1.y*t*(1-t)*(1-t)
                + 3*point2.y*t*t*(1-t)
                + point3.y*t*t*t;
        return pointF;
    }

}
