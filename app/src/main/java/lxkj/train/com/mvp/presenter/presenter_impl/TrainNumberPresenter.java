package lxkj.train.com.mvp.presenter.presenter_impl;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.protobuf.ByteString;

import java.util.ArrayList;
import java.util.List;

import lxkj.train.com.R;
import lxkj.train.com.adapter.LableAdapter;
import lxkj.train.com.adapter.TrainNumberAdapter;
import lxkj.train.com.databinding.ActivityTrainNumberBinding;
import lxkj.train.com.entity.DmsDrawEntity;
import lxkj.train.com.entity.MainEntity;
import lxkj.train.com.entity.TrainNumberEntity;
import lxkj.train.com.entity.base.BaseEntity;
import lxkj.train.com.interfaces.OnRecyclerItemClickListener;
import lxkj.train.com.interfaces.SQLDataInterface;
import lxkj.train.com.mvp.view.activity.base.BaseActivity;
import lxkj.train.com.overall.OverallData;
import lxkj.train.com.overall.TextUtil;
import lxkj.train.com.utils.EditTextUtil;
import lxkj.train.com.utils.LogUtils;
import lxkj.train.com.utils.RxJavaUtil;
import lxkj.train.com.utils.SQLiteUtil;
import lxkj.train.com.utils.SharedPreferencesUtil;
import lxkj.train.com.view.LoadingDialog;
import lxkj.train.com.view.PopupWindows;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * Created by dell on 2018/6/26.
 */

public class TrainNumberPresenter extends BasePresenter implements SQLDataInterface,OnRecyclerItemClickListener {
    private ActivityTrainNumberBinding binding;
    private List<TrainNumberEntity> datas = new ArrayList<>();
    private TrainNumberAdapter adapter;
    private SQLiteUtil sqLiteUtil =   new SQLiteUtil(OverallData.sdkPath+"/sqlitedb/lxkjtrainnums.db"); //查看车次
    private  LoadingDialog dialog;
    private String table1 = "B_TRAINLIST";
    private String table2 = "B_SCHEDULE";
    private String search;
    private PopupWindows popupWindows = new PopupWindows();
    private List<BaseEntity> lableDatas = new ArrayList<>();
    private List<BaseEntity> momentLableDatas = new ArrayList<>();
    private LableAdapter lableAdapter;
    public TrainNumberPresenter(BaseActivity activity, ActivityTrainNumberBinding binding) {
        super(activity, binding);
        this.binding = binding;
        setOnViewClick(binding.ivSearch,binding.etSearch);
        initData();
        initUi();
    }
    private void initData(){
        String lablestr = SharedPreferencesUtil.getStringData(activity,"lableDatasNumber","");
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
        EditTextUtil.disableShowSoftInput(binding.etSearch);

    }
    private void initUi() {
        activity.mTitle_bar.setCentreText("查看车次");
        setOnViewClick(binding.ivSearch);
        dialog = new LoadingDialog(activity);
        adapter = new TrainNumberAdapter(activity, datas, null);
        setLinearAdapter(binding.recyclerView, RecyclerView.VERTICAL, adapter, null);
    }
    private void setLableDatas(){
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
        SharedPreferencesUtil.saveStringData(activity,"lableDatasNumber",tableText);//保存标签栏的数据
    }
    @Override
    public void requestData() {

    }

    @Override
    public void succeed(byte servicetype, int subtype, ByteString bytes) {

    }

    @Override
    public void onViewClick(View v) {
        super.onViewClick(v);
        if (v.getId() == R.id.iv_search) {//搜索
            popupWindows.dismissKey();
            search =binding.etSearch.getText().toString().trim();
            if (TextUtils.isEmpty(search)) {
                Toast.makeText(activity, "请输入搜索内容", Toast.LENGTH_SHORT).show();
                return;
            }
            dialog.show();
            setLableDatas();
            sqLiteUtil.sqlQuery(this,table1,search,2,6);
        }else if (v.getId() == R.id.et_search) { //点击输入文本
            popupWindows.keyPopupWindow(activity,binding.layoutSearch,this);
        }
    }

    @Override
    public void getSQLData(List<List<String>> list,String tableName) {
        LogUtils.i("SQLData",""+list.size());
        dialog.dismiss();
        if (table1.equals(tableName)) { //表一结果
            if (list.size()==0) {
                return;
            }
            dialog.show();
            sqLiteUtil.sqlQuery(this,table2,list.get(0).get(3),1,7);
        }else if (table2.equals(tableName)) { //表二结果
            if (datas.size()>0) {
                datas.clear();
            }
            for (int i = 0; i <list.size() ; i++) {
                TrainNumberEntity trainNumberEntity = new TrainNumberEntity();
                trainNumberEntity.setStr1(search);
                trainNumberEntity.setStr2(list.get(0).get(3)+"--"+list.get(list.size()-1).get(3));
                trainNumberEntity.setStr3(list.get(i).get(3));
                trainNumberEntity.setStr4(list.get(i).get(4));
                trainNumberEntity.setStr5(list.get(i).get(5));
                trainNumberEntity.setStr6(list.get(i).get(6));
                datas.add(trainNumberEntity);
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getItem(View view, List<Object> mDatas, int position) {
        String etText = binding.etSearch.getText().toString().trim();
        if (mDatas.get(position) instanceof BaseEntity) {
            binding.etSearch.setText(lableDatas.get(position).getContent());
            sqLiteUtil.sqlQuery(this,table1,search,2,6);
        }else {
            if ("-1".equals(((MainEntity)mDatas.get(position)).getText())) {
                if (!TextUtils.isEmpty(etText)) {
                    binding.etSearch.setText(etText.substring(0,etText.length()-1));
                }
            }else if ("-2".equals(((MainEntity)mDatas.get(position)).getText())) {
                popupWindows.dismissKey();
            }else {
                binding.etSearch.setText(etText+((MainEntity)mDatas.get(position)).getText());
            }
        }

    }
}
