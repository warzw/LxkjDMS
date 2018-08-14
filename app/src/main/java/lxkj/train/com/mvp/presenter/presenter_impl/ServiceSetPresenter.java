package lxkj.train.com.mvp.presenter.presenter_impl;


import android.view.View;

import com.google.protobuf.ByteString;

import lxkj.train.com.R;
import lxkj.train.com.databinding.ActivityServiceSetBinding;

import lxkj.train.com.mvp.view.activity.base.BaseActivity;
import lxkj.train.com.utils.MacAddressUtil;
import lxkj.train.com.utils.http.NetworkUtils;
import lxkj.train.com.view.DialogView;

/**
 * Created by dell on 2018/6/27.
 */

public class ServiceSetPresenter extends BasePresenter {
    private ActivityServiceSetBinding binding;
    public ServiceSetPresenter(BaseActivity activity, ActivityServiceSetBinding binding) {
        super(activity, binding);
        this.binding = binding;
        setOnViewClick(binding.tv15,binding.tv16);
        initData();
    }
    private void initData(){
        activity.mTitle_bar.setCentreText("服务器设置");
        binding.tv7.setText(MacAddressUtil.getMac(activity));
        if (!NetworkUtils.getWifiEnabled()||!NetworkUtils.isWifiConnected()) {
            binding.tv5.setText("wifi为连接");
        }else {
            binding.tv5.setText("wifi连接成功");
            binding.tv3.setText(NetworkUtils.getConnectWifiSsid()+"");
        }
    }
    @Override
    public void onViewClick(View v) {
        super.onViewClick(v);
        if (v.getId() == R.id.tv_15) { //取消
            activity.finish();
        }else if (v.getId() == R.id.tv_16) { //确定
            DialogView.hintDialog(activity,null,"","暂时无法设置",false);
        }
    }

    @Override
    public void requestData() {

    }

    @Override
    public void succeed(byte servicetype,int subtype,ByteString bytes) {

    }
}
