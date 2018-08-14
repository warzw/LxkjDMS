package lxkj.train.com.mvp.view.activity;

import android.view.WindowManager;

import lxkj.train.com.R;
import lxkj.train.com.databinding.ActivityTrainQueryBinding;
import lxkj.train.com.mvp.presenter.presenter_impl.TrainQueryPresenter;
import lxkj.train.com.mvp.view.activity.base.BaseActivity;

public class TrainQueryActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_train_query;
    }

    @Override
    protected void initData() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        new TrainQueryPresenter(this,ActivityTrainQueryBinding.bind(mView));

    }
}
