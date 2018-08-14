package lxkj.train.com.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import lxkj.train.com.R;
import lxkj.train.com.adapter.base.BaseRecyclerViewAdapter;
import lxkj.train.com.databinding.PlayTapingItemBinding;
import lxkj.train.com.entity.DirectoryOrFileEntity;
import lxkj.train.com.mvp.view.activity.base.BaseActivity;
import lxkj.train.com.overall.OverallData;

/**
 * Created by dell on 2018/6/26.
 */

public class PlayTapingAdapter extends BaseRecyclerViewAdapter<PlayTapingItemBinding> {
    private List<DirectoryOrFileEntity> datas;
    private List<View> list = new ArrayList<>();
    public PlayTapingAdapter(BaseActivity mActivity, List mDatas, OnRefreshListener onRefreshListener) {
        super(mActivity, mDatas, onRefreshListener);
        datas = mDatas;
    }

    @Override
    public void bindingItemViewModel(PlayTapingItemBinding binding, int position) {
        binding.setViewModel(datas.get(position));
        binding.mLayout.setTag(position);
        binding.mLayout.setOnClickListener(this);
        list.add(binding.mLayout);
    }

    @Override
    public RecyclerView.ViewHolder newCreateViewHolder(ViewGroup parent, int viewType) {
        return createViewBinding(R.layout.play_taping_item);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.mLayout) {
            for (int i = 0; i < list.size(); i++) {
                TextView tv_play = list.get(i).findViewById(R.id.tv_play);
                if (((Integer) v.getTag())== i) {
                    tv_play.setText("播放中...");
                    list.get(i).setBackgroundColor(ContextCompat.getColor(OverallData.app, R.color.gray_b4));
                }else {
                    tv_play.setText("");
                    list.get(i).setBackgroundColor(ContextCompat.getColor(OverallData.app, R.color.white));
                }
            }
        }
    }
}
