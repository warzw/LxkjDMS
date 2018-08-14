package lxkj.train.com.mvp.view.activity;


import lxkj.train.com.R;
import lxkj.train.com.databinding.ActivityWebViewWordBinding;
import lxkj.train.com.mvp.presenter.presenter_impl.ReadFilePresenter;
import lxkj.train.com.mvp.view.activity.base.BaseActivity;

public class ReadFileActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web_view_word;
    }

    @Override
    protected void initData() {
        new ReadFilePresenter(this, ActivityWebViewWordBinding.bind(mView));
    }

}
