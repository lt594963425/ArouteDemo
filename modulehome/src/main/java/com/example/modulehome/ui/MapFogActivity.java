package com.example.modulehome.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

import com.example.modulehome.R;
import com.example.modulehome.R2;
import com.example.modulehome.entity.ImgSimple;
import com.example.modulehome.entity.PointSimple;
import com.example.modulehome.view.ImageLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by V7SupportActivity.
 * User: Administrator
 * Name: ArouteDemo
 * functiona:
 * Date: 2019/9/4 0004
 * Time: 上午 9:26
 */
public class MapFogActivity extends AppCompatActivity {


    @BindView(R2.id.layoutContent)
    ImageLayout layoutContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_img_browse);
        ButterKnife.bind(this);

        ImgSimple imgSimple1 = new ImgSimple();
        imgSimple1.url = R.drawable.logo;
        imgSimple1.scale = 1.6f;

        ArrayList<PointSimple> pointSimples = new ArrayList<>();
        PointSimple pointSimple1 = new PointSimple();
        pointSimple1.width_scale = 0.36f;
        pointSimple1.height_scale = 0.75f;

        PointSimple pointSimple2 = new PointSimple();
        pointSimple2.width_scale = 0.64f;
        pointSimple2.height_scale = 0.5f;


        PointSimple pointSimple3 = new PointSimple();
        pointSimple3.width_scale = 0.276f;
        pointSimple3.height_scale = 0.764f;

        PointSimple pointSimple4 = new PointSimple();
        pointSimple4.width_scale = 0.638f;
        pointSimple4.height_scale = 0.74f;

        PointSimple pointSimple5 = new PointSimple();
        pointSimple5.width_scale = 0.796f;
        pointSimple5.height_scale = 0.526f;

        PointSimple pointSimple6 = new PointSimple();
        pointSimple6.width_scale = 0.486f;
        pointSimple6.height_scale = 0.364f;

        pointSimples.add(pointSimple1);
        pointSimples.add(pointSimple2);
        pointSimples.add(pointSimple3);
        pointSimples.add(pointSimple4);
        pointSimples.add(pointSimple5);
        pointSimples.add(pointSimple6);

        imgSimple1.pointSimples = pointSimples;
        layoutContent.setPoints(pointSimples);
        float scale = imgSimple1.scale;

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = (int) (width * scale);
        layoutContent.setImgBg(width, height, imgSimple1.url);
    }


}
