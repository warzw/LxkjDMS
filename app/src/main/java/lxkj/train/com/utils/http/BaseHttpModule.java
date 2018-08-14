package lxkj.train.com.utils.http;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dhc on 2017/4/13.
 */

public class BaseHttpModule {
    private static final Retrofit.Builder builder = new Retrofit.Builder();
    private static final OkHttpClient.Builder okBuilder = new OkHttpClient().newBuilder();
    private static Retrofit retrofit;
    private static Retrofit updateVersionRetrofit;
    public static String TxnCode = "";

    public static void initBuilder() {
        retrofit = null;
        createRetrofit();
    }

    /*因为3des加密之后的字符，httpHead有些识别不了，所以需要这个方法转译一下
     */
    private static String encodeHeadInfo(String headInfo) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0, length = headInfo.length(); i < length; i++) {
            char c = headInfo.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                stringBuffer.append(String.format("\\u%04x", (int) c));
            } else {
                stringBuffer.append(c);
            }
        }
        return stringBuffer.toString();
    }

    /**
     * 提供http的帮助类 以下部分现在都放在子类。
     * 更换链接的请求，需要添加如AppModule的provideRetrofitZhiHuUtils()方法 命名规则provideRetrofitXXXUtils()
     * HttpModule的provideZhiHuRetrofit()和provideZhihuService() 命名规则provideXXXService
     * 还有以下方法 命名规则retrofitXXXUtils  命名规则怎么开心怎么来。
     *
     * @return
     */
    public static OkHttpClient provideClient() {
        //设置超时
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okBuilder .addInterceptor(loggingInterceptor);
        okBuilder.connectTimeout(10, TimeUnit.SECONDS);
        okBuilder.readTimeout(600, TimeUnit.SECONDS);
        okBuilder.writeTimeout(600, TimeUnit.SECONDS);
        //错误重连
        okBuilder.retryOnConnectionFailure(true);
        return okBuilder.build();
    }

    public static Retrofit createRetrofit() {
        if (retrofit == null) {
            retrofit = builder.baseUrl("http://171.8.225.33:11281/").client(provideClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
