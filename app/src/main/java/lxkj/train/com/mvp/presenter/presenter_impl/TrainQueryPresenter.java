package lxkj.train.com.mvp.presenter.presenter_impl;

import android.app.AlertDialog;
import android.content.Context;
import android.databinding.ViewDataBinding;
import android.icu.text.DecimalFormat;
import android.os.Handler;
import android.text.InputType;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.tutorial.MReqFileDownProto;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import lxkj.train.com.R;
import lxkj.train.com.adapter.LableAdapter;
import lxkj.train.com.adapter.TrainQueryAdapter;
import lxkj.train.com.adapter.TrainQueryKeyAdapter;
import lxkj.train.com.databinding.ActivityTrainQueryBinding;
import lxkj.train.com.entity.DmsDrawEntity;
import lxkj.train.com.entity.MainEntity;
import lxkj.train.com.entity.TrainQueryEntity;
import lxkj.train.com.entity.base.BaseEntity;
import lxkj.train.com.interfaces.LifecyclePresenterInterface;
import lxkj.train.com.interfaces.OnRecyclerItemClickListener;
import lxkj.train.com.mvp.view.activity.base.BaseActivity;
import lxkj.train.com.overall.TextUtil;
import lxkj.train.com.utils.EditTextUtil;
import lxkj.train.com.utils.LogUtils;
import lxkj.train.com.utils.MillerCoordinate;
import lxkj.train.com.utils.ProtoUtil;
import lxkj.train.com.utils.RequestUtil;
import lxkj.train.com.utils.RxJavaUtil;
import lxkj.train.com.utils.ScreenUtils;
import lxkj.train.com.utils.SharedPreferencesUtil;
import lxkj.train.com.view.PopupWindows;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * Created by dell on 2018/7/10.
 */

public class TrainQueryPresenter extends BasePresenter implements OnRecyclerItemClickListener , Observable.OnSubscribe<String>, Observer<String> ,LifecyclePresenterInterface {
    private ActivityTrainQueryBinding binding;
    private int screenW;
    private int screenH;
    private List<DmsDrawEntity> canvasDatas = new ArrayList<>();
    private int x;
    private int y;
    private int xBottom;
    private int yBottom;
    private List<TrainQueryEntity> datas = new ArrayList<>();
    private List<TrainQueryEntity> datas2 = new ArrayList<>();
    private PopupWindows  popupWindows = new PopupWindows();
    private RxJavaUtil<String> rxJavaUtil;
    private boolean isSeach;
    private List<BaseEntity> lableDatas = new ArrayList<>();
    private List<BaseEntity> momentLableDatas = new ArrayList<>();
    private LableAdapter lableAdapter;
    private Handler hd = new Handler();


