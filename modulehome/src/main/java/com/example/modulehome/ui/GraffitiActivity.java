package com.example.modulehome.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.modulebase.base.BaseActivity;
import com.example.modulehome.R;
import com.example.modulehome.R2;
import com.example.modulehome.view.GraffitiView1;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by GraffitiViewActivity.
 * User: Administrator
 * Name: ArouteDemo
 * functiona:
 * Date: 2019/10/23 0023
 * Time: 上午 9:50
 */
public class GraffitiActivity extends BaseActivity {
    @BindView(R2.id.save_btn)
    Button saveBtn;
    @BindView(R2.id.grffitiview)
    GraffitiView1 grffitiview;
    @BindView(R2.id.show_path)
    TextView show_path;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graffiti_activity);
        ButterKnife.bind(this);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grffitiview.saveBitmap(show_path);
            }
        });
    }
}
