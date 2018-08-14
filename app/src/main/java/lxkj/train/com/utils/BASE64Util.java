package lxkj.train.com.utils;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Base64;

/**
 * Created with IntelliJ IDEA.
 * User: william
 * Date: 13-5-9
 * Time: 下午2:25
 * Mobi:18601920351 Email: lishu@qdcf.com
 */
@TargetApi(Build.VERSION_CODES.FROYO)
public class BASE64Util {
    private BASE64Util() {
    }

    /**
     * 解密BASE64
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptBASE64(String key) throws Exception {
        return Base64.decode(key, Base64.DEFAULT);
    }

    /**
     * 加密BASE64
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(byte[] key){
        return Base64.encodeToString(key, Base64.DEFAULT);
    }

}
