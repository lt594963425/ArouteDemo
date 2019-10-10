package com.example.modulehome.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.android.library.utils.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.modulehome.R;
import com.example.modulehome.entity.MonthEntity;

import java.util.List;

/**
 * @author MiTa
 * @date 2017/11/20.
 */
public class TitleNumberMonthAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {
    public TitleNumberMonthAdapter(List<Integer> list) {
        super(R.layout.item_calendar_number_month, list);
    }
    @Override
    protected void convert(BaseViewHolder helper, Integer item) {
        TextView textView = helper.getView(R.id.text_number_month);
        textView.setText(String.valueOf(item));
    }


}
