package lxkj.train.com.view;

import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lxkj.train.com.R;
import lxkj.train.com.adapter.TrainQueryKeyAdapter;
import lxkj.train.com.adapter.base.BaseRecyclerViewAdapter;
import lxkj.train.com.entity.MainEntity;
import lxkj.train.com.interfaces.OnRecyclerItemClickListener;
import lxkj.train.com.mvp.view.activity.base.BaseActivity;


/**
 * Created by dhc on 2017/9/28.
 */

public class PopupWindows {
    private String[] texts ={"T","G","D","C","1","2","3","-1","4","5","6","0","7","8","9","-2"};
    public BaseActivity activity;
    private PopupWindow selectPopupWindow;
    private WindowManager.LayoutParams lp;
    private List<MainEntity> textData = new ArrayList<>();
    public void keyPopupWindow(final BaseActivity activity, View layout,OnRecyclerItemClickListener onRecyclerItemClickListener) {
        if (selectPopupWindow != null) {
            lp.alpha = 0.4f;
            activity.getWindow().setAttributes(lp);
            selectPopupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);
            return;
        }
        View view = LayoutInflater.from(activity).inflate(R.layout.train_query_popuwindow, null);
        RecyclerView recyclerView = view.findViewById(R.id.textRecyclerView);
        for (int i = 0; i < texts.length; i++) {
            MainEntity mainEntity = new MainEntity();
            mainEntity.setText(texts[i]);
            textData.add(mainEntity);
        }
        setGridAdapter(recyclerView,4,new TrainQueryKeyAdapter(activity,textData,null),onRecyclerItemClickListener);
        selectPopupWindow =  new PopupWindow(view,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        lp = activity.getWindow().getAttributes();
        lp.alpha = 0.4f;
         activity.getWindow().setAttributes(lp);
        //设置popWindow弹出窗体可点击
        selectPopupWindow.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        selectPopupWindow.setBackgroundDrawable(dw);
        // 设置popWindow的显示和消失动画
        selectPopupWindow.setAnimationStyle(R.style.PopupAnimation);
        selectPopupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);
        selectPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            public void onDismiss() {
                lp.alpha = 1f;
                activity.getWindow().setAttributes(lp);
            }
        });
    }
    public void dismissKey(){
        if (selectPopupWindow != null) {
            selectPopupWindow.dismiss();
        }
    }
    public  void  setGridAdapter(RecyclerView recyclerView, int spanCount, BaseRecyclerViewAdapter adapter, OnRecyclerItemClickListener onRecyclerItemClickListener){
        GridLayoutManager mgr = new GridLayoutManager(activity, spanCount);
        recyclerView.setLayoutManager(mgr);
        adapter.setOnRecyclerItemClickListener(onRecyclerItemClickListener);
        recyclerView.setAdapter(adapter);
    }
}
