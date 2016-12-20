package com.hmh0512.testorange;

import android.app.Application;

import com.hmh0512.orangeretrofit.OrangeRetrofit;
import com.hmh0512.testorange.common.ApiUrl;

/**
 * Created by 00012927 on 2016/11/28.
 */

public class EApp extends Application {

    private static EApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        OrangeRetrofit.init(this);//把Application Context传给库
        configOrangeRetrofit();//这一操作不是必须的，如果不调用，OrangeRetrofit有默认值
    }

    private void configOrangeRetrofit() {
        //可以在Splash中进行第一次请求，拿到这些配置信息后覆盖OrangeRetrofit中的默认值
        OrangeRetrofit.BASE_URL = ApiUrl.BASE_URL_INNER;
        OrangeRetrofit.DOWNLOAD_PROGRESS_INTERVAL = 300;
        OrangeRetrofit.UPLOAD_PROGRESS_INTERVAL = 200;
        OrangeRetrofit.SESSION_KEY = "JSESSIONID";
        OrangeRetrofit.UPLOAD_DEFAULT_KEY = "file";
    }

    public static EApp getInstance() {
        return instance;
    }


}
