package lxkj.train.com.mvp.presenter.presenter_impl;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.artifex.mupdflib.MuPDFActivity;
import com.google.protobuf.ByteString;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import lxkj.train.com.adapter.ShowFileAdapter;
import lxkj.train.com.databinding.ActivityShowFileFragmentBinding;
import lxkj.train.com.entity.DirectoryOrFileEntity;
import lxkj.train.com.interfaces.OnRecyclerItemClickListener;
import lxkj.train.com.interfaces.OnRecyclerItemLongClickListener;
import lxkj.train.com.mvp.view.activity.ReadFileActivity;
import lxkj.train.com.mvp.view.activity.base.BaseActivity;
import lxkj.train.com.overall.OverallData;
import lxkj.train.com.utils.LogUtils;
import lxkj.train.com.utils.SharedPreferencesUtil;
import lxkj.train.com.view.DialogView;

/**
 * Created by dell on 2018/6/15.
 */

public class MyBookmarkFragmentPresenter extends BasePresenter implements OnRecyclerItemClickListener, OnRecyclerItemLongClickListener, DialogView.DialogConfirm {
    private ActivityShowFileFragmentBinding binding;
    private ShowFileAdapter adapter;
    private int tag;
    private List<DirectoryOrFileEntity> datas;
    private DirectoryOrFileEntity directoryOrFileEntity;
    private int position;
    private String jsonData;

    public MyBookmarkFragmentPresenter(BaseActivity activity, ActivityShowFileFragmentBinding binding, int tag) {
        super(activity, binding);
        this.binding = binding;
        this.tag = tag;
        initUi();
    }

    public void initUi() {
        binding.layoutSearch.setVisibility(View.GONE);
        if (tag == 3) {  //我的书签
            jsonData = SharedPreferencesUtil.getStringData(activity, "labelFile", "");
            if (TextUtils.isEmpty(jsonData)) {
                return;
            }

        } else if (tag == 4) {
            jsonData = SharedPreferencesUtil.getStringData(activity, "enshrineFile", "");
            if (TextUtils.isEmpty(jsonData)) {
                return;
            }
        }
        LogUtils.i("jsonFile",jsonData);
        directoryOrFileEntity = gson.fromJson(jsonData, DirectoryOrFileEntity.class);
        datas = directoryOrFileEntity.getDirectoryOrFileEntities();
        initAdapter(datas);
    }

    private void initAdapter(List<DirectoryOrFileEntity> datas) {
        adapter = new ShowFileAdapter(activity, datas, null);
        adapter.setOnRecyclerItemLongClickListener(this);
        setGridAdapter(binding.recyclerView, 4, adapter, this);
    }

    @Override
    public void requestData() {

    }

    @Override
    public void succeed(byte servicetype,int subtype,ByteString bytes) {

    }

    @Override
    public void getItem(View view, List<Object> mDatas, int position) {

        //打开pdf文件
        String url = datas.get(position).getPath();
        if ("pdf".equals(url.substring(url.length() - 3, url.length())) || "PDF".equals(url.substring(url.length() - 3, url.length()))) {
            //打开pdf文件
            Intent intent = new Intent(activity, MuPDFActivity.class);
            intent.setAction(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(new File(url));
            intent.setData(uri);
            intent.putExtra("fileName",datas.get(position).getName());
            intent.putExtra("url",datas.get(position).getPage());
            intent.putExtra("image",datas.get(position).getImage());

            activity.startActivity(intent);

        } else {
            Toast.makeText(activity, "文件错误", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getLongItem(View view, List<Object> mDatas, int position) {
        this.position = position;
        DialogView.hintDialog(activity, this, null, "确定删除书签:" + datas.get(position).getName(), true);
    }

    @Override
    public void confirm() {
        datas.remove(position);
        adapter.notifyDataSetChanged();
        LogUtils.i("labelFile", datas.toString());
        directoryOrFileEntity.setDirectoryOrFileEntities(datas);
        String json = gson.toJson(directoryOrFileEntity, DirectoryOrFileEntity.class);
        if (tag == 3) {  //我的书签
            SharedPreferencesUtil.saveStringData(activity, "labelFile", json);
        } else if (tag == 4) {
            SharedPreferencesUtil.saveStringData(activity, "enshrineFile", json);
        }
    }
}
