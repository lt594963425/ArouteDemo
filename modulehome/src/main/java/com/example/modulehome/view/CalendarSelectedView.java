package com.example.modulehome.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.utils.LogUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.modulehome.R;
import com.example.modulehome.adapter.MonthAdapter;
import com.example.modulehome.adapter.TitleNumberMonthAdapter;
import com.example.modulehome.adapter.YearAdapter;
import com.example.modulehome.adapter.YearAndMonthAdapter;
import com.example.modulehome.entity.DateEntity;
import com.example.modulehome.entity.MonthEntity;
import com.example.modulehome.entity.SelectedDataBean;
import com.example.modulehome.utils.Lunar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by CalendarSelectedView.
 * User: Administrator
 * Name: ArouteDemo
 * functiona: 日期选择控件
 * Date: 2019/10/9 0009
 * Time: 下午 13:57
 */
public class CalendarSelectedView extends LinearLayout implements MonthAdapter.OnMonthChildClickListener, View.OnClickListener {

    private int nowYear, nowMonth, nowDay;

    private List<MonthEntity> monthList = new ArrayList<>();
    public RecyclerView mRvCalendar;
    public MonthAdapter mAdapter;

    private int lastDateSelect = -1, lastMonthSelect = -1;
    private List<Integer> selectMonth = new ArrayList<>();
    private List<Integer> selectDate = new ArrayList<>();
    private boolean selectComplete;
    public TextView mTvCalTitle;

    //目标项是否在最后一个可见项之后
    private boolean mShouldScroll;
    //记录目标项位置
    private int mToPosition = 0;
    public RecyclerView mRvMonthAndYearCalendar;
    public TextView mTvYearTitle;
    public LinearLayout mYearAndMonthLayout;
    public LinearLayout mDataLayout;
    public LinearLayout mYearAndMonthTabLayout;
    public YearAndMonthAdapter mYearAndMonthAdapter;
    private int month = 0;
    private List<Integer> mYearLists;
    private List<Integer> mMonthLists;
    public RecyclerView mRvYearCalendar;
    public YearAdapter mYearAdapter;
    public RadioGroup mSelectedTimeRg;
    public TextView mShow_time_tv;

    public CalendarSelectedView(Context context) {
        super(context);
        init(context);
    }

