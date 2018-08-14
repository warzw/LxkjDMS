package lxkj.train.com.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import lxkj.train.com.R;
import lxkj.train.com.utils.ScreenUtils;
import lxkj.train.com.utils.StringUtil;

/**
 * Created by dhc on 2017/6/7.
 */

public class TimePopupWindow implements View.OnClickListener, PopupWindow.OnDismissListener {

    private DatePicker datePickerStart1;
    private DatePicker datePickerStart2;
    private Calendar c;
    private int year1;
    private int month1;
    private int day1;
    private TimeLiCallBack timeLiCallBack;
    private PopupWindow popupWindow;
    private StringBuffer lend_time1;
    private StringBuffer lend_time2;
    private WindowManager.LayoutParams lp;
    private Activity activity;
    private SimpleDateFormat sdf;
    private String startTime = "";
    private String endTime = "";
    private String minTime;
    private String maxTime;
    private int width;
    private int height;

    public void showTime(Context context, View layoutView, String minTime, String maxTime, TimeLiCallBack timeLiCallBack) {
        activity = (Activity) context;
        this.timeLiCallBack = timeLiCallBack;
        this.minTime = minTime;
        this.maxTime = maxTime;
        sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        LayoutInflater inflater = LayoutInflater.from(context);
        View son_layout = inflater.inflate(R.layout.popupwindow_time, null);
        TextView tv_cancel = (TextView) son_layout.findViewById(R.id.tv_cancel);
        TextView tv_confirm = (TextView) son_layout.findViewById(R.id.tv_confirm);
        tv_cancel.setOnClickListener(this);
        tv_confirm.setOnClickListener(this);
        datePickerStart1 = (DatePicker) son_layout.findViewById(R.id.datePickerStart);
        datePickerStart2 = (DatePicker) son_layout.findViewById(R.id.datePickerStart2);
        getWH(son_layout);
        resizePikcer(datePickerStart1);
        resizePikcer(datePickerStart2);
        initTime(datePickerStart1, datePickerStart2, minTime, maxTime);  //初始化时间选择器
        popupWindow = new PopupWindow(son_layout,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        //产生背景变暗效果
        lp = activity.getWindow().getAttributes();
        lp.alpha = 0.4f;
        activity.getWindow().setAttributes(lp);
        //设置popWindow弹出窗体可点击
        popupWindow.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        popupWindow.setBackgroundDrawable(dw);
        // 设置popWindow的显示和消失动画
        popupWindow.setAnimationStyle(R.style.PopupAnimation);
        popupWindow.showAtLocation(layoutView, Gravity.CENTER, 0, 120);
        popupWindow.setOnDismissListener(this);
    }

    private void initTime(DatePicker datePickerStart1, DatePicker datePickerStart2, String minTime, String maxTime) {
        Calendar c = Calendar.getInstance();
        year1 = c.get(Calendar.YEAR);
        c.add(Calendar.MONTH, 1);
        month1 = c.get(Calendar.MONTH);
        day1 = c.get(Calendar.DAY_OF_MONTH);
        if (!TextUtils.isEmpty(minTime)&&!TextUtils.isEmpty(maxTime)) {
            datePickerStart1.setMinDate(StringUtil.stringToLong(minTime, "yyyy-MM-dd"));
            datePickerStart1.setMaxDate(StringUtil.stringToLong(maxTime, "yyyy-MM-dd"));
        }
        if (!TextUtils.isEmpty(minTime)&&!TextUtils.isEmpty(maxTime)) {
            datePickerStart2.setMinDate(StringUtil.stringToLong(minTime, "yyyy-MM-dd"));
            datePickerStart2.setMaxDate(StringUtil.stringToLong(maxTime, "yyyy-MM-dd"));
        }
        datePickerStart1.init(datePickerStart1.getYear(), datePickerStart1.getMonth(), datePickerStart1.getDayOfMonth(), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                year1 = year;
                month1 = monthOfYear + 1;
                day1 = dayOfMonth;
                lend_time1 = new StringBuffer();
                lend_time1.append(year1);
                lend_time1.append("-");
                if (month1 < 10) {
                    lend_time1.append("0" + month1);
                } else {
                    lend_time1.append(month1);
                }
                lend_time1.append("-");
                if (day1 < 10) {
                    lend_time1.append("0" + day1);
                } else {
                    lend_time1.append(day1);
                }
                startTime = lend_time1.toString();
//                if (1 == tag) {  //开始时间
//                    startTime=lend_time.toString();
//                }else if (2 == tag){   //结束时间
//                    endTime=lend_time.toString();
//                }
            }
        });
        datePickerStart2.init(datePickerStart2.getYear(), datePickerStart2.getMonth(), datePickerStart2.getDayOfMonth(), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                // TODO Auto-generated method stub
                year1 = year;
                month1 = monthOfYear + 1;
                day1 = dayOfMonth;
                lend_time2 = new StringBuffer();
                lend_time2.append(year1);
                lend_time2.append("-");
                if (month1 < 10) {
                    lend_time2.append("0" + month1);
                } else {
                    lend_time2.append(month1);
                }
                lend_time2.append("-");
                if (day1 < 10) {
                    lend_time2.append("0" + day1);
                } else {
                    lend_time2.append(day1);
                }
                endTime = lend_time2.toString();
//                if (1 == tag) {  //开始时间
//                    startTime=lend_time.toString();
//                }else if (2 == tag){   //结束时间
//                    endTime=lend_time.toString();
//                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_cancel) {  //取消

        } else if (v.getId() == R.id.tv_confirm) {
            if (timeLiCallBack != null) {
                if (TextUtils.isEmpty(startTime)) {
                    startTime = maxTime;
                }
                if (TextUtils.isEmpty(endTime)) {
                    endTime = maxTime;
                }
                timeLiCallBack.getTime("" + startTime, endTime);
            }
        }
        lp.alpha = 1f;
        activity.getWindow().setAttributes(lp);
        popupWindow.dismiss();


    }

    @Override
    public void onDismiss() {
        lp.alpha = 1f;
        activity.getWindow().setAttributes(lp);
    }

    public interface TimeLiCallBack {
        void getTime(String time, String time2);
    }

    /**
     * 调整FrameLayout大小
     *
     * @param tp
     */
    private void resizePikcer(FrameLayout tp) {
        List<NumberPicker> npList = findNumberPicker(tp);
        for (NumberPicker np : npList) {
            resizeNumberPicker(np);
        }
    }

    /**
     * 得到viewGroup里面的numberpicker组件
     *
     * @param viewGroup
     * @return
     */
    private List<NumberPicker> findNumberPicker(ViewGroup viewGroup) {
        List<NumberPicker> npList = new ArrayList<NumberPicker>();
        View child = null;
        if (null != viewGroup) {
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                child = viewGroup.getChildAt(i);
                if (child instanceof NumberPicker) {
                    npList.add((NumberPicker) child);
                } else if (child instanceof LinearLayout) {
                    List<NumberPicker> result = findNumberPicker((ViewGroup) child);
                    if (result.size() > 0) {
                        return result;
                    }
                }
            }
        }
        return npList;
    }

    private void getWH(View view) {
        int width = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(width, height);
        this.width=view.getMeasuredWidth(); // 获取宽度
        this.height=view.getMeasuredHeight(); // 获取高度
    }

    /*
 * 调整numberpicker大小
 */
    private void resizeNumberPicker(NumberPicker np) {
        ScreenUtils.initScreen(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(height / 10,  (int)(width / 2.5));
        np.setLayoutParams(params);
    }
}
