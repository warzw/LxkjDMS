package lxkj.train.com.mvp.presenter.presenter_impl;


import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.google.protobuf.ByteString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lxkj.train.com.R;
import lxkj.train.com.adapter.CallTheWatchAdapter;
import lxkj.train.com.adapter.LableAdapter;
import lxkj.train.com.databinding.ActivityUnloadBinding;
import lxkj.train.com.entity.base.BaseEntity;
import lxkj.train.com.interfaces.OnRecyclerItemClickListener;
import lxkj.train.com.mvp.view.activity.base.BaseActivity;
import lxkj.train.com.utils.EditTextUtil;
import lxkj.train.com.utils.SharedPreferencesUtil;
import lxkj.train.com.view.TimePopupWindow;

/**
 * Created by dell on 2018/8/9.
 */

public class UnloadPresenter extends BasePresenter implements OnRecyclerItemClickListener ,TimePopupWindow.TimeLiCallBack {
    private ActivityUnloadBinding binding;
    private List<BaseEntity> lableDatas = new ArrayList<>();
    private List<BaseEntity> momentLableDatas = new ArrayList<>();
    private LableAdapter lableAdapter;
    private String time1 = "";
    private String time2 = "";
    public UnloadPresenter(BaseActivity activity, ActivityUnloadBinding binding) {
        super(activity, binding);
        this.binding = binding;
        activity.mTitle_bar.setCentreText("退勤转储");
        EditTextUtil.closeKeybord(binding.etSearch,activity);
        setOnViewClick(binding.ivSearch,binding.ivCalendar);
        initData();
    }
    private void initData(){
        String lablestr = SharedPreferencesUtil.getStringData(activity,"lableDatasUnload","");
        if (!TextUtils.isEmpty(lablestr)) { //说明有过搜索记录
            String[] lablestrs = lablestr.split(",");
            if (lablestrs.length>0) {//添加过两条
                for (int i = 0; i <lablestrs.length ; i++) {
                    BaseEntity baseEntity = new BaseEntity();
                    baseEntity.setContent(lablestrs[i]);
                    lableDatas.add(baseEntity);
                }
            }else {
                BaseEntity baseEntity = new BaseEntity();
                baseEntity.setContent(lablestr);
                lableDatas.add(baseEntity);
            }
            momentLableDatas.addAll(lableDatas);
        }
        lableAdapter = new LableAdapter(activity,lableDatas,null);
        setGridAdapter(binding.labelRecycler,8, lableAdapter,this);
    }
    @Override
    public void onViewClick(View v) {
        super.onViewClick(v);
        EditTextUtil.closeKeybord(binding.etSearch,activity);
        if (v.getId() == R.id.iv_search) { //搜索
            requestData();
        }else if (v.getId() == R.id.iv_calendar) { //点击日历查询
            new TimePopupWindow().showTime(activity,activity.mView,"2018-08-06",activity.sdf_date.format(new Date()), this);
        }
    }
    @Override
    public void requestData() { //搜索数据库
        setLableDatas();
    }

    @Override
    public void succeed(byte servicetype, int subtype, ByteString bytes) {

    }

    @Override
    public void getItem(View view, List<Object> mDatas, int position) {
        binding.etSearch.setText(lableDatas.get(position).getContent());
        requestData();
    }
    private void setLableDatas(){
        if (TextUtils.isEmpty(binding.etSearch.getText().toString().trim())) {
            return;
        }
        BaseEntity baseEntity = new BaseEntity();
        baseEntity.setContent(binding.etSearch.getText().toString().trim());
        if (momentLableDatas.size()==0) {
            momentLableDatas.addAll(lableDatas);
        }
        for (int i = 0; i < momentLableDatas.size(); i++) {
            if (momentLableDatas.get(i).getContent().equals(binding.etSearch.getText().toString().trim())) {
                momentLableDatas.remove(i);
            }
        }
        lableDatas.clear();
        lableDatas.add(baseEntity);
        if (momentLableDatas.size()<8) {
            lableDatas.addAll(momentLableDatas); //标签栏的数据
        }else {
            for (int i = 0; i < 7; i++) {
                lableDatas.add(momentLableDatas.get(i));
            }
        }
        momentLableDatas.clear();
        momentLableDatas.addAll(lableDatas);
        if (lableAdapter != null) {
            lableAdapter.notifyDataSetChanged();
        }
        String tableText = lableDatas.get(0).getContent();
        for (int i = 1; i < lableDatas.size(); i++) {
            tableText+=(","+lableDatas.get(i).getContent());
        }
        SharedPreferencesUtil.saveStringData(activity,"lableDatasUnload",tableText);//保存标签栏的数据
    }

    @Override
    public void getTime(String time, String time2) {

    }
}
