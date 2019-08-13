package com.example.moduleuser;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.android.rxbus2.RxBus;
import com.android.rxbus2.Subscribe;
import com.android.rxbus2.ThreadMode;
import com.android.utils.UIUtils;
import com.android.view.colorpicker.ColorPickerView;
import com.android.view.colorpicker.OnColorChangedListener;
import com.android.view.colorpicker.OnColorSelectedListener;
import com.android.view.colorpicker.slider.AlphaSlider;
import com.android.view.colorpicker.slider.LightnessSlider;
import com.example.modulebase.aroute.UserAroutePath;
import com.example.modulebase.base.BaseFragment;
import com.example.moduleuser.utils.AnimalUtil;


@Route(path = UserAroutePath.USER_FRAGMENT)
public class UserFragment extends BaseFragment {
    EditText suspensionTextEt;
    TextView suspensionTextSize;
    SeekBar seekbarLevel;
    TextView suspensionTextColor;
    TextView suspensionColorTv;
    LinearLayout parentContaintLly;
    ColorPickerView colorPickerView;
    LightnessSlider vLightnessSlider;
    AlphaSlider vAlphaSlider;
    Button openSuspension;


    private int textSize = 15;
    private boolean expandView = false;
    public LinearLayout mColorPickViewLly;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, null);
        if (!RxBus.getDefault().isRegistered(this)) {
            RxBus.getDefault().register(this);
        }
        mColorPickViewLly = view.findViewById(R.id.color_pick_view_lly);
        suspensionTextEt = view.findViewById(R.id.suspension_text_et);
        suspensionTextSize = view.findViewById(R.id.suspension_text_size);
        seekbarLevel = view.findViewById(R.id.seekbar_level);
        suspensionTextColor = view.findViewById(R.id.suspension_text_color);
        suspensionColorTv = view.findViewById(R.id.suspension_color_tv);
        parentContaintLly = view.findViewById(R.id.parent_containt_lly);
        colorPickerView = view.findViewById(R.id.color_picker_view);
        vLightnessSlider = view.findViewById(R.id.v_lightness_slider);
        vAlphaSlider = view.findViewById(R.id.v_alpha_slider);
        openSuspension = view.findViewById(R.id.open_suspension);
        initView();

        return view;
    }

    private void initView() {
        colorPickerView.addOnColorChangedListener(new OnColorChangedListener() {
            @Override
            public void onColorChanged(int selectedColor) {
                suspensionTextEt.setTextColor(selectedColor);
                suspensionColorTv.setBackgroundColor(selectedColor);
            }
        });
        colorPickerView.addOnColorSelectedListener(new OnColorSelectedListener() {
            @Override
            public void onColorSelected(int selectedColor) {
                suspensionTextEt.setTextColor(selectedColor);
                suspensionColorTv.setBackgroundColor(selectedColor);
            }
        });

        parentContaintLly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expandView) {
                    expandView = false;
                    AnimalUtil.startAnimal(mColorPickViewLly, UIUtils.dip2Px(370), 0);
                    AnimalUtil.startRotation(parentContaintLly, 0, 90);
                } else {
                    expandView = true;
                    mColorPickViewLly.setVisibility(View.VISIBLE);
                    AnimalUtil.startAnimal(mColorPickViewLly, 0, UIUtils.dip2Px(370));
                    AnimalUtil.startRotation(parentContaintLly, 0, 90);
                }

            }
        });
        seekbarLevel.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textSize = progress + 1;
                if (fromUser) {
                    suspensionTextSize.setText("悬浮字体大小：" + textSize);
                    suspensionTextEt.setTextSize(textSize);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dispose();
        if (RxBus.getDefault().isRegistered(this)) {
            RxBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
