package lxkj.train.com.mvp.presenter.presenter_impl;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.artifex.mupdflib.MD5;
import com.example.tutorial.MReqFileDownProto;
import com.google.protobuf.ByteString;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import lxkj.train.com.adapter.ShowFileFragmentPagerAdapter;
import lxkj.train.com.databinding.ActivityShowFileBinding;
import lxkj.train.com.interfaces.SQLDataInterface;
import lxkj.train.com.mvp.view.activity.base.BaseActivity;
import lxkj.train.com.mvp.view.fragment.ShowFileFragment;
import lxkj.train.com.overall.OverallData;
import lxkj.train.com.utils.FileUtil;
import lxkj.train.com.utils.LogUtils;
import lxkj.train.com.utils.Md5Util;
import lxkj.train.com.utils.ProtoUtil;
import lxkj.train.com.utils.RequestUtil;
import lxkj.train.com.utils.SQLiteUtil;
import lxkj.train.com.view.LoadingDialog;

/**
 * Created by dell on 2018/6/12.
 */

public class ShowFilePresenter extends BasePresenter implements ViewPager.OnPageChangeListener ,SQLDataInterface {
    private String[] title = {"资料分类","待读文件", "文件搜索","我的书签","我的收藏"};
    private List<ShowFileFragment>  fragments = new ArrayList<>();
    private ActivityShowFileBinding binding;
    private SQLiteUtil sqLiteUtil =   new SQLiteUtil(OverallData.sdkPath+"/sqlitedb/lxkjbase.db"); //查看车次
    private String table1 = "I_DOCMENTLIST";
    private FileUtil fileUtil;
    public ShowFilePresenter(BaseActivity activity, ActivityShowFileBinding binding) {
        super(activity, binding);
        this.binding = binding;
        fileUtil = new FileUtil();
        sqLiteUtil.sqlQuery(this,table1,"",0,4); //查询资料文件,并写入本地
        initFragment();


    }
    private void initFragment(){
        for (int i = 0; i <5 ; i++) {
            fragments.add(ShowFileFragment.getInstance(i));
        }
        ShowFileFragmentPagerAdapter showFileFragmentPagerAdapter =
                new ShowFileFragmentPagerAdapter(activity.getSupportFragmentManager(),fragments,title);
        binding.vp1.setAdapter(showFileFragmentPagerAdapter);
        binding.tabLayout.setupWithViewPager(binding.vp1);
        binding.vp1.setOffscreenPageLimit(fragments.size());
        binding.vp1.addOnPageChangeListener(this);
    }
    @Override
    public void requestData() {}

    @Override
    public void succeed(byte servicetype,int subtype,ByteString bytes) {
        MReqFileDownProto.MRespBizFileData mMRespBizFileData = ProtoUtil.getMRespBizFileData(bytes);
        assert mMRespBizFileData != null;
        String[] files = mMRespBizFileData.getFilepath().toStringUtf8().split("/");
        String fileDire = null;
        String fileName = null;
        for (int i = 0; i < files.length-1; i++) {
            fileDire+=(files[i]+"/");
        }
        fileName = files[files.length-1];
        fileUtil.saveFile(OverallData.sdkPath+"/lxkj/"+fileDire+"/",fileName,mMRespBizFileData.getData().toByteArray(),new LoadingDialog(activity));
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 3||position ==4) {
            fragments.get(position).myBookmarkFragmentPresenter.initUi();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void getSQLData(List<List<String>> list, String tableName) {
        for (int i = 0; i < list.size(); i++) {
            String fileDire = list.get(i).get(3)+"/";
            String fileName = list.get(i).get(1);
            String path = fileDire+"/" + fileName;
            ByteString byteString = null;
            if (new File(path).exists()) {
                byteString = ByteString.copyFrom(Md5Util.getFileMD5(new File(path)).getBytes());
            }else {
                byteString = ByteString.copyFrom("0".getBytes());
            }
            try {
                LogUtils.i("RequestUtil",byteString.toString()+"---"+path);
                dataModel.getData(RequestUtil.requestByteData((byte)2, (short) 2,
                        ProtoUtil.getMReqFileDown(2,(int) (System.currentTimeMillis()/1000),1,
                                ProtoUtil.getMReqBizFileData(byteString,ByteString.copyFrom(path.getBytes("GBK"))))));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }
}
