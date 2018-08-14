package lxkj.train.com.overall;

import android.os.Environment;

import java.util.List;

import lxkj.train.com.entity.DirectoryOrFileEntity;
import lxkj.train.com.mvp.view.activity.base.BaseApplication;
import retrofit2.http.PUT;

/**
 * Created by dell on 2018/6/7.
 */

public class OverallData {
    public static BaseApplication app;
    public static boolean exceptionSwitch = false;
    public static DirectoryOrFileEntity mDirectoryOrFileEntity = new DirectoryOrFileEntity();
    public final static String sdkPath = Environment.getExternalStorageDirectory().getPath();
    public static final String TAG = "tag";
    public static String driverNum;
}
