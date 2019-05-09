package com.example.moduleuser;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.modulebase.aroute.UserAroutePath;
import com.example.modulebase.base.BaseFragment;
import com.example.modulebase.data.IUserInfo;

@Route(path = UserAroutePath.USER_FRAGMENT)
public class UserFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_user, null);
        TextView tv_text = view.findViewById(R.id.tv_text);
        return view;
    }
}