    public TrainQueryPresenter(BaseActivity activity, ActivityTrainQueryBinding binding) {
        super(activity, binding);
        this.binding = binding;
        activity.mTitle_bar.setCentreText("列车查询");
        activity.mLifecyclePresenterInterface = this;
        setOnViewClick(binding.ivSearch,binding.etSearch,binding.tvSeek);
        initData();
    }
    private void initData(){
        binding.tvInfo2.setText("司机号:"+SharedPreferencesUtil.getStringData(activity,"driverNum",""));
        String lablestr = SharedPreferencesUtil.getStringData(activity,"lableDatas","");
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
        initScreen();
        DmsDrawEntity map1 = new DmsDrawEntity();
        map1.setX(x);    map1.setY(y);    map1.setName("北京西");
        canvasDatas.add(map1);
        setData(116.3921299f,39.90071f);
        binding.dmsGuideView.dataDraws(canvasDatas);
    }
    private void getData(MReqFileDownProto.MRespTrainPos mRespTrainPos,List<MReqFileDownProto.MArriveInfo> list,int arriveid){
        if (arriveid>=list.size()) {
            arriveid--;
        }
        LogUtils.i("啦啦啦啦",arriveid+"--"+String.format("%.2f",((double)list.get(arriveid-1).getDistance()/1000))+"km"+"---"+
                list.get(arriveid-1).getNextstation()+"---"+getTime(list.get(arriveid-1).getExpecttime()));
        isSeach = true;
        if (!binding.recyclerView.isShown()) {
            binding.rightLayout.setVisibility(View.VISIBLE);
        }
        final int dis1 = list.get(arriveid-1).getDistance();
        final int dis2 = list.get(arriveid).getDistance();
        //binding.tv11.setText("到站实际时间:"+getTime(list.get(arriveid-1).getRealtime()));
        binding.tv12.setText(list.get(arriveid-1).getNextstation());
        //binding.tv13.setText("到站预计时间:"+getTime(list.get(arriveid-1).getExpecttime()));

        binding.tv21.setText("到站实际时间:\n"+getTime(list.get(arriveid).getRealtime()));
        binding.tv22.setText(list.get(arriveid).getNextstation());
        binding.tv23.setText("到站预计时间:\n"+getTime(list.get(arriveid-1).getExpecttime()));

        binding.tvCurre1.setText("当前车站:"+mRespTrainPos.getCurstation());
        binding.tvCurre2.setText("当前车速:"+mRespTrainPos.getSpeed());
        binding.tvCurre3.setText("距前站距离:"+String.format("%.2f",((double)list.get(arriveid).getDistance()/1000))+"km");
        binding.tvCurre4.setText("距前站剩余时间:"+list.get(arriveid).getTimespan());
        hd.postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.lineSite.setPadding(binding.line1.getMeasuredWidth()*dis1/(dis1+dis2),0,0,0);
                LogUtils.i("MeasuredWidth","---"+binding.line1.getMeasuredWidth()*dis1/(dis1+dis2));
                LogUtils.i("MeasuredWidth",binding.line1.getMeasuredWidth()+"---"+binding.line1.getWidth());
            }
        }, 200);
        if (rxJavaUtil == null) {
            rxJavaUtil = new RxJavaUtil<>(this,this);
        }
        binding.tvBottom12.setText(mRespTrainPos.getIslate()==0?"正点":"晚点");
        binding.tvBottom2.setText("预计进路股道号:"+mRespTrainPos.getArriveinfoList().get(arriveid).getExpecttrackno());
        binding.tvBottom3.setText("进路文本信息:"+mRespTrainPos.getRouterinfo());




        datas.clear();
        datas2.clear();
        for (int i = 0; i <list.size() ; i++) {
            TrainQueryEntity trainQueryEntity = new TrainQueryEntity();
            trainQueryEntity.setText1(list.get(i).getNextstation());
            trainQueryEntity.setText2(""+getTime(list.get(i).getExpecttime()));
            trainQueryEntity.setText3( String.format("%.2f",((double)list.get(i).getDistance()/1000))+"km");
            if (i == arriveid) {
                trainQueryEntity.setTag(2);
            }else if (arriveid>0&&i==(arriveid-1)) {
                trainQueryEntity.setTag(1);
            }else if (arriveid>0&&i<(arriveid-1)) {
                trainQueryEntity.setTag(3);
            }
            datas2.add(trainQueryEntity);
        }
        if (datas2.size()%4 == 0) { //被四整除
            for (int i = 0; i < datas2.size()/4; i++) {  //四的倍数的长度
                List<TrainQueryEntity> datas3 = new ArrayList<>();
                if ((i+1)%2==0) { //偶数行，数据是倒叙
                    datas3.add(datas2.get(i*4+3));
                    datas3.add(datas2.get(i*4+2));
                    datas3.add(datas2.get(i*4+1));
                    datas3.add(datas2.get(i*4));
                }else {
                    datas3.add(datas2.get(i*4));
                    datas3.add(datas2.get(i*4+1));
                    datas3.add(datas2.get(i*4+2));
                    datas3.add(datas2.get(i*4+3));
                }
                datas.addAll(datas3);
            }
        }else {//不能被4整除
            for (int i = 0; i < datas2.size()/4+1; i++) {  //四的倍数的长度
                List<TrainQueryEntity> datas3 = new ArrayList<>();
                if (i == datas2.size()/4) {//如果是最后一行
                    if ((i+1)%2==0) { //偶数行，数据是倒叙,需要补全数据
                        for (int j = 1; j <5 ; j++) {
                            if (datas2.size()%4<j) {
                                datas3.add(datas2.get(i*4+j));
                            }else {
                                datas3.add(new TrainQueryEntity());

                            }
                        }
                        datas3.add(datas2.get(i*4+3));
                        datas3.add(datas2.get(i*4+2));
                        datas3.add(datas2.get(i*4+1));
                        datas3.add(datas2.get(i*4));
                    }else {
                        for (int j = 0; j <datas2.size()%4 ; j++) {
                            datas3.add(datas2.get(i*4+j));
                        }
                    }
                }else {
                    if ((i+1)%2==0) { //偶数行，数据是倒叙
                        datas3.add(datas2.get(i*4+3));
                        datas3.add(datas2.get(i*4+2));
                        datas3.add(datas2.get(i*4+1));
                        datas3.add(datas2.get(i*4));
                    }else {
                        datas3.add(datas2.get(i*4));
                        datas3.add(datas2.get(i*4+1));
                        datas3.add(datas2.get(i*4+2));
                        datas3.add(datas2.get(i*4+3));
                    }
                }
                datas.addAll(datas3);
            }
        }
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
    @Override
    public void requestData() {
        //列车当前位置查询
        dataModel.getData(RequestUtil.requestByteData((byte)3, (short) 2, ProtoUtil.getMReqQuery(3,
                (int) (System.currentTimeMillis()/1000),ProtoUtil.getMReqTrainPos(binding.etSearch.getText().toString().trim()))));
    }

    @Override
    public void succeed(byte servicetype,int subtype,ByteString bytes) {
       if (servicetype == 3&&subtype == 3){   //列车当前位置查询
           MReqFileDownProto.MRespTrainPos mRespTrainPos =   ProtoUtil.getMRespTrainPos(bytes);
           assert mRespTrainPos != null;
           int arriveid = mRespTrainPos.getArriveid();
           List<MReqFileDownProto.MArriveInfo> list = mRespTrainPos.getArriveinfoList();
            binding.tvInfo1.setText("车次:"+mRespTrainPos.getTrainnum());
           if (arriveid>=list.size()) {
               arriveid--;
           }else if (arriveid<=0){
               arriveid++;
           }
//           binding.mapLinerView6.setRightText(""+mRespTrainPos.getArriveinfoList().get(arriveid).getNextstation());
//           binding.mapLinerView7.setRightText(""+String.format("%.2f",((double)mRespTrainPos.getArriveinfoList().get(arriveid).getDistance()/1000))+"km");
//           binding.mapLinerView8.setRightText(""+getTime(mRespTrainPos.getArriveinfoList().get(arriveid).getExpecttime()));
//           binding.mapLinerView9.setRightText(""+mRespTrainPos.getArriveinfoList().get(arriveid).getExpecttrackno());

           LogUtils.i("MRespTrainPos",mRespTrainPos.getArriveinfoList().size()+"----"+arriveid);

           if (list != null&&list.size()>0) {
               setLableDatas();
               getData(mRespTrainPos,list,arriveid);
               setGridAdapter(binding.recyclerView,4,new TrainQueryAdapter(activity,datas,null),null);
           }

        }
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
        SharedPreferencesUtil.saveStringData(activity,"lableDatas",tableText);//保存标签栏的数据
    }
    @Override
    public void onViewClick(View v) {
        super.onViewClick(v);
        if (v.getId() == R.id.iv_search) { //搜索
            popupWindows.dismissKey();
            if (TextUtils.isEmpty(binding.etSearch.getText().toString().trim())) {
                Toast.makeText(activity, "请输入车次", Toast.LENGTH_SHORT).show();
                return;
            }
            requestData();
        }else if (v.getId() == R.id.et_search) { //点击输入文本
            popupWindows.keyPopupWindow(activity,binding.layoutSearch,this);
        }else if (v.getId() == R.id.tv_seek) {  //查看全站图
            if ("查看全站图".equals(binding.tvSeek.getText().toString().trim())) {
                binding.recyclerView.setVisibility(View.VISIBLE);
                binding.rightLayout.setVisibility(View.GONE);
                binding.tvSeek.setText("查看当前站");
            }else {
                binding.recyclerView.setVisibility(View.GONE);
                binding.rightLayout.setVisibility(View.VISIBLE);
                binding.tvSeek.setText("查看全站图");
            }
        }
    }
    @Override
    public void getItem(View view, List<Object> mDatas, int position) {
        if (mDatas.get(position) instanceof BaseEntity) {
            binding.etSearch.setText(lableDatas.get(position).getContent());
            requestData();
        }else {
            String etText = binding.etSearch.getText().toString().trim();
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


    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(String s) {
    }
    @Override
    public void call(Subscriber<? super String> subscriber) {
        while (isSeach){
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            requestData();
        }
        subscriber.onNext("1");
        System.gc();
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {
        isSeach = false;
        if (rxJavaUtil != null) {
            rxJavaUtil.unsubscribeOn();
        }
    }
}
