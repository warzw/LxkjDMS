package lxkj.train.com.adapter;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.aspsine.swipetoloadlayout.OnRefreshListener;

import java.util.List;

import lxkj.train.com.R;
import lxkj.train.com.adapter.base.BaseRecyclerViewAdapter;
import lxkj.train.com.databinding.UnloadItemBinding;
import lxkj.train.com.entity.realm.UnloadRealm;
import lxkj.train.com.mvp.view.activity.base.BaseActivity;

/**
 * Created by dell on 2018/8/15.
 */

public class UnloadAdapter extends BaseRecyclerViewAdapter<UnloadItemBinding> {
    private List<UnloadRealm> datas;
    public UnloadAdapter(BaseActivity mActivity, List mDatas, OnRefreshListener onRefreshListener) {
        super(mActivity, mDatas, onRefreshListener);
        datas = mDatas;
    }

    @Override
    public void bindingItemViewModel(UnloadItemBinding binding, int position) {
        binding.setViewModel(datas.get(position));
    }

    @Override
    public RecyclerView.ViewHolder newCreateViewHolder(ViewGroup parent, int viewType) {
        return createViewBinding(R.layout.unload_item);
    }
}
