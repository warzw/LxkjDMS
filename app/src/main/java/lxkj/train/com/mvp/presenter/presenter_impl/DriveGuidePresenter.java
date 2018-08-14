package lxkj.train.com.mvp.presenter.presenter_impl;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.text.TextUtils;
import android.view.View;

import com.google.protobuf.ByteString;

import java.util.ArrayList;
import java.util.List;

import lxkj.train.com.R;
import lxkj.train.com.adapter.DriveGuideAdapter;
import lxkj.train.com.databinding.ActivityDriveGuideBinding;
import lxkj.train.com.entity.DriveGuideItemEntity;
import lxkj.train.com.interfaces.OnRecyclerItemClickListener;
import lxkj.train.com.interfaces.OnRecyclerItemLongClickListener;
import lxkj.train.com.mvp.view.activity.DriveGuideActivity;
import lxkj.train.com.mvp.view.activity.SimulateGuideActivity;
import lxkj.train.com.mvp.view.activity.base.BaseActivity;
import lxkj.train.com.overall.TextUtil;
import lxkj.train.com.view.DialogView;

/**
 * Created by dell on 2018/6/25.
 */

public class DriveGuidePresenter extends BasePresenter implements OnRecyclerItemClickListener,DialogView.DialogConfirm,DialogView.DialogCancel {
    private List<DriveGuideItemEntity> datas = new ArrayList<>();

    private  DriveGuideAdapter driveGuideAdapter;
    private  String flag;

    public DriveGuidePresenter(BaseActivity activity, ActivityDriveGuideBinding binding) {
        super(activity, binding);
        flag = activity.getIntent().getStringExtra("flag");
        initData();
        driveGuideAdapter = new DriveGuideAdapter(activity,datas,null);
        setGridAdapter(binding.recyclerView,2,driveGuideAdapter,this);
    }
    private void initData(){
        activity.mTitle_bar.setCentreText(activity.getIntent().getStringExtra("title"));
        activity.mTitle_bar.setRight_tv("下一步");
        setOnViewClick(activity.mTitle_bar.getRight_tv());
        if (TextUtils.isEmpty(flag)) {  //第一步
            for (int i = 0; i < TextUtil.textTitle.length; i++) {
                DriveGuideItemEntity driveGuideItemEntity = new DriveGuideItemEntity();
                driveGuideItemEntity.setTextTitle(TextUtil.textTitle[i]);
                driveGuideItemEntity.setTextContent(TextUtil.textContent[i]);
                datas.add(driveGuideItemEntity);
            }
        }else if ("LibraryOut".equals(flag)) {  //库出
            for (int i = 0; i < TextUtil.kTextTitle.length; i++) {
                DriveGuideItemEntity driveGuideItemEntity = new DriveGuideItemEntity();
                driveGuideItemEntity.setTextTitle(TextUtil.kTextTitle[i]);
                driveGuideItemEntity.setTextContent(TextUtil.kTextContent[i]);
                datas.add(driveGuideItemEntity);
            }
        }else if ("ContinueToDrive".equals(flag)) { //继乘
            for (int i = 0; i < TextUtil.jTextTitle.length; i++) {
                DriveGuideItemEntity driveGuideItemEntity = new DriveGuideItemEntity();
                driveGuideItemEntity.setTextTitle(TextUtil.jTextTitle[i]);
                driveGuideItemEntity.setTextContent(TextUtil.jTextContent[i]);
                datas.add(driveGuideItemEntity);
            }
        }


    }
    @Override
    public void requestData() {

    }

    @Override
    public void succeed(byte servicetype,int subtype,ByteString bytes) {

    }

    @Override
    public void onViewClick(View v) {
        super.onViewClick(v);
        if (v.getId() == R.id.right_tv) {  //点击下一步
            if (TextUtils.isEmpty(flag)) {
                for (int i = 0; i < datas.size(); i++) {
                    if (!datas.get(i).isCheck()) {
                        DialogView.hintDialog(activity,null,null,"你有未完成项，不能进行下一步",false);
                        return;
                    }
                }
                DialogView.shareBbenefitQueryDialog(activity,this,this,null,"库出","继乘","");
            }else {
                Intent intent = new Intent(activity, SimulateGuideActivity.class);
                activity.startActivity(intent);
            }
        }
    }

    @Override
    public void getItem(View view, List<Object> mDatas, int position) {
        if (datas.get(position).isCheck()) {
            datas.get(position).setCheck(false);
        }else {
            datas.get(position).setCheck(true);
            DialogView.hintDialog(activity,null,datas.get(position).getTextTitle(),datas.get(position).getTextContent(),false);
        }
        driveGuideAdapter.notifyDataSetChanged();
    }

    @Override
    public void confirm() { //库出
        Intent intent = new Intent(activity,DriveGuideActivity.class);
        intent.putExtra("flag","LibraryOut");
        intent.putExtra("title","库出");
        activity.startActivity(intent);
    }

    @Override
    public void cancel() { //继乘
        Intent intent = new Intent(activity,DriveGuideActivity.class);
        intent.putExtra("title","继乘");
        intent.putExtra("flag","ContinueToDrive");
        activity.startActivity(intent);
    }
}
