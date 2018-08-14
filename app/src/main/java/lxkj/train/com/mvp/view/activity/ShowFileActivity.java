package lxkj.train.com.mvp.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import lxkj.train.com.R;
import lxkj.train.com.databinding.ActivityShowFileBinding;
import lxkj.train.com.mvp.presenter.presenter_impl.ShowFilePresenter;
import lxkj.train.com.mvp.view.activity.base.BaseActivity;

public class ShowFileActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_show_file;
    }

    @Override
    protected void initData() {
        mTitle_bar.setCentreText(getIntent().getStringExtra("title"));
        new ShowFilePresenter(this,ActivityShowFileBinding.bind(mView));

    }
}
