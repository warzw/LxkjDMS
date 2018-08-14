package lxkj.train.com.mvp.view.activity;

import lxkj.train.com.R;
import lxkj.train.com.databinding.ActivityBasicInfoBinding;
import lxkj.train.com.mvp.presenter.presenter_impl.BasicInfoPresenter;
import lxkj.train.com.mvp.view.activity.base.BaseActivity;

public class BasicInfoActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_basic_info;
    }

    @Override
    protected void initData() {
        mTitle_bar.setCentreText("基本信息");
        new BasicInfoPresenter(this, ActivityBasicInfoBinding.bind(mView));

    }
}
