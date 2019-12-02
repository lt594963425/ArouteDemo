package com.example.modulehome.ui;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.library.utils.ToastUtils;
import com.android.utils.GlideUtils;
import com.example.modulebase.base.BaseActivity;
import com.example.modulehome.R;
import com.example.modulehome.R2;
import com.example.modulehome.utils.PictureSelectUtils;
import com.example.modulehome.utils.SysUtils;
import com.example.modulehome.view.DoodleView2;
import com.example.modulehome.view.GraffitiView1;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    @BindView(R2.id.doodle_layout)
    DoodleView2 doodleView2;
    @BindView(R2.id.grffitiview)
    GraffitiView1 grffitiview;
    @BindView(R2.id.show_path)
    TextView show_path;
    private List<LocalMedia> selectList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graffiti_activity);
        ButterKnife.bind(this);
        Button selected_btn = findViewById(R.id.selected_btn);
        Button undo_btn = findViewById(R.id.undo_btn);
        selected_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureSelectUtils.pictureSelectorSingleFormPhoto(GraffitiActivity.this, PictureMimeType.ofImage(),
                        false, PictureConfig.MULTIPLE, PictureConfig.CHOOSE_REQUEST);
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doodleView2.saveCanvas(show_path);
            }
        });
        undo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doodleView2.revertPath();
            }
        });

    }

    /**
     * 设置原始的截图
     */
    public void setOriginBitmap(String path) {
        File file = new File(path);
        if (file.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            int bw = bitmap.getWidth();
            int bh = bitmap.getHeight();
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) doodleView2.getLayoutParams();
            int pw = SysUtils.getScreenWidth(this);
            params.height = (bh * pw) / bw;
            params.width = pw;
            params.gravity = Gravity.CENTER;
            doodleView2.setOriginBitmap(bitmap);
            doodleView2.requestLayout();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    for (LocalMedia media : selectList) {
                        Log.i("图片-----》", media.getPath());
                    }

                    setOriginBitmap(selectList.get(0).getPath());
                    break;
            }
        }
    }
}
