package com.example.modulebase.data.iservice;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.template.IProvider;
import com.example.modulebase.aroute.UserAroutePath;
import com.example.modulebase.data.entity.User;

/**
 * Created by ${LiuTao}.
 * User: Administrator
 * Name: ArouteDemo
 * functiona:
 * Date: 2019/5/9 0009
 * Time: 下午 16:44
 */

public interface IUserInfoService extends IProvider {
    User getUser();
}
