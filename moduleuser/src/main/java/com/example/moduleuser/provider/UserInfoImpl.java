package com.example.moduleuser.provider;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.modulebase.aroute.UserAroutePath;
import com.example.modulebase.data.entity.User;
import com.example.modulebase.data.iservice.IUserInfoService;


/**
 * Created by ${LiuTao}.
 * User: Administrator
 * Name: ArouteDemo
 * functiona:
 * Date: 2019/5/9 0009
 * Time: 下午 16:47
 */

@Route(path = UserAroutePath.USER_INFO)
public class UserInfoImpl implements IUserInfoService {


    @Override
    public User getUser() {
        return new User();
    }

    @Override
    public void init(Context context) {

    }
}
