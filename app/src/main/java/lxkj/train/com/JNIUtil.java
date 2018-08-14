package lxkj.train.com;

/**
 * Created by dell on 2018/6/22.
 */

public class JNIUtil {
    static
    {
        System.loadLibrary("native-lib");
    }
    public static native String getWorld();
}
