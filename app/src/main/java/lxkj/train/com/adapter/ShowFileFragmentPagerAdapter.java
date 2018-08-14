package lxkj.train.com.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import lxkj.train.com.mvp.view.fragment.ShowFileFragment;

/**
 * Created by dell on 2018/6/13.
 */

public class ShowFileFragmentPagerAdapter extends FragmentPagerAdapter {
    private String[] title = {"资料分类","待读文件", "文件搜索","我的书签","我的收藏"};
    private List<ShowFileFragment> datas;
    public ShowFileFragmentPagerAdapter(FragmentManager fm, List<ShowFileFragment> datas, String[] title) {
        super(fm);
        if (title!=null) {
            this.title=title;
        }
        this.datas=datas;
    }
    @Override
    public Fragment getItem(int position) {
        return datas.get(position);
    }

    @Override
    public int getCount() {
        return datas.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
