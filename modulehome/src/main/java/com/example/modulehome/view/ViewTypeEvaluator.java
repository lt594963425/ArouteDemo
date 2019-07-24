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
public class ViewTypeEvaluator implements TypeEvaluator<PointF> {
    private PointF point1;
    private PointF point2;

    public ViewTypeEvaluator(PointF point1, PointF point2) {
        this.point1 = point1;
        this.point2 = point2;
    }

    //计算三阶贝塞尔曲线
    @Override
    public PointF evaluate(float t, PointF point0, PointF point3) {
        PointF pointF = new PointF();
        pointF.x = point0.x + t * (point3.x - point0.x);
        pointF.y = point0.y + t * (point3.y - point0.y);
        return pointF;
    }

}
