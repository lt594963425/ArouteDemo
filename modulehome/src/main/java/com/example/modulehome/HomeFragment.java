package com.example.modulehome;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.modulebase.aroute.HomeAroutePath;
import com.example.modulebase.base.BaseFragment;

/**
 * Created by ${LiuTao}.
 * User: Administrator
 * Name: ArouteDemo2
 * functiona:
 * Date: 2019/5/8 0008
 * Time: 下午 17:47
 */
@Route(path = HomeAroutePath.HOME_FRAGMENT)
public class HomeFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home, null);
        return view;
    }
}
