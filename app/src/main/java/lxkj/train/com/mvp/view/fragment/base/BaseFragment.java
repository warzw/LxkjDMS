package lxkj.train.com.mvp.view.fragment.base;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import lxkj.train.com.interfaces.ActivityResultInterface;
import lxkj.train.com.interfaces.HttpResponse;
import lxkj.train.com.interfaces.LifecyclePresenterInterface;
import lxkj.train.com.interfaces.ViewClicksInterface;
import lxkj.train.com.mvp.view.activity.base.BaseActivity;
import lxkj.train.com.mvp.view.activity.base.BaseApplication;
import lxkj.train.com.overall.OverallData;
import lxkj.train.com.utils.http.HttpUtils;
import retrofit2.Call;


public abstract class BaseFragment extends Fragment implements HttpUtils.DeleteHttp, View.OnClickListener {

    protected View mView;
    public BaseActivity activity;
    public BaseApplication app;
    private SimpleDateFormat sdf;
    private Call call;
    private HttpResponse responses;
    public LifecyclePresenterInterface mLifecyclePresenterInterface;
    public ActivityResultInterface activityResultInterface;
    public ViewClicksInterface viewClicksInterface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(setOnCreateView(), null);
        sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        app = (BaseApplication) getActivity().getApplication();
        activity = (BaseActivity) getActivity();
        if (OverallData.exceptionSwitch) {
            try {
                initData();
            } catch (Exception e) {
            }
        } else {
            initData();
        }
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        HttpUtils.getIncetanc().initDeleteHttp(this);
        if (mLifecyclePresenterInterface != null) {
            mLifecyclePresenterInterface.onResume();
        }
    }


    public void setViewListener(ViewClicksInterface viewListener, View... views) {
        this.viewClicksInterface = viewListener;
        for (int i = 0; i < views.length; i++) {
            views[i].setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        if (OverallData.exceptionSwitch) {
            try {
                if (viewClicksInterface != null) {
                    viewClicksInterface.clickViews(v);
                }
            } catch (Exception e) {

            }
        } else {
            if (viewClicksInterface != null) {
                viewClicksInterface.clickViews(v);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public abstract int setOnCreateView();

    protected abstract void initData();



    @Override
    public void onDestroy() {
        super.onDestroy();
        if (call != null) {
            call.cancel();
            call = null;
        }
        if (responses != null) {
            responses = null;
        }
    }

    public void delete(Call call, HttpResponse responses) {
        this.call = call;
        this.responses = responses;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (activityResultInterface != null) {
            activityResultInterface.onActivityResult(requestCode, resultCode, data);
        }
    }
}
