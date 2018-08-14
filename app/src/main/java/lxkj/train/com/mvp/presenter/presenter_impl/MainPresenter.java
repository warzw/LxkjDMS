package lxkj.train.com.mvp.presenter.presenter_impl;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.SeekBar;


import com.example.tutorial.MReqFileDownProto;
import com.google.protobuf.ByteString;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


import io.realm.Realm;
import lxkj.train.com.R;
import lxkj.train.com.adapter.MainAdapter;
import lxkj.train.com.databinding.ActivityMainBinding;
import lxkj.train.com.entity.DirectoryOrFileEntity;
import lxkj.train.com.entity.MainEntity;
import lxkj.train.com.entity.realm.AnnouncementRealm;
import lxkj.train.com.interfaces.ActivityResultInterface;
import lxkj.train.com.interfaces.DataBaseRealmInterface;
import lxkj.train.com.interfaces.LifecyclePresenterInterface;
import lxkj.train.com.interfaces.OnRecyclerItemClickListener;
import lxkj.train.com.mvp.view.activity.AnnouncementActivity;
import lxkj.train.com.mvp.view.activity.BasicInfoActivity;
import lxkj.train.com.mvp.view.activity.CallTheWatchActivity;
import lxkj.train.com.mvp.view.activity.DriveGuideActivity;
import lxkj.train.com.mvp.view.activity.GestureActivity;
import lxkj.train.com.mvp.view.activity.LoginActivity;
import lxkj.train.com.mvp.view.activity.MyTaskActivity;
import lxkj.train.com.mvp.view.activity.PlayTapingActivity;
import lxkj.train.com.mvp.view.activity.ServiceSetActivity;
import lxkj.train.com.mvp.view.activity.ShowFileActivity;
import lxkj.train.com.mvp.view.activity.SimulateGuideActivity;
import lxkj.train.com.mvp.view.activity.TrainNumberActivity;
import lxkj.train.com.mvp.view.activity.TrainQueryActivity;
import lxkj.train.com.mvp.view.activity.UnloadActivity;
import lxkj.train.com.mvp.view.activity.base.BaseActivity;
import lxkj.train.com.overall.OverallData;
import lxkj.train.com.service.ZeroMQMessageUtil;
import lxkj.train.com.utils.FileUtil;
import lxkj.train.com.utils.LogUtils;
import lxkj.train.com.utils.PermissionsManager;
import lxkj.train.com.utils.PicUtil;
import lxkj.train.com.utils.ProtoUtil;
import lxkj.train.com.utils.RequestUtil;
import lxkj.train.com.utils.SearchFile;
import lxkj.train.com.utils.SetBrightnessUtil;
import lxkj.train.com.utils.SharedPreferencesUtil;
import lxkj.train.com.utils.StringUtil;
import lxkj.train.com.utils.SystemCapacityUtil;
import lxkj.train.com.utils.http.NetworkUtils;
import lxkj.train.com.view.DialogView;
import lxkj.train.com.view.LoadingDialog;
import lxkj.train.com.view.PayPasswordUtil;
import lxkj.train.com.view.PayPwdView;
import lxkj.train.com.view.TapingDialog;
import rx.Observer;

import static android.app.Activity.RESULT_OK;

/**
 * Created by dell on 2018/6/8.
 */

public class MainPresenter extends BasePresenter implements AdapterView.OnItemSelectedListener,DataBaseRealmInterface,ActivityResultInterface, PermissionsManager.CheckCallBack, OnRecyclerItemClickListener, DialogView.DialogCancel, Observer<String>, LifecyclePresenterInterface, CompoundButton.OnCheckedChangeListener, PayPwdView.InputCallBack, SeekBar.OnSeekBarChangeListener {
    private ActivityMainBinding binding;
    private List<MainEntity> datas = new ArrayList<>();
    private int[] images = {R.mipmap.icon01, R.mipmap.icon02, R.mipmap.icon03,
            R.mipmap.icon08, R.mipmap.icon07, R.mipmap.icon09,R.mipmap.icon10};
    private String[] texts = {"公告查看", "规章资料", "学习资料", "应急处理",
            "我的任务", "列车查询","退勤转储"}; //"行车导引","安全预想",
    private Intent intent;
    private String environmentPath = Environment.getExternalStorageDirectory().getPath() + "/" + "lxkj" + "/";
    private int currentBrightness;
    private List<MReqFileDownProto.mRespNotice> list;
    private MReqFileDownProto.MRespBasicData mRespBasicData;
    private FileUtil fileUtil;
    private TapingDialog tapingDialog;
    private Handler hd = new Handler();
    String[] arr={"10分钟","20分钟","30分钟","40分钟","50分钟","60分钟","70分钟","80分钟","90分钟","100分钟","110分钟","120分钟"};
    public MainPresenter(BaseActivity activity, ActivityMainBinding binding) {
        super(activity, binding);
        this.binding = binding;
        activity.activityResultInterface = this;
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        activity.mLifecyclePresenterInterface = this;
        new ZeroMQMessageUtil(this);
        setOnViewClick(binding.layoutInfo, binding.marqueeText,binding.headPic);
        requestData();
        initEvent();

    }

