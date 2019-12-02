package com.example.modulehome;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.tao.xiaoyuanyuan.aidl.IBackGroundAidl;

/**
 * Created by LiuTao.
 * Date: 2019/11/1 0001
 * Time: 下午 17:50
 * functiona:
 */
public class IMServer extends Service {


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
