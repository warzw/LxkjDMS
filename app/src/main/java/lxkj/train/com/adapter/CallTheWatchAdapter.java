package lxkj.train.com.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import java.util.List;

import lxkj.train.com.R;
import lxkj.train.com.adapter.base.BaseRecyclerViewAdapter;
import lxkj.train.com.databinding.CallTheWatchItemBinding;
import lxkj.train.com.entity.CallTheWatchEntity;
import lxkj.train.com.mvp.view.activity.base.BaseActivity;

/**
 * Created by dell on 2018/8/8.
 */

public class CallTheWatchAdapter extends BaseRecyclerViewAdapter<CallTheWatchItemBinding> {
    private List<CallTheWatchEntity> datas ;
    public CallTheWatchAdapter(BaseActivity mActivity, List mDatas, OnRefreshListener onRefreshListener) {
        super(mActivity, mDatas, onRefreshListener);
        datas =mDatas;
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public void bindingItemViewModel(CallTheWatchItemBinding binding, int position) {
        binding.setViewModel(datas.get(position));
    }

    @Override
    public RecyclerView.ViewHolder newCreateViewHolder(ViewGroup parent, int viewType) {
        return createViewBinding(R.layout.call_the_watch_item);
    }
}
