package com.example.modulehome.view;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

/**
 * @author caoyu
 * @date 2019/8/8
 */
class MyXFormatter implements IAxisValueFormatter {

    private String[] mValues;

    public MyXFormatter(String[] values) {
        this.mValues = values;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return mValues[(int) value % mValues.length];
    }
}
