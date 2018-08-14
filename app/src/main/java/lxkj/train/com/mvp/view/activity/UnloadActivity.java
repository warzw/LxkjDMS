package lxkj.train.com.mvp.view.activity;
import lxkj.train.com.R;
import lxkj.train.com.databinding.ActivityUnloadBinding;
import lxkj.train.com.mvp.presenter.presenter_impl.UnloadPresenter;
import lxkj.train.com.mvp.view.activity.base.BaseActivity;

public class UnloadActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_unload;
    }

    @Override
    protected void initData() {
        new UnloadPresenter(this, ActivityUnloadBinding.bind(mView));
    }
}
