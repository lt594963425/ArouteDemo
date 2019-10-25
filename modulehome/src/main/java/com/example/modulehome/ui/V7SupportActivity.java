package com.example.modulehome.ui;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.utils.LogUtil;
import com.android.utils.UIUtils;
import com.example.modulehome.R;
import com.example.modulehome.R2;
import com.example.modulehome.entity.ChartDataBean;
import com.example.modulehome.entity.MyXValueBean;
import com.example.modulehome.view.MyValueFormatter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.modulebase.base.base.App.getContext;

/**
 * Created by V7SupportActivity.
 * User: Administrator
 * Name: ArouteDemo
 * functiona:
 * Date: 2019/9/4 0004
 * Time: 上午 9:26
 */
public class V7SupportActivity extends AppCompatActivity implements OnChartValueSelectedListener {

    public TextView mTimeSelectedTv;
    @BindView(R2.id.time_selected_tv)
    TextView timeSelectedTv;

    public BarChart barChart1;

    protected Typeface tfLight;
    public LineChart mLineChart1;
    public BarDataSet mSet1;


    ArrayList<Entry> numberValus = new ArrayList<>();

    List<MyXValueBean> xNumberValus = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_v7support);
        ButterKnife.bind(this);
//        initChart(barChart1);
//        initChart(barChart2);
        barChart1 = findViewById(R.id.bar_chart_1);
        mLineChart1 = findViewById(R.id.line_chart_1);
        mTimeSelectedTv = findViewById(R.id.time_selected_tv);
        mTimeSelectedTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(V7SupportActivity.this).inflate(R.layout.item_time_selected_view_layout, null);
                PopupWindow mPopupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.WRAP_CONTENT, false);
                mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                mPopupWindow.setOutsideTouchable(true);
                mPopupWindow.setFocusable(true);
                mPopupWindow.getContentView().measure(makeDropDownMeasureSpec(mPopupWindow.getWidth()),
                        makeDropDownMeasureSpec(mPopupWindow.getHeight()));
                mPopupWindow.showAsDropDown(mTimeSelectedTv, 0, UIUtils.dip2Px(6));

            }
        });
        tfLight = Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf");
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                LogUtil.e("线程", Thread.currentThread().getName() + "");
            }
        });

        initChart(barChart1);
        initLineBar(mLineChart1);

        //次数
        xNumberValus = new ArrayList<>();
        numberValus = new ArrayList<>();
        int allNumber = 0;
        int maxValue = 0;
        int minValue = 0;
        for (int i = 1; i <= 30; i++) {
            int number = (int) ((Math.random() * (50 / 2f)) + 10);
            xNumberValus.add(new MyXValueBean(i + "日", number));
            allNumber = allNumber + number;
            numberValus.add(new Entry(i - 1, number));
            if (maxValue < number) {
                maxValue = number;
            }
            if (minValue > number) {
                minValue = number;
            }
        }
        setData(mLineChart1, xNumberValus, numberValus, maxValue, minValue);
    }

    private void initLineBar(LineChart lineChart) {

        lineChart.setOnChartValueSelectedListener(this);
        // no description text
        lineChart.getDescription().setEnabled(false);
        // enable touch gestures
        lineChart.setTouchEnabled(true);
        lineChart.setDragDecelerationFrictionCoef(1f);
        // enable scaling and dragging
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.setDrawGridBackground(false);
        lineChart.setHighlightPerDragEnabled(true);
        // if disabled, scaling can be done on x- and y-axis separately
        lineChart.setPinchZoom(false);
        // set an alternative background color
        lineChart.setBackgroundColor(Color.WHITE);
        lineChart.animateX(1500);
        // get the legend (only possible after setting data)
        Legend l = lineChart.getLegend();
        // modify the legend ...
        l.setForm(Legend.LegendForm.EMPTY);
        l.setTextColor(Color.RED);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
        l.setDrawInside(false);
        l.setTextSize(20f);
        //X轴
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setGranularity(1);
        xAxis.setTextSize(9f);
        xAxis.setTextColor(Color.BLUE);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setSpaceMax(1);
        xAxis.setAvoidFirstLastClipping(false);
        //Y轴
        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        leftAxis.setAxisMaximum(200f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(false);
        //横向虚线
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        lineChart.getAxisRight().setEnabled(false);

    }

    private void setData(LineChart lineChart, final List<MyXValueBean> list, ArrayList<Entry> values, int maxValue, int minValue) {
        ValueFormatter formatter = new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int v = (int) value;
                if (v < list.size() && v >= 0) {
                    String st = list.get(v).getxValue();
                    String tim1 = "";
                    tim1 = st;
                    return tim1;
                } else {
                    return "";
                }
            }
        };
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setLabelCount(list.size());
        xAxis.setGranularity(1);     //这个很重要
        xAxis.setValueFormatter(formatter);
        if (list.size() > 15) {
            xAxis.setTextSize(6f);
        } else {
            xAxis.setTextSize(12f);
        }
        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setAxisMaximum(maxValue + 10);
        leftAxis.setAxisMinimum(minValue - 5);
        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(values, "");
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setColor(ColorTemplate.getHoloBlue());
        set1.setCircleColor(ContextCompat.getColor(this, R.color.color_pink));
        set1.setLineWidth(2f);
        set1.setCircleRadius(3f);
        set1.setFillAlpha(65);
        if (list.size() < 2) {
            set1.setDrawCircles(true);
        } else {
            set1.setDrawCircles(false);
        }
        set1.setFillColor(ContextCompat.getColor(this, R.color.color_pink));
        set1.setHighLightColor(ContextCompat.getColor(this, R.color.color_pink));
        set1.setDrawCircleHole(false);
        set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        // create a data object with the data sets
        LineData data = new LineData(set1);
        data.setValueTextColor(ContextCompat.getColor(this, R.color.color_pink));
        data.setValueTextSize(9f);
        data.setDrawValues(false);
        // set data
        lineChart.setData(data);
        lineChart.invalidate();

    }
    @SuppressWarnings("ResourceType")
    private int makeDropDownMeasureSpec(int measureSpec) {
        int mode;
        if (measureSpec == ViewGroup.LayoutParams.WRAP_CONTENT) {
            mode = View.MeasureSpec.UNSPECIFIED;
        } else {
            mode = View.MeasureSpec.EXACTLY;
        }
        return View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(measureSpec), mode);
    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.i("Entry selected", e.toString());

        mLineChart1.centerViewToAnimated(e.getX(), e.getY(), mLineChart1.getData().getDataSetByIndex(h.getDataSetIndex())
                .getAxisDependency(), 500);
        //chart.zoomAndCenterAnimated(2.5f, 2.5f, e.getX(), e.getY(), chart.getData().getDataSetByIndex(dataSetIndex)
        // .getAxisDependency(), 1000);
        //chart.zoomAndCenterAnimated(1.8f, 1.8f, e.getX(), e.getY(), chart.getData().getDataSetByIndex(dataSetIndex)
        // .getAxisDependency(), 1000);
    }

    @Override
    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
    }


    //线性表
    private void initChart(BarChart barChart) {
        barChart.setMaxVisibleValueCount(60);
        barChart.setPinchZoom(true);
        barChart.setDrawBarShadow(false);
        barChart.setDrawGridBackground(false);
        barChart.getDescription().setEnabled(false);

        barChart.getAxisLeft().setDrawGridLines(false);
        YAxis leftAxis = barChart.getAxisLeft();//获取左侧y轴
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);//设置y轴标签显示在外侧
        leftAxis.setAxisMinimum(0f);//设置Y轴最小值
        leftAxis.setAxisMaximum(50f);
        leftAxis.setTextColor(ContextCompat.getColor(this, R.color.blue));
        leftAxis.setDrawGridLines(true);
        leftAxis.setDrawLabels(true);//禁止绘制y轴标签
        leftAxis.setDrawAxisLine(true);//禁止绘制y轴
        barChart.getAxisRight().setEnabled(false);//禁用右侧y轴
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        // add a nice and smooth animation
        barChart.animateY(0);
        barChart.getLegend().setEnabled(false);
        //图例设置
        Legend legend = barChart.getLegend();
        legend.setEnabled(true);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);//图例水平居中
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);//图例在图表上方
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);//图例的方向为水平
        legend.setDrawInside(false);//绘制在chart的外侧
        legend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);//图例中的文字方向
        legend.setTextColor(ContextCompat.getColor(this, R.color.blue));
        legend.setForm(Legend.LegendForm.SQUARE);//图例窗体的形状
        legend.setFormSize(16f);//图例窗体的大小
        legend.setTextSize(16f);//图例文字的大小

        barChart.setExtraBottomOffset(5);//距视图窗口底部的偏移，类似与paddingbottom
        barChart.setExtraTopOffset(30);//距视图窗口顶部的偏移，类似与paddingtop
        barChart.setBorderWidth(3);
        barChart.setFitBars(true);//使两侧的柱图完全显示
        List<ChartDataBean> beanList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            beanList.add(new ChartDataBean(i, 15));
        }
        setBarChartData(barChart, beanList);
    }

    private void setBarChartData(BarChart mChart, List<ChartDataBean> beanList) {
        try {
            if (beanList == null) {
                return;
            }
            ArrayList<BarEntry> yVals1 = new ArrayList<>();
            XAxis mXAxis = mChart.getXAxis();
            mXAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            mXAxis.setDrawGridLines(false);
            mXAxis.setTextColor(ContextCompat.getColor(this, R.color.blue));
            mXAxis.setLabelCount(beanList.size());
            for (int i = 0; i < beanList.size(); i++) {
                float valY = beanList.get(i).getData() + 100;
                if (valY < 0) {
                    valY = 0;
                }
                yVals1.add(new BarEntry((float) beanList.get(i).getId(), valY));
            }

            if (mChart.getData() != null && mChart.getData().getDataSetCount() > 0) {
                mSet1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);
                mSet1.setValues(yVals1);
                mChart.getData().notifyDataChanged();
                mChart.notifyDataSetChanged();
            } else {
                mSet1 = new BarDataSet(yVals1, "信道干扰强度(数值越低，干扰越小)");
                mSet1.setColors(new int[]{Color.BLUE, Color.RED, Color.GREEN});
                mSet1.setHighLightColor(ContextCompat.getColor(this, R.color.blue));
                mSet1.setBarBorderColor(ContextCompat.getColor(this, R.color.blue));
                mSet1.setDrawValues(true);

                ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
                dataSets.add(mSet1);
                BarData data = new BarData(dataSets);
                data.setValueTextColor(ContextCompat.getColor(this, R.color.blue));
                data.setValueTextSize(9);
                data.setBarWidth(0.1f);
                data.setValueFormatter(new MyValueFormatter());
                mChart.setData(data);
                mChart.setFitBars(true);
            }
            mChart.invalidate();
        } catch (Exception e) {
            e.printStackTrace();

            //ToastUtils.showToast(e.toString());
        }
    }
}
