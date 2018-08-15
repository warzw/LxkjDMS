package lxkj.train.com.mvp.view.activity.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import java.text.SimpleDateFormat;
import java.util.Locale;

import lxkj.train.com.R;
import lxkj.train.com.interfaces.ActivityBackPressedInterface;
import lxkj.train.com.interfaces.ActivityResultInterface;
import lxkj.train.com.interfaces.HttpResponse;
import lxkj.train.com.interfaces.LifecyclePresenterInterface;
import lxkj.train.com.interfaces.ViewClicksInterface;
import lxkj.train.com.overall.OverallData;
import lxkj.train.com.utils.DataBaseUtil;
import lxkj.train.com.utils.PermissionsManager;
import lxkj.train.com.utils.SystemUtil;
import lxkj.train.com.utils.http.HttpUtils;
import lxkj.train.com.view.DialogView;
import lxkj.train.com.view.TitleBar;
import retrofit2.Call;


public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener, HttpUtils.DeleteHttp, HttpResponse{
    public TitleBar mTitle_bar;
    public SimpleDateFormat sdf;
    public SimpleDateFormat sdf_date;
    private Call call;
    private HttpResponse responses;
    public View mView;
    private FrameLayout fl_toolbar_base;
    public LifecyclePresenterInterface mLifecyclePresenterInterface;
    public ActivityResultInterface activityResultInterface;
    public ViewClicksInterface viewClicksInterface;
    public ActivityBackPressedInterface backPressedInterface;
    public BaseApplication app;
    private View alarm_top;
    private View alarm_bottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        setContentView(R.layout.activity_base);
        getSupportActionBar().hide();
        SystemUtil.setStatusBar(this, false, 0);
        app = (BaseApplication) getApplication();
        mTitle_bar = findViewById(R.id.title_bar);
        fl_toolbar_base = findViewById(R.id.fl_toolbar_base);
        alarm_top = findViewById(R.id.alarm_top);
        alarm_bottom = findViewById(R.id.alarm_bottom);
        mView = LayoutInflater.from(this).inflate(getLayoutId(), null);
        fl_toolbar_base.addView(mView);
        app = (BaseApplication) getApplication();
        OverallData.app = app;
        sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        sdf_date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        HttpUtils.getIncetanc().initDeleteHttp(this);
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //SystemUtil.setStatusHeight(this,mTitle_bar, fl_toolbar_base);
        if (mLifecyclePresenterInterface != null) {
            mLifecyclePresenterInterface.onResume();
        }
    }
    @Override
    public void onBackPressed() {
        if (backPressedInterface != null) {
            backPressedInterface.onBackPressed();
        }else {
            super.onBackPressed();
            overridePendingTransition(R.anim.fade_in_left, R.anim.fade_out_left);
        }

    }

    @SuppressLint("RestrictedApi")
    @Override
    public void startActivityForResult(Intent intent, int requestCode, Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
        //转场动画
        overridePendingTransition(R.anim.fade_in_right, R.anim.fade_out_right);
    }

    /**
     * 重写onCreate
     */
    protected abstract int getLayoutId();


    /**
     * 初始化控件
     */
    protected abstract void initData();

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //检验权限
        PermissionsManager.get().onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLifecyclePresenterInterface != null) {
            mLifecyclePresenterInterface.onDestroy();
            mLifecyclePresenterInterface = null;
        }
        if (call != null) {
            call.cancel();
            call = null;
        }
        if (responses != null) {
            responses = null;
        }
        mLifecyclePresenterInterface = null;
        activityResultInterface = null;
        viewClicksInterface = null;
        backPressedInterface = null;
        DataBaseUtil.getInstanc().onDestroy();
    }

    public void delete(Call call, HttpResponse responses) {
        this.call = call;
        this.responses = responses;
    }



    @Override
    public void response(byte[] txnCode, Object dataEntity) {


    }

    public void setViewListener(View... views) {
        for (int i = 0; i < views.length; i++) {
            views[i].setOnClickListener(this);
        }
    }
    public void setViewListener(ViewClicksInterface viewListener,View... views) {
        this.viewClicksInterface = viewListener;
        for (int i = 0; i < views.length; i++) {
            views[i].setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        if (viewClicksInterface != null) {
            viewClicksInterface.clickViews(v);
        }
    }

    public void onClickView(View v) {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (activityResultInterface != null) {
            activityResultInterface.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) { //APP结束时，保存数据
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }
    public void setDialog(String content,DialogView.DialogConfirm dialogConfirm){
        DialogView.showDialog(this,dialogConfirm,"叫班提醒",content,false);
    }
    public void startAnimation() {
        alarm_top.setVisibility(View.VISIBLE);
        alarm_bottom.setVisibility(View.VISIBLE);
        alpha();
    }

    public void closeAnimation() {
        alarm_bottom.clearAnimation();
        alarm_top.clearAnimation();
        alarm_bottom.setVisibility(View.GONE);
        alarm_top.setVisibility(View.GONE);
    }
    /**
     * 淡入淡出动画方法
     *
     * @param
     */
    public void alpha() {
        // 创建透明度动画，第一个参数是开始的透明度，第二个参数是要转换到的透明度
        AlphaAnimation alphaAni = new AlphaAnimation(1f, 0);
        //设置动画执行的时间，单位是毫秒
        alphaAni.setDuration(500);
        // 设置动画重复次数
        // -1或者Animation.INFINITE表示无限重复，正数表示重复次数，0表示不重复只播放一次
        alphaAni.setRepeatCount(Animation.INFINITE);
        // 设置动画模式（Animation.REVERSE设置循环反转播放动画,Animation.RESTART每次都从头开始）
        alphaAni.setRepeatMode(Animation.REVERSE);
        // 启动动画
        alarm_bottom.startAnimation(alphaAni);
        alarm_top.startAnimation(alphaAni);
    }
}
