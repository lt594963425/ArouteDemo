package com.example.modulebase.base;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.modulebase.base.base.App;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * $person_name
 *
 * @author ${LiuTao}
 * @date 2017/12/8/008
 */

public class BaseFragment extends Fragment {

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        compositeDisposable = new CompositeDisposable();

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    //    /**
//     * 处理回退事件
//     *
//     * @return
//     */
//    @Override
//    public boolean o() {
//        if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
//            mActivity.finish();
//        } else {
//            TOUCH_TIME = System.currentTimeMillis();
//            Toast.makeText(mActivity, R.string.press_again_exit, Toast.LENGTH_SHORT).show();
//        }
//        return true;
//    }
    private ProgressDialog dialog;

    public void showPDLoading(String s) {
        if (dialog != null && dialog.isShowing()) return;
        dialog = new ProgressDialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        if (TextUtils.isEmpty(s)) {
            dialog.setMessage("加载中");
        } else {
            dialog.setMessage(s);
        }

        dialog.show();
    }

    public void dismissLoading() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    private CompositeDisposable compositeDisposable;

    public void addDisposable(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    public void dispose() {
        if (compositeDisposable != null) compositeDisposable.dispose();
    }
}
