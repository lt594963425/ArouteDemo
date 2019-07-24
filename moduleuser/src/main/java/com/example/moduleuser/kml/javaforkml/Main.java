package com.example.moduleuser.kml.javaforkml;


import com.example.moduleuser.kml.javaforkml.entity.KmlLine;
import com.example.moduleuser.kml.javaforkml.entity.KmlPoint;
import com.example.moduleuser.kml.javaforkml.entity.KmlPolygon;
import com.example.moduleuser.kml.javaforkml.entity.KmlProperty;
import com.example.moduleuser.kml.javaforkml.utils.ParsingKmlUtil;
import com.example.moduleuser.kml.javaforkml.utils.UnicodeInputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by ${LiuTao}.
 * User: Administrator
 * Name: AirLineView
 * functiona:
 * Date: 2019/7/2 0002
 * Time: 上午 8:44
 */
public class Main {
    public static void main(String[] args) throws FileNotFoundException {


        try {
            KmlProperty kmlProperty;
            long starttime = System.currentTimeMillis();
            ParsingKmlUtil parsingKmlUtil = new ParsingKmlUtil();
            File file = new File("D:/DJI/AirLineView/app/kml/point.kml");
//            File file = new File("D:/DJI/AirLineView/app/kml/我的地点.kml");


//            File file = new File("D:/DJI/AirLineView/app/kml/永定增减挂验收.kml");
//          File targetFile = new File("D:/DJI/AirLineView/app/kml/永定增减挂验收2.kml");
            FileInputStream in = new FileInputStream(file);

            String code = getTxtEncode(in);

            System.out.println(code);
//
//            try {
//                FileEndCodeUtils.turnUTF8withoutBOM(file, targetFile);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            kmlProperty = parsingKmlUtil.parseKmlForJAK(file);


            if (kmlProperty.getKmlPoints().size() > 0) {
                for (KmlPoint k : kmlProperty.getKmlPoints()) {
                    System.out.println("点：" + k.getName());
                }
                System.out.println("点");
            }
            if (kmlProperty.getKmlLines().size() > 0) {
                for (KmlLine k : kmlProperty.getKmlLines()) {
                    System.out.println(k.getName());
                }
                System.out.println("线");
            }
            if (kmlProperty.getKmlPolygons().size() > 0) {
                for (KmlPolygon k : kmlProperty.getKmlPolygons()) {
                    System.out.println(k.getPoints());
                }
                System.out.println("面");
            }
            long endtime = System.currentTimeMillis();
            long time = endtime - starttime;

            System.out.println(time + "ms");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String getTxtEncode(FileInputStream in) throws IOException {
        String dc = Charset.defaultCharset().name();
        UnicodeInputStream uin = new UnicodeInputStream(in, dc);

        if ("UTF-8".equals(uin.getEncoding())) {
            uin.close();
            return "UTF-8";
        }
        uin.close();

        byte[] head = new byte[3];
        in.read(head);
        String code = "GBK";
        if (head[0] == -1 && head[1] == -2)
            code = "UTF-16";
        if (head[0] == -2 && head[1] == -1)
            code = "Unicode";
        //带BOM
        if (head[0] == -17 && head[1] == -69 && head[2] == -65)
            code = "带BOM UTF-8";
        if ("Unicode".equals(code)) {
            code = "UTF-16";
        }
        return code;
    }
}
