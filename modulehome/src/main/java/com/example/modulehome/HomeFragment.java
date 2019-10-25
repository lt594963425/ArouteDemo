package com.example.modulehome;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.PointF;
import android.opengl.Matrix;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.android.library.utils.ToastUtils;
import com.android.utils.AndroidUtil;
import com.android.utils.FastJsonUtils;
import com.android.utils.Hmacmd5Utils;
import com.android.utils.LogUtil;
import com.android.utils.UIUtils;
import com.android.view.TagLayout;
import com.example.modulebase.aroute.HomeAroutePath;
import com.example.modulebase.aroute.UserAroutePath;
import com.example.modulebase.base.AppManager;
import com.example.modulebase.base.BaseFragment;
import com.example.modulebase.base.base.App;
import com.example.modulebase.data.constant.NetUrls;
import com.example.modulebase.data.entity.User;
import com.example.modulehome.entity.DJISetverAreoist;
import com.example.modulehome.ui.GraffitiActivity;
import com.example.modulehome.ui.MapFogActivity;
import com.example.modulehome.ui.V7SupportActivity;
import com.example.modulehome.view.ClipRectDemo;
import com.example.modulehome.view.LoveTypeEvaluator;
import com.example.modulehome.view.QQStepView;
import com.example.modulehome.view.ViewTypeEvaluator;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okrx2.adapter.ObservableResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ${LiuTao}.
 * User: Administrator
 * Name: ArouteDemo2
 * functiona:
 * Date: 2019/5/8 0008
 * Time: 下午 17:47
 */
@Route(path = HomeAroutePath.HOME_FRAGMENT)
public class HomeFragment extends BaseFragment {
    QQStepView qqstepView;
    ClipRectDemo cliprectView;
    public TextView mTextView;
    public TextView mTvList;
    private final float[] modelMatrix = new float[16];  //获得一个model矩阵
    private float rotateAngle;  //获得一个model矩阵
    public ValueAnimator mValueAnimator;
    public Button mV7_layout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mTextView = view.findViewById(R.id.tv_text);
        ImageView imageIv = view.findViewById(R.id.image_iv);
        Button preImaageIv = view.findViewById(R.id.pre_imaage_iv);
        final Button tagLayout = view.findViewById(R.id.tag_Layout);
         Button graff_btn = view.findViewById(R.id.graff_btn);

        Button start_btn = view.findViewById(R.id.start_btn);
        Button end_btn = view.findViewById(R.id.end_btn);
        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PointF point1 = new PointF(0, 0);
                PointF point2 = new PointF(300, 300);
                startAnimal(tagLayout, point1, point2);
                mValueAnimator.start();
            }
        });
        end_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PointF point2 = new PointF(100, 50);
                PointF point1 = new PointF(300, 300);
                startAnimal(tagLayout, point1, point2);
                mValueAnimator.cancel();
            }
        });
        graff_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), GraffitiActivity.class));
            }
        });
        mTvList = view.findViewById(R.id.tv_list);
        Button queryListenerListBtn = view.findViewById(R.id.query_listener_list_btn);
        Button queryFlyuavListBtn = view.findViewById(R.id.query_flyuav_list_btn);
        Button queryFlyHistoryListBtn = view.findViewById(R.id.query_fly_history_list_btn);
        mV7_layout = view.findViewById(R.id.V7_Layout);
        Button  map_btn = view.findViewById(R.id.map_btn);
        map_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), MapFogActivity.class));
            }
        });
        mV7_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), V7SupportActivity.class));
            }
        });
        cliprectView = view.findViewById(R.id.cliprect_view);
        mTextView.setText("主页\n" + AndroidUtil.getLocalVersionName());

        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Aroute();
            }
        });

        qqstepView = view.findViewById(R.id.qqstep_view);
        qqstepView.setMaxStep(6000);
        qqstepView.setCurrentStep((int) 0);

//        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 5500);
//        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                Float stepValue = (Float) animation.getAnimatedValue();
//                LogUtil.e("动画", stepValue + "");
//                float step = stepValue;
//                qqstepView.setCurrentStep((int) step);
//            }
//
//        });
//        valueAnimator.setDuration(4000);
//        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
//        valueAnimator.start();
        ValueAnimator valueAnimato2 = ValueAnimator.ofFloat(0, 360);

        valueAnimato2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float stepValue = (Float) animation.getAnimatedValue();

                double rad = 2 * Math.PI / 360;  //1度对应的弧度
                double step = (float) stepValue * rad;
                cliprectView.setCurrentRotationAngle((float) step);
            }

        });
        valueAnimato2.setRepeatCount(-1);
        valueAnimato2.setDuration(2000);
        valueAnimato2.setInterpolator(new LinearInterpolator());
        valueAnimato2.start();
        initTextViewAnima();
        queryListenerListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getQueryListenerListList();
            }
        });
        queryFlyuavListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFlyListenerListList();
            }
        });

        queryFlyHistoryListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFlyListenerHistoryListList();
            }
        });

        preImaageIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(HomeAroutePath.HOME_PatImageView).navigation();
            }
        });
        tagLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(HomeAroutePath.HOME_TagLayout).navigation();
            }
        });
