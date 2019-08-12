package com.example.modulebase.base.base;

import com.android.library.utils.NetWorkUtils;
import com.android.utils.NetworkUtils;
import com.example.modulebase.base.TInterceptor;
import com.example.modulebase.data.constant.NetUrls;
import com.example.modulebase.data.source.helper.SPManager;
import com.example.modulebase.utils.RxObservableTransformer;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.SPCookieStore;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求类
 * Created by caoyu on 2017/11/25/025.
 */

public class RetrofitClient {
    /**
     * 请求失败重连次数
     */
    private String token = "";
    private int RETRY_COUNT = 0;
    private static RetrofitClient mInstance;
    private RetrofitApi mRetrofitApi;
    public Retrofit mRetrofit;
    public final OkHttpClient mOkHttpClient;
    public static final String CACHE_NAME = App.getInstance().getPackageName();

    public static RetrofitClient getRetrofit() {
        if (mInstance == null) {
            synchronized (Retrofit.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitClient();
                }
            }
        }
        return mInstance;
    }

    private RetrofitClient() {
        setToken(SPManager.getString(SPManager.SP_MAIN_FLAG, "token", token));
        /**
         * 设置缓存
         */

        File cacheFile = new File(App.getInstance().getExternalCacheDir(), CACHE_NAME);
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetworkUtils.isNetworkConnected(App.getContext())) {//无网络
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (NetworkUtils.isNetworkConnected(App.getContext())) {
                    int maxAge = 0;  //有网络不会使用缓存数据,直接获取网络数据
                    // 有网络时 设置缓存超时时间0个小时
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)//固定写法
                            .removeHeader(CACHE_NAME)// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                            .build();
                } else {
                    // 无网络时，使用缓存设置超时为4周
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)//固定写法
                            .removeHeader(CACHE_NAME)
                            .build();
                }
                return response;
            }
        };
        /**
         * 设置头信息
         */
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request.Builder requestBuilder = originalRequest.newBuilder()
                        .header("token", getToken())
                        .header("client", "android")
                        .method(originalRequest.method(), originalRequest.body());
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };
        // 初始化 OkHttpClient
        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)//连接超时
                .readTimeout(10, TimeUnit.SECONDS)//读取超时
                .writeTimeout(10, TimeUnit.SECONDS)
                .cache(cache)
                .addInterceptor(cacheInterceptor)//缓存
                .cookieJar(new CookieJarImpl(new SPCookieStore(App.getContext()))) //使用sp保持cookie，如果cookie不过期，则一直有效
                .addInterceptor(new TInterceptor())// 加入日志拦截
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(NetUrls.BASE_IP)
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())////添加Gson解析
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//  添加 rxjava 支持
                .build();
        mRetrofitApi = mRetrofit.create(RetrofitApi.class);
    }

    public RetrofitApi getRetrofitApi() {
        return mRetrofitApi;
    }

    private RetrofitClient changeBaseUrl(String baseUrl) {
        mRetrofit = new Retrofit.Builder()
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();
        mRetrofitApi = mRetrofit.create(RetrofitApi.class);
        return mInstance;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
        SPManager.saveString(SPManager.SP_MAIN_FLAG, "token", token);
    }

}