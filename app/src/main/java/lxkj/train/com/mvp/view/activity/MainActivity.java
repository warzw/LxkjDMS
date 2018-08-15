package lxkj.train.com.mvp.view.activity;


import android.widget.Toast;

import lxkj.train.com.R;
import lxkj.train.com.databinding.ActivityMainBinding;
import lxkj.train.com.entity.base.BaseEntity;
import lxkj.train.com.mvp.presenter.presenter_impl.MainPresenter;
import lxkj.train.com.mvp.view.activity.base.BaseActivity;

public class MainActivity extends BaseActivity {
    private long exitTime = 0;
    private MainPresenter mainPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        ActivityMainBinding binding = ActivityMainBinding.bind(mView);
        binding.setViewModel(new BaseEntity());
        mainPresenter = new MainPresenter(this,binding);
    }


    @Override
    public void onBackPressed() {
        if (mainPresenter.isOpenDrawer) { //侧滑菜单是否打开，如果打开就关闭
            mainPresenter.binding.drawerLayout.closeDrawers();
            return;
        }
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this,"再按一次推出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            System.exit(0);
        }
    }
}
