package lxkj.train.com.mvp.view.activity;


import lxkj.train.com.R;
import lxkj.train.com.databinding.ActivitySimulateGuideBinding;
import lxkj.train.com.mvp.presenter.presenter_impl.SimulateGuidePresenter;
import lxkj.train.com.mvp.view.activity.base.BaseActivity;

public class SimulateGuideActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_simulate_guide;
    }

    @Override
    protected void initData() {
        new SimulateGuidePresenter(this, ActivitySimulateGuideBinding.bind(mView));
    }
}
