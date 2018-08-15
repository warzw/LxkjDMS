package lxkj.train.com.mvp.presenter.presenter_impl;

import android.databinding.ViewDataBinding;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Toast;

import com.example.tutorial.MReqFileDownProto;
import com.google.protobuf.ByteString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lxkj.train.com.R;
import lxkj.train.com.adapter.CallTheWatchAdapter;
import lxkj.train.com.adapter.LableAdapter;
import lxkj.train.com.adapter.TrainQueryAdapter;
import lxkj.train.com.databinding.ActivityCallTheWatchBinding;
import lxkj.train.com.databinding.ActivityMyTaskBinding;
import lxkj.train.com.databinding.ActivityTrainQueryBinding;
import lxkj.train.com.entity.CallTheWatchEntity;
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
import lxkj.train.com.utils.StringUtil;
import lxkj.train.com.view.DialogView;
import lxkj.train.com.view.PopupWindows;
import lxkj.train.com.view.TimePopupWindow;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * Created by dell on 2018/8/10.
 */

public class MyTaskPresenter extends BasePresenter implements TimePopupWindow.TimeLiCallBack,LifecyclePresenterInterface, DialogView.DialogConfirm,OnRecyclerItemClickListener {
    private ActivityMyTaskBinding binding;
    private AlphaAnimation alphaAni;
    private List<CallTheWatchEntity> datas = new ArrayList<>();
    private CallTheWatchAdapter adapter;
    private List<BaseEntity> lableDatas = new ArrayList<>();
    private List<BaseEntity> momentLableDatas = new ArrayList<>();
    private LableAdapter lableAdapter;
    private int startTime;
    private int endTime;
    public MyTaskPresenter(BaseActivity activity, ActivityMyTaskBinding binding) {
        super(activity, binding);
        this.binding = binding;
        activity.mTitle_bar.setCentreText("出勤叫班");
        activity.mLifecyclePresenterInterface = this;
        setOnViewClick(binding.ivSearch,binding.ivCalendar);
        initData();
    }

    private void initData(){
        String lablestr = SharedPreferencesUtil.getStringData(activity,"lableDatas1","");
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

        adapter = new CallTheWatchAdapter(activity,datas,null);
        setLinearAdapter(binding.recyclerView, RecyclerView.VERTICAL,adapter,null);
    }
    @Override
    public void onViewClick(View v) {
        super.onViewClick(v);
        EditTextUtil.closeKeybord(binding.etSearch,activity);
        if (v.getId() == R.id.iv_search) { //搜索
            requestData();
        }else if (v.getId() == R.id.iv_calendar) { //点击日历查询
            new TimePopupWindow().showTime(activity,activity.mView,"2018-08-06",activity.sdf_date.format(new Date()), this);
        }
    }

    /**
     * 淡入淡出动画方法
     *
     * @param
     */
    public void alpha() {
        // 创建透明度动画，第一个参数是开始的透明度，第二个参数是要转换到的透明度
        alphaAni = new AlphaAnimation(1f, 0);
        //设置动画执行的时间，单位是毫秒
        alphaAni.setDuration(500);
        // 设置动画重复次数
        // -1或者Animation.INFINITE表示无限重复，正数表示重复次数，0表示不重复只播放一次
        alphaAni.setRepeatCount(Animation.INFINITE);
        // 设置动画模式（Animation.REVERSE设置循环反转播放动画,Animation.RESTART每次都从头开始）
        alphaAni.setRepeatMode(Animation.REVERSE);
        // 启动动画
        binding.alarmBottom.startAnimation(alphaAni);
        binding.alarmTop.startAnimation(alphaAni);
    }

    @Override
    public void requestData() {
        //司机出勤计划查询
        if (startTime==0||endTime==0) {
            startTime = StringUtil.getTimesmorning();
            endTime = StringUtil.getTimesnight();
        }
        LogUtils.i("currentTimeMillis",startTime+"---"+endTime+"--"+System.currentTimeMillis() / 1000);
        dataModel.getData(RequestUtil.requestByteData((byte) 3, (short) 2, ProtoUtil.getMReqQuery(1,
                (int) (System.currentTimeMillis() / 1000), ProtoUtil.getMReqAttndPlan(13769185,startTime,endTime))));
    }

