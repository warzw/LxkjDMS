package lxkj.train.com.adapter;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.aspsine.swipetoloadlayout.OnRefreshListener;

import java.util.List;

import lxkj.train.com.R;
import lxkj.train.com.adapter.base.BaseRecyclerViewAdapter;
import lxkj.train.com.databinding.DriveGuideItemBinding;
import lxkj.train.com.entity.DriveGuideItemEntity;
import lxkj.train.com.mvp.view.activity.base.BaseActivity;

/**
 * Created by dell on 2018/6/25.
 */

public class DriveGuideAdapter extends BaseRecyclerViewAdapter<DriveGuideItemBinding> {
    private List<DriveGuideItemEntity> datas;
    public DriveGuideAdapter(BaseActivity mActivity, List mDatas, OnRefreshListener onRefreshListener) {
        super(mActivity, mDatas, onRefreshListener);
        datas = mDatas;
    }

    @Override
    public void bindingItemViewModel(DriveGuideItemBinding binding, int position) {
        binding.setViewModel(datas.get(position));
        datas.get(position).setCheckState(binding.iv1);
    }

    @Override
    public RecyclerView.ViewHolder newCreateViewHolder(ViewGroup parent, int viewType) {
        return createViewBinding(R.layout.drive_guide_item);
    }
}
