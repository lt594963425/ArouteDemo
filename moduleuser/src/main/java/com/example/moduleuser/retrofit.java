package com.example.moduleuser;
/**
 * Created by ${LiuTao}.
 * User: Administrator
 * Name: ArouteDemo
 * functiona:
 * Date: 2019/8/13 0013
 * Time: 下午 14:34
 */
public class retrofit {
//    final TextView tv_text = view.findViewById(R.id.tv_text);
//    Button user_kml_btn = view.findViewById(R.id.user_kml_btn);
//    Button userJinshanBtn = view.findViewById(R.id.user_jinshan_btn);
//    Button userjinshan2btn = view.findViewById(R.id.user_jinshan2_btn);
//    final EditText tokenInput = view.findViewById(R.id.token_input);
//    final EditText contentInput = view.findViewById(R.id.content_input);
//
//    //***************************************************************
//    final Button loginBtn = view.findViewById(R.id.login_btn);
//    final Button exitLoginBtn = view.findViewById(R.id.exit_login_btn);
//    final Button getUserDataBtn = view.findViewById(R.id.getUser_data_btn);
//        getUserDataBtn.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//
//            //获取个人数据
//            RetrofitClient.getRetrofit().getRetrofitApi()
//                    .getUserData()
//                    .compose(RxObservableTransformer.<Response<String>>transformer())
//                    .subscribe(new Observer<Response<String>>() {
//                        @Override
//                        public void onSubscribe(Disposable d) {
//
//                        }
//
//                        @Override
//                        public void onNext(Response<String> stringResponse) {
//                            LogUtil.e("结果 个人信息", stringResponse.body());
//
//
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//
//                        }
//
//                        @Override
//                        public void onComplete() {
//
//                        }
//                    });
//
//        }
//    });
//        loginBtn.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            HashMap<String, Object> params = new HashMap<>();
//            params.put("cellphone", "15974255013");
//            params.put("password", "88888888");
//            params.put("type", 2);
//
//            //登录
//            RetrofitClient.getRetrofit().getRetrofitApi()
//                    .loginResult(params)
//                    .compose(RxObservableTransformer.<ResponseBody>transformer())
//                    .subscribe(new Observer<ResponseBody>() {
//                        @Override
//                        public void onSubscribe(Disposable d) {
//
//                        }
//
//                        @Override
//                        public void onNext(ResponseBody stringResponse) {
//
//                            String bodyString = null;
//                            try {
//                                bodyString = stringResponse.string();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                            LogUtil.e("结果", bodyString);
//                            try {
//                                JSONObject jsonObject1 = new JSONObject(bodyString);
//                                String code = jsonObject1.optString("code");
//                                String msgCustomer = jsonObject1.optString("msg_customer");
//                                if ("1".equals(code)) {
//                                    JSONObject dataJSONObject = jsonObject1.optJSONObject("data");
//                                    LPersonUserBean userBean = FastJsonUtils.toBean(dataJSONObject.toString(), LPersonUserBean.class);
//                                    ToastUtils.showToast("登录成功！");
//                                    RetrofitClient.getRetrofit().setToken(userBean.getToken());
//                                    //todo 保存
//                                }
//                                if ("0".equals(code)) {
//                                    ToastUtils.showToast("登陆失败，" + msgCustomer);
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//
//
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//
//                        }
//
//                        @Override
//                        public void onComplete() {
//
//                        }
//                    });
//
//        }
//    });
//        exitLoginBtn.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            //退出登录
//
//            RetrofitClient.getRetrofit().getRetrofitApi()
//                    .exitLogin()
//                    .compose(RxObservableTransformer.<Response<String>>transformer())
//                    .subscribe(new Observer<Response<String>>() {
//                        @Override
//                        public void onSubscribe(Disposable d) {
//
//                        }
//
//                        @Override
//                        public void onNext(Response<String> stringResponse) {
//
//                            String bodyString = stringResponse.body();
//                            LogUtil.e("结果 退出登录", bodyString);
//
//
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//
//                        }
//
//                        @Override
//                        public void onComplete() {
//
//                        }
//                    });
//
//        }
//    });
//
//
//    Button tokenSet = view.findViewById(R.id.token_set);
//
//        tokenSet.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            if (TextUtils.isEmpty(tokenInput.getText())) {
//                return;
//            }
//            RetrofitClient.getRetrofit().setToken(tokenInput.getText().toString());
//        }
//    });
//
//        user_kml_btn.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            ToastUtils.showToast("点击");
//            String logPath = FileUtils.getDir("Crash");
//            LogUtil.e("路径", logPath);
//        }
//    });
//        userJinshanBtn.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//
//            Map<String, Object> h = new HashMap<>();
//            //?a=fy&f=auto&t=auto&w=hello%20world
//            h.put("a", "fy");
//            h.put("f", "auto");
//            h.put("t", "auto");
//            if (!TextUtils.isEmpty(contentInput.getText())) {
//                h.put("w", contentInput.getText().toString());
//            } else {
//                h.put("w", "hello world");
//            }
//            RetrofitClient.getRetrofit()
//                    .getRetrofitApi()
//                    .getResultCall(h)
//                    .compose(RxObservableTransformer.<Translation>transformer())
//                    .subscribe(new Observer<Translation>() {
//                        @Override
//                        public void onSubscribe(Disposable d) {
//                            addDisposable(d);
//                        }
//
//                        @Override
//                        public void onNext(Translation translation) {
//                            LogUtil.e("数据 1", translation.toString() + "线程：" + Thread.currentThread().getName());
//                            tv_text.setText(translation.toString());
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//
//                        }
//
//                        @Override
//                        public void onComplete() {
//
//                        }
//                    });
//
//        }
//    });
//        userjinshan2btn.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//
//            RetrofitClient.getRetrofit()
//                    .getRetrofitApi()
//                    .getResultCall2("I love you")
//                    .compose(RxObservableTransformer.<Translation1>transformer())
//                    .retry(5)
//                    .subscribe(new Observer<Translation1>() {
//                        @Override
//                        public void onSubscribe(Disposable d) {
//                            addDisposable(d);
//                        }
//
//                        @Override
//                        public void onNext(Translation1 translation1) {
//                            try {
//
//                                LogUtil.e("数据", translation1.toString());
//                                tv_text.setText(translation1.getTranslateResult().get(0).toString());
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//                        @Override
//                        public void onError(Throwable t) {
//                            ToastUtils.showToast("请求失败");
//                            System.out.println(t.getMessage());
//                        }
//
//                        @Override
//                        public void onComplete() {
//
//                        }
//                    });
//
//        }
//    });
}
