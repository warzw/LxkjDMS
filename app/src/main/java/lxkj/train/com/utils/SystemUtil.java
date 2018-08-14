package lxkj.train.com.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import lxkj.train.com.R;
import lxkj.train.com.mvp.view.activity.base.BaseActivity;
import lxkj.train.com.view.TitleBar;


/**
 * Created by dhc on 2017/6/9.
 */

public class SystemUtil {

    private static String version;

    public static String getVersionName(Context context) {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packInfo != null) {
            version = packInfo.versionName;
        }
        return version;
    }
    /**
     * 计时器
     */
    public static void bindingTimer(final TextView view){
          new CountDownTimer(60 * 1000, 1000) {

            @Override
            public void onTick(long endTime) {
                view.setText(endTime / 1000 + "秒后可重发");
                view.setBackgroundResource(R.color.gray_bb);
                view.setEnabled(false);
            }
            @Override
            public void onFinish() {
                view.setText("重新获取");
                view.setBackgroundResource(R.color.gray_b4);
                view.setEnabled(true);
            }
        }.start();
    }

    public static void setStatusBar(Activity activity, boolean useThemestatusBarColor, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = activity.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            //根据上面设置是否对状态栏单独设置颜色
            if (useThemestatusBarColor) {
                activity. getWindow().setStatusBarColor(activity.getResources().getColor(color));
            } else {
                activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = activity.getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//android6.0以后可以对状态栏文字颜色和图标进行修改
            activity.getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN| View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }
    public static void setStatusHeight(BaseActivity activity,TitleBar titleBar,FrameLayout fl_toolbar_base) {
        int statusBarHeight1 = -1;
//获取status_bar_height资源的ID
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = activity.getResources().getDimensionPixelSize(resourceId);
        }
        if (titleBar.getVisibility() == View.VISIBLE) {
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) titleBar.getLayoutParams();
            lp.topMargin = statusBarHeight1;
            titleBar.setLayoutParams(lp);
        } else {
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) fl_toolbar_base.getLayoutParams();
            lp.topMargin = statusBarHeight1;
            fl_toolbar_base.setLayoutParams(lp);
        }

    }
}
