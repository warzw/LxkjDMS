package lxkj.train.com.adapter;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.aspsine.swipetoloadlayout.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import lxkj.train.com.R;
import lxkj.train.com.adapter.base.BaseRecyclerViewAdapter;
import lxkj.train.com.databinding.TrainQueryKeyItemBinding;
import lxkj.train.com.entity.MainEntity;
import lxkj.train.com.mvp.view.activity.base.BaseActivity;

/**
 * Created by dell on 2018/8/1.
 */

public class TrainQueryKeyAdapter extends BaseRecyclerViewAdapter<TrainQueryKeyItemBinding> {
    private List<MainEntity> textData = new ArrayList<>();
    public TrainQueryKeyAdapter(BaseActivity mActivity, List mDatas, OnRefreshListener onRefreshListener) {
        super(mActivity, mDatas, onRefreshListener);
        textData = mDatas;
    }

    @Override
    public void bindingItemViewModel(TrainQueryKeyItemBinding binding, int position) {
        binding.setViewModel(textData.get(position));
        textData.get(position).setKeyImage(binding.tv1,binding.iv1);
    }

    @Override
    public RecyclerView.ViewHolder newCreateViewHolder(ViewGroup parent, int viewType) {
        return createViewBinding(R.layout.train_query_key_item);
    }
}
