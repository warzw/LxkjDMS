package lxkj.train.com.mvp.view.activity;


import lxkj.train.com.R;
import lxkj.train.com.databinding.ActivityAnnouncementBinding;
import lxkj.train.com.mvp.presenter.presenter_impl.AnnouncementPresenter;
import lxkj.train.com.mvp.view.activity.base.BaseActivity;
/**
 * 公告查询
 */
public class AnnouncementActivity extends BaseActivity {

    private AnnouncementPresenter announcementPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_announcement;
    }

    @Override
    protected void initData() {
        announcementPresenter = new AnnouncementPresenter(this, ActivityAnnouncementBinding.bind(mView));
    }

    @Override
    protected void onPause() {
        super.onPause();
        announcementPresenter.onPause();
    }
}
