package lxkj.train.com.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;

import java.util.ArrayList;
import java.util.List;

import lxkj.train.com.interfaces.OnRollInterface;

/**
 * Created by dhc on 2018/3/20.
 */

public class RollSwipeToLoadLayout extends SwipeToLoadLayout {
    private float y=0;
    private float y2= 0;
    private OnRollInterface onRollInterface;
    private List<Float> datas = new ArrayList<>();
    public RollSwipeToLoadLayout(Context context) {
        super(context);
    }

    public RollSwipeToLoadLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RollSwipeToLoadLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public void setOnRollInterface(OnRollInterface onRollInterface){
        this.onRollInterface = onRollInterface;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
        }else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (datas.size()<2) {
                datas.add(event.getY());
            }
        }
        if (datas.size()==2&&(!datas.get(0).equals(datas.get(1)))) {
            if (datas.get(0)<datas.get(1)) {  //向下滑动
                if (onRollInterface != null) {
                    datas.clear();
                    onRollInterface.swipedown();
                }
            }else { //向上滑动
                if (onRollInterface != null) {
                    datas.clear();
                    onRollInterface.swipeup();
                }
            }
        }
        return super.onTouchEvent(event);
    }
}
