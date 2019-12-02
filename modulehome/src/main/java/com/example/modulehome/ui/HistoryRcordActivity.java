package com.example.modulehome.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.library.utils.StatusBarUtils;
import com.android.utils.LogUtil;
import com.example.modulebase.base.BaseActivity;
import com.example.modulehome.R;
import com.example.modulehome.adapter.RecordHistoryAdapter;
import com.example.modulehome.entity.DateRecodBean;
import com.example.modulehome.view.GraFFPhotoView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * Created by GraffitiViewActivity.
 * User: Administrator
 * Name: ArouteDemo
 * functiona:
 * Date: 2019/10/23 0023
 * Time: 上午 9:50
 */
public class HistoryRcordActivity extends BaseActivity {

    private List<DateRecodBean> mDateRecodBeanList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StatusBarUtils.setStatusBarColor(this, ContextCompat.getColor(this, R.color.color_323457), false);

        setContentView(R.layout.activity_hitory);
        RelativeLayout relativeLayout = findViewById(R.id.ll_tablayout);
        setAdapterStatusBar(relativeLayout);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RecordHistoryAdapter recordHistoryAdapter = new RecordHistoryAdapter(null);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recordHistoryAdapter);
        mDateRecodBeanList.add(new DateRecodBean("啊哈", "12月01日 17:02 - 12月01日 20:11", "4.5w", "3小时9分钟"));
        mDateRecodBeanList.add(new DateRecodBean("啊哈", "11月30日 22:32 - 12月01日 00:53", "3.5w", "2小时21分钟"));
        mDateRecodBeanList.add(new DateRecodBean("啊哈", "11月29日 09:58 - 11月29日 12:09", "4.3w", "2小时10分钟"));
        mDateRecodBeanList.add(new DateRecodBean("啊哈", "11月28日 20:00 - 11月28日 22:40", "3.3w", "2小时39分钟"));
        mDateRecodBeanList.add(new DateRecodBean("啊哈", "11月27日 14:11 - 11月27日 17:23", "4.2w", "3小时11分钟"));
        mDateRecodBeanList.add(new DateRecodBean("啊哈", "11月26日 17:16 - 11月26日 19:35", "4.3w", "2小时19分钟"));
        mDateRecodBeanList.add(new DateRecodBean("啊哈", "11月25日 13:18 - 11月25日 16:42", "4.3w", "3小时24分钟"));
        recordHistoryAdapter.setNewData(mDateRecodBeanList);
    }


}
