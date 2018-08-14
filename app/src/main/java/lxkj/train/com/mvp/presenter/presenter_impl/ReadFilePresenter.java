package lxkj.train.com.mvp.presenter.presenter_impl;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;


import com.artifex.mupdflib.MuPDFActivity;
import com.google.protobuf.ByteString;

import java.io.File;
import java.util.ArrayList;

import lxkj.train.com.R;
import lxkj.train.com.databinding.ActivityWebViewWordBinding;
import lxkj.train.com.entity.DirectoryOrFileEntity;
import lxkj.train.com.mvp.view.activity.base.BaseActivity;
import lxkj.train.com.utils.FileUtil;
import lxkj.train.com.utils.LogUtils;
import lxkj.train.com.utils.SharedPreferencesUtil;
import lxkj.train.com.view.DialogView;

/**
 * Created by dell on 2018/6/11.
 */

public class ReadFilePresenter extends BasePresenter   {
    private ActivityWebViewWordBinding binding;
    private int page;
    private DirectoryOrFileEntity labelFileDirectoryOrFileEntity = new DirectoryOrFileEntity();
    private DirectoryOrFileEntity enshrineFileDirectoryOrFileEntity = new DirectoryOrFileEntity();


    public ReadFilePresenter(BaseActivity activity, ActivityWebViewWordBinding binding) {
        super(activity, binding);
        this.binding = binding;
        iniUi();
        requestData();
    }

    private void iniUi() {
        activity.mTitle_bar.setCentreText(activity.getIntent().getStringExtra("fileName"));
        View view = LayoutInflater.from(activity).inflate(R.layout.titlebar_right_layout, null);
        setOnViewClick(view.findViewById(R.id.tv_1), view.findViewById(R.id.tv_2));
        activity.mTitle_bar.addRightLayout(view);
        String labelFile = SharedPreferencesUtil.getStringData(activity, "labelFile", "");
        if (!TextUtils.isEmpty(labelFile)) {
            labelFileDirectoryOrFileEntity = gson.fromJson(labelFile, DirectoryOrFileEntity.class);
        } else {
            labelFileDirectoryOrFileEntity.setDirectoryOrFileEntities(new ArrayList<DirectoryOrFileEntity>());
        }
        String enshrineFile = SharedPreferencesUtil.getStringData(activity, "enshrineFile", "");
        if (!TextUtils.isEmpty(enshrineFile)) {
            enshrineFileDirectoryOrFileEntity = gson.fromJson(enshrineFile, DirectoryOrFileEntity.class);
        } else {
            enshrineFileDirectoryOrFileEntity.setDirectoryOrFileEntities(new ArrayList<DirectoryOrFileEntity>());
        }
    }

    @Override
    public void onViewClick(View v) {
        super.onViewClick(v);
        if (v.getId() == R.id.tv_1) { //标签
            DirectoryOrFileEntity directoryOrFileEntity = new DirectoryOrFileEntity();
            directoryOrFileEntity.setName(activity.getIntent().getStringExtra("fileName"));
            directoryOrFileEntity.setPath(activity.getIntent().getStringExtra("url"));
            directoryOrFileEntity.setType("file");
            directoryOrFileEntity.setImage(activity.getIntent().getIntExtra("image", 0));
            directoryOrFileEntity.setReadTag(View.GONE);
            directoryOrFileEntity.setPage(page);
            labelFileDirectoryOrFileEntity.getDirectoryOrFileEntities().add(directoryOrFileEntity);
            String json = gson.toJson(labelFileDirectoryOrFileEntity, DirectoryOrFileEntity.class);
            SharedPreferencesUtil.saveStringData(activity, "labelFile", json);
            DialogView.hintDialog(activity, null, null, "添加成功", false);
        } else if (v.getId() == R.id.tv_2) {  //收藏
            DirectoryOrFileEntity directoryOrFileEntity = new DirectoryOrFileEntity();
            directoryOrFileEntity.setName(activity.getIntent().getStringExtra("fileName"));
            directoryOrFileEntity.setPath(activity.getIntent().getStringExtra("url"));
            directoryOrFileEntity.setType("file");
            directoryOrFileEntity.setImage(activity.getIntent().getIntExtra("image", 0));
            directoryOrFileEntity.setReadTag(View.GONE);
            enshrineFileDirectoryOrFileEntity.getDirectoryOrFileEntities().add(directoryOrFileEntity);
            String json = gson.toJson(enshrineFileDirectoryOrFileEntity, DirectoryOrFileEntity.class);
            SharedPreferencesUtil.saveStringData(activity, "enshrineFile", json);
            DialogView.hintDialog(activity, null, null, "添加成功", false);
            LogUtils.i("jsonFile", json);
        }
    }

    @Override
    public void requestData() {
        String url = activity.getIntent().getStringExtra("url");
        if ("pdf".equals(url.substring(url.length() - 3, url.length())) || "PDF".equals(url.substring(url.length() - 3, url.length()))) {
            //打开pdf文件
            Intent intent = new Intent(activity, MuPDFActivity.class);
            intent.setAction(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(new File(url));
            intent.setData(uri);
            activity.startActivity(intent);

        } else {
            Toast.makeText(activity, "文件错误", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void succeed(byte servicetype,int subtype,ByteString bytes) {

    }

}
