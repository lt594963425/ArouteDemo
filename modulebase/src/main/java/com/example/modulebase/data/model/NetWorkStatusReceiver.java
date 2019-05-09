package com.example.modulebase.data.model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.android.library.NetworkType;
import com.android.library.utils.NetWorkUtils;
import com.android.utils.LogUtil;
import com.android.utils.ToastUtils;
import org.greenrobot.eventbus.EventBus;


/**
 * 监听网络状态
 * Created by LiuTao on 2017/7/28 0028.
 */

public class NetWorkStatusReceiver extends BroadcastReceiver {


    public NetChangeListener mNetChangeListener;

    public void setNetChangeListener(NetChangeListener netChangeListener) {
        mNetChangeListener = netChangeListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            NetworkType netWorkState = NetWorkUtils.getNetworkType();
            LogUtil.e("网络状态变化:", netWorkState + "");
            if (mNetChangeListener != null) {
                mNetChangeListener.onNetChange(netWorkState);
            }
            if (netWorkState == NetworkType.NETWORK_UNKNOWN) {
                ToastUtils.showToast("当前网络不可用，请检查网络设置");
            }

        }
    }

    public interface NetChangeListener {
        void onNetChange(NetworkType type);
    }
}