package lxkj.train.com.interfaces;

import android.view.View;

import java.util.List;

/**
 * Created by dell on 2018/6/15.
 */

public interface OnRecyclerItemLongClickListener {
    void getLongItem(View view, List<Object> mDatas, int position);
}
