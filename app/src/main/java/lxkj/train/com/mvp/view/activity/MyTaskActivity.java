package lxkj.train.com.mvp.view.activity;
import lxkj.train.com.R;
import lxkj.train.com.databinding.ActivityMyTaskBinding;
import lxkj.train.com.mvp.presenter.presenter_impl.MyTaskPresenter;
import lxkj.train.com.mvp.view.activity.base.BaseActivity;

public class MyTaskActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_task;
    }

    @Override
    protected void initData() {
        new MyTaskPresenter(this, ActivityMyTaskBinding.bind(mView));
    }
}
