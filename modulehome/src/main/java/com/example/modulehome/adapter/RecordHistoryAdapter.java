package com.example.modulehome.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.library.utils.LogUtils;
import com.android.utils.UIUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.modulehome.R;
import com.example.modulehome.entity.DateRecodBean;
import com.example.modulehome.entity.MonthEntity;

import java.util.List;

/**
 * @author MiTa
 * @date 2017/11/20.
 */
public class RecordHistoryAdapter extends BaseQuickAdapter<DateRecodBean, BaseViewHolder> {

    public RecordHistoryAdapter( List<DateRecodBean> list) {
        super(R.layout.item_history_record, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, DateRecodBean item) {
        LinearLayout layout = helper.getView(R.id.layout);
        TextView title = helper.getView(R.id.title);
        TextView time = helper.getView(R.id.time);
        TextView count_tv = helper.getView(R.id.count_tv);
        TextView time_long = helper.getView(R.id.time_long);

        ImageView image1 = helper.getView(R.id.image1);
        ImageView image2 = helper.getView(R.id.image2);
        ImageView image3 = helper.getView(R.id.image3);

        title.setText(item.getTitle());
        time.setText(item.getTime());
        count_tv.setText(item.getCount());
        time_long.setText(item.getTimeLong());
        if (helper.getAdapterPosition() % 2 == 0) {
            image1.setImageDrawable(UIUtils.getResources().getDrawable(R.drawable.person_g));
            image2.setImageDrawable(UIUtils.getResources().getDrawable(R.drawable.bean_w));
            image3.setImageDrawable(UIUtils.getResources().getDrawable(R.drawable.clock_w));
            layout.setBackgroundColor(UIUtils.getColor(R.color.white));
        } else {
            image1.setImageDrawable(UIUtils.getResources().getDrawable(R.drawable.person_w));
            image2.setImageDrawable(UIUtils.getResources().getDrawable(R.drawable.bean_g));
            image3.setImageDrawable(UIUtils.getResources().getDrawable(R.drawable.clock_g));
            layout.setBackgroundColor(UIUtils.getColor(R.color.color_f3f3f3));

        }
    }

}
