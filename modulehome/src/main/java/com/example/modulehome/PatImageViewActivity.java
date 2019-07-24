package com.example.modulehome;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.modulebase.aroute.HomeAroutePath;
import com.example.modulehome.view.PatImageView;

/**
 * Created by ${LiuTao}.
 * User: Administrator
 * Name: ArouteDemo
 * functiona:
 * Date: 2019/6/28 0028
 * Time: 上午 11:31
 */
@Route(path = HomeAroutePath.HOME_PatImageView)
public class PatImageViewActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        PatImageView piv = new PatImageView(this);
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.base_yuansu);

        piv.setImageBitmap(bmp);

        setContentView(piv);
    }

}
