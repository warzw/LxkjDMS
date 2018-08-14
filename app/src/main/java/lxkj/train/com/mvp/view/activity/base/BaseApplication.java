package lxkj.train.com.mvp.view.activity.base;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.Build;
import android.os.StrictMode;
import com.iflytek.cloud.Setting;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import io.realm.Realm;
import lxkj.train.com.overall.OverallData;


@SuppressLint("Registered")
public class BaseApplication extends Application  {
    @SuppressLint("UseSparseArrays")
    @Override
    public void onCreate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        OverallData.app = this;
        OverallData.exceptionSwitch = false;
        Realm.init(this);
        StringBuffer param = new StringBuffer();
        param.append("appid=5b344b03");
        param.append(",");
        // 设置使用v5+
        param.append(SpeechConstant.ENGINE_MODE+"="+SpeechConstant.MODE_MSC);
        SpeechUtility.createUtility(this, param.toString());
        Setting.setShowLog(true);
        //Realm.init(this);//初始化数据库
        super.onCreate();
    }
}
