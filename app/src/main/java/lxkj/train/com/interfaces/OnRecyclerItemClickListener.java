package lxkj.train.com.interfaces;

import android.view.View;

import java.util.List;

/**
 * Created by dhc on 2017/7/6.
 */

public interface OnRecyclerItemClickListener {
    void getItem(View view, List<Object> mDatas, int position);
}
