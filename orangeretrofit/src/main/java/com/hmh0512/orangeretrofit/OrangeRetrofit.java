package com.hmh0512.orangeretrofit;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

/**
 * Created by 00012927 on 2016/12/19.
 */
public class OrangeRetrofit {

    public static Context appContext;//application context,在Application的onCreate中调用OrangeRetrofit.init(this)传进来
    public static Handler mainHandler = new Handler(Looper.getMainLooper());//主线程的Handler

    //以下是OrangeRetrofit的一些配置信息
    public static String BASE_URL = "http://192.168.1.19/app/";
    public static String SESSION_KEY = "JSESSIONID";//放在http header中的session的名字
    public static long DOWNLOAD_PROGRESS_INTERVAL = 300;//进度回调的时间间隔, 单位：毫秒
    public static long UPLOAD_PROGRESS_INTERVAL = 200;//进度回调的时间间隔, 单位：毫秒
    public static String UPLOAD_DEFAULT_KEY = "file";

    public static String session = "";//暂时保存JSESSIONID，实际应用中应写进SP或者文件

    /**
     * 使用库时必须在Application的onCreate方法中显式调用OrangeRetrofit.init(this);
     */
    public static void init(Context context) {
        if (null != context) {
            appContext = context;
        }
    }


    public static void runOnUiThread(Runnable task) {
        mainHandler.post(task);
    }

    public static void runOnUiThreadDelayed(Runnable task, long miliis) {
        mainHandler.postDelayed(task, miliis);
    }
}
