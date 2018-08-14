package lxkj.train.com.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.aspsine.swipetoloadlayout.OnRefreshListener;

import java.util.List;

import lxkj.train.com.R;
import lxkj.train.com.adapter.base.BaseRecyclerViewAdapter;
import lxkj.train.com.databinding.ShowFileItemBinding;
import lxkj.train.com.entity.DirectoryOrFileEntity;
import lxkj.train.com.mvp.view.activity.base.BaseActivity;
import lxkj.train.com.utils.LogUtils;

/**
 * Created by dell on 2018/6/12.
 */

public class ShowFileAdapter extends BaseRecyclerViewAdapter<ShowFileItemBinding> {
    private List<DirectoryOrFileEntity> datas;
    public ShowFileAdapter(BaseActivity mActivity, List mDatas, OnRefreshListener onRefreshListener) {
        super(mActivity, mDatas, onRefreshListener);
        datas = mDatas;
    }

    @Override
    public void bindingItemViewModel(ShowFileItemBinding binding, int position) {
        binding.setViewModel(datas.get(position));
        binding.iv1.setImageResource(datas.get(position).getImage());
        LogUtils.i("itemPath",datas.get(position).getPath());
    }

    @Override
    public RecyclerView.ViewHolder newCreateViewHolder(ViewGroup parent, int viewType) {
        return createViewBinding(R.layout.show_file_item);
    }
}
