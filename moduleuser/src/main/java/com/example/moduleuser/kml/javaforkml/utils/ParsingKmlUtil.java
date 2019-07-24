package com.example.moduleuser.kml.javaforkml.utils;


import com.example.moduleuser.kml.javaforkml.entity.KmlLine;
import com.example.moduleuser.kml.javaforkml.entity.KmlPoint;
import com.example.moduleuser.kml.javaforkml.entity.KmlPolygon;
import com.example.moduleuser.kml.javaforkml.entity.KmlProperty;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.micromata.opengis.kml.v_2_2_0.Boundary;
import de.micromata.opengis.kml.v_2_2_0.Coordinate;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Feature;
import de.micromata.opengis.kml.v_2_2_0.Folder;
import de.micromata.opengis.kml.v_2_2_0.Geometry;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.LineString;
import de.micromata.opengis.kml.v_2_2_0.LinearRing;
import de.micromata.opengis.kml.v_2_2_0.MultiGeometry;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.Point;
import de.micromata.opengis.kml.v_2_2_0.Polygon;

/**
 * Created by ${LiuTao}.
 * User: Administrator
 * Name: AirLineView
 * functiona:
 * Date: 2019/7/2 0002
 * Time: 上午 8:43
 */
public class ParsingKmlUtil {

    private List<KmlPoint> kmlPointList = new ArrayList<>();
    private List<KmlLine> kmlLineList = new ArrayList<>();
    private List<KmlPolygon> kmlPolygonList = new ArrayList<>();
    private KmlProperty kmlProperty = new KmlProperty();

    /**
     * 淇濆瓨kml鏁版嵁鍒颁复鏃惰〃
     *
     * @param file 涓婁紶鐨勬枃浠跺疄浣?     * @return 鑷畾涔夌殑KML鏂囦欢瀹炰綋
     */
    public KmlProperty parseKmlForJAK(File file) {
        Kml kml = Kml.unmarshal(file);
        Feature feature = kml.getFeature();
        parseFeature(feature);
        kmlProperty.setKmlPoints(kmlPointList);
        kmlProperty.setKmlLines(kmlLineList);
        kmlProperty.setKmlPolygons(kmlPolygonList);
        System.out.println(kmlProperty);
        return kmlProperty;
    }

    private void parseFeature(Feature feature) {
        if (feature != null) {
            if (feature instanceof Document) {
                List<Feature> featureList = ((Document) feature).getFeature();
                for (Feature documentFeature: featureList) {
                    if (documentFeature instanceof Placemark) {
                        getPlaceMark((Placemark) documentFeature);
                    } else {
                        parseFeature(documentFeature);
                    }
                }

            } else if (feature instanceof Folder) {
                List<Feature> featureList = ((Folder) feature).getFeature();
                for (Feature documentFeature: featureList) {
                    if (documentFeature instanceof Placemark) {
                        getPlaceMark((Placemark) documentFeature);
                    }
                    else {
                        parseFeature(documentFeature);
                    }
                }
            }
        }
    }

    private void getPlaceMark(Placemark placemark) {
        Geometry geometry = placemark.getGeometry();
        String name = placemark.getName();
        parseGeometry(name, geometry);
    }

    private void parseGeometry(String name, Geometry geometry) {
        if (geometry != null) {
            if (geometry instanceof Polygon) {
                Polygon polygon = (Polygon) geometry;
                Boundary outerBoundaryIs = polygon.getOuterBoundaryIs();
                if (outerBoundaryIs != null) {
                    LinearRing linearRing = outerBoundaryIs.getLinearRing();
                    if (linearRing != null) {
                        List<Coordinate> coordinates = linearRing.getCoordinates();
                        if (coordinates != null) {
                            outerBoundaryIs = ((Polygon) geometry).getOuterBoundaryIs();
                            addPolygonToList(kmlPolygonList, name, outerBoundaryIs);
                        }
                    }
                }
            } else if (geometry instanceof LineString) {
                LineString lineString = (LineString) geometry;
                List<Coordinate> coordinates = lineString.getCoordinates();
                if (coordinates != null) {
                    int width = 0;
                    coordinates = ((LineString) geometry).getCoordinates();
                    addLineStringToList(width, kmlLineList, coordinates, name);
                }
            } else if (geometry instanceof Point) {
                Point point = (Point) geometry;
                List<Coordinate> coordinates = point.getCoordinates();
                if (coordinates != null) {
                    coordinates = ((Point) geometry).getCoordinates();
                    addPointToList(kmlPointList, coordinates, name);
                }
            } else if (geometry instanceof MultiGeometry) {
                List<Geometry> geometries = ((MultiGeometry) geometry).getGeometry();
                for (Geometry geometryToMult : geometries) {
                    Boundary outerBoundaryIs;
                    List<Coordinate> coordinates;
                    if (geometryToMult instanceof Point) {
                        coordinates = ((Point) geometryToMult).getCoordinates();
                        addPointToList(kmlPointList, coordinates, name);
                    } else if (geometryToMult instanceof LineString) {
                        int width = 0;
                        coordinates = ((LineString) geometryToMult).getCoordinates();
                        addLineStringToList(width, kmlLineList, coordinates, name);
                    } else if (geometryToMult instanceof Polygon) {
                        outerBoundaryIs = ((Polygon) geometryToMult).getOuterBoundaryIs();
                        addPolygonToList(kmlPolygonList, name, outerBoundaryIs);
                    }
                }
            }
        }
    }

    private void addPolygonToList(List<KmlPolygon> kmlPolygonList, String name, Boundary outerBoundaryIs) {
        LinearRing linearRing;
        List<Coordinate> coordinates ;
        linearRing = outerBoundaryIs.getLinearRing();
        coordinates = linearRing.getCoordinates();
        ShowCoordinates(coordinates, name);
        KmlPolygon kmlPolygon = new KmlPolygon();
        kmlPolygon.setPoints(coordinates);
        kmlPolygon.setName(name);
        kmlPolygonList.add(kmlPolygon);
    }

    private void addLineStringToList(int width, List<KmlLine> kmlLineList, List<Coordinate> coordinates, String name) {
        ShowCoordinates(coordinates, name);
        KmlLine kmlLine = new KmlLine();
        kmlLine.setPoints(coordinates);
        kmlLine.setWidth(width);
        kmlLine.setName(name);
        kmlLineList.add(kmlLine);
    }

    private void addPointToList(List<KmlPoint> kmlPointList, List<Coordinate> coordinates, String name) {
        ShowCoordinates(coordinates, name);
        KmlPoint kmlPoint = new KmlPoint();
        kmlPoint.setName(name);
        kmlPoint.setPoints(coordinates);
        kmlPointList.add(kmlPoint);
    }

    private void ShowCoordinates(List<Coordinate> coordinates, String name) {
        for (Coordinate coordinate : coordinates) {
            System.out.println(coordinate);
        }
        System.out.println(name);
    }


}
