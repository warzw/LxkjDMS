package lxkj.train.com.utils.service;

import android.support.annotation.RequiresApi;

import io.realm.annotations.Required;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;


/**
 * Created by dhc on 2017/4/12.
 */

public interface RegisterService {
    @GET("/FileService/default_method/{data}")
    Call<ResponseBody> getData(@Path("data")String data);

    @GET("/cityjson?ie=utf-8")
    Call<ResponseBody> getNetIp();

    @Multipart
    @POST("/tbMobileBusiness/mobileServer.do?reqJson=")  // 通用接口方法
    Call<ResponseBody> call(@Part MultipartBody.Part no, @Part MultipartBody.Part pass);

    @Multipart
    @POST("/tbMobileBusiness/idSaveImage.do")  // 上传身份证照片
    Call<ResponseBody> uploadIdImage(@Part MultipartBody.Part no, @Part MultipartBody.Part pass,
                                     @Part MultipartBody.Part image1,
                                     @Part MultipartBody.Part image2,
                                     @Part MultipartBody.Part image3);
    @Multipart
    @POST("/linkfaceLogin")  // 人脸识别
    Call<ResponseBody> linkfaceLogin(@Part MultipartBody.Part no, @Part MultipartBody.Part pass,
                                     @Part MultipartBody.Part image1);
    @Multipart
    @POST("/tbMobileBusiness/saveBusinessLicenseImage.do")  // 上传商户证件
    Call<ResponseBody> uploadBusinessImage(@Part MultipartBody.Part no, @Part MultipartBody.Part pass,
                                           @Part MultipartBody.Part image1,
                                           @Part MultipartBody.Part image2,
                                           @Part MultipartBody.Part image3,
                                           @Part MultipartBody.Part image4,
                                           @Part MultipartBody.Part image5);
    @Multipart
    @POST("/tbMobileBusiness/saveHeadImage.do")  // 上传个人头像照片
    Call<ResponseBody> uploadUserImage(@Part MultipartBody.Part no, @Part MultipartBody.Part pass,
                                       @Part MultipartBody.Part image1);
    @Multipart
    @POST("/uploadImage")  // 单张图片上传
    Call<ResponseBody> aloneUploadImage(@Part MultipartBody.Part image1);

    @POST("/FileService/default_method/")  // post上传流
    Call<ResponseBody> getData(@Body RequestBody requestBody);
}
