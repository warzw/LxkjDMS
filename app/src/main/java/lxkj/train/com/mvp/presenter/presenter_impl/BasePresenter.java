package lxkj.train.com.mvp.presenter.presenter_impl;


import android.databinding.ViewDataBinding;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.protobuf.ByteString;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import lxkj.train.com.adapter.base.BaseRecyclerViewAdapter;
import lxkj.train.com.interfaces.LifecyclePresenterInterface;
import lxkj.train.com.interfaces.OnRecyclerItemClickListener;
import lxkj.train.com.mvp.model.model_Impl.BaseModelImapl;
import lxkj.train.com.mvp.model.model_interface.DataModel;
import lxkj.train.com.mvp.view.activity.base.BaseActivity;
import lxkj.train.com.overall.OverallData;
import lxkj.train.com.utils.DataBaseUtil;
import lxkj.train.com.utils.TTSUtil;

/**
 * Created by dhc on 2017/6/28.
 */

public abstract class BasePresenter implements View.OnClickListener {
    public BaseActivity activity;
    public String currentTime_ymd;
    public String currentTime_hms;
    public String currentTime_week;
    public  DataBaseUtil dataBaseUtil;
    public  Gson gson;
    public  TTSUtil tts;
    public DataModel dataModel;
    public BasePresenter(BaseActivity activity, ViewDataBinding binding) {
        this.activity = activity;
        dataModel = new BaseModelImapl(activity,this);
        currentTime_ymd = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        currentTime_hms = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        currentTime_week = getWeek();
        dataBaseUtil = DataBaseUtil.getInstanc();
        gson = new Gson();
        tts = new TTSUtil(activity);
    }
    /*获取星期几*/
    public  String getWeek() {
        Calendar cal = Calendar.getInstance();
        int i = cal.get(Calendar.DAY_OF_WEEK);
        switch (i) {
            case 1:
                return "星期日";
            case 2:
                return "星期一";
            case 3:
                return "星期二";
            case 4:
                return "星期三";
            case 5:
                return "星期四";
            case 6:
                return "星期五";
            case 7:
                return "星期六";
            default:
                return "";
        }
    }
    public  void  setLinearAdapter(RecyclerView recyclerView,int orientation, BaseRecyclerViewAdapter adapter, OnRecyclerItemClickListener onRecyclerItemClickListener){
        LinearLayoutManager mgr = new LinearLayoutManager(activity);
        mgr.setOrientation(orientation);
        recyclerView.setLayoutManager(mgr);
        adapter.setOnRecyclerItemClickListener(onRecyclerItemClickListener);
        recyclerView.setAdapter(adapter);
    }
    public  void  setGridAdapter(RecyclerView recyclerView,int spanCount, BaseRecyclerViewAdapter adapter, OnRecyclerItemClickListener onRecyclerItemClickListener){
        GridLayoutManager mgr = new GridLayoutManager(activity, spanCount);
        recyclerView.setLayoutManager(mgr);
        adapter.setOnRecyclerItemClickListener(onRecyclerItemClickListener);
        recyclerView.setAdapter(adapter);
    }
    public void setOnViewClick(View... views){
        for (int i = 0; i <views.length ; i++) {
            views[i].setOnClickListener(this);
        }
    }
    @Override
    public void onClick(View v) {
        if (OverallData.exceptionSwitch) {
            try {
                onViewClick(v);
            } catch (Exception e) {

            }
        } else {
            onViewClick(v);
        }
    }
    public String getTime(int time){
        return new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault()).format(new Date(((long)(time))*1000L));
    }
    public void onViewClick(View v){
    }
    public abstract void requestData();   //请求数据

    public abstract void succeed(byte servicetype,int subtype,ByteString bytes);  //返回数据
}
