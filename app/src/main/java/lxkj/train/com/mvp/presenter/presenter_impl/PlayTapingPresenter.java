package lxkj.train.com.mvp.presenter.presenter_impl;

import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.protobuf.ByteString;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lxkj.train.com.adapter.PlayTapingAdapter;
import lxkj.train.com.databinding.ActivityPlayTapingBinding;
import lxkj.train.com.entity.DirectoryOrFileEntity;
import lxkj.train.com.interfaces.LifecyclePresenterInterface;
import lxkj.train.com.interfaces.OnRecyclerItemClickListener;
import lxkj.train.com.mvp.view.activity.base.BaseActivity;
import lxkj.train.com.overall.OverallData;
import lxkj.train.com.utils.LogUtils;
import lxkj.train.com.utils.SearchFile;

/**
 * Created by dell on 2018/6/26.
 */

public class PlayTapingPresenter extends BasePresenter implements OnRecyclerItemClickListener, LifecyclePresenterInterface {
    private ActivityPlayTapingBinding binding;
    private MediaPlayer player;
    private PlayTapingAdapter adapter;
    private List<DirectoryOrFileEntity> datas;

    public PlayTapingPresenter(BaseActivity activity, ActivityPlayTapingBinding binding) {
        super(activity, binding);
        this.binding = binding;
        activity.mLifecyclePresenterInterface = this;
        initUi();
    }

    private void initUi() {
        activity.mTitle_bar.setCentreText("播放录音");
        player = new MediaPlayer();
        datas = new SearchFile().searchFile(new File(OverallData.sdkPath + "/taping/"), new ArrayList<DirectoryOrFileEntity>());
        if (datas != null && datas.size() > 0) {
            adapter = new PlayTapingAdapter(activity, datas, null);
            setLinearAdapter(binding.recyclerView, RecyclerView.VERTICAL, adapter, this);
        }
        for (int i = 0; i < datas.size(); i++) {
            LogUtils.i("dataPlayTaping",datas.get(i).getName());
        }
    }

    @Override
    public void requestData() {

    }

    @Override
    public void succeed(byte servicetype,int subtype,ByteString bytes) {

    }

    @Override
    public void getItem(View view, List<Object> mDatas, int position) {
        play(datas.get(position).getPath());

    }

    /**
     * 播放录音
     */
    private void play(String voicePath) {
        if (player != null) {
            player.stop();
            player.reset();
            try {
                //设置语言的来源
                player.setDataSource(voicePath);
                //初始化
                player.prepare();
                //开始播放
                player.start();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {
        if (player != null) {
            player.stop();
            player.release();
            player = null;
            System.gc();
        }
    }
}
