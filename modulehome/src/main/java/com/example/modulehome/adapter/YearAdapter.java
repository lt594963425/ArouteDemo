package com.example.modulehome.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.modulehome.R;

import java.util.List;

/**
 * $activityName
 *
 * @author LiuTao
 * @date 2019/3/21/021
 */


public class YearAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {
    private int selectedId = -1;
    private Context context;

    public YearAdapter(Context context, @Nullable List<Integer> data) {
        super(R.layout.item_year_selected_view, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, Integer item) {
        helper.addOnClickListener(R.id.tab_text_tv);
        TextView textView = helper.getView(R.id.tab_text_tv);
        textView.setText(String.valueOf(item));
        if (selectedId != -1) {
            if (selectedId == helper.getLayoutPosition()) {
                textView.setBackground(context.getResources().getDrawable(R.drawable.bg_ad_tab_selected));
                textView.setTextColor(Color.WHITE);
            } else {
                textView.setBackground(context.getResources().getDrawable(R.drawable.bg_ad_tab_unselected));
                textView.setTextColor(Color.BLACK);
            }
        } else {
            textView.setBackground(context.getResources().getDrawable(R.drawable.bg_ad_tab_unselected));
            textView.setTextColor(Color.BLACK);
        }
    }

    public void setSelectedId(int position) {
        selectedId = position;
        notifyDataSetChanged();
    }

    public int getSelectedId() {
        return selectedId;
    }
}
