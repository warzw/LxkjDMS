package lxkj.train.com.mvp.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import lxkj.train.com.R;
import lxkj.train.com.databinding.ActivityServiceSetBinding;
import lxkj.train.com.mvp.presenter.presenter_impl.ServiceSetPresenter;
import lxkj.train.com.mvp.view.activity.base.BaseActivity;

public class ServiceSetActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_service_set;
    }

    @Override
    protected void initData() {
        new ServiceSetPresenter(this,ActivityServiceSetBinding.bind(mView));
    }
}
