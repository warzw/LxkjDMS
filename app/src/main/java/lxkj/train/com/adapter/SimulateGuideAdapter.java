package lxkj.train.com.adapter;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.aspsine.swipetoloadlayout.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import lxkj.train.com.R;
import lxkj.train.com.adapter.base.BaseRecyclerViewAdapter;
import lxkj.train.com.databinding.SimulateGuideItemBinding;
import lxkj.train.com.entity.MainEntity;
import lxkj.train.com.mvp.view.activity.base.BaseActivity;

/**
 * Created by dell on 2018/6/29.
 */

public class SimulateGuideAdapter extends BaseRecyclerViewAdapter<SimulateGuideItemBinding> {
    private List<MainEntity> datas = new ArrayList<>();
    public SimulateGuideAdapter(BaseActivity mActivity, List mDatas, OnRefreshListener onRefreshListener) {
        super(mActivity, mDatas, onRefreshListener);
        datas = mDatas ;
    }

    @Override
    public void bindingItemViewModel(SimulateGuideItemBinding binding, int position) {
        binding.setViewModel(datas.get(position));
        if (position == 0) {
            binding.tv1.setBackgroundResource(R.drawable.shape_simulate_guide);
        }else if (position == datas.size()-1) {
            binding.tv1.setBackgroundResource(R.drawable.shape_simulate_guide_ri);
        } else {
            binding.tv1.setBackgroundResource(R.color.gray_66);
        }
    }

    @Override
    public RecyclerView.ViewHolder newCreateViewHolder(ViewGroup parent, int viewType) {
        return createViewBinding(R.layout.simulate_guide_item);
    }
}
