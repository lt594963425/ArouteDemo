package com.example.moduleuser.kml.javaforkml.entity;

import java.util.List;

/**
 * Created by ${LiuTao}.
 * User: Administrator
 * Name: AirLineView
 * functiona:
 * Date: 2019/7/2 0002
 * Time: 上午 8:51
 */
public class KmlProperty {

    private List<KmlPoint> kmlPoints;
    private List<KmlLine> kmlLines;
    private List<KmlPolygon> kmlPolygons;

    public List<KmlPoint> getKmlPoints() {
        return kmlPoints;
    }

    public void setKmlPoints(List<KmlPoint> kmlPoints) {
        this.kmlPoints = kmlPoints;
    }

    public List<KmlLine> getKmlLines() {
        return kmlLines;
    }

    public void setKmlLines(List<KmlLine> kmlLines) {
        this.kmlLines = kmlLines;
    }

    public List<KmlPolygon> getKmlPolygons() {
        return kmlPolygons;
    }

    public void setKmlPolygons(List<KmlPolygon> kmlPolygons) {
        this.kmlPolygons = kmlPolygons;
    }

}
