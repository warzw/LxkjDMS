package lxkj.train.com.utils.http;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.example.tutorial.MReqFileDownProto;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.util.Map;

import lxkj.train.com.entity.base.BaseEntity;
import lxkj.train.com.interfaces.HttpResponse;
import lxkj.train.com.mvp.view.activity.base.BaseActivity;
import lxkj.train.com.utils.LogUtils;
import lxkj.train.com.utils.ProtoUtil;
import lxkj.train.com.utils.service.RegisterService;
import lxkj.train.com.view.DialogView;
import lxkj.train.com.view.HeadFooterRecyclerView;
import lxkj.train.com.view.LoadingDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by dhc on 2017/4/13.
 */

public class HttpUtils {
    private static HttpUtils mHttpUtils;
    private Gson gson = new Gson();
    private BaseActivity activity;
    public boolean isShow = true;
    private boolean isErr;
    private HttpUtils() {
    }

    public static HttpUtils getIncetanc() {
        if (mHttpUtils == null) {
            mHttpUtils = new HttpUtils();
        }
        return mHttpUtils;
    }

    public <T> T createObservable(Class<T> service) {
        Retrofit retrofit = BaseHttpModule.createRetrofit();
        return retrofit.create(service);
    }

    /**
     * @param
     * @param
     * @param responses  因为返回的是一个responseBody，还需要通过流获取字符转，再解析，所以不用rxjava来回调
     *                   responseBody,而是获取字符串后，再用自己接口来回调最终数据！
     */
    public synchronized  void request(final Activity activity,Object data, final HttpResponse responses) {
        this.activity = (BaseActivity) activity;
        Call<ResponseBody> call;
        if (data instanceof String) {
            call = createObservable(RegisterService.class).getData((String) data);
        }else {
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), (byte[]) data);
            call = createObservable(RegisterService.class).getData(requestFile);
        }

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response == null || response.body() == null) {
                    LogUtils.i("解析前", "返回为空");
                    return;
                }
                try {
                    byte[] data = response.body().bytes();
                    responses.response(data,null);
                    LogUtils.i("解析前", new String(data));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("错误", t.toString());
                if (deleteHttp != null) {
                    deleteHttp.delete(call, responses);
                }
            }
        });
        /**
         * 先判断网络连接状态和网络是否可用，放在回调那里好呢，还是放这里每次请求都去判断下网络是否可用好呢？
         * 如果放在请求前面太耗时了，如果放回掉提示的速度慢，要10秒钟请求超时后才提示。
         * 最后采取的方法是判断网络是否连接放在外面，网络是否可用放在回掉。
         */
        if (!NetworkUtils.isConnected()) {
            DialogView.hintDialog(activity, null, "","网络连接已断开", false);
        }
    }

    private DeleteHttp deleteHttp;

    public void initDeleteHttp(DeleteHttp deleteHttp) {
        this.deleteHttp = deleteHttp;
    }

    public interface DeleteHttp {
        void delete(Call call, HttpResponse responses);
    }
}
