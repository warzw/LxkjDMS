package lxkj.train.com.mvp.view.activity;

import lxkj.train.com.R;
import lxkj.train.com.databinding.ActivityLoginBinding;
import lxkj.train.com.mvp.presenter.presenter_impl.LoginPresenter;
import lxkj.train.com.mvp.view.activity.base.BaseActivity;

public class LoginActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {
        new LoginPresenter(this, ActivityLoginBinding.bind(mView));
    }

}
