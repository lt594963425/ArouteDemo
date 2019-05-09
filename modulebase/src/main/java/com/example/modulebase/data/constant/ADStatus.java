package com.example.modulebase.data.constant;

/**
 * $activityName
 * 广告发布状态
 *
 * @author LiuTao
 * @date 2019/3/16/016
 */
public  enum ADStatus {
    UN_KNOWN(0, "草稿"),
    AD_IN_RELEASE(1, "发布中"),
    AD_CHECK_PENDING(2, "待审核"),
    AD_WITHDRAWN(3, "已撤回"),
    AD_REJECTED(4, "已驳回"),
    AD_OFF_SHELF(5, "已下架"),
    AD_COMPLETED(6, "已完成"),
    AD_TOBE_ANNOUNCED(7, "待发布"),
    AD_DELETED(8, "已删除");

    private int status;
    private String desc;


    ADStatus(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static ADStatus getStatus(int status) {
        for (ADStatus taskStatus : values()) {
            if (status == taskStatus.status) {
                return taskStatus;
            }
        }
        return UN_KNOWN;
    }

}
