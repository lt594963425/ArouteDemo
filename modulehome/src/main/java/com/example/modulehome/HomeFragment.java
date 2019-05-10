package com.example.modulehome;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.core.LogisticsCenter;
import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.android.utils.AndroidUtil;
import com.android.utils.LogUtil;
import com.example.modulebase.aroute.HomeAroutePath;
import com.example.modulebase.aroute.UserAroutePath;
import com.example.modulebase.base.AppManager;
import com.example.modulebase.base.BaseFragment;
import com.example.modulebase.data.entity.User;

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
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        TextView textView = view.findViewById(R.id.tv_text);
        textView.setText("主页\n" + AndroidUtil.getLocalVersionName());

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Aroute();
            }
        });

        return view;
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
}