//        Matrix.setIdentityM(modelMatrix, 0);   //利用android自带的Matrix单位化矩阵
//        Matrix.translateM(modelMatrix, 0, 1.1f, -1.2f, 0);
//        Matrix.rotateM(modelMatrix, 0, rotateAngle, 1f, 0f, 0f);
//        Matrix.translateM(modelMatrix, 0, -1.1f, 1.2f, 0);
//        mTextView.getMatrix().mapVectors();

        startRun2SecondQueryUavUpdateToMap();

        return view;
    }

    public void startAnimal(final View view, PointF point1, PointF point2) {


        ViewTypeEvaluator evaluator = new ViewTypeEvaluator(point1, point2);
        //会调用自定义路径属性动画evaluate方法
        ValueAnimator objectAnimator = ObjectAnimator.ofObject(evaluator, point1, point2);
        objectAnimator.setDuration(500);
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                view.getLayoutParams().width = (int) pointF.x;
                view.getLayoutParams().height = (int) pointF.y;
                view.requestLayout();
            }
        });
        objectAnimator.start();
    }

    public void startRun2SecondQueryUavUpdateToMap() {
        mValueAnimator = ValueAnimator.ofInt(0, 20);
        mValueAnimator.setDuration(2000);
        mValueAnimator.setRepeatCount(-1);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                long time = System.currentTimeMillis();
                //LogUtil.e("定时动画", "----------onAnimationStart----------" + time);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                long time = System.currentTimeMillis();
                //LogUtil.e("定时动画", "----------onAnimationEnd----------" + time);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                long time = System.currentTimeMillis();
                //LogUtil.e("定时动画", "----------onAnimationCancel----------" + time);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                long time = System.currentTimeMillis();
                //LogUtil.e("定时动画", "----------onAnimationRepeat----------" + time);
            }
        });
        mValueAnimator.start();
    }

    private void initTextViewAnima() {

        AnimatorSet innerAnimatorSet = new AnimatorSet();

        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(mTextView, "alpha", 1.0f, 0.0f, 1.0f);
        alphaAnimation.setRepeatCount(-1);
        alphaAnimation.setInterpolator(new LinearInterpolator());

        ValueAnimator valueAnimato = ValueAnimator.ofInt(mTextView.getLayoutParams().width, 500);

        valueAnimato.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int stepValue = (Integer) animation.getAnimatedValue();
                mTextView.getLayoutParams().width = stepValue;
                mTextView.requestLayout();
            }

        });
        valueAnimato.setRepeatCount(-1);
        valueAnimato.setInterpolator(new LinearInterpolator());

