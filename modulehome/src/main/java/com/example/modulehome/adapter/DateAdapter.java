package com.example.modulehome.adapter;

import android.content.Context;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.library.utils.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.modulehome.R;
import com.example.modulehome.entity.DateEntity;
import java.util.List;

import static android.content.Context.VIBRATOR_SERVICE;

/**
 * Created by DateAdapter.
 * User: Administrator
 * Name: ArouteDemo
 * functiona:
 * Date: 2019/10/9 0009
 * Time: 下午 14:11
 */
public class DateAdapter extends BaseQuickAdapter<DateEntity, BaseViewHolder> {
    private Context context;

    public DateAdapter(Context context, @Nullable List<DateEntity> data) {
        super(R.layout.item_date, data);
        this.context = context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final DateEntity de) {
        TextView mTvDate = (TextView) helper.getView(R.id.tv_date);
        TextView mTvDesc = (TextView) helper.getView(R.id.tv_desc);
        LinearLayout mLlDate = (LinearLayout) helper.getView(R.id.ll_date);
        int date = de.getDate();
        int type = de.getType();

        if (type == 1) {//留白
            mTvDate.setText("");
            mTvDesc.setText("");
            helper.itemView.setClickable(false);
        } else if (type == 0) {//日常
            mTvDate.setText(date == 77 ? "今天" : String.valueOf(de.getDate()));
            mTvDate.setTextColor(date == 77 ? ContextCompat.getColor(context, R.color.blue_85) : ContextCompat.getColor(context, R.color.black_2c));
            mTvDesc.setText(de.getDesc());
            int mod = helper.getAdapterPosition() % 7;
            if (mod == 5 || mod == 6) {
                mTvDate.setTextColor(ContextCompat.getColor(context, R.color.color_red));
                mTvDesc.setTextColor(ContextCompat.getColor(context, R.color.color_red));
            }
        } else if (type == 3) {//日常选中
            mTvDate.setText(date == 77 ? "今天" : String.valueOf(de.getDate()));
            mTvDesc.setText(de.getDesc());
            mTvDate.setTextColor(ContextCompat.getColor(context, R.color.white));
            mTvDesc.setTextColor(ContextCompat.getColor(context, R.color.white));
            mLlDate.setBackgroundResource(R.drawable.state_selected);
        } else if (type == 4) {//今天之前的日期
            helper.itemView.setClickable(false);
            mTvDate.setText(String.valueOf(de.getDate()));
            mTvDesc.setText(de.getDesc());
            mTvDate.setTextColor(ContextCompat.getColor(context, R.color.black_cc));
            mTvDesc.setTextColor(ContextCompat.getColor(context, R.color.black_cc));
        } else if (type == 5) {//中间
            mTvDate.setText(String.valueOf(de.getDate()));
            mTvDesc.setText(de.getDesc());
            mTvDate.setTextColor(ContextCompat.getColor(context, R.color.white));
            mTvDesc.setTextColor(ContextCompat.getColor(context, R.color.white));
            mLlDate.setBackgroundResource(R.drawable.state_middle_range);
        } else if (type == 6) {//终点
            mTvDate.setText(date == 77 ? "今天" : String.valueOf(de.getDate()));
            mTvDesc.setText("结束");
            mTvDate.setTextColor(ContextCompat.getColor(context, R.color.white));
            mTvDesc.setTextColor(ContextCompat.getColor(context, R.color.white));
            mLlDate.setBackgroundResource(R.drawable.state_end_range);
        } else if (type == 7) {//起点
            mTvDate.setText(date == 77 ? "今天" : String.valueOf(de.getDate()));
            mTvDesc.setText("开始");
            mTvDate.setTextColor(ContextCompat.getColor(context, R.color.white));
            mTvDesc.setTextColor(ContextCompat.getColor(context, R.color.white));
            mLlDate.setBackgroundResource(R.drawable.state_first_range);
        } else if (type == 8) {//单选
            mTvDate.setText(date == 77 ? "今天" : String.valueOf(de.getDate()));
            mTvDesc.setText(de.getDesc());
            mTvDate.setTextColor(ContextCompat.getColor(context, R.color.white));
            mTvDesc.setTextColor(ContextCompat.getColor(context, R.color.white));
            mLlDate.setBackgroundResource(R.drawable.state_selected);
        }

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.e("CalendarSelectedView --MonthAdapter", 1 + "----------" + 1);
                if (clickListener != null) {
                    if (v != null) {
                        if (de.getType() != 4) {
                            clickListener.onDateClick(de.getParentPos(), helper.getAdapterPosition());
                        }else {
                            Vibrator vibrator = (Vibrator) context.getSystemService(VIBRATOR_SERVICE);
                            vibrator.vibrate(100);
                        }
                    }
                }
            }
        });

    }


    private OnDateClickListener clickListener;

    public interface OnDateClickListener {
        void onDateClick(int parentPos, int pos);
    }

    public void setClickListener(OnDateClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public int getItemCount() {
        return 42;
    }
}
