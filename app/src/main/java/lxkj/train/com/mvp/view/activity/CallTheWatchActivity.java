package lxkj.train.com.mvp.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import lxkj.train.com.R;
import lxkj.train.com.databinding.ActivityCallTheWatchBinding;
import lxkj.train.com.mvp.presenter.presenter_impl.CallTheWatchPresenter;
import lxkj.train.com.mvp.view.activity.base.BaseActivity;

public class CallTheWatchActivity extends BaseActivity {  //晚点叫班

    @Override
    protected int getLayoutId() {
        return R.layout.activity_call_the_watch;
    }

    @Override
    protected void initData() {
        new CallTheWatchPresenter(this, ActivityCallTheWatchBinding.bind(mView));
    }
}