//一起执行
        innerAnimatorSet.playTogether(alphaAnimation, valueAnimato);
        innerAnimatorSet.setDuration(1200);
        innerAnimatorSet.start();


    }

    /**
     * 2.1、查询侦听机列表
     */
    private void getQueryListenerListList() {
        String userName = "Hd_Monitor";

//        String statusCode = "0";
        String timestamp = System.currentTimeMillis() + "";
        String token = "A4L0IT9XHC35MG2G";
        String signature = timestamp;
        String singe;
        try {
            singe = Hmacmd5Utils.byteArrayToHexString(Hmacmd5Utils.encryptHMAC(signature.getBytes(), token));
            OkGo.<String>get("https://aeroscope-cn.djiservice.org/webapi/v1/get_aeroscope_info")
                    .headers("x-aeroscope-username", userName)
                    .headers("x-aeroscope-signature", singe)
                    .params("timestamp", timestamp)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            if (response.isSuccessful()) {
                                String s = response.body();
                                LogUtil.e("结果", s);
                                mTvList.setText(s + "\n");

                            }
                        }

                        @Override
                        public void onFinish() {
                            super.onFinish();
                            LogUtil.e("结果", "---onFinish---");

                        }

                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);
                            LogUtil.e("结果", "---onError---" + response.body());
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 2.2、查询实时上报的飞机信息
     */
    private void getFlyListenerListList() {
        String userName = "Hd_Monitor";
        String token = "A4L0IT9XHC35MG2G";
        int page = 1;
        String aeroscopeID = "0QRDE8F0010012";//droneID
        String beginTs = "";
        String endTs = "";
        long timestamp = System.currentTimeMillis();
        String signature = page + aeroscopeID + timestamp;
        try {

            String singe = Hmacmd5Utils.byteArrayToHexString(Hmacmd5Utils.encryptHMAC(signature.getBytes(), token));
            OkGo.<String>get("https://aeroscope-cn.djiservice.org/webapi/v1/get_drone_record")
                    .headers("x-aeroscope-username", userName)
                    .headers("x-aeroscope-signature", singe)
                    .params("page", page)
                    .params("aeroscopeID", aeroscopeID)
                    .params("beginTs", beginTs)
                    .params("endTs", endTs)
                    .params("timestamp", timestamp)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            if (response.isSuccessful()) {
                                String s = response.body();
                                LogUtil.e("结果", s);
                                mTvList.setText(s + "\n");
//                                DJISetverAreoist djiSetverAreoist = FastJsonUtils.toBean(s, DJISetverAreoist.class);
//                                if (djiSetverAreoist.isSuccess()) {
//                                    List<DJISetverAreoist.DataBean.ListBean> listBeanList = djiSetverAreoist.getData().getList();
//
//                                    if (djiSetverAreoist.getData().getTotalCount() > 0) {
//                                        for (int i = 0; i < listBeanList.size(); i++) {
//                                            LogUtil.e("结果：", listBeanList.get(i).toString());
//                                            mTvList.append(listBeanList.get(i).toString() + "\n");
//                                        }
//                                    } else {
//                                        mTvList.append(s + "\n");
//                                    }
//                                } else {
//                                    mTvList.append(s + "\n");
//                                }
                            }

                        }

                        @Override
                        public void onFinish() {
                            super.onFinish();
                            LogUtil.e("结果", "---onFinish---");
                        }

                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);
                            LogUtil.e("结果", "---onError---" + response.body());
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 2.3、查询飞行历史记录
     */
    private void getFlyListenerHistoryListList() {
        String userName = "Hd_Monitor";
        String token = "A4L0IT9XHC35MG2G";
        int page = 10;//
        String orderID = "";
        String aeroscopeID = "0QRDE8F0010012";
        String droneID = "";
        String beginTs = "1559628938000";
        String endTs = "1562134544000";
        long timestamp = System.currentTimeMillis();
        String signature = page + aeroscopeID + beginTs + endTs + timestamp;
        try {
            String singe = Hmacmd5Utils.byteArrayToHexString(Hmacmd5Utils.encryptHMAC(signature.getBytes(), token));
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.put("x-aeroscope-username", userName);
            httpHeaders.put("x-aeroscope-signature", singe);
            HttpParams httpParams = new HttpParams();
            httpParams.put("page", page);
            httpParams.put("aeroscopeID", aeroscopeID);
            httpParams.put("beginTs", beginTs);
            httpParams.put("endTs", endTs);
            httpParams.put("timestamp", timestamp);

            OkGo.<String>get("https://aeroscope-cn.djiservice.org/webapi/v1/get_flight_record")
                    .headers(httpHeaders)
                    .params(httpParams)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            if (response.isSuccessful()) {
                                String s = response.body();
                                LogUtil.e("结果", s);
                                mTvList.setText(s + "\n");
//                                DJISetverAreoist djiSetverAreoist = FastJsonUtils.toBean(s, DJISetverAreoist.class);
//                                if (djiSetverAreoist.isSuccess()) {
//                                    List<DJISetverAreoist.DataBean.ListBean> listBeanList = djiSetverAreoist.getData().getList();
//
//                                    if (djiSetverAreoist.getData().getTotalCount() > 0) {
//                                        for (int i = 0; i < listBeanList.size(); i++) {
//                                            LogUtil.e("结果：", listBeanList.get(i).toString());
//                                            mTvList.append(listBeanList.get(i).toString() + "\n");
//                                        }
//                                    } else {
//                                        mTvList.append(s + "\n");
//                                    }
//                                } else {
//                                    mTvList.append(s + "\n");
//                                }
                            }

                        }

                        @Override
                        public void onFinish() {
                            super.onFinish();
                            LogUtil.e("结果", "---onFinish---");
                        }

                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);
                            LogUtil.e("结果", "---onError---" + response.body());
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void Aroute() {
//        ARouter.getInstance().build(UserAroutePath.USER_ACTIVITY)
//                .withString("name", "刘涛")
//                .withInt("age", 20)
//                .withString("sex", "男")
//                .withParcelable("user", new User())
//                .navigation(getActivity(), 5);
        ARouter.getInstance().build(UserAroutePath.USER_ACTIVITY)
                .withString("name", "刘涛")
                .withInt("age", 20)
                .withString("sex", "男")
                .withParcelable("user", new User())
                .navigation(AppManager.getAppManager().currentActivity(), 5, new NavCallback() {
                    @Override
                    public void onArrival(Postcard postcard) {

                    }

                    @Override
                    public void onInterrupt(Postcard postcard) {

                    }
                });

//        Postcard postcard = ARouter.getInstance().build(UserAroutePath.USER_ACTIVITY);
//
//        LogisticsCenter.completion(postcard);
//        Class<?> destination = postcard.getDestination();
//        Intent intent = new Intent(getContext(), destination);
//        startActivityForResult(intent, 5);
//        ARouter.getInstance().build(UserAroutePath.USER_ACTIVITY, "ap").navigation(getActivity(), 5);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        LogUtil.e("返回", requestCode + "-------------" + requestCode + "");
        if (data != null) {
            String dataStringExtra = data.getStringExtra("data");
            LogUtil.e("返回", "------------------" + dataStringExtra + "------------------");
        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
