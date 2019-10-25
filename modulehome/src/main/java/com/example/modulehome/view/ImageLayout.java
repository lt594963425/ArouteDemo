package com.example.modulehome.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.android.utils.UIUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.modulehome.R;
import com.example.modulehome.entity.PointSimple;

import java.util.ArrayList;

/**
 * Created by ImageLayout .
 * User: Administrator
 * Name: ArouteDemo
 * functiona:
 * Date: 2019/10/22 0022
 * Time: 下午 15:20
 */
public class ImageLayout extends FrameLayout implements View.OnClickListener {
    ArrayList<PointSimple> points;

    FrameLayout layouPoints;

    ImageView imgBg;

    Context mContext;

    public ImageLayout(Context context) {
        this(context, null);
    }

    public ImageLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context, attrs);
    }


    private void initView(Context context, AttributeSet attrs) {

        mContext = context;

        View imgPointLayout = inflate(context, R.layout.layout_imgview_point, this);

        imgBg = (ImageView) imgPointLayout.findViewById(R.id.imgBg);
        layouPoints = (FrameLayout) imgPointLayout.findViewById(R.id.layouPoints);
    }

    public void setImgBg(int width, int height, int imgUrl) {

        ViewGroup.LayoutParams lp = imgBg.getLayoutParams();
        lp.width = width;
        lp.height = height;

        imgBg.setLayoutParams(lp);

        ViewGroup.LayoutParams lp1 = layouPoints.getLayoutParams();
        lp1.width = width;
        lp1.height = height;

        layouPoints.setLayoutParams(lp1);

        RequestOptions requestOptions = new RequestOptions()
                .centerCrop();
        Glide.with(UIUtils.getContext())
                .load(imgUrl)
                .apply(requestOptions)
                .into(imgBg);
//        Glide.with(mContext).load(imgUrl).asBitmap().into(imgBg);

        addPoints(width, height);

    }

    public void setPoints(ArrayList<PointSimple> points) {

        this.points = points;
    }

    private void addPoints(int width, int height) {

        layouPoints.removeAllViews();

        for (int i = 0; i < points.size(); i++) {

            double width_scale = points.get(i).width_scale;
            double height_scale = points.get(i).height_scale;


            LinearLayout view = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.layout_img_point, this, false);
            ImageView imageView = (ImageView) view.findViewById(R.id.imgPoint);
            imageView.setTag(i);

            AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getDrawable();
            animationDrawable.start();

            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();

            layoutParams.leftMargin = (int) (width * width_scale);
            layoutParams.topMargin = (int) (height * height_scale);

            imageView.setOnClickListener(this);

            layouPoints.addView(view, layoutParams);
        }
    }


    @Override
    public void onClick(View view) {
        int pos = (int) view.getTag();
        Toast.makeText(getContext(), "pos : " + pos, Toast.LENGTH_SHORT).show();
    }

}
