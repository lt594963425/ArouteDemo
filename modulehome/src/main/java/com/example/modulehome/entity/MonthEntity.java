package com.example.modulehome.entity;

import java.util.List;

/**
 * @author MiTa
 * @date 2017/11/20.
 */
public class MonthEntity {

    /**
     * 2017年12月
     */
    private String title;
    private int year;
    private int month;
    private List<DateEntity> list;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public List<DateEntity> getList() {
        return list;
    }


    public void setList(List<DateEntity> list) {

        this.list = list;
    }
}
