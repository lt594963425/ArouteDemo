package com.example.moduleuser;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.android.utils.LogUtil;
import com.android.view.XToolbar;
import com.example.modulebase.aroute.UserAroutePath;
import com.example.modulebase.base.BaseActivity;
import com.example.modulebase.data.entity.User;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 独编译运行时调用
 */
@Route(path = UserAroutePath.USER_ACTIVITY)
public class UserActivity extends BaseActivity {

    @Autowired
    public String name;
    @Autowired
    public int age;
    @Autowired
    public String sex;
    @Autowired(name = "user")
    public User mUser;
    @BindView(R2.id.toolbar)
    XToolbar mXToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);
        mXToolbar.setTitle("我的");
        ARouter.getInstance().inject(this);
        //LogUtil.e("收到信息", "名字：" + name + "，年龄：" + age + "，性别：" + sex + "," + mUser.toString());
        mXToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("data", "🔚了");
                setResult(5, intent);
                finish();
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();


    }
}
