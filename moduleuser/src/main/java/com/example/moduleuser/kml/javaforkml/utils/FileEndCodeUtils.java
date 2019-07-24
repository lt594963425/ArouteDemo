package com.example.moduleuser.kml.javaforkml.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by ${LiuTao}.
 * User: Administrator
 * Name: AirLineView
 * functiona:文件转换成UTF-8无BOM格式
 * Date: 2019/7/2 0002
 * Time: 上午 9:46
 */
public class FileEndCodeUtils {

    public static void turnUTF8withoutBOM(File file, File targetFile) throws IOException {
        if (!targetFile.exists()) {
            targetFile.createNewFile();
        }
        BufferedReader br = null;
        BufferedWriter bw = null;
        br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFile), "UTF-8"));
        int i = 0;
        String str = "";
        while ((str = br.readLine()) != null) {
            if (i == 0)//读取第一行，将前三个字节去掉，重新new个String对象
            {
                byte[] bytes = str.getBytes("UTF-8");
                str = new String(bytes, 3, bytes.length - 3);
                bw.write(str + "\r\n");
                i++;
            } else
                bw.write(str + "\r\n");
        }
        br.close();
        bw.close();
    }
}
