package com.example.modulehome;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.android.view.TagLayout;
import com.example.modulebase.aroute.HomeAroutePath;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${LiuTao}.
 * User: Administrator
 * Name: ArouteDemo
 * functiona:
 * Date: 2019/6/28 0028
 * Time: 下午 12:02
 */
@Route(path = HomeAroutePath.HOME_TagLayout)
public class TagLayoutActivity extends AppCompatActivity {
    private TagLayout mTagLayout;
    private List<String> mItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);
        mTagLayout = (TagLayout) findViewById(R.id.tag_layout);
        // 标签 后台获取 很多的实现方式  加List<String> 的集合
        mItems = new ArrayList<>();
        mItems.add("春去春又回");
        mItems.add("春天又一次拖着长长的裙裾回归季节的怀抱");
        mItems.add("一缕暖香飞上岁月的枝头");
        mItems.add("染绿了杨柳");
        mItems.add("染绿了大地");
        mItems.add("莫愁湖畔水盈盈");
        mItems.add("一颦一笑总关情");
        mItems.add("万水千山走遍");
        mItems.add("依然走不出念你的那一泉深潭");
        mItems.add("世界最美的爱情");
        mItems.add("是从青丝到白头");
        mItems.add("你做我的情有独钟");
        mItems.add("总拥有一份执手相看两不厌的缱绻");
        mItems.add("我定让如水的心绪在素笺上缓缓流淌");
        mTagLayout.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return mItems.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                final TextView tagTv = (TextView) LayoutInflater.from(TagLayoutActivity.this).inflate(R.layout.item_tag, parent, false);
                tagTv.setText(mItems.get(position));
                tagTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(TagLayoutActivity.this, tagTv.getText().toString(), Toast.LENGTH_LONG).show();
                    }
                });
                return tagTv;
            }
        });

    }
}
