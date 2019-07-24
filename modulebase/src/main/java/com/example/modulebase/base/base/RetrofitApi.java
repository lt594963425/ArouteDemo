package com.example.modulebase.base.base;

import com.example.modulebase.data.entity.LPersonUserBean;
import com.example.modulebase.data.entity.Translation;
import com.example.modulebase.data.entity.Translation1;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by ${LiuTao}.
 * User: Administrator
 * Name: ArouteDemo
 * functiona:
 * Date: 2019/7/22 0022
 * Time: 下午 16:58
 */
public interface RetrofitApi {

    /**
     * 英文翻译成中文
     *
     * @return
     */
    //ajax.php?a=fy&f=auto&t=auto&w=hello%20world
    @GET("ajax.php")
    Observable<Translation> getResultCall(@QueryMap Map<String, Object> map);
    // 注解里传入 网络请求 的部分URL地址
    // Retrofit把网络请求的URL分成了两部分：一部分放在Retrofit对象里，另一部分放在网络请求接口里
    // 如果接口里的url是一个完整的网址，那么放在Retrofit对象里的URL可以忽略
    // getCall()是接受网络请求数据的方法

    /**
     * 中文翻译成英文
     */
    // URL实例
    //http://fanyi.youdao.com/translate?doctype=json&jsonversion=&type=&keyfrom=&model=&mid=&imei=&vendor=&screen=&ssid=&network=&abtest=
    @POST("translate?doctype=json&jsonversion=&type=&keyfrom=&model=&mid=&imei=&vendor=&screen=&ssid=&network=&abtest=")
    @FormUrlEncoded
    Observable<Translation1> getResultCall2(@Field("i") String targetSentence);

    @POST("base/Login/pwdLogin")
    Observable<ResponseBody> loginResult(@QueryMap Map<String, Object> map);

    @POST("base/common/getBaseData")
    Observable<Response<String>> getUserData();

    @POST("base/common/logOut")
    Observable<Response<String>> exitLogin();
}
