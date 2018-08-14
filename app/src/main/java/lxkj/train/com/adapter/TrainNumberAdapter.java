package lxkj.train.com.adapter;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.aspsine.swipetoloadlayout.OnRefreshListener;

import java.util.List;

import lxkj.train.com.R;
import lxkj.train.com.adapter.base.BaseRecyclerViewAdapter;
import lxkj.train.com.databinding.TrainNumberItemBinding;
import lxkj.train.com.entity.TrainNumberEntity;
import lxkj.train.com.mvp.view.activity.base.BaseActivity;

/**
 * Created by dell on 2018/6/26.
 */

public class TrainNumberAdapter extends BaseRecyclerViewAdapter<TrainNumberItemBinding> {
    private List<TrainNumberEntity> datas;
    public TrainNumberAdapter(BaseActivity mActivity, List mDatas, OnRefreshListener onRefreshListener) {
        super(mActivity, mDatas, onRefreshListener);
        datas = mDatas;
    }

    @Override
    public void bindingItemViewModel(TrainNumberItemBinding binding, int position) {
        binding.setViewModel(datas.get(position));
    }

    @Override
    public RecyclerView.ViewHolder newCreateViewHolder(ViewGroup parent, int viewType) {
        return createViewBinding(R.layout.train_number_item);
    }
}
