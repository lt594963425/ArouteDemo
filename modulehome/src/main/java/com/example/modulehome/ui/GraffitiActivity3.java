package com.example.modulehome.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.utils.LogUtil;
import com.example.modulebase.base.BaseActivity;
import com.example.modulehome.R;
import com.example.modulehome.R2;
import com.example.modulehome.utils.PictureSelectUtils;
import com.example.modulehome.view.DoodleView2;
import com.example.modulehome.view.GraFFPhotoView;
import com.example.modulehome.view.GraffitiView1;
import com.example.modulehome.view.PhotoEditorView;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.Executors;

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
public class GraffitiActivity3 extends BaseActivity {


    public GraFFPhotoView mFfPhotoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        mFfPhotoView = findViewById(R.id.graff_view);
        mFfPhotoView.setZoomable(false);
        Button mSaveBtn = findViewById(R.id.save_btn);
        Button drag_btn = findViewById(R.id.drag_btn);
        Button selected_btn = findViewById(R.id.selected_btn);

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sdcard = Environment.getExternalStorageDirectory().getPath() + "/ACanVas/";
                String name = System.currentTimeMillis() + ".jpg";
                saveBitmapCanvas(mFfPhotoView.getDrawingCache(), sdcard + name);

            }
        });
        drag_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFfPhotoView.setZoomable(true);
                mFfPhotoView.setEditable(false);
            }
        });
        selected_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFfPhotoView.setZoomable(false);
                mFfPhotoView.setEditable(true);
            }
        });
    }


    public void saveBitmapCanvas(final Bitmap bitmap, final String path) {
        try {

            LogUtil.e("路径", path);
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    LogUtil.e("路径", "-----------" + Thread.currentThread().getName());
                    FileOutputStream fos;
                    try {
                        File file = new File(path);
                        File pfIle = file.getParentFile();
                        if (pfIle != null && !pfIle.exists()) {
                            pfIle.mkdirs();
                        }
                        if (file.exists()) {
                            file.delete();
                        }
                        fos = new FileOutputStream(file);
                        //保存图片的设置，压缩图片
                        if (bitmap != null) {
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
                        }
                        fos.flush();
                        fos.close();//关闭流
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
