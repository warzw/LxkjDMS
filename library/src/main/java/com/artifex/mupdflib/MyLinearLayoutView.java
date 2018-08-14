package com.artifex.mupdflib;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by dell on 2018/7/24.
 */

public class MyLinearLayoutView extends LinearLayout {
    private SearchTextAdapter.OnItemClick onItemClick;
    public MyLinearLayoutView(Context context) {
        super(context);
    }

    public MyLinearLayoutView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLinearLayoutView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs);
    }
    public void setOnItemClick(SearchTextAdapter.OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }
    float y = 0;
    float y2 = 0;
    float y3 = 0;
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {  //分发
        if (ev.getAction() == MotionEvent.ACTION_DOWN) { // 按下
            y = ev.getY();
        }else  if (ev.getAction() == MotionEvent.ACTION_MOVE){  //移动
            y2 = ev.getY();
        }else if (ev.getAction() == MotionEvent.ACTION_UP||ev.getAction() == MotionEvent.ACTION_POINTER_UP) {//抬起
            y3 = ev.getY();
            if (y3 == y&&onItemClick!=null) {
                onItemClick.itemClick(this, (Integer) getTag());
            }
            Log.i("ACTION_UPdispatch",y3+"--"+(y)+"--"+y2);
        }
      return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) { // 按下
            y = ev.getY();
        }else  if (ev.getAction() == MotionEvent.ACTION_MOVE){  //移动
            y2 = ev.getY();
        }else if (ev.getAction() == MotionEvent.ACTION_UP||ev.getAction() == MotionEvent.ACTION_POINTER_UP) {//抬起
            y3 = ev.getY();
            if (y3 == y&&onItemClick!=null) {
                onItemClick.itemClick(this, (Integer) getTag());
            }
            Log.i("ACTION_UPdispatch",y3+"--"+(y)+"--"+y2);
        }

        return super.onInterceptTouchEvent(ev);
    }
}
