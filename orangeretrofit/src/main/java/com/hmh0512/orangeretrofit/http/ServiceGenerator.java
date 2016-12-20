package com.hmh0512.orangeretrofit.http;

import com.hmh0512.orangeretrofit.OrangeRetrofit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by 00012927 on 2016/11/29.
 */

public class ServiceGenerator {
    private static OkHttpClient.Builder okHttpclientBuilder;
    private static Retrofit.Builder retrofitBuilder;

    public static <S> S createService(Class<S> serviceClass) {
        okHttpclientBuilder = new OkHttpClient.Builder();
        //实际上并没有依靠这里的base url，因为在自己封装的api中会加上base url
        //目前封装的几个接口都是没有用到GsonConverter，是传入ResponseBody，拿到响应结果后自己解析
        retrofitBuilder = new Retrofit.Builder()
                .baseUrl(OrangeRetrofit.BASE_URL)/*.addConverterFactory(GsonConverterFactory.create())*/;
        //千万不要手贱在这里加上HttpLoggingInterceptor，因为如果加上，上传回调进度会失败，大大的坑
        //okHttpclientBuilder.addInterceptor(new HttpLoggingInterceptor());
        Retrofit retrofit = retrofitBuilder.client(okHttpclientBuilder.build()).build();
        return retrofit.create(serviceClass);
    }
}
