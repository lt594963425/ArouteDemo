package com.example.modulebase.base.base;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.multidex.MultiDex;

import com.android.utils.LogUtil;
import com.example.modulebase.base.IComponentApplication;
import com.example.modulebase.data.source.helper.DBManager;
import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;


/**
 * $activityName
 *
 * @author LiuTao
 * @date 2019/2/20/020
 */


public class App extends TinkerApplication {
    private static App INSTANCE;


    public App() {
        super(ShareConstants.TINKER_ENABLE_ALL, "com.example.modulebase.base.base.AppLike",
                "com.tencent.tinker.loader.TinkerLoader", false);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        DBManager.getInstance().onCreate();
        init();
    }
    protected void init() {
        LogUtil.d("App","------------------------初始化-----------------------------------");
        //  CrashHandler.getInstance().init(getApplicationContext());
        modulesApplicationInit();
        initNotificationManager();
    }

    public static App getInstance() {
        return INSTANCE;
    }


    public static Context getContext() {
        return INSTANCE.getApplicationContext();
    }



    private void initNotificationManager() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "updateApp";
            String channelName = "升级程序";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            createNotificationChannel(channelId, channelName, importance);
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        NotificationManager notificationManager = (NotificationManager) getSystemService(
                NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    }
    private void modulesApplicationInit(){
        for (String moduleImpl : ModuleConfig.MODULESLIST){
            try {
                Class<?> clazz = Class.forName(moduleImpl);
                Object obj = clazz.newInstance();
                if (obj instanceof IComponentApplication){
                    ((IComponentApplication) obj).onCreate(App.getInstance());
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

}
