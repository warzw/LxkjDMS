package lxkj.train.com.mvp.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import lxkj.train.com.R;
import lxkj.train.com.databinding.ActivityDriveGuideBinding;
import lxkj.train.com.mvp.presenter.presenter_impl.DriveGuidePresenter;
import lxkj.train.com.mvp.view.activity.base.BaseActivity;

public class DriveGuideActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_drive_guide;
    }

    @Override
    protected void initData() {
        new DriveGuidePresenter(this, ActivityDriveGuideBinding.bind(mView));
    }
}
