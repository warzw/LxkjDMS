package lxkj.train.com.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.SwipeRefreshTrigger;
import com.aspsine.swipetoloadlayout.SwipeTrigger;

import lxkj.train.com.R;

/**
 * Created by dhc on 2017/7/4.
 */

public class RecyclerViewHead extends LinearLayout implements SwipeRefreshTrigger, SwipeTrigger {

    private ImageView iv_pull_to_prefresh_icon;
    private ProgressBar pb_pull_to_prefresh_progress;
    private TextView tv_pull_to_refresh_state;

    private RotateAnimation upAnimation;
    private RotateAnimation downAnimation;

    public RecyclerViewHead(Context context) {
        super(context);
        initView();
    }

    public RecyclerViewHead(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public RecyclerViewHead(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void initView(){
        View recycler_view_head= View.inflate(getContext(), R.layout.recycler_view_head,this);
        iv_pull_to_prefresh_icon = (ImageView) recycler_view_head.findViewById(R.id.iv_pull_to_prefresh_icon);
        pb_pull_to_prefresh_progress = (ProgressBar) recycler_view_head.findViewById(R.id.pb_pull_to_prefresh_progress);
        tv_pull_to_refresh_state = (TextView)recycler_view_head.findViewById(R.id.tv_pull_to_refresh_state);
        initAnimation();
    }

    private void initAnimation() {
        upAnimation = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        upAnimation.setDuration(500);
        upAnimation.setFillAfter(true);

        downAnimation = new RotateAnimation(-180, -360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        downAnimation.setDuration(500);
        downAnimation.setFillAfter(true);
    }


    @Override
    public void onRefresh() {   //正在刷新
        iv_pull_to_prefresh_icon.setVisibility(View.INVISIBLE);
        iv_pull_to_prefresh_icon.clearAnimation();
        pb_pull_to_prefresh_progress.setVisibility(View.VISIBLE);
        tv_pull_to_refresh_state.setText("正在刷新");
    }

    @Override
    public void onPrepare() {  //准备
        iv_pull_to_prefresh_icon.setVisibility(View.VISIBLE);
        iv_pull_to_prefresh_icon.setAnimation(downAnimation);
        tv_pull_to_refresh_state.setText("下拉刷新");
    }

    @Override
    public void onMove(int i, boolean b, boolean b1) {  //只要下拉就会调用这个方法
    }

    @Override
    public void onRelease() {   //释放
        iv_pull_to_prefresh_icon.setAnimation(upAnimation);
    }

    @Override
    public void onComplete() {  //刷新成功
        pb_pull_to_prefresh_progress.setVisibility(View.INVISIBLE);
        tv_pull_to_refresh_state.setText("刷新完成");
    }

    @Override
    public void onReset() {  //恢复
    }
}