    @Override
    public void succeed(byte servicetype, int subtype, ByteString bytes) {
        if (servicetype == 3 && subtype == 1) { //数据查询，查询司机出勤计划
            MReqFileDownProto.MRespAttndPlan mAttndPlanInfo = ProtoUtil.getMRespAttndPlan(bytes);
            assert mAttndPlanInfo != null;
            List<MReqFileDownProto.MAttndPlanInfo> list = mAttndPlanInfo.getInfoList();
            if (list !=null&&list.size()>0) {
                datas.clear();
            }
            for (int i = 0; i <list.size() ; i++) {
                CallTheWatchEntity callTheWatchEntity = new CallTheWatchEntity();
                callTheWatchEntity.setAttndstation(list.get(i).getPlantrainnum());
                callTheWatchEntity.setAttndtime(""+ StringUtil.getTimeStringToInt(list.get(i).getPlanondutytime()));
                callTheWatchEntity.setRetreatstation(list.get(i).getPlandepstation());
                callTheWatchEntity.setRetreattime(""+StringUtil.getTimeStringToInt(list.get(i).getPlandeptime()));
                callTheWatchEntity.setStarttime(""+list.get(i).getDispmancode());
                callTheWatchEntity.setStarttrackno(""+list.get(i).getLowercode());
                callTheWatchEntity.setTrainnum(list.get(i).getOndutycode());
                callTheWatchEntity.setOffdutycode(list.get(i).getOffdutycode());
                datas.add(callTheWatchEntity);
            }
            adapter.notifyDataSetChanged();
            setLableDatas();
            LogUtils.i("MAttndPlanInfo", "" + list.size());
            remind(list);
        }
    }
    private void remind(List<MReqFileDownProto.MAttndPlanInfo> list){
        for (int i = 0; i < list.size(); i++) {
            if ((int)System.currentTimeMillis()/1000>=list.get(i).getPlanondutytime()) { //出勤时间需要大于当前时间
                int timeLag  = (int) System.currentTimeMillis()/1000 - list.get(i).getPlanondutytime();
                if (timeLag<=60000*15) { //出勤前15分钟开始提醒
                    DialogView.hintDialog(activity,this,"叫班提醒","距您下次出勤还有15分钟,请准备",false);
                    startAnimation();
                    tts.startSpeaking("距您下次出勤还有15分钟,请做好准备");
                }
            }
        }
    }

    private void startAnimation() {
        binding.alarmBottom.setVisibility(View.VISIBLE);
        binding.alarmTop.setVisibility(View.VISIBLE);
        alpha();
    }

    private void closeAnimation() {
        binding.alarmBottom.clearAnimation();
        binding.alarmTop.clearAnimation();
        binding.alarmBottom.setVisibility(View.GONE);
        binding.alarmTop.setVisibility(View.GONE);
    }

    @Override
    public void confirm() {
        if (binding.alarmBottom.isShown()) {
            closeAnimation();
        }
        tts.stopSpeaking();
        Toast.makeText(activity, "已确认", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {
        tts.stopSpeaking();
    }

    @Override
    public void getTime(String time, String time2) {
        startTime =((int) (StringUtil.stringToLong(time, "yyyy-MM-dd"))/1000);
        endTime =((int) (StringUtil.stringToLong(time2, "yyyy-MM-dd"))/1000);
    }

    @Override
    public void getItem(View view, List<Object> mDatas, int position) {
        if (mDatas.get(position) instanceof BaseEntity) {
            binding.etSearch.setText(lableDatas.get(position).getContent());
            requestData();
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
        SharedPreferencesUtil.saveStringData(activity,"lableDatas1",tableText);//保存标签栏的数据
    }
}
