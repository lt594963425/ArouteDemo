package com.example.moduleuser.kml.javaforkml.entity;



import java.util.List;

import de.micromata.opengis.kml.v_2_2_0.Coordinate;

/**
 * Created by ${LiuTao}.
 * User: Administrator
 * Name: AirLineView
 * functiona:
 * Date: 2019/7/2 0002
 * Time: 上午 8:42
 */
public class KmlPoint extends KmlElenmt{

    private  String color;
    private List<Coordinate> points;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<Coordinate> getPoints() {
        return points;
    }

    public void setPoints(List<Coordinate> points) {
        this.points = points;
    }


}