    public CalendarSelectedView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CalendarSelectedView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_calendar, this, true);
        mShow_time_tv = view.findViewById(R.id.show_time_tv);
        mDataLayout = view.findViewById(R.id.data_layout);
        mTvCalTitle = view.findViewById(R.id.tv_cal_title);

        ImageView before_month = view.findViewById(R.id.before_month);
        ImageView after_month = view.findViewById(R.id.after_month);

        mTvYearTitle = view.findViewById(R.id.tv_year_title);
        ImageView before_year = view.findViewById(R.id.before_year);
        ImageView after_year = view.findViewById(R.id.after_year);

        mSelectedTimeRg = view.findViewById(R.id.selected_time_rg);
        RadioButton selected_day_time = view.findViewById(R.id.selected_day_time);
        RadioButton selected_month_time = view.findViewById(R.id.selected_month_time);
        RadioButton selected_year_time = view.findViewById(R.id.selected_year_time);


        mSelectedTimeRg.check(R.id.selected_day_time);
        selected_day_time.setOnClickListener(this);
        selected_month_time.setOnClickListener(this);
        selected_year_time.setOnClickListener(this);

        before_month.setOnClickListener(this);
        after_year.setOnClickListener(this);

        before_year.setOnClickListener(this);
        after_month.setOnClickListener(this);

        mRvCalendar = view.findViewById(R.id.rv_calendar);
        mRvMonthAndYearCalendar = view.findViewById(R.id.rv_month_and_year_calendar);
        mRvYearCalendar = view.findViewById(R.id.rv_year_calendar);

        mYearAndMonthLayout = view.findViewById(R.id.year_and_month_layout);
        mYearAndMonthTabLayout = view.findViewById(R.id.year_and_month_tab_layout);
        mYearAndMonthLayout.setVisibility(GONE);
        initMonthRecyclerView();
        initYearhRecyclerView();
        LinearLayoutManager llm = new LinearLayoutManager(context);
        mRvCalendar.setLayoutManager(llm);
        llm.setOrientation(HORIZONTAL);
        mAdapter = new MonthAdapter(getContext(), null);
        mAdapter.setChildClickListener(this);
        mRvCalendar.setAdapter(mAdapter);
        PagerSnapHelper snapHelper = new PagerSnapHelper() {
            @Override
            public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
                mToPosition = super.findTargetSnapPosition(layoutManager, velocityX, velocityY);
                monthRVPisition = mToPosition;
                if (mYearAndMonthAdapter != null) {
                    mYearAndMonthAdapter.setSelectedId(monthRVPisition);
                }
                updateTitleText();
                return mToPosition;
            }

            @Nullable
            @Override
            public View findSnapView(RecyclerView.LayoutManager layoutManager) {
                View view = super.findSnapView(layoutManager);
                return view;
            }

        };
        snapHelper.attachToRecyclerView(mRvCalendar);
        mRvCalendar.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }
        });
        initData();
    }

    private int yearRVPisition = 0;
    private int monthRVPisition = 0;

    private void initYearhRecyclerView() {
        mRvYearCalendar.setLayoutManager(new GridLayoutManager(getContext(), 4));
        mYearAdapter = new YearAdapter(getContext(), null);
        mYearAdapter.openLoadAnimation();
        mYearAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                yearRVPisition = position;
                mSelectedTimeRg.check(R.id.selected_month_time);
                mYearAdapter.setSelectedId(position);
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //选择月份
                        mDataLayout.setVisibility(GONE);
                        mRvYearCalendar.setVisibility(GONE);
                        mYearAndMonthLayout.setVisibility(VISIBLE);
                        mYearAndMonthTabLayout.setVisibility(VISIBLE);
                        mTvYearTitle.setText(String.valueOf(mYearAdapter.getData().get(position)));
                        //装载某一年的月份数据
                        initYearData(mYearAdapter.getData().get(position));
                    }
                }, 200);

            }
        });
        mRvYearCalendar.setAdapter(mYearAdapter);
    }

    private void initMonthRecyclerView() {
        mRvMonthAndYearCalendar.setLayoutManager(new GridLayoutManager(getContext(), 4));
        mYearAndMonthAdapter = new YearAndMonthAdapter(getContext(), null);
        mYearAndMonthAdapter.openLoadAnimation();
        mYearAndMonthAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                monthRVPisition = position;
                mYearAndMonthAdapter.setSelectedId(position);
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSelectedTimeRg.check(R.id.selected_day_time);
                        mDataLayout.setVisibility(VISIBLE);
                        mYearAndMonthLayout.setVisibility(GONE);
                        mYearAndMonthTabLayout.setVisibility(GONE);
                        mRvYearCalendar.setVisibility(GONE);
                        //选择日期
                        smoothMoveToPosition(mRvCalendar, mMonthLists.get(position) - 1, false);
                        updateTitleText();
                    }
                }, 200);

            }
        });
        mRvMonthAndYearCalendar.setAdapter(mYearAndMonthAdapter);
    }

    @Override
    public void onClick(View v) {
        //选择自定义日期
        if (v.getId() == R.id.selected_day_time) {
            mDataLayout.setVisibility(VISIBLE);
            mYearAndMonthLayout.setVisibility(GONE);
            mYearAndMonthTabLayout.setVisibility(GONE);
            mRvYearCalendar.setVisibility(GONE);

        }
        //选择月份
        if (v.getId() == R.id.selected_month_time) {
            mDataLayout.setVisibility(GONE);
            mRvYearCalendar.setVisibility(GONE);
            mYearAndMonthLayout.setVisibility(VISIBLE);
            mYearAndMonthTabLayout.setVisibility(VISIBLE);
        }
        //选择年份
        if (v.getId() == R.id.selected_year_time) {
            mDataLayout.setVisibility(GONE);
            mYearAndMonthLayout.setVisibility(GONE);
            mYearAndMonthTabLayout.setVisibility(GONE);
            mRvYearCalendar.setVisibility(GONE);
            mRvYearCalendar.setVisibility(VISIBLE);
        }
        //上一年
        if (v.getId() == R.id.before_year) {
            if (mYearLists != null) {
                yearRVPisition = yearRVPisition - 1;
                if (yearRVPisition >= mYearLists.size()) {
                    yearRVPisition = mYearLists.size() - 1;
                }
                if (yearRVPisition < 0) {
                    yearRVPisition = 0;
                }
                mYearAdapter.setSelectedId(yearRVPisition);
                mTvYearTitle.setText(String.valueOf(mYearLists.get(yearRVPisition)));
                //装载某一年的月份数据
                initYearData(mYearAdapter.getData().get(yearRVPisition));
            }

        }
        //下一年
        if (v.getId() == R.id.after_year) {
            if (mYearLists != null) {
                yearRVPisition = yearRVPisition + 1;
                if (yearRVPisition >= mYearLists.size()) {
                    yearRVPisition = mYearLists.size() - 1;
                }
                if (yearRVPisition < 0) {
                    yearRVPisition = 0;
                }
                mYearAdapter.setSelectedId(yearRVPisition);
                mTvYearTitle.setText(String.valueOf(mYearLists.get(yearRVPisition)));
                //装载某一年的月份数据
                initYearData(mYearAdapter.getData().get(yearRVPisition));
            }
        }
        if (v.getId() == R.id.before_month) {
            smoothMoveToPosition(mRvCalendar, mToPosition - 1, true);
            updateTitleText();
        }
        if (v.getId() == R.id.after_month) {
            smoothMoveToPosition(mRvCalendar, mToPosition + 1, true);
            updateTitleText();
        }
    }


    private void initData() {
        monthList = new ArrayList<>();
        mMonthLists = new ArrayList<>();
        mYearLists = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        nowYear = calendar.get(Calendar.YEAR);
        nowMonth = calendar.get(Calendar.MONTH);
        nowDay = calendar.get(Calendar.DAY_OF_MONTH);
        //2019 月 9 日10
        //设置当前的年月日
        mToPosition = 0;
        month = 0;

        calendar.set(nowYear, month, 1);
        for (int i = 0; i < 12; i++) {
            mMonthLists.add(i + 1);
            List<DateEntity> deList = new ArrayList<>();
            MonthEntity monthEntity = new MonthEntity();
            //这个月的天数
            int maxDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            int empty = calendar.get(Calendar.DAY_OF_WEEK);
            //周
            empty = empty == 1 ? 6 : empty - 2;
            for (int j = 0; j < empty; j++) {
                DateEntity de = new DateEntity();
                de.setType(1);
                deList.add(de);
            }
            month = month + 1;
            //天
            for (int j = 1; j <= maxDayOfMonth; j++) {
                DateEntity de = new DateEntity();
                //判断月份 判断天数
                if (i > nowMonth) {
                    de.setType(4);
                } else {
                    if (i == nowMonth) {
                        de.setType(j > nowDay ? 4 : 0);
                    } else {
                        de.setType(0);
                    }
                }
                if (nowDay == j && month == nowMonth + 1) {
                    de.setDate(77);
                } else {
                    de.setDate(j);
                }
                de.setParentPos(i);
                de.setDesc(Lunar.getLunarDate(nowYear, month, j));
                deList.add(de);
            }

            nowYear = calendar.get(Calendar.YEAR);
            monthEntity.setTitle(nowYear + "年" + month + "月");
            monthEntity.setYear(nowYear);
            monthEntity.setMonth(month);
            monthEntity.setList(deList);
            monthList.add(monthEntity);
            calendar.add(Calendar.MONTH, 1);

        }
        mTvCalTitle.setText(monthList.get(0).getTitle());
        mAdapter.setNewData(monthList);
        for (int i = 0; i < 10; i++) {
            mYearLists.add(nowYear - i);
        }

        mYearAndMonthAdapter.setNewData(mMonthLists);
        mYearAdapter.setNewData(mYearLists);
        monthRVPisition = mToPosition;
        if (mYearAndMonthAdapter != null) {
            mYearAndMonthAdapter.setSelectedId(monthRVPisition);
        }
        yearRVPisition = mYearLists.indexOf(nowYear);
        if (mYearAdapter != null) {
            mYearAdapter.setSelectedId(yearRVPisition);
        }
        smoothMoveToPosition(mRvCalendar, mToPosition, false);
        updateTitleText();
    }

    private void initYearData(int nyear) {
        monthList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        //设置当前的年月日
        mToPosition = 0;
        month = 0;
        nowYear = calendar.get(Calendar.YEAR);
        calendar.set(nyear, month, 1);
        for (int i = 0; i < 12; i++) {
            List<DateEntity> deList = new ArrayList<>();
            MonthEntity monthEntity = new MonthEntity();
            //这个月的天数
            int maxDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            int empty = calendar.get(Calendar.DAY_OF_WEEK);
            //周
            empty = empty == 1 ? 6 : empty - 2;
            for (int j = 0; j < empty; j++) {
                DateEntity de = new DateEntity();
                de.setType(1);
                deList.add(de);
            }
            month = month + 1;
            //月份
            for (int j = 1; j <= maxDayOfMonth; j++) {
                DateEntity de = new DateEntity();
                if (i > nowMonth && nowYear == nyear) {
                    de.setType(4);
                } else {
                    if (i == nowMonth && nowYear == nyear) {
                        de.setType(j > nowDay ? 4 : 0);
                    } else {
                        de.setType(0);
                    }
                }
                if (nowDay == j && month == nowMonth + 1 && nowYear == nyear) {
                    de.setDate(77);
                } else {
                    de.setDate(j);
                }
                de.setParentPos(i);
                de.setDesc(Lunar.getLunarDate(nyear, month, j));
                deList.add(de);
            }
            monthEntity.setTitle(nyear + "年" + month + "月");
            monthEntity.setYear(nyear);
            monthEntity.setMonth(month);
            monthEntity.setList(deList);
            monthList.add(monthEntity);
            calendar.add(Calendar.MONTH, 1);

        }
        mTvCalTitle.setText(monthList.get(mToPosition).getYear() + "年");
        mAdapter.setNewData(monthList);
    }

    private void updateTitleText() {
        if (mToPosition >= monthList.size()) {
            mToPosition = monthList.size() - 1;
        }
        if (mToPosition < 0) {
            mToPosition = 0;
        }

        MonthEntity monthEntity = monthList.get(mToPosition);
        mTvCalTitle.setText(monthEntity.getTitle());


    }

    private SelectedDataBean mStartSelectedDataBean;
    private SelectedDataBean mEndSelectedDataBean;

    @Override
    public void onMonthClick(int parentPos, int pos) {
        if (parentPos != lastMonthSelect || pos != lastDateSelect) {

            //1、第二次选择；2、选择的月份相等日期比之前选择的大或者选择的月份比之前的大；3、选择未完成
            boolean haveMiddle = lastMonthSelect != -1 && ((lastMonthSelect == parentPos && pos > lastDateSelect) || (parentPos > lastMonthSelect))
                    && !selectComplete;

            if (haveMiddle) {
                //起始年月
                int startYear = monthList.get(lastMonthSelect).getYear();
                int startMonth = monthList.get(lastMonthSelect).getMonth();
                int startDay = monthList.get(lastMonthSelect).getList().get(lastDateSelect).getDate();
                mStartSelectedDataBean = new SelectedDataBean(startYear, startMonth, startDay);
                int endYear = monthList.get(parentPos).getYear();
                int endMonth = monthList.get(parentPos).getMonth();
                int endDay = monthList.get(parentPos).getList().get(pos).getDate();
                mEndSelectedDataBean = new SelectedDataBean(endYear, endMonth, endDay);

                monthList.get(parentPos).getList().get(pos).setType(6);
                selectDate.add(1);
                monthList.get(lastMonthSelect).getList().get(lastDateSelect).setType(7);
                selectDate.add(1);
                Log.d("日期", "选择的年份：" + monthList.get(parentPos).getYear());
                Log.d("日期", "选择的月份：" + monthList.get(parentPos).getMonth());
                List<DateEntity> lists = monthList.get(parentPos).getList();

                int monthLen = parentPos - lastMonthSelect;
                List<DateEntity> list;
                int dateLen;
                if (monthLen == 0) {
                    dateLen = pos - lastDateSelect;
                    for (int i = 1; i < dateLen; i++) {
                        monthList.get(parentPos).getList().get(i + lastDateSelect).setType(5);
                        selectDate.add(1);
                    }
                    mAdapter.notifyItemChanged(lastMonthSelect);
                    //选择了这个月
                    selectMonth.add(parentPos);
                } else {
                    //第一个月
                    int lastMonthSize = monthList.get(lastMonthSelect).getList().size();
                    dateLen = lastMonthSize - lastDateSelect;
                    for (int i = 1; i < dateLen; i++) {
                        monthList.get(lastMonthSelect).getList().get(i + lastDateSelect).setType(5);
                        selectDate.add(1);
                    }
                    mAdapter.notifyItemChanged(lastMonthSelect);
                    //选择了这个月
                    selectMonth.add(lastMonthSelect);

                    //中间月份
                    int month;
                    int middleMonthLen = parentPos - lastMonthSelect;
                    for (int i = 1; i < middleMonthLen; i++) {
                        month = lastMonthSelect + i;
                        list = monthList.get(month).getList();
                        dateLen = list.size();
                        for (int j = 0; j < dateLen; j++) {
                            if (list.get(j).getType() != 1) {
                                list.get(j).setType(5);
                                selectDate.add(1);
                            }
                        }
                        mAdapter.notifyItemChanged(month);
                        //选择了这个月
                        selectMonth.add(month);
                    }

                    //最后那个月
                    dateLen = pos;
                    for (int i = 0; i < dateLen; i++) {
                        DateEntity de = monthList.get(parentPos).getList().get(i);
                        if (de.getType() != 1) {
                            de.setType(5);
                            selectDate.add(1);
                        }
                    }
                    mAdapter.notifyItemChanged(parentPos);
                    //选择了这个月
                    selectMonth.add(parentPos);
                }
                selectComplete = true;
                lastMonthSelect = -1;
                lastDateSelect = -1;
            } else {
                selectDate.clear();
                //清除已选
                if (selectComplete) {
                    List<DateEntity> list;
                    DateEntity de;
                    int len = selectMonth.size();
                    for (int i = 0; i < len; i++) {
                        list = monthList.get(selectMonth.get(i)).getList();
                        int size = list.size();
                        for (int j = 0; j < size; j++) {
                            de = list.get(j);
                            if (de.getType() == 5 || de.getType() == 6 || de.getType() == 7) {
                                de.setType(0);
                            }
                        }
                        mAdapter.notifyItemChanged(selectMonth.get(i));
                    }
                    selectMonth.clear();
                }

                monthList.get(parentPos).getList().get(pos).setType(3);
                mAdapter.notifyItemChanged(parentPos);
                if (lastDateSelect != -1) {
                    monthList.get(lastMonthSelect).getList().get(lastDateSelect).setType(0);
                    mAdapter.notifyItemChanged(lastMonthSelect);
                }
                lastMonthSelect = parentPos;
                lastDateSelect = pos;
                selectComplete = false;
            }
        }
        if (mStartSelectedDataBean != null && mEndSelectedDataBean != null) {
            LogUtil.e("日期", "起始：" + mStartSelectedDataBean.toString() + "\n 结束：" + mEndSelectedDataBean.toString());
            mShow_time_tv.setText("已选择天数：" + selectDate.size() + "\n起始：" + mStartSelectedDataBean.toString() + "\n结束：" + mEndSelectedDataBean.toString());
        }
        //singleSelecte(parentPos, pos);
    }

    /**
     * 单选
     *
     * @param parentPos
     * @param pos
     */
    private void singleSelecte(int parentPos, int pos) {
        if (parentPos == lastMonthSelect && pos == lastDateSelect) {
            return;
        }
        monthList.get(parentPos).getList().get(pos).setType(8);
        mAdapter.notifyItemChanged(parentPos);
        if (lastDateSelect != -1) {
            monthList.get(lastMonthSelect).getList().get(lastDateSelect).setType(0);
            mAdapter.notifyItemChanged(lastMonthSelect);
        }
        lastMonthSelect = parentPos;
        lastDateSelect = pos;
    }


    /**
     * 滑动到指定位置
     */
    private void smoothMoveToPosition(RecyclerView mRecyclerView, final int position, boolean isSooth) {
        // 第一个可见位置
        int firstItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(0));
        // 最后一个可见位置
        int lastItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(mRecyclerView.getChildCount() - 1));
        if (position < firstItem && position >= 0) {
            // 第一种可能:跳转位置在第一个可见位置之前，使用smoothScrollToPosition
            if (isSooth) {
                mRecyclerView.smoothScrollToPosition(position);
            } else {
                mRecyclerView.scrollToPosition(position);
            }
            mToPosition = position;
        } else if (position <= lastItem) {
            // 第二种可能:跳转位置在第一个可见位置之后，最后一个可见项之前
            int movePosition = position - firstItem;
            if (movePosition >= 0 && movePosition < mRecyclerView.getChildCount()) {
                int top = mRecyclerView.getChildAt(movePosition).getLeft();
                // smoothScrollToPosition 不会有效果，此时调用smoothScrollBy来滑动到指定位置
                if (isSooth) {
                    mRecyclerView.smoothScrollBy(top, 0);
                } else {
                    mRecyclerView.scrollBy(top, 0);
                }

            }
            mToPosition = position;
        } else {
            // 第三种可能:跳转位置在最后可见项之后，则先调用smoothScrollToPosition将要跳转的位置滚动到可见位置
            // 再通过onScrollStateChanged控制再次调用smoothMoveToPosition，执行上一个判断中的方法
            if (isSooth) {
                mRecyclerView.smoothScrollToPosition(position);
            } else {
                mRecyclerView.scrollToPosition(position);
            }
            mToPosition = position;
            mShouldScroll = true;
        }

        monthRVPisition = mToPosition;
        if (mYearAndMonthAdapter != null) {
            mYearAndMonthAdapter.setSelectedId(monthRVPisition);
        }
    }


}
