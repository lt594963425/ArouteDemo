package com.example.modulehome;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.modulebase.aroute.HomeAroutePath;
import com.example.modulebase.base.BaseActivity;

/**
 * 独编译运行时调用
 */
@Route(path = HomeAroutePath.HOME_ACTIVITY)
public class HomeActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setToolbarTitle("主页");
    }
}
