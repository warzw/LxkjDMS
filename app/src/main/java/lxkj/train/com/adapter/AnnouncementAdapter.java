package lxkj.train.com.adapter;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.swipetoloadlayout.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import lxkj.train.com.R;
import lxkj.train.com.adapter.base.BaseRecyclerViewAdapter;
import lxkj.train.com.databinding.AnnouncementItemBinding;
import lxkj.train.com.entity.AnnouncementEntity;
import lxkj.train.com.mvp.view.activity.base.BaseActivity;
import lxkj.train.com.overall.TextUtil;

/**
 * Created by dell on 2018/6/25.
 */

public class AnnouncementAdapter extends BaseRecyclerViewAdapter<AnnouncementItemBinding> {
    private List<AnnouncementEntity> datas;
    public AnnouncementAdapter(BaseActivity mActivity, List mDatas, OnRefreshListener onRefreshListener) {
        super(mActivity, mDatas, onRefreshListener);
        datas = mDatas;
    }

    @Override
    public void bindingItemViewModel(AnnouncementItemBinding binding, int position) {
        binding.setViewModel(datas.get(position));
        if (TextUtils.isEmpty(datas.get(position).getIsShow())) {
            binding.ivTag.setVisibility(View.VISIBLE);
        }else {
            binding.ivTag.setVisibility(Integer.parseInt(datas.get(position).getIsShow()));
        }

    }

    @Override
    public RecyclerView.ViewHolder newCreateViewHolder(ViewGroup parent, int viewType) {
        return createViewBinding(R.layout.announcement_item);
    }
}
