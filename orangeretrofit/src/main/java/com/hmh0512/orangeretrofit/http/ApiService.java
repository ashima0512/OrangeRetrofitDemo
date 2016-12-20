package com.hmh0512.orangeretrofit.http;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by 00012927 on 2016/11/29.
 */

public interface ApiService {

    /**
     * 普通请求，request和response都是json形式，不使用系统默认的GsonConverter，拿到response后自己用Gson解析成bean
     */
    @POST
    Call<ResponseBody> postWithJson(@Url String url, @HeaderMap Map<String, String> headers, @Body RequestBody paramBody);


    /**
     * 不带进度回调的文件上传
     */
    @Multipart
    @POST
    Call<ResponseBody> upload(@Url String url, @HeaderMap Map<String, String> headers, @Part MultipartBody.Part file);

    /**
     * 带进度回调的文件上传
     */
    @Multipart
    @POST
    Call<ResponseBody> uploadWithProgress(@Url String url, @HeaderMap Map<String, String> headers, @PartMap Map<String, RequestBody> partMap);

    @Streaming
    @GET
    Call<ResponseBody> downloadFile(@Url String url, @HeaderMap Map<String, String> headers);
}
