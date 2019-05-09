package com.example.modulebase.base;

import android.content.Intent;
import android.text.TextUtils;

import com.android.utils.LogUtil;
import com.lzy.okgo.utils.IOUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;

/**
 * $activityName
 *
 * @author ${LiuTao}
 * @date 2018/3/17/017
 */

public class NetInterceptor implements Interceptor {
    public static final String TAG = "NetInterceptor";
    private String tag;
    private boolean showResponse;
    private static final Charset UTF8 = Charset.forName("UTF-8");

    public NetInterceptor(String tag, boolean showResponse) {
        if (TextUtils.isEmpty(tag)) {
            tag = TAG;
        }
        this.showResponse = showResponse;
        this.tag = tag;
    }

    public NetInterceptor(String tag) {
        this(tag, true);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        logForRequest(request);
        Response response = chain.proceed(request);
        return logForResponse(response);
    }

    private static Charset getCharset(MediaType contentType) {
        Charset charset = contentType != null ? contentType.charset(UTF8) : UTF8;
        if (charset == null) charset = UTF8;
        return charset;
    }

    /**
     * 响应结果
     *
     * @param response
     * @return
     */
    private Response logForResponse(Response response) {
        try {
            //===>response log
            LogUtil.e(tag, "\nstart==========response'log============");
            Response.Builder builder = response.newBuilder();
            Response clone = builder.build();

            LogUtil.e(tag, "\n|url : " + clone.request().url());
            LogUtil.e(tag, "\n|code : " + clone.code());
            LogUtil.e(tag, "\n|protocol : " + clone.protocol());
            if (!TextUtils.isEmpty(clone.message())) {
                LogUtil.e(tag, "\n|message : " + clone.message());
            }
            if (showResponse) {
                ResponseBody responseBody = clone.body();
                if (HttpHeaders.hasBody(clone)) {
                    if (responseBody != null) {
                        MediaType contentType = responseBody.contentType();
                        if (contentType != null) {
                            //todo
                            byte[] bytes = IOUtils.toByteArray(responseBody.byteStream());
                            String body = new String(bytes, getCharset(contentType));
                            LogUtil.e(tag, "\n|responseBody's contentType : " + contentType.toString());
                            if (isText(contentType)) {
                                LogUtil.e(tag, "\n|responseBody's content : " + body);
                                checkToken(body);
                                responseBody = ResponseBody.create(responseBody.contentType(), bytes);
                                return response.newBuilder().body(responseBody).build();
                            } else {
                                LogUtil.e(tag, "\n|responseBody's content : " + " maybe [file part] , too large too print , ignored!");
                            }
                        }

                    }
                }
            }

            LogUtil.e(tag, "\nend===========response'log=============");
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e(tag, "\nend===========拦截数据异常=============");
        }

        return response;
    }

    private void checkToken(String resp) throws JSONException {

        JSONObject jsonObject = new JSONObject(resp);
        String code = jsonObject.optString("code");
        String msg_customer = jsonObject.optString("msg_customer");
        //{"code":0,"msg_customer":"用户无权操作或尚未登陆","msg_programmer":"用户无权操作或尚未登陆"}
        if ("2".equals(code)) {
            reLogin();
        }
    }

    private void reLogin() {


    }



    /**
     * 请求
     *
     * @param request
     */
    private void logForRequest(Request request) {
        try {
            String url = request.url().toString();
            Headers headers = request.headers();
            //token,client,
            LogUtil.e(tag, "\nstart==========request'log=============");
            LogUtil.e(tag, "\n|method : " + request.method());
            LogUtil.e(tag, "\n|url : " + url);
            if (headers != null && headers.size() > 0) {
                LogUtil.e(tag, "\n|headers : " + headers.toString());
            }

            RequestBody requestBody = request.body();
            if (requestBody != null) {
                MediaType mediaType = requestBody.contentType();
                if (mediaType != null) {
                    LogUtil.e(tag, "\n|requestBody's contentType : " + mediaType.toString());
                    if (isText(mediaType)) {
                        LogUtil.e(tag, "\n|requestBody's content : " + bodyToString(request));
                    } else {
                        LogUtil.e(tag, "\n|requestBody's content : " + " maybe [file part] , too large too print , ignored!");
                    }
                }
            }
            LogUtil.e(tag, "\nend==============request'log===========");
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e(tag, "\nend===========拦截数据异常=============");
        }
    }

    private boolean isText(MediaType mediaType) {
        if (mediaType.type() != null && mediaType.type().equals("text")) {
            return true;
        }
        if (mediaType.subtype() != null) {
            if (mediaType.subtype().equals("json") ||
                    mediaType.subtype().equals("xml") ||
                    mediaType.subtype().equals("html") ||
                    mediaType.subtype().equals("webviewhtml")
            ) {
                return true;
            }
        }
        return false;
    }

    private String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "显示请求体时出错.";
        }
    }
}
