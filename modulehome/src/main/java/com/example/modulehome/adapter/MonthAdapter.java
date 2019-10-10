package com.example.modulehome.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
public class MonthAdapter extends BaseQuickAdapter<MonthEntity, BaseViewHolder> {

    private Context context;

    public MonthAdapter(Context context, List<MonthEntity> list) {
        super(R.layout.item_calendar, list);
        this.context = context;

    }

    @Override
    protected void convert(BaseViewHolder helper, MonthEntity item) {
        RecyclerView recyclerView = helper.getView(R.id.rv_cal);

        GridLayoutManager glm = new GridLayoutManager(context, 7) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        glm.setAutoMeasureEnabled(true);
        DateAdapter adapter = new DateAdapter(context, item.getList());

        adapter.setClickListener(new DateAdapter.OnDateClickListener() {
            @Override
            public void onDateClick(int parentPos, int pos) {
                LogUtils.e("CalendarSelectedView --MonthAdapter", parentPos + "----------" + pos);
                if (childClickListener != null) {
                    childClickListener.onMonthClick(parentPos, pos);
                }
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(glm);
    }

    private static OnMonthChildClickListener childClickListener;

    public interface OnMonthChildClickListener {
        void onMonthClick(int parentPos, int pos);
    }

    public void setChildClickListener(OnMonthChildClickListener childClickListener) {
        MonthAdapter.childClickListener = childClickListener;
    }
}
