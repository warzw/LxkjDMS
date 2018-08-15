package lxkj.train.com.mvp.presenter.presenter_impl;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.protobuf.ByteString;

import lxkj.train.com.R;
import lxkj.train.com.databinding.ActivityLoginBinding;
import lxkj.train.com.mvp.view.activity.MainActivity;
import lxkj.train.com.mvp.view.activity.base.BaseActivity;
import lxkj.train.com.overall.OverallData;
import lxkj.train.com.overall.TextUtil;
import lxkj.train.com.utils.SharedPreferencesUtil;

/**
 * Created by dell on 2018/8/3.
 */

public class LoginPresenter extends BasePresenter {
    private ActivityLoginBinding binding;
    private String content = "";
    public LoginPresenter(BaseActivity activity, ActivityLoginBinding binding) {
        super(activity, binding);
        if (!TextUtils.isEmpty(SharedPreferencesUtil.getStringData(activity,"driverNum",""))) {
            OverallData.driverNum = SharedPreferencesUtil.getStringData(activity,"driverNum","");
            Intent intent = new Intent(activity,MainActivity.class);
            activity.startActivity(intent);
            activity.finish();
            return;
        }
        this.binding = binding;
        activity.mTitle_bar.setCentreText("初始化配置");
        setOnViewClick(binding.tv1,binding.tv2,binding.tv3,binding.tv4,binding.tv5,binding.tv8);
    }

    @Override
    public void onViewClick(View v) {
        super.onViewClick(v);
        if (v.getId() == R.id.tv_1) {
            binding.tv1.setBackgroundResource(R.drawable.set_back_tag_shape);
            binding.tv2.setBackgroundResource(R.drawable.circle_round);
            binding.tv3.setBackgroundResource(R.drawable.circle_round);
            binding.tv4.setBackgroundResource(R.drawable.circle_round);
            binding.tv5.setBackgroundResource(R.drawable.circle_round);
            content = "杭州机务段";
            binding.tv6.setText("192.168.11.61");
            binding.tv7.setText("5623");
        }else if (v.getId() == R.id.tv_2) {
            binding.tv1.setBackgroundResource(R.drawable.circle_round);
            binding.tv2.setBackgroundResource(R.drawable.set_back_tag_shape);
            binding.tv3.setBackgroundResource(R.drawable.circle_round);
            binding.tv4.setBackgroundResource(R.drawable.circle_round);
            binding.tv5.setBackgroundResource(R.drawable.circle_round);
            content = "南京机务段";
            binding.tv6.setText("192.168.11.62");
            binding.tv7.setText("5623");
        }else if (v.getId() == R.id.tv_3) {
            binding.tv1.setBackgroundResource(R.drawable.circle_round);
            binding.tv2.setBackgroundResource(R.drawable.circle_round);
            binding.tv3.setBackgroundResource(R.drawable.set_back_tag_shape);
            binding.tv4.setBackgroundResource(R.drawable.circle_round);
            binding.tv5.setBackgroundResource(R.drawable.circle_round);
            content = "合肥机务段";
            binding.tv6.setText("192.168.11.63");
            binding.tv7.setText("5623");
        }else if (v.getId() == R.id.tv_4) {
            binding.tv1.setBackgroundResource(R.drawable.circle_round);
            binding.tv2.setBackgroundResource(R.drawable.circle_round);
            binding.tv3.setBackgroundResource(R.drawable.circle_round);
            binding.tv4.setBackgroundResource(R.drawable.set_back_tag_shape);
            binding.tv5.setBackgroundResource(R.drawable.circle_round);
            content = "上海机务段";
            binding.tv6.setText("192.168.11.64");
            binding.tv7.setText("5623");
        }else if (v.getId() == R.id.tv_5) {
            binding.tv1.setBackgroundResource(R.drawable.circle_round);
            binding.tv2.setBackgroundResource(R.drawable.circle_round);
            binding.tv3.setBackgroundResource(R.drawable.circle_round);
            binding.tv4.setBackgroundResource(R.drawable.circle_round);
            binding.tv5.setBackgroundResource(R.drawable.set_back_tag_shape);
            content = "徐州机务段";
            binding.tv6.setText("192.168.11.65");
            binding.tv7.setText("5623");
        }else if (v.getId() == R.id.tv_8) { //确定
            if (TextUtils.isEmpty(content)) {
                Toast.makeText(activity, "请选择机务段", Toast.LENGTH_SHORT).show();
                return;
            }else if (TextUtils.isEmpty(binding.et21.getText().toString().trim())) {
                Toast.makeText(activity, "请输入工号", Toast.LENGTH_SHORT).show();
                return;
            }
            SharedPreferencesUtil.saveStringData(activity,"locomotiveDepot ",binding.tv6.getText().toString().trim()+":"+binding.tv7.getText().toString().trim());
            SharedPreferencesUtil.saveStringData(activity,"driverNum",binding.et21.getText().toString().trim());
            OverallData.driverNum = binding.et21.getText().toString().trim();
            Intent intent = new Intent(activity, MainActivity.class);
            intent.putExtra("driverNum",binding.et21.getText().toString().trim());
            activity.startActivity(intent);
            activity.finish();
        }

    }

    @Override
    public void requestData() {

    }

    @Override
    public void succeed(byte servicetype, int subtype, ByteString bytes) {

    }
}
