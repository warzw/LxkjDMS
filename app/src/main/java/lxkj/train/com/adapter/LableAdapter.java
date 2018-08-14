package lxkj.train.com.adapter;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.aspsine.swipetoloadlayout.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import lxkj.train.com.R;
import lxkj.train.com.adapter.base.BaseRecyclerViewAdapter;
import lxkj.train.com.databinding.LableItemBinding;
import lxkj.train.com.entity.base.BaseEntity;
import lxkj.train.com.mvp.view.activity.base.BaseActivity;

/**
 * Created by dell on 2018/8/7.
 */

public class LableAdapter extends BaseRecyclerViewAdapter<LableItemBinding> {
    private List<BaseEntity> lableDatas = new ArrayList<>();
    public LableAdapter(BaseActivity mActivity, List mDatas, OnRefreshListener onRefreshListener) {
        super(mActivity, mDatas, onRefreshListener);
        lableDatas = mDatas;
    }

    @Override
    public void bindingItemViewModel(LableItemBinding binding, int position) {
        binding.setViewModel(lableDatas.get(position));
    }

    @Override
    public RecyclerView.ViewHolder newCreateViewHolder(ViewGroup parent, int viewType) {
        return createViewBinding(R.layout.lable_item);
    }
}
