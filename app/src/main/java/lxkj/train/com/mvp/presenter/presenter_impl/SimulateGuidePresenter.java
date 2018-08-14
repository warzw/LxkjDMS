package lxkj.train.com.mvp.presenter.presenter_impl;

import android.app.Activity;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.protobuf.ByteString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lxkj.train.com.adapter.SimulateGuideAdapter;
import lxkj.train.com.databinding.ActivitySimulateGuideBinding;
import lxkj.train.com.entity.DmsDrawEntity;
import lxkj.train.com.entity.MainEntity;
import lxkj.train.com.interfaces.OnRecyclerItemClickListener;
import lxkj.train.com.mvp.view.activity.RecordActivity;
import lxkj.train.com.mvp.view.activity.base.BaseActivity;
import lxkj.train.com.utils.LogUtils;
import lxkj.train.com.utils.MillerCoordinate;
import lxkj.train.com.utils.ScreenUtils;
import lxkj.train.com.view.DialogView;

/**
 * Created by dell on 2018/6/29.
 */

public class SimulateGuidePresenter extends BasePresenter implements OnRecyclerItemClickListener{
    private ActivitySimulateGuideBinding binding;
    private List<MainEntity> datas = new ArrayList<>();
    private String[] texts = {"站场示意图","股道号查询","操纵示意图","施工明示图","拍照录音","提示信息","电子手帐","临时调令","时刻表","进出库操作"};
    private  Intent intent;
    private int screenW;
    private int screenH;
    private List<DmsDrawEntity> canvasDatas = new ArrayList<>();
    private int x;
    private int y;
    private int xBottom;
    private int yBottom;
    public SimulateGuidePresenter(BaseActivity activity, ActivitySimulateGuideBinding binding) {
        super(activity, binding);
        this.binding = binding;
        intent = new Intent();
        initData();
        initUi();
    }
    private void initData(){
        initScreen();
        DmsDrawEntity map1 = new DmsDrawEntity();
        map1.setX(x);    map1.setY(y);    map1.setName("北京西");
        canvasDatas.add(map1);
        setData(116.3921299f,39.90071f);


        binding.dmsGuideView.dataDraws(canvasDatas);
    }
    private void setData(float lon,float lat){
        int[] millier1 = MillerCoordinate.MillierConvertion(116.3921299,39.90071);
        int[] millier2 = MillerCoordinate.MillierConvertion(117.27201461791992,39.06316536339376);
        int[] millier3 = MillerCoordinate.MillierConvertion(116.83,38.30);
        int[] millier4 = MillerCoordinate.MillierConvertion(116.30,37.45);
        int[] millier5 = MillerCoordinate.MillierConvertion(116.98 ,36.65);
        int[] millier6 = MillerCoordinate.MillierConvertion(118.46,32.03);
        int[] millier7 = MillerCoordinate.MillierConvertion(119.95,31.78);
        int[] millier8 = MillerCoordinate.MillierConvertion(121.48,31.22);
        int millierRight = millier8[0] - millier1[0];  //最大X轴差
        int millierBottom = millier8[1] - millier1[1];  //最大Y轴差
        LogUtils.i("millierBottom",""+yBottom*(millier2[1] - millier1[1])/millierBottom);
        LogUtils.i("millierBottom",""+yBottom*(millier3[1] - millier1[1])/millierBottom);

        int linex1 =  ((xBottom*(millier2[0]-millier1[0])/millierRight))+x;
        int liney1 =  y+(yBottom*(millier2[1] - millier1[1])/millierBottom);
        DmsDrawEntity map2 = new DmsDrawEntity();
        map2.setX(linex1);    map2.setY(liney1);    map2.setName("天津");
        canvasDatas.add(map2);
        int linex2 = ((xBottom*(millier3[0]-millier1[0])/millierRight))+x;
        int liney2 = y+(yBottom*(millier3[1] - millier1[1])/millierBottom);
        DmsDrawEntity map3 = new DmsDrawEntity();
        map3.setX(linex2);    map3.setY(liney2);  map3.setName("沧州西");
        canvasDatas.add(map3);
        int linex3 = ((xBottom*(millier4[0]-millier1[0])/millierRight))+x;
        int liney3 = y+(yBottom*(millier4[1] - millier1[1])/millierBottom);
        DmsDrawEntity map4 = new DmsDrawEntity();
        map4.setX(linex3);    map4.setY(liney3);  map4.setName("德州东");
        canvasDatas.add(map4);

        int linex4 = ((xBottom*(millier5[0]-millier1[0])/millierRight))+x;
        int liney4 = y+(yBottom*(millier5[1] - millier1[1])/millierBottom);
        DmsDrawEntity map5 = new DmsDrawEntity();
        map5.setX(linex4);    map5.setY(liney4);  map5.setName("曲阜东");
        canvasDatas.add(map5);
        int linex5 = ((xBottom*(millier6[0]-millier1[0])/millierRight))+x;
        int liney5 = y+(yBottom*(millier6[1] - millier1[1])/millierBottom);
        DmsDrawEntity map6 = new DmsDrawEntity();
        map6.setX(linex5);    map6.setY(liney5);  map6.setName("南京南");
        canvasDatas.add(map6);
        int linex6 = ((xBottom*(millier7[0]-millier1[0])/millierRight))+x;
        int liney6 = y+(yBottom*(millier7[1] - millier1[1])/millierBottom);
        DmsDrawEntity map7 = new DmsDrawEntity();
        map7.setX(linex6);    map7.setY(liney6);  map7.setName("常州北");
        canvasDatas.add(map7);
        int linex7 = ((xBottom*(millier8[0]-millier1[0])/millierRight))+x;
        int liney7 = y+(yBottom*(millier8[1] - millier1[1])/millierBottom);
        DmsDrawEntity map8 = new DmsDrawEntity();
        map8.setX(linex7);    map8.setY(liney7);  map8.setName("上海虹桥");
        canvasDatas.add(map8);
    }
    private void initScreen() {
        ScreenUtils.initScreen(activity);
        screenW = ScreenUtils.getScreenW();
        screenH = ScreenUtils.getScreenH();
        LogUtils.i("screenWscreenH",screenW+"--"+screenH);
        x = screenW/13;
        y = screenH/9;
        xBottom = screenW*4/10-x;   //显示的范围宽度
        yBottom = screenH*5/7 - y;  //显示的范围高度
    }
    private void initUi(){
        for (int i = 0; i < texts.length; i++) {
            MainEntity mainEntity = new MainEntity();
            mainEntity.setText(texts[i]);
            datas.add(mainEntity);
        }
        activity.mTitle_bar.setCentreText("行车指导");
        setLinearAdapter(binding.recyclerView, RecyclerView.HORIZONTAL,new SimulateGuideAdapter(activity,datas,null),this);
    }
    @Override
    public void requestData() {

    }

    @Override
    public void succeed(byte servicetype,int subtype,ByteString bytes) {

    }

    @Override
    public void getItem(View view, List<Object> mDatas, int position) {
        if (position == 0) {
            DialogView.hintDialog(activity,null,"","暂无信息",false);
        }else if (position == 1) {
            DialogView.hintDialog(activity,null,"","暂无信息",false);
        }else if (position == 2) {
            DialogView.hintDialog(activity,null,"","暂无信息",false);
        }else if (position == 3) {
            DialogView.hintDialog(activity,null,"","暂无信息",false);
        }else if (position == 4) { //手帐记录
            intent = new Intent(activity, RecordActivity.class);
            activity.startActivity(intent);
        }else if (position == 5) {
            DialogView.hintDialog(activity,null,"","暂无信息",false);
        }else if (position == 6) {

        }else if (position == 7) {
            DialogView.hintDialog(activity,null,"","暂无信息",false);
        }else if (position == 8) {

        }else if (position == 9) {
            DialogView.hintDialog(activity,null,"","暂无信息",false);
        }
    }
}
