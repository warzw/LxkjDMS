package lxkj.train.com.adapter.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.swipetoloadlayout.OnRefreshListener;

import java.util.List;

import lxkj.train.com.R;
import lxkj.train.com.interfaces.OnRecyclerItemClickListener;
import lxkj.train.com.interfaces.OnRecyclerItemLongClickListener;
import lxkj.train.com.mvp.view.activity.base.BaseActivity;
import lxkj.train.com.overall.OverallData;

/**
 * Created by dhc on 2017/10/31.
 */

public abstract class BaseRecyclerViewAdapter<B extends ViewDataBinding> extends RecyclerView.Adapter implements View.OnClickListener ,View.OnLongClickListener {
    public BaseActivity mActivity;
    private List<Object> mDatas;
    public LayoutInflater inflater;
    private OnRefreshListener onRefreshListener;
    public OnRecyclerItemClickListener onRecyclerItemClickListener;
    public OnRecyclerItemLongClickListener onRecyclerItemLongClickListener;
    private ViewGroup parent;

    public BaseRecyclerViewAdapter(BaseActivity mActivity, List mDatas, OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
        this.mActivity = mActivity;
        this.mDatas = mDatas;
        inflater = LayoutInflater.from(mActivity);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (mDatas.size() > 0) {
            return newGetItemViewType(position);
        } else {
            return -10;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.parent = parent;
        if (viewType == -10&&onRefreshListener!=null) {
            return new MMyViewHolder(inflater.inflate(R.layout.no_data_layout, parent, false));
        } else {
            return newCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        if (getItemViewType(position) == -10) {
            MMyViewHolder mmyViewHolder = (MMyViewHolder) holder;
            mmyViewHolder.itemView.setOnClickListener(this);
        } else {
            if (OverallData.exceptionSwitch) {
                try {
                    newBindViewHolder(holder, position);
                    B mBinding = DataBindingUtil.getBinding(holder.itemView);
                    if (mBinding != null) {
                        bindingItemViewModel(mBinding, position);
                        mBinding.executePendingBindings();
                    }
                } catch (Exception e) {
                }
            } else {
                newBindViewHolder(holder, position);
                B binding = DataBindingUtil.getBinding(holder.itemView);
                if (binding != null) {
                    bindingItemViewModel(binding, position);
                    binding.executePendingBindings();
                }
            }
        }
    }
    public void setOnViewClicks(int position,View...views){
        for (int i = 0; i <views.length ; i++) {
            views[i].setTag(position);
            views[i].setOnClickListener(this);
        }
    }
    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }
    public void setOnRecyclerItemLongClickListener(OnRecyclerItemLongClickListener onRecyclerItemLongClickListener) {
        this.onRecyclerItemLongClickListener = onRecyclerItemLongClickListener;
    }

    @Override
    public int getItemCount() {
        if (mDatas.size() > 0) {
            return mDatas.size();
        } else if (onRefreshListener!=null){
            return 1;
        }else {
            return 0;
        }
    }

    class MMyViewHolder extends RecyclerView.ViewHolder {
        MMyViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public void onClick(View v) {
        if (OverallData.exceptionSwitch) {
            try {
                if (onRefreshListener != null && v.getId() == R.id.no_data_layout) {
                    onRefreshListener.onRefresh();
                } else if (onRecyclerItemClickListener != null) {
                    onRecyclerItemClickListener.getItem(v,mDatas, (Integer) v.getTag());
                }
            } catch (Exception e) {
            }
        } else {
            if (onRefreshListener != null && v.getId() == R.id.no_data_layout) {
                onRefreshListener.onRefresh();
            } else if (onRecyclerItemClickListener != null) {
                onRecyclerItemClickListener.getItem(v,mDatas, (Integer) v.getTag());
            }
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (onRecyclerItemLongClickListener == null) {
            return false;
        }
        if (OverallData.exceptionSwitch) {
            try {
                onRecyclerItemLongClickListener.getLongItem(v,mDatas, (Integer) v.getTag());
            } catch (Exception e) {
            }
        } else {
            onRecyclerItemLongClickListener.getLongItem(v,mDatas, (Integer) v.getTag());
        }

        return true;
    }

    public RecyclerView.ViewHolder createViewBinding(int layoutResId) {
        B viewDataBinding = DataBindingUtil.inflate(inflater, layoutResId, parent, false);
        return new MMyViewHolder(viewDataBinding.getRoot());
    }

    public abstract void bindingItemViewModel(B binding, int position);

    public abstract RecyclerView.ViewHolder newCreateViewHolder(ViewGroup parent, int viewType);

    public void newBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(this);
        holder.itemView.setOnLongClickListener(this);
    }
    public int newGetItemViewType(int position) {
        return -11;
    }

}
