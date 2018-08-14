package lxkj.train.com.adapter;

import android.databinding.ViewDataBinding;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.aspsine.swipetoloadlayout.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import lxkj.train.com.R;
import lxkj.train.com.adapter.base.BaseRecyclerViewAdapter;
import lxkj.train.com.databinding.MainItemBinding;
import lxkj.train.com.entity.MainEntity;
import lxkj.train.com.mvp.view.activity.base.BaseActivity;

/**
 * Created by dell on 2018/6/11.
 */

public class MainAdapter extends BaseRecyclerViewAdapter<MainItemBinding> {
    private List<MainEntity> datas = new ArrayList<>();
    public MainAdapter(BaseActivity mActivity, List mDatas, OnRefreshListener onRefreshListener) {
        super(mActivity, mDatas, onRefreshListener);
        datas = mDatas;
    }

    @Override
    public RecyclerView.ViewHolder newCreateViewHolder(ViewGroup parent, int viewType) {
        return createViewBinding(R.layout.main_item);
    }

    @Override
    public void bindingItemViewModel(MainItemBinding binding, int position) {
        binding.setViewModel(datas.get(position));
        datas.get(position).setImage(binding.iv1);

    }
}
