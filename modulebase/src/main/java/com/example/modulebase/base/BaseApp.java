package com.example.modulebase.base;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.android.utils.LogUtil;
import com.example.modulebase.BuildConfig;
import com.example.modulebase.data.source.helper.DBManager;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.SPCookieStore;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.OkHttpClient;

/**
 * Created by ${LiuTao}.
 * User: Administrator
 * Name: ArouteDemo2
 * functiona:
 * Date: 2019/5/8 0008
 * Time: 下午 17:05
 */
public class BaseApp implements IComponentApplication {


    @Override
    public void onCreate(Application application) {
        DBManager.getInstance().onCreate();
        initHttpOkgo( application);
        initRouter(application);
        LogUtil.d("baseApp","------------------------初始化-----------------------------------");
    }


    private void initRouter(Application myApplication) {
        if (BuildConfig.DEBUG) {
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(myApplication);
    }

    @Override
    public Application getAppliaction() {
        return null;
    }

    private void initHttpOkgo(Application application) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        //log颜色级别，决定了log在控制台显示的颜色
        loggingInterceptor.setColorLevel(Level.INFO);
        //初始化网络框架
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(loggingInterceptor);
        //网络拦截器
        builder.addNetworkInterceptor(new NetInterceptor("网络拦截器", true));
        //全局的读取超时时间
        builder.readTimeout(1000, TimeUnit.SECONDS);
        //全局的写入超时时间
        builder.writeTimeout(1000, TimeUnit.SECONDS);
        //全局的连接超时时间
        builder.connectTimeout(7, TimeUnit.SECONDS);
        //使用sp保持cookie，如果cookie不过期，则一直有效
        builder.cookieJar(new CookieJarImpl(new SPCookieStore(application)));
        OkGo.getInstance().init(application)//必须调用初始化
                .setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置将使用默认的
                .setCacheMode(CacheMode.NO_CACHE)    //全局统一缓存模式，默认不使用缓存，可以不传
                .setRetryCount(0)
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE);   //全局统一缓存时间，默认永不过期，可以不传
    }
}
