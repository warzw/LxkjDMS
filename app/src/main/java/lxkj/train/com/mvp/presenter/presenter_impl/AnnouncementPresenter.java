package lxkj.train.com.mvp.presenter.presenter_impl;


import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;


import com.google.protobuf.ByteString;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmModel;
import lxkj.train.com.R;
import lxkj.train.com.adapter.AnnouncementAdapter;
import lxkj.train.com.databinding.ActivityAnnouncementBinding;

import lxkj.train.com.entity.AnnouncementEntity;
import lxkj.train.com.entity.realm.AnnouncementRealm;
import lxkj.train.com.interfaces.DataBaseRealmInterface;
import lxkj.train.com.interfaces.LifecyclePresenterInterface;
import lxkj.train.com.interfaces.OnRecyclerItemClickListener;
import lxkj.train.com.interfaces.RealmDeleteInterface;
import lxkj.train.com.mvp.view.activity.base.BaseActivity;
import lxkj.train.com.overall.OverallData;
import lxkj.train.com.overall.TextUtil;
import lxkj.train.com.utils.DataBaseUtil;
import lxkj.train.com.utils.LogUtils;
import lxkj.train.com.utils.SharedPreferencesUtil;
import lxkj.train.com.view.DialogView;

/**
 * Created by dell on 2018/6/21.
 */

public class AnnouncementPresenter extends BasePresenter implements DialogView.DialogConfirm,DialogView.DialogCancel,DialogView.DialogExtra ,OnRecyclerItemClickListener,DataBaseRealmInterface {
    private ActivityAnnouncementBinding binding;
    private List<AnnouncementEntity> datas = new ArrayList<>();
    private AnnouncementAdapter adapter;


    public AnnouncementPresenter(BaseActivity activity, ActivityAnnouncementBinding binding) {
        super(activity, binding);
        this.binding = binding;
        initUi();
    }
    private void initUi(){
        List<AnnouncementRealm> realms =dataBaseUtil.getQueryData(AnnouncementRealm.class);
        for (int i = 0; i <realms.size() ; i++) {
            AnnouncementEntity announcementEntity = new AnnouncementEntity();
            announcementEntity.setIsShow(realms.get(i).getIsShow());
            announcementEntity.setTime(realms.get(i).getTime());
            announcementEntity.setContent(realms.get(i).getContent());
            announcementEntity.setTitle(realms.get(i).getTitle());
            datas.add(announcementEntity);
        }
        activity.mTitle_bar.setCentreText("公告查看");
        activity.mTitle_bar.setRight_tv("公告分类");
        setOnViewClick(activity.mTitle_bar.getRight_tv());
        initAdapter(datas);
    }
    private void initAdapter(List<AnnouncementEntity> datas){
        if (datas == null) {
            return;
        }
        adapter = new AnnouncementAdapter(activity,datas,null);
        setLinearAdapter(binding.recyclerView,RecyclerView.VERTICAL,adapter,this);
    }
    @Override
    public void onViewClick(View v) {
        super.onViewClick(v);
        if (v.getId() == R.id.right_tv) { //右上角筛选
            DialogView.shareBbenefitQueryDialog(activity,this, this,this, "全部", "上级要求","安全通报");
        }
    }

    @Override
    public void requestData() {

    }

    @Override
    public void succeed(byte servicetype,int subtype,ByteString bytes) {

    }

    @Override
    public void confirm(){ //全部
        initAdapter(datas);
    }

    @Override
    public void cancel() { //上级要求
        List<AnnouncementEntity> list = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            if ("上级要求:".equals(datas.get(i).getTitle())) {
                list.add(datas.get(i));
            }
        }
        initAdapter(list);
    }

    @Override
    public void extra() {  //安全通报
        List<AnnouncementEntity> list = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            if ("安全通报:".equals(datas.get(i).getTitle())) {
                list.add(datas.get(i));
            }
        }
        initAdapter(list);
    }

    @Override
    public void getItem(View view, List<Object> mDatas, int position) {
        if ("0".equals(datas.get(position).getIsShow())|| TextUtils.isEmpty(datas.get(position).getIsShow())) {
            datas.get(position).setIsShow("8");
            adapter.notifyDataSetChanged();
        }
    }
    public void onPause() {
        int announcementIsShow = View.GONE;
        for (int i = 0; i < datas.size(); i++) {
            if ("0".equals(datas.get(i).getIsShow())) { //有显示，说明外层还是未读
                announcementIsShow = View.VISIBLE;
            }
        }
        SharedPreferencesUtil.saveStringData(activity,"announcementIsShow",""+announcementIsShow);
        LogUtils.i("announcementIsShow",""+announcementIsShow);
        if (dataBaseUtil.getQueryData(AnnouncementRealm.class)==null||dataBaseUtil.getQueryData(AnnouncementRealm.class).size()==0) {
            dataBaseUtil.addData(this);  //从新添加数据库
        }
    }
    @Override
    public void getRealm(Realm realm) {
        for (int i = 0; i < datas.size(); i++) {
            AnnouncementRealm realm1 = new AnnouncementRealm();
            realm1.setContent(datas.get(i).getContent());
            realm1.setIsShow(datas.get(i).getIsShow());
            realm1.setTime(datas.get(i).getTime());
            realm1.setTitle(datas.get(i).getTitle());
            realm.copyToRealm(realm1);
        }
    }
}
