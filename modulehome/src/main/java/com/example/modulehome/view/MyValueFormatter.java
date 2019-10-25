package com.example.modulehome.view;


import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;


import java.text.NumberFormat;


/**
 * $activityName
 *
 * @author ${LiuTao}
 * @date 2018/4/7/007
 */

public class MyValueFormatter extends ValueFormatter {

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        return double2String(value, 2);
    }

    /**
     * 将double转为数值，并最多保留num位小数。例如当num为2时，1.268为1.27，1.2仍为1.2；1仍为1，而非1.00;100.00则返回100。
     *
     * @param d
     * @param num 小数位数
     * @return
     */
    public static String double2String(double d, int num) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(num);//保留两位小数
        nf.setGroupingUsed(false);//去掉数值中的千位分隔符

        String temp = nf.format(d);
        if (temp.contains(".")) {
            String s1 = temp.split("\\.")[0];
            String s2 = temp.split("\\.")[1];
            for (int i = s2.length(); i > 0; --i) {
                if (!"0".equals(s2.substring(i - 1, i))) {
                    return s1 + "." + s2.substring(0, i);
                }
            }
            return s1;
        }
        return temp;
    }
}
