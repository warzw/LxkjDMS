package lxkj.train.com.mvp.view.activity;
import lxkj.train.com.R;
import lxkj.train.com.databinding.ActivityTrainNumberBinding;
import lxkj.train.com.mvp.presenter.presenter_impl.TrainNumberPresenter;
import lxkj.train.com.mvp.view.activity.base.BaseActivity;

public class TrainNumberActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_train_number;
    }

    @Override
    protected void initData() {
        new TrainNumberPresenter(this, ActivityTrainNumberBinding.bind(mView));
    }
}
