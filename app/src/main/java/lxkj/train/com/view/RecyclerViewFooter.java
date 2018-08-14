package lxkj.train.com.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.SwipeLoadMoreTrigger;
import com.aspsine.swipetoloadlayout.SwipeTrigger;

import lxkj.train.com.R;

/**
 * Created by dhc on 2017/7/4.
 */

public class RecyclerViewFooter extends LinearLayout implements SwipeLoadMoreTrigger,SwipeTrigger {

    private ProgressBar progressBar;
    private TextView tv_load;

    public RecyclerViewFooter(Context context) {
        super(context);
        initView();
    }

    public RecyclerViewFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public RecyclerViewFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void initView(){
        View view_footer= View.inflate(getContext(), R.layout.view_footer,this);
        progressBar = (ProgressBar)view_footer.findViewById(R.id.load_progress);
        tv_load = (TextView)view_footer.findViewById(R.id.tv_load);
    }


    @Override
    public void onLoadMore() {  //加载更多
        tv_load.setText("努力加载中");
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPrepare() {  //准备
        progressBar.setVisibility(View.INVISIBLE);
        tv_load.setText("上拉加载更多");
    }

    @Override
    public void onMove(int i, boolean b, boolean b1) {  //移动

    }

    @Override
    public void onRelease() {  //释放

    }

    @Override
    public void onComplete() {  //成功
        progressBar.setVisibility(View.INVISIBLE);
        tv_load.setText("加载完成");
    }

    @Override
    public void onReset() {//重置

    }
}
