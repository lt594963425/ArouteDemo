package com.example.aroutedemo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.android.library.utils.StatusBarUtils;
import com.android.utils.LogUtil;
import com.example.aroutedemo.adapter.TabFragmentPagerAdapter;
import com.example.modulebase.aroute.HomeAroutePath;
import com.example.modulebase.aroute.MainAroutePath;
import com.example.modulebase.aroute.UserAroutePath;
import com.example.modulebase.base.BaseActivity;
import com.example.modulebase.base.BaseFragment;
import com.example.aroutedemo.R;

import java.util.ArrayList;
import java.util.List;


/**
 * 主页
 */
@Route(path = MainAroutePath.MAIN_ACTIVITY)
public class MainActivity extends BaseActivity {
    private List<BaseFragment> mFragmensts;
    public BaseFragment mFragmentUser;
    public BaseFragment mFragmentHome;
    private static Activity activity;

    public static Activity getThis() {
        return activity;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_main);
            activity = this;
            // setStatusBarTrans();
            //StatusBarUtils.setNavBarColor(this, Color.WHITE);
            final ViewPager mViewPager = findViewById(R.id.viewpager);
            final RadioGroup mBottomTabGroup = findViewById(R.id.bottom_tab_group);
            RadioButton mTabHome = findViewById(R.id.tab_home);
            RadioButton mTabMine = findViewById(R.id.tab_mine);
            mFragmentHome = (BaseFragment) ARouter.getInstance().build(HomeAroutePath.HOME_FRAGMENT).navigation();
            mFragmentUser = (BaseFragment) ARouter.getInstance().build(UserAroutePath.USER_FRAGMENT).navigation();
            mFragmensts = new ArrayList<>();
            mFragmensts.add(mFragmentHome);
            mFragmensts.add(mFragmentUser);
            TabFragmentPagerAdapter mAdapter = new TabFragmentPagerAdapter(getSupportFragmentManager(), mFragmensts);
            mViewPager.setAdapter(mAdapter);
            mViewPager.setOffscreenPageLimit(2);
            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float v, int i1) {

                }

                @Override
                public void onPageSelected(int position) {
                    switch (position) {
                        case 0:
                            mBottomTabGroup.check(R.id.tab_home);
                            break;
                        case 1:
                            mBottomTabGroup.check(R.id.tab_mine);
                        default:
                            break;
                    }

                }

                @Override
                public void onPageScrollStateChanged(int position) {

                }
            });

            mTabHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mViewPager.setCurrentItem(0, false);
                }
            });
            mTabMine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mViewPager.setCurrentItem(1, false);

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.e("返回M", requestCode + "-------------" + requestCode + "");

        mFragmentHome.onActivityResult(requestCode, resultCode, data);
    }
}
