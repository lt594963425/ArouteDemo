package com.example.modulehome.ui;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.utils.UIUtils;
import com.example.modulehome.R;

/**
 * Created by V7SupportActivity.
 * User: Administrator
 * Name: ArouteDemo
 * functiona:
 * Date: 2019/9/4 0004
 * Time: 上午 9:26
 */
public class V7SupportActivity extends AppCompatActivity {

    public TextView mTimeSelectedTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_v7support);

        mTimeSelectedTv = findViewById(R.id.time_selected_tv);
        mTimeSelectedTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(V7SupportActivity.this).inflate(R.layout.item_time_selected_view_layout, null);
                PopupWindow     mPopupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, false);
                mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                mPopupWindow.setOutsideTouchable(true);
                mPopupWindow.setFocusable(true);
                mPopupWindow.getContentView().
                        measure(makeDropDownMeasureSpec(mPopupWindow.getWidth()),
                                makeDropDownMeasureSpec(mPopupWindow.getHeight()));
                mPopupWindow.showAsDropDown(mTimeSelectedTv, 0, UIUtils.dip2Px(6));

            }
        });
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

}
