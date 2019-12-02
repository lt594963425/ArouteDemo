package com.example.modulehome.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.library.utils.ToastUtils;
import com.android.utils.GlideUtils;
import com.android.utils.LogUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.modulebase.base.BaseActivity;
import com.example.modulebase.base.base.App;
import com.example.modulehome.R;
import com.example.modulehome.utils.PictureSelectUtils;
import com.example.modulehome.utils.SysUtils;
import com.example.modulehome.view.PhotoEditorView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by GraffitiViewActivity.
 * User: Administrator
 * Name: ArouteDemo
 * functiona:
 * Date: 2019/10/23 0023
 * Time: 上午 9:50
 */
public class GraffitiActivity2 extends BaseActivity {

    public PhotoEditorView mPhotoEditorView;
    public Button mSaveBtn;
    public TextView mShowPath;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graffiti_activity2);
        mPhotoEditorView = findViewById(R.id.ba_iiii);
        mShowPath = findViewById(R.id.show_path);
        Button selected_btn = findViewById(R.id.selected_btn);
        Button drag_btn = findViewById(R.id.drag_btn);
        mSaveBtn = findViewById(R.id.save_btn);
        mPhotoEditorView.setEditable(true);
        selected_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureSelectUtils.pictureSelectorSingleFormPhoto(GraffitiActivity2.this, PictureMimeType.ofImage(),
                        false, PictureConfig.MULTIPLE, PictureConfig.CHOOSE_REQUEST);
            }
        });
        drag_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sdcard = Environment.getExternalStorageDirectory().getPath() + "/ACanVas/";
                String name = System.currentTimeMillis() + ".jpg";
                mPhotoEditorView.saveBitmapCanvas(sdcard + name);
                mShowPath.setText(sdcard + name);
            }
        });
        String image_path = Environment.getExternalStorageDirectory().getPath() + "/quantum/" + "DJI_0083.jpg";

        setOriginBitmap(image_path);
    }


    /**
     * 设置原始图
     */
    public void setOriginBitmap(String path) {
        if (TextUtils.isEmpty(path)) {
            ToastUtils.showToast("图片路径不能为空");
            finish();
            return;
        }
        File file = new File(path);
        if (file.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mPhotoEditorView.getLayoutParams();
            int pw = SysUtils.getScreenWidth(this);
            if (bitmap != null) {
                int bw = bitmap.getWidth();
                int bh = bitmap.getHeight();
                params.height = (bh * pw) / bw;
            }
            params.width = pw;
            params.gravity = Gravity.CENTER;
            RequestOptions requestOptions = new RequestOptions()
                    .override(Target.SIZE_ORIGINAL)
                    .error(R.drawable.photo_defect_d_timg)
                    .placeholder(R.drawable.photo_defect_d_timg)
                    .fitCenter();
            Glide.with(App.getContext())
                    .asBitmap()
                    .load(path)
                    .apply(requestOptions)
                    .into(mPhotoEditorView);
            mPhotoEditorView.requestLayout();
        } else {
            ToastUtils.showToast("图片不存在");
            finish();
        }
    }

    private List<LocalMedia> selectList = new ArrayList<>();

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

//                    GlideUtils.intoAdImage(selectList.get(0).getPath(),mBaseDragZoomImageView);
                    setOriginBitmap(selectList.get(0).getPath());
                    break;
            }
        }
    }

}
