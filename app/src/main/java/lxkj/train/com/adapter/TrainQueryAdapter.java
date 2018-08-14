package lxkj.train.com.adapter;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.swipetoloadlayout.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import lxkj.train.com.R;
import lxkj.train.com.adapter.base.BaseRecyclerViewAdapter;
import lxkj.train.com.databinding.TrainQueryItemBinding;
import lxkj.train.com.entity.TrainQueryEntity;
import lxkj.train.com.mvp.view.activity.base.BaseActivity;

/**
 * Created by dell on 2018/7/31.
 */

public class TrainQueryAdapter extends BaseRecyclerViewAdapter<TrainQueryItemBinding> {
    private List<TrainQueryEntity> datas = new ArrayList<>();
    public TrainQueryAdapter(BaseActivity mActivity, List mDatas, OnRefreshListener onRefreshListener) {
        super(mActivity, mDatas, onRefreshListener);
        datas = mDatas;
    }

    @Override
    public void bindingItemViewModel(TrainQueryItemBinding binding, int position) {
        binding.setViewModel(datas.get(position));
        setData(binding,position);
    }

    @Override
    public RecyclerView.ViewHolder newCreateViewHolder(ViewGroup parent, int viewType) {
        return createViewBinding(R.layout.train_query_item);
    }
    private void setData(TrainQueryItemBinding binding, int position){
        if (datas.get(position).getTag()==1) {
            binding.layout1.setBackgroundResource(R.drawable.set_back_tag2_shape);
        }else  if (datas.get(position).getTag()==2){
            binding.layout1.setBackgroundResource(R.drawable.set_back_tag_shape);
        }else  if (datas.get(position).getTag()==3){
            binding.layout1.setBackgroundResource(R.drawable.set_back_tag3_shape);
        }else {
            binding.layout1.setBackgroundResource(R.drawable.set_back_shape);
        }


        if ((position+1)%4 == 0) { //说明是最右边一个
            binding.line2.setVisibility(View.INVISIBLE);
        }else {
            binding.line2.setVisibility(View.VISIBLE);
        }
        if (position == datas.size()-1||position ==datas.size()-4) {
            binding.line3.setVisibility(View.INVISIBLE);
            if (position == datas.size()-1) {
                binding.line2.setVisibility(View.INVISIBLE);
            }
        }else {
            if ((position+1)%4 == 0&&(position+1)/4%2!=0) { //最右边一个，并且是奇数行，2，4
                binding.line3.setVisibility(View.VISIBLE);
            }else {
                if (position%4 == 0&&position/4%2!=0) { //最左边一个，并且是偶数行，2，4
                    binding.line3.setVisibility(View.VISIBLE);
                }else {
                    binding.line3.setVisibility(View.INVISIBLE);
                }
            }

        }
    }
}
