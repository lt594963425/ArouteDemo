package com.example.modulebase.base;


import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.MenuRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;

import com.android.library.utils.StatusBarUtils;
import com.android.utils.LogUtil;
import com.android.view.XToolbar;
import com.example.modulebase.R;


import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * $activityName
 *
 * @author LiuTao
 * @date 2018/7/17/017
 */


public class BaseActivity extends AppCompatActivity {

    private XToolbar mToolbar;
    private View mNotDataView;
    private View mErrorView;

    protected XToolbar getToolbar() {
        return mToolbar;
    }

    public void initToolbar() {
        mToolbar = (XToolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
    }

    public void setToolbarTitle(CharSequence title) {
        if (mToolbar == null) {
            initToolbar();
        }
        mToolbar.setTitle(title);
    }

    public void setInflateMenu(@MenuRes int resId) {
        mToolbar.inflateMenu(resId);
    }

    public void setInflateMenu(@MenuRes int resId, Toolbar.OnMenuItemClickListener itemClickListener) {
        if (mToolbar == null) {
            initToolbar();
        }
        mToolbar.inflateMenu(resId);
        mToolbar.setOnMenuItemClickListener(itemClickListener);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        compositeDisposable = new CompositeDisposable();
        LogUtil.i("BaseActivity", "*****onCreate()方法******");
        initBaseView();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().removeActivity(this);
    }

    /**
     * 隐藏状态栏的高度 ，设置为透明
     */
    public void setStatusBarTrans() {
        //StatusBarUtils.setStatusBarColor(this, ContextCompat.getColor(this, R.color.transparent));
        //侵入状态栏
        StatusBarUtils.setStatusBarColor(this, Color.argb(0, 0, 0, 0), true);
    }


    /**
     * 保留状态栏高度
     */
    public void setStatusBarShowHeight() {
        StatusBarUtils.setStatusBarColor(this, ContextCompat.getColor(this, R.color.transparent));
    }

    /**
     * 黑白模式
     */
    public void setStatusBarLightMode(boolean isLightMode) {
        StatusBarUtils.setStatusBarLightMode(this, isLightMode);
    }

    private ProgressDialog dialog;

    public void showPDLoading(String s) {
        if (dialog != null && dialog.isShowing()) return;
        dialog = new ProgressDialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        if (TextUtils.isEmpty(s)) {
            dialog.setMessage("请求网络中...");
        } else {
            dialog.setMessage(s);
        }

        dialog.show();
    }

    public void dismissLoading() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    private CompositeDisposable compositeDisposable;

    public void addDisposable(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    public void dispose() {

        if (compositeDisposable != null) compositeDisposable.dispose();
    }

    public void initBaseView() {
        mNotDataView = getLayoutInflater().inflate(R.layout.empty_view, null, false);
        mErrorView = getLayoutInflater().inflate(R.layout.error_view, null, false);

    }

}
