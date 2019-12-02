package com.example.modulehome.entity;

/**
 * Created by LiuTao on 2019/11/18 0018 上午 11:24
 * functiona:
 */
public class DateRecodBean {

    String title ;
    String time;
    String count;
    String timeLong;

    public DateRecodBean(String title, String time, String count, String timeLong) {
        this.title = title;
        this.time = time;
        this.count = count;
        this.timeLong = timeLong;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getTimeLong() {
        return timeLong;
    }

    public void setTimeLong(String timeLong) {
        this.timeLong = timeLong;
    }
}