    private void initPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            readSuccess = false;
            WRITESuccess = false;
            PermissionsManager.get().checkPermissions(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE, this);
            PermissionsManager.get().checkPermissions(activity, Manifest.permission.READ_PHONE_STATE, this);
        } else {
            selecteDatas();
            initData();
            initUi();
        }
    }

    private void selecteDatas() {  //初始化文件数据
        List<DirectoryOrFileEntity> directoryOrFileEntities = new ArrayList<>();
        File environmentFile = new File(environmentPath);
        String mdatas = SharedPreferencesUtil.getStringData(activity, "mdatas", "");
        LogUtils.i("龙哥sharedMdatas", mdatas);
        if (TextUtils.isEmpty(mdatas)) {
            List<DirectoryOrFileEntity> list = new SearchFile().search(environmentFile, directoryOrFileEntities);
            OverallData.mDirectoryOrFileEntity.setReadTag(View.VISIBLE);
            OverallData.mDirectoryOrFileEntity.setImage(0);
            OverallData.mDirectoryOrFileEntity.setType("directory");
            OverallData.mDirectoryOrFileEntity.setPath(environmentFile.getAbsolutePath());
            OverallData.mDirectoryOrFileEntity.setName(environmentFile.getName());
            OverallData.mDirectoryOrFileEntity.setDirectoryOrFileEntities(list);
        } else {
            OverallData.mDirectoryOrFileEntity = gson.fromJson(mdatas, DirectoryOrFileEntity.class);
        }
    }

    private void initData() {
        datas.clear();
        for (int i = 0; i < images.length; i++) {
            MainEntity mainEntity = new MainEntity();
            mainEntity.setImage(images[i]);
            mainEntity.setText(texts[i]);
            if (i == 1) {
                mainEntity.setTag(OverallData.mDirectoryOrFileEntity.getReadTag());
            } else if (i == 0) {
                String announcementIsShow = SharedPreferencesUtil.getStringData(activity, "announcementIsShow", "");
                if (TextUtils.isEmpty(announcementIsShow) || ("" + View.VISIBLE).equals(announcementIsShow)) {
                    mainEntity.setTag(View.VISIBLE);
                } else {
                    mainEntity.setTag(View.GONE);
                }
                LogUtils.i("announcementIsShow", "mainEntity:" + mainEntity.getTag());
            } else {
                mainEntity.setTag(View.GONE);
            }
            datas.add(mainEntity);
        }
    }

    private void initUi() {
        activity.mTitle_bar.setCentreText("动车组司机作业辅助系统");
        binding.tvLeftBottom1.setText(currentTime_week);
        binding.tvLeftBottom2.setText(currentTime_hms);
        binding.tvLeftBottom3.setText(currentTime_ymd);
        binding.tvNumber.setText(""+OverallData.driverNum);
        setGridAdapter(binding.recyclerView, 4, new MainAdapter(activity, datas, null), this);
    }

    @Override
    public void onViewClick(View v) {
        super.onViewClick(v);
        if (v.getId() == R.id.layout_info) { //查看基本信息
            intent = new Intent(activity, BasicInfoActivity.class);
            activity.startActivity(intent);
        } else if (v.getId() == R.id.headPic) { //登陆认证
            intent = new Intent(activity, LoginActivity.class);
            activity.startActivity(intent);
        }else if (v.getId() == R.id.marqueeText) { //系统消息弹窗
            DialogView.systemNoticeDialog(activity, "系统消息", "发文动车组司机操做规则发文动车组司机操做规则发文动车组司机操做规则发文动车组司机操做规则发文动车组司机操做规则发文动车组司机操做规则发文动车组司机操做规则发文动车组司机操做规则", this);
        } else if (v.getId() == R.id.drawer_tv_taping) { //录音
            if (Build.VERSION.SDK_INT >= 23) {
                PermissionsManager.get().checkPermissions(activity, Manifest.permission.RECORD_AUDIO, new PermissionsManager.CheckCallBack() {
                    @Override
                    public void onSuccess(String permission) {
                        if (tapingDialog == null) {
                            tapingDialog = new TapingDialog(activity);
                        }else {
                            tapingDialog.show();
                        }

                    }
                    @Override
                    public void onError(String permission) {
                    }
                });
            } else {
                if (tapingDialog == null) {
                    tapingDialog = new TapingDialog(activity);
                }else {
                    tapingDialog.show();
                }
            }

        } else if (v.getId() == R.id.drawer_tv_hearTaping) {//查看录音
            intent = new Intent(activity, PlayTapingActivity.class);
            activity.startActivity(intent);
        } else if (v.getId() == R.id.drawer_tv_trainNumber) {//查看车次
            intent = new Intent(activity, TrainNumberActivity.class);
            activity.startActivity(intent);
        } else if (v.getId() == R.id.drawer_tv_remind) {
            binding.spinner.performClick();
        } else if (v.getId() == R.id.drawer_data_sync) { //数据同步

        }else if (v.getId() == R.id.drawer_tv_setService) { //服务器设置
            intent = new Intent(activity, ServiceSetActivity.class);
            activity.startActivity(intent);
        } else if (v.getId() == R.id.drawer_tv_navigation) { //模拟导引
            intent = new Intent(activity, SimulateGuideActivity.class);
            activity.startActivity(intent);
        } else if (v.getId() == R.id.drawer_tv_photograph) {  //拍照
            PicUtil.openCamera(activity);
        }
        if (v.getId() == R.id.drawer_tv_video) {  //拍视频
            PicUtil.openVideo(activity);
        }
    }

    @Override
    public void requestData() {
        fileUtil = new FileUtil();
        //基础下载数据
        dataModel.getData(RequestUtil.requestByteData((byte)2, (short) 2,ProtoUtil.getMReqFileDown(1,(int) (System.currentTimeMillis()/1000),1,ProtoUtil.getMReqBasicData(0,ByteString.copyFrom("0".getBytes())))));
        //基础下载数据---车次数据下载
        dataModel.getData(RequestUtil.requestByteData((byte)2, (short) 2,ProtoUtil.getMReqFileDown(1,(int) (System.currentTimeMillis()/1000),1,ProtoUtil.getMReqBasicData(1,ByteString.copyFrom("1".getBytes())))));
        //公告查询数据
        dataModel.getData(RequestUtil.requestByteData((byte)3, (short) 2,ProtoUtil.getMReqQuery(4,(int) (System.currentTimeMillis()/1000),ProtoUtil.getMReqNotice(1))));
        //司机出勤计划查询
        dataModel.getData(RequestUtil.requestByteData((byte) 3, (short) 2, ProtoUtil.getMReqQuery(1,
                (int) (System.currentTimeMillis() / 1000), ProtoUtil.getMReqAttndPlan(1,(int) System.currentTimeMillis()/1000,(int) System.currentTimeMillis()/1000))));
    }

    @Override
    public void succeed(byte servicetype,int subtype,ByteString bytes) {  //返回数据
        LogUtils.i("mRespNoticeList",servicetype+"--"+subtype);
        if (servicetype == 0x03&&subtype==0x04) { // 数据查询 && 公告查询
            MReqFileDownProto.MRespNoticeList mRespNoticeList =  ProtoUtil.getMRespNoticeList(bytes);
            assert mRespNoticeList != null;
            list = mRespNoticeList.getNoticeList();
            if (list == null) {
                list = new ArrayList<>();
            }
            LogUtils.i("mRespNoticeList",list.size()+"--"+mRespNoticeList.getNoticeList().size());
            if (list.size() >0) {
                dataBaseUtil.deleteAllData(activity,AnnouncementRealm.class,null);
            }
            dataBaseUtil.addData(this);  //从新添加数据库
        }else if (servicetype == 0x02&&subtype==0x01) {  //文件下载  &&基础文件下载
            mRespBasicData = ProtoUtil.getMRespBasicData(bytes);
            assert mRespBasicData != null;
            LogUtils.i("mRespBasicData",""+mRespBasicData.getBasetaype());
            if (mRespBasicData.getBasetaype()==0) { //基础数据
                fileUtil.saveFile(OverallData.sdkPath+"/sqlitedb/","lxkjbase.db", mRespBasicData.getData().toByteArray(),new LoadingDialog(activity));
            }else if (mRespBasicData.getBasetaype()==1) { //时刻表
                fileUtil.saveFile(OverallData.sdkPath+"/sqlitedb/","lxkjtrainnums.db", mRespBasicData.getData().toByteArray(),new LoadingDialog(activity));
            }
        }else if (servicetype == 3 && subtype == 1) { //数据查询，查询司机出勤计划
            MReqFileDownProto.MRespAttndPlan mAttndPlanInfo = ProtoUtil.getMAttndPlanInfo(bytes);
            assert mAttndPlanInfo != null;
            List<MReqFileDownProto.MAttndPlanInfo> list = mAttndPlanInfo.getInfoList();
            LogUtils.i("MAttndPlanInfo", "" + list.size());
            remind(list);
        }
    }
    private void remind(List<MReqFileDownProto.MAttndPlanInfo> list){
        for (int i = 0; i < list.size(); i++) {
            if ((int)System.currentTimeMillis()/1000>=list.get(i).getAttndtime()) { //出勤时间需要大于当前时间
                int timeLag  = (int) System.currentTimeMillis()/1000 - list.get(i).getAttndtime();
               final String spinnerData = SharedPreferencesUtil.getStringData(activity,"spinnerData","");
               final int time = StringUtil.timeInt(spinnerData);
                if (timeLag>time) { //设定时间后开始提醒
                    hd.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            String spinnerData2 = SharedPreferencesUtil.getStringData(activity,"spinnerData","");
                            if (spinnerData2.equals(spinnerData)) {
                                activity.setDialog();
                                tts.startSpeaking("距您下次出勤还有"+spinnerData2+",请做好准备");
                            }
                        }
                    },timeLag-time);
                }else { //如果设定时间以内，立即提醒，弹窗和语音同时提醒
                    activity.setDialog();
                    tts.startSpeaking("距您下次出勤还有"+spinnerData+",请做好准备");
                }
            }
        }
    }
    @Override
    public void getRealm(Realm realm) {  //添加数据库
        for (int i = 0; i < list.size(); i++) {
            AnnouncementRealm announcementEntity = new AnnouncementRealm();
            announcementEntity.setTitle(list.get(i).getType().toStringUtf8());
            announcementEntity.setContent(list.get(i).getContent().toStringUtf8());
            announcementEntity.setIsShow("0");
            LogUtils.i(" Announcementtime",System.currentTimeMillis()+"----"+((long)(list.get(i).getTime()))*1000L);
            announcementEntity.setTime(getTime(list.get(i).getTime()));
            realm.copyToRealm(announcementEntity);
        }
    }
    @Override
    public void getItem(View view, List<Object> mdatas, final int position) {
        if (position == 0) { //公告查询
            intent = new Intent(activity, AnnouncementActivity.class);
            activity.startActivity(intent);
        } else if (position == 1) {//规章资料
            intent = new Intent(activity, ShowFileActivity.class);
            intent.putExtra("environmentPath", environmentPath);
            intent.putExtra("title", datas.get(position).getText());
            activity.startActivity(intent);
        } else if (position == 2) {//学习资料
            intent = new Intent(activity, ShowFileActivity.class);
            intent.putExtra("environmentPath", environmentPath);
            intent.putExtra("title", datas.get(position).getText());
            activity.startActivity(intent);
        } else if (position == -3) {//行车导引
            intent = new Intent(activity, DriveGuideActivity.class);
            intent.putExtra("title", datas.get(position).getText());
            activity.startActivity(intent);
        } else if (position == 3) { //应急处理
            intent = new Intent(activity, ShowFileActivity.class);
            intent.putExtra("environmentPath", environmentPath);
            intent.putExtra("title", datas.get(position).getText());
            activity.startActivity(intent);
        } else if (position == 4) { //我的任务
            intent = new Intent(activity, MyTaskActivity.class);
            activity.startActivity(intent);
            //tts.startSpeaking("前方直行16.7公里，左转进入2股道，限速500千米/时，到站后请开左侧车门下车");
        } else if (position == -6) { //安全预想
            if (Build.VERSION.SDK_INT >= 23) {
                PermissionsManager.get().checkPermissions(activity, Manifest.permission.RECORD_AUDIO, new PermissionsManager.CheckCallBack() {
                    @Override
                    public void onSuccess(String permission) {
                        tts.initSpeech(activity);
                    }

                    @Override
                    public void onError(String permission) {
                    }
                });
            } else {
                tts.initSpeech(activity);
            }
            //打开指定wifi
//            WifiConnector wac = new WifiConnector((WifiManager) (OverallData.app.getSystemService(Context.WIFI_SERVICE)));
//            wac.connect("LXKJ","", WifiConnector.WifiCipherType.WIFICIPHER_NOPASS);//WifiCipherType.WIFICIPHER_NOPASS:WifiCipherType.WIFICIPHER_WPA
        } else if (position == -5) {  //出勤叫班
            intent = new Intent(activity, CallTheWatchActivity.class);
            activity.startActivity(intent);
        } else if (position == 5) {  //列车查询
            intent = new Intent(activity, TrainQueryActivity.class);
            activity.startActivity(intent);
        }else if (position == 6) {  //退勤转储
            intent = new Intent(activity, UnloadActivity.class);
            activity.startActivity(intent);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK && null != data) {

                ArrayList<String> text = data
                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                activity.mTitle_bar.setCentreText(text.get(0));
            }
        } else {
            String cameraPath = (String) PicUtil.onActivityResult(activity, requestCode, resultCode, data);
            Log.i("cameraPath", "" + cameraPath);
        }
    }

    @Override
    public void cancel() {

    }

    @Override
    public void onResume() {
        initPermissions();
    }

    @Override
    public void onDestroy() {

    }

    //监听滑动栏
    private void initEvent() {
        ActionBarDrawerToggle drawerbar = new ActionBarDrawerToggle(activity, binding.drawerLayout, R.string.open, R.string.close) {
            //菜单打开
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                initDrawerLayout();
                setSeekBar();
                LogUtils.i("ActionBarDrawerToggle", "" + true);
            }

            // 菜单关闭
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                LogUtils.i("ActionBarDrawerToggle", "" + false);
            }

        };
        binding.drawerLayout.addDrawerListener(drawerbar);
    }

    private void initDrawerLayout() {//侧滑菜单,设置 内存大小UI
        initSDKUi();
        //创建ArrayAdapter对象
        ArrayAdapter<String> adapter=new ArrayAdapter<>(activity,android.R.layout.simple_spinner_dropdown_item,arr);
        binding.spinner.setAdapter(adapter);
        binding.spinner.setSelection(1);
        binding.spinner.setOnItemSelectedListener(this);

        setOnViewClick(binding.drawerTvTaping, binding.drawerTvHearTaping, binding.drawerTvTrainNumber,
                binding.drawerDataSync, binding.drawerTvSetService, binding.drawerTvNavigation,
                binding.drawerTvPhotograph, binding.drawerTvVideo,binding.drawerTvRemind);
        binding.drawerSetSwitch.setOnCheckedChangeListener(this);
        binding.drawerNightSwitch.setOnCheckedChangeListener(this);
        binding.drawerText4G.setText(NetworkUtils.getNetworkType());
        binding.drawerWifiSwitch.setOnCheckedChangeListener(this);
        binding.drawerWifiSwitch.setChecked(NetworkUtils.getWifiEnabled());
        currentBrightness = 100 * SetBrightnessUtil.getScreenBrightness(activity) / 255;
        binding.drawerSeekBar.setProgress(currentBrightness);
        binding.drawerGestureSwitch.setOnCheckedChangeListener(this);
    }

    private void initSDKUi() { //显示SDK容量大小
        String SDTotalSize = SystemCapacityUtil.getSDTotalSize(activity); //SDK总容量大小
        String SDAvailableSize = SystemCapacityUtil.getSDAvailableSize(activity);//SDK剩余大小
        String[] SDTotalSizes = SDTotalSize.split("\\.");
        String[] SDAvailableSizes = SDAvailableSize.split("\\.");
        if (SDTotalSizes.length > 0) {
            SDTotalSize = SDTotalSizes[0];
        } else {
            SDTotalSize = SDTotalSize.substring(0, SDTotalSize.length() - 2);
        }
        if (SDAvailableSizes.length > 0) {
            SDAvailableSize = SDAvailableSizes[0];
        } else {
            SDAvailableSize = SDAvailableSize.substring(0, SDAvailableSize.length() - 2);
        }
        int SDAlready = Integer.parseInt(SDTotalSize) - Integer.parseInt(SDAvailableSize);  //已经用的大小
        binding.drawerTvSize.setText(SDAlready + "GB/" + SDTotalSize + "GB");
        ViewGroup.LayoutParams layoutParams = binding.drawerV2.getLayoutParams();
        layoutParams.width = binding.drawerV1.getMeasuredWidth() * SDAlready / Integer.parseInt(SDTotalSize);
        binding.drawerV2.setLayoutParams(layoutParams);
    }

    private void setSeekBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //如果当前平台版本大于23平台
            if (!Settings.System.canWrite(activity)) {
                //如果没有修改系统的权限这请求修改系统的权限
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + activity.getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivityForResult(intent, 0);
            } else {
                //有了权限，你要做什么呢？具体的动作
                binding.drawerSeekBar.setOnSeekBarChangeListener(this);
            }
        } else {
            binding.drawerSeekBar.setOnSeekBarChangeListener(this);
        }
    }

    private boolean mIsChecked;

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {  //设置开关选中
        this.mIsChecked = isChecked;
        if (buttonView.getId() == R.id.drawer_setSwitch) {  //设置的开关
            if (isChecked) {//打开
                new PayPasswordUtil(activity, this);
            }
        } else if (buttonView.getId() == R.id.drawer_wifiSwitch) { //WiFi的开关
            if (Build.VERSION.SDK_INT >= 23) {
                PermissionsManager.get().checkPermissions(activity, Manifest.permission.RECORD_AUDIO, new PermissionsManager.CheckCallBack() {
                    @Override
                    public void onSuccess(String permission) {
                        if (mIsChecked) { //打开
                            NetworkUtils.setWifiEnabled(true);
                        } else { //关闭
                            NetworkUtils.setWifiEnabled(false);
                        }
                    }

                    @Override
                    public void onError(String permission) {
                    }
                });
            } else {
                if (isChecked) { //打开
                    NetworkUtils.setWifiEnabled(true);
                } else { //关闭
                    NetworkUtils.setWifiEnabled(false);
                }
            }
        } else if (buttonView.getId() == R.id.drawer_nightSwitch) {  //夜间模式
            if (isChecked) {
                SetBrightnessUtil.setScreenBrightness(activity, 0);
                binding.drawerSeekBar.setProgress(0);
            } else {
                LogUtils.i("currentBrightness", "" + currentBrightness);
                binding.drawerSeekBar.setProgress(currentBrightness);
            }
        } else if (buttonView.getId() == R.id.drawer_gestureSwitch) {//手势密码
            intent = new Intent(activity, GestureActivity.class);
            activity.startActivity(intent);
        }
    }

    @Override
    public void onInputFinish(String result) { //设置输入密码
        binding.drawerSetSwitch.setChecked(false);
        DialogView.hintDialog(activity, null, "", "密码错误", false);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {  //调节亮度监听
        LogUtils.i("seekBardata", seekBar.getProgress() + "---" + progress + "---" + fromUser);
        if (progress != 0) {
            currentBrightness = progress;
        }
        if (binding.drawerNightSwitch.isChecked() && progress != 0) {
            binding.drawerNightSwitch.setChecked(false);
        }
        SetBrightnessUtil.setScreenBrightness(activity, progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    private boolean readSuccess;
    private boolean WRITESuccess;

    @Override
    public void onSuccess(String permission) {  //权限通过回调方法
        if (Manifest.permission.READ_PHONE_STATE.equals(permission)) {

            readSuccess = true;
        }
        if (Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permission)) {
            WRITESuccess = true;
        }
        if (readSuccess && WRITESuccess) {
            selecteDatas();
            initData();
            initUi();
        }
    }

    @Override
    public void onError(String permission) {

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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String spinnerData = arr[position];  //更改的提醒叫班时间
        SharedPreferencesUtil.saveStringData(activity,"spinnerData",spinnerData);
        SharedPreferencesUtil.saveStringData(activity,"spinnerPosition",position+"");
        //司机出勤计划查询
        dataModel.getData(RequestUtil.requestByteData((byte) 3, (short) 2, ProtoUtil.getMReqQuery(1,
                (int) (System.currentTimeMillis() / 1000), ProtoUtil.getMReqAttndPlan(1,(int) System.currentTimeMillis()/1000,(int) System.currentTimeMillis()/1000))));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
