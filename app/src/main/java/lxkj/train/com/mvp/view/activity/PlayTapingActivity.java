package lxkj.train.com.mvp.view.activity;


import lxkj.train.com.R;
import lxkj.train.com.databinding.ActivityPlayTapingBinding;
import lxkj.train.com.mvp.presenter.presenter_impl.PlayTapingPresenter;
import lxkj.train.com.mvp.view.activity.base.BaseActivity;

public class PlayTapingActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_play_taping;
    }

    @Override
    protected void initData() {
        new PlayTapingPresenter(this, ActivityPlayTapingBinding.bind(mView));
    }
}
