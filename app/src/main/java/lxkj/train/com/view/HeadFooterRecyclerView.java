package lxkj.train.com.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;

import lxkj.train.com.R;

/**
 * Created by dhc on 2017/7/4.
 */

public class HeadFooterRecyclerView extends LinearLayout {
    private RecyclerView recyclerView;
    private RollSwipeToLoadLayout swipeToLoadLayout;

    public HeadFooterRecyclerView(Context context) {
        super(context);
        initView();
    }

    public HeadFooterRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public HeadFooterRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }
    public RollSwipeToLoadLayout getRollSwipeToLoadLayout() {
        return swipeToLoadLayout;
    }
    private void initView() {
        View headFooterRecyclerView = View.inflate(getContext(), R.layout.head_footer_recycler_view, this);
        swipeToLoadLayout = (RollSwipeToLoadLayout) headFooterRecyclerView.findViewById(R.id.swipeToLoadLayout);
        recyclerView = (RecyclerView) headFooterRecyclerView.findViewById(R.id.swipe_target);
    }

    public void setAdapter(RecyclerView.Adapter crAdapter, Context context) {
        LinearLayoutManager mgr = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mgr);
        recyclerView.setAdapter(crAdapter);
    }

    public void setOnRefreshLoadListener(Object object) {
        if (object instanceof OnRefreshListener) {
            swipeToLoadLayout.setOnRefreshListener((OnRefreshListener) object);
        }
        if (object instanceof OnLoadMoreListener) {
            swipeToLoadLayout.setOnLoadMoreListener((OnLoadMoreListener) object);
        }
    }
    public void stopRefreshLoad(){
        swipeToLoadLayout.setRefreshing(false);
        swipeToLoadLayout.setLoadingMore(false);
    }
}
