package lxkj.train.com.mvp.presenter.presenter_impl;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.artifex.mupdflib.MuPDFActivity;
import com.google.protobuf.ByteString;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import lxkj.train.com.R;
import lxkj.train.com.adapter.ShowFileAdapter;
import lxkj.train.com.databinding.ActivityShowFileFragmentBinding;
import lxkj.train.com.entity.DirectoryOrFileEntity;
import lxkj.train.com.interfaces.OnRecyclerItemClickListener;
import lxkj.train.com.mvp.view.activity.ReadFileActivity;
import lxkj.train.com.mvp.view.activity.base.BaseActivity;
import lxkj.train.com.overall.OverallData;
import lxkj.train.com.utils.LogUtils;
import lxkj.train.com.utils.RxJavaUtil;
import lxkj.train.com.utils.SharedPreferencesUtil;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * Created by dell on 2018/6/13.
 */

public class ShowFileFragmentPresenter extends BasePresenter  implements OnRecyclerItemClickListener , Observable.OnSubscribe<String> , Observer<String> {
    private ShowFileAdapter adapter;
    private ActivityShowFileFragmentBinding binding;
    private List<List<DirectoryOrFileEntity>> mList = new ArrayList<>();
    private List<DirectoryOrFileEntity> searchDatas = new ArrayList<>();
    private int tag;
    private List<DirectoryOrFileEntity> datas = new ArrayList<>(); //待读文件的数据源
    public ShowFileFragmentPresenter(BaseActivity activity, ActivityShowFileFragmentBinding binding,int tag) {
        super(activity, binding);
        this.tag = tag;
        this.binding=binding;
        initData();
    }
    private void initData() {
        setOnViewClick(binding.ivSearch,binding.layoutBackground);
        //初始化当前目录下的所有数据
        selecteDatas();
    }
    private void selecteDatas(){
        if (tag == 0) { //所有文件
            initAdapter(OverallData.mDirectoryOrFileEntity.getDirectoryOrFileEntities());
            mList.add(OverallData.mDirectoryOrFileEntity.getDirectoryOrFileEntities());
        }else if (tag == 1) { //待读文件
            readItLaterFile(OverallData.mDirectoryOrFileEntity.getDirectoryOrFileEntities()); //查询待读文件
            initAdapter(datas);
            mList.add(datas);
        }
    }
    private void initAdapter(List<DirectoryOrFileEntity> datas){
        adapter = new ShowFileAdapter(activity, datas, null);
        setGridAdapter(binding.recyclerView, 4, adapter, this);
    }
    private void readItLaterFile(List<DirectoryOrFileEntity> data) { //查询待读文件
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getReadTag() == View.VISIBLE) {  //有一个是显示，就显示
                datas.add(data.get(i));
            }
            if (data.get(i).getDirectoryOrFileEntities()!=null&&data.get(i).getDirectoryOrFileEntities().size()>0) {
                readItLaterFile(data.get(i).getDirectoryOrFileEntities());
            }
        }
    }
    @Override
    public void onViewClick(View view) {
        super.onViewClick(view);
        if (view.getId() == R.id.iv_search) {//搜索查询
            if (!TextUtils.isEmpty(binding.etSearch.getText().toString().trim())) {
                searchDatas.clear();
                searchFileData(OverallData.mDirectoryOrFileEntity.getDirectoryOrFileEntities(),binding.etSearch.getText().toString().trim());
                initAdapter(searchDatas);
                mList.add(searchDatas);
            }
        }else if (view.getId() == R.id.layout_background) { //返回上级
            if (mList.size()>0) {
                mList.remove(mList.size()-1);
                if (mList.size()==0) {
                    activity.finish();
                }else {
                    readTagType(OverallData.mDirectoryOrFileEntity.getDirectoryOrFileEntities(),OverallData.mDirectoryOrFileEntity);
                    initAdapter(mList.get(mList.size()-1));
                }
            }else {
                activity.finish();
            }
            Log.i("mList",""+mList.size());

        }
    }

    private void searchFileData(List<DirectoryOrFileEntity> datas,String key) { //搜索所有文件
        if (TextUtils.isEmpty(key)) {
           return;
        }
        for (int i = 0; i < datas.size(); i++) {
            if (datas.get(i).getName().contains(key)) {
                searchDatas.add(datas.get(i));
            }
            if (datas.get(i).getDirectoryOrFileEntities()!=null&&datas.get(i).getDirectoryOrFileEntities().size()>0) {
                searchFileData(datas.get(i).getDirectoryOrFileEntities(),key);
            }
        }
    }
    @Override
    public void getItem(View view,List<Object> mdatas, int position) {
        DirectoryOrFileEntity directoryOrFileEntity = (DirectoryOrFileEntity) mdatas.get(position);
        if ("directory".equals(directoryOrFileEntity.getType())) { //是目录，进入下级
            initAdapter(directoryOrFileEntity.getDirectoryOrFileEntities());
            mList.add(directoryOrFileEntity.getDirectoryOrFileEntities());
        }else  if ("file".equals(directoryOrFileEntity.getType())) {  //是文件直接打开
            directoryOrFileEntity.setReadTag(View.GONE);
            adapter.notifyDataSetChanged();
            //打开pdf文件
            String url = directoryOrFileEntity.getPath();
            if ("pdf".equals(url.substring(url.length() - 3, url.length())) || "PDF".equals(url.substring(url.length() - 3, url.length()))) {
                //打开pdf文件
                Intent intent = new Intent(activity, MuPDFActivity.class);
                intent.setAction(Intent.ACTION_VIEW);
                Uri uri = Uri.fromFile(new File(url));
                intent.setData(uri);
                intent.putExtra("fileName",directoryOrFileEntity.getName());
                intent.putExtra("url",directoryOrFileEntity.getPath());
                intent.putExtra("image",directoryOrFileEntity.getImage());
                activity.startActivity(intent);

            } else {
                Toast.makeText(activity, "文件错误", Toast.LENGTH_SHORT).show();
            }
        }
        new RxJavaUtil<>(this,this);
    }
    //Android获取一个用于打开PPT文件的intent

    public static Intent getPptFileIntent( String param ){
        Intent intent = new Intent("android.intent.action.VIEW");

        intent.addCategory("android.intent.category.DEFAULT");

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Uri uri = Uri.fromFile(new File(param ));

        intent.setDataAndType(uri, "application/vnd.ms-powerpoint");

        return intent;

    }
    @Override
    public void requestData() {

    }
    @Override
    public void succeed(byte servicetype,int subtype,ByteString bytes) {

    }
    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(String s) {
        LogUtils.i("保存状态",s);
    }

    @Override
    public void call(Subscriber<? super String> subscriber) {
        readTagType(OverallData.mDirectoryOrFileEntity.getDirectoryOrFileEntities(),OverallData.mDirectoryOrFileEntity);
        String json = gson.toJson(OverallData.mDirectoryOrFileEntity,DirectoryOrFileEntity.class);
        SharedPreferencesUtil.saveStringData(activity,"mdatas",json);
        subscriber.onNext("保存成功："+json);
    }
    private void readTagType(List<DirectoryOrFileEntity> datas,DirectoryOrFileEntity directoryOrFileEntity) { //判断文件
        int readTag = View.GONE;
        for (int i = 0; i < datas.size(); i++) {
            if (datas.get(i).getDirectoryOrFileEntities()!=null&&datas.get(i).getDirectoryOrFileEntities().size()>0) {
                readTagType(datas.get(i).getDirectoryOrFileEntities(),datas.get(i));
            }
            if (datas.get(i).getReadTag() == View.VISIBLE) {  //有一个是显示，就显示
                readTag = View.VISIBLE;
            }
            directoryOrFileEntity.setReadTag(readTag);
        }
    }
}
