package com.example.modulehome.entity;

/**
 * Created by MyXValueBean.
 * User: Administrator
 * Name: UAV_Android
 * functiona:
 * Date: 2019/10/25 0025
 * Time: 上午 9:18
 */
public class MyXValueBean {
    private String xValue;
    private int yValue;

    public MyXValueBean(String xValue, int yValue) {
        this.xValue = xValue;
        this.yValue = yValue;
    }

    public String getxValue() {
        return xValue;
    }

    public void setxValue(String xValue) {
        this.xValue = xValue;
    }

    public int getyValue() {
        return yValue;
    }

    public void setyValue(int yValue) {
        this.yValue = yValue;
    }
}
