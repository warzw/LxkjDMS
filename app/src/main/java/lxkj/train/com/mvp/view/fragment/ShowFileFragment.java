package lxkj.train.com.mvp.view.fragment;

import android.os.Bundle;
import android.widget.Toast;

import lxkj.train.com.R;
import lxkj.train.com.databinding.ActivityShowFileFragmentBinding;
import lxkj.train.com.mvp.presenter.presenter_impl.MyBookmarkFragmentPresenter;
import lxkj.train.com.mvp.presenter.presenter_impl.ShowFileFragmentPresenter;
import lxkj.train.com.mvp.view.fragment.base.BaseFragment;

public class ShowFileFragment extends BaseFragment{


    public MyBookmarkFragmentPresenter myBookmarkFragmentPresenter;

    public static ShowFileFragment getInstance(int tag){
        ShowFileFragment showFileFragment = new ShowFileFragment();
        Bundle bd = new Bundle();
        bd.putInt("tag", tag);
        showFileFragment.setArguments(bd);
        return showFileFragment;
    }
    @Override
    public int setOnCreateView() {
        return R.layout.activity_show_file_fragment;
    }

    @Override
    protected void initData() {
        if (getArguments().getInt("tag") == 0) { //资料分类
            new ShowFileFragmentPresenter(activity, ActivityShowFileFragmentBinding.bind(mView),0);
        }else if (getArguments().getInt("tag") == 1) { // 待读文件
            new ShowFileFragmentPresenter(activity, ActivityShowFileFragmentBinding.bind(mView),1);
        }else if (getArguments().getInt("tag") == 2) { //文件搜索
            new ShowFileFragmentPresenter(activity, ActivityShowFileFragmentBinding.bind(mView),2);
        }else if (getArguments().getInt("tag") == 3) {  // 我的书签
            myBookmarkFragmentPresenter = new MyBookmarkFragmentPresenter(activity, ActivityShowFileFragmentBinding.bind(mView),3);
        }else if (getArguments().getInt("tag") == 4) {  // 我的收藏
            myBookmarkFragmentPresenter = new MyBookmarkFragmentPresenter(activity, ActivityShowFileFragmentBinding.bind(mView),4);
        }
    }
}
