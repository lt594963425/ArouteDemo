package com.example.modulehome.entity;

/**
 * Created by ChartData.
 * User: Administrator
 * Name: ArouteDemo
 * functiona:
 * Date: 2019/10/22 0022
 * Time: 上午 10:39
 */
public class ChartDataBean {

    private  int id;
    private float data;

    public ChartDataBean() {
    }

    public ChartDataBean(int id, float data) {
        this.id = id;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getData() {
        return data;
    }

    public void setData(float data) {
        this.data = data;
    }
}
