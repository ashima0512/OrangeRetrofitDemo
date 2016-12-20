package com.hmh0512.orangeretrofit.util;

import com.hmh0512.orangeretrofit.OrangeRetrofit;

/**
 * Created by 00012927 on 2016/12/6.
 */

public class SessionUtils {

    public static String getSessionValue() {
        //从文件中读取JSESSIONID
        return OrangeRetrofit.session;//暂时先放这里
    }

    public static void setSession(String session) {
        //将JSESSIONID覆写到文件中
        OrangeRetrofit.session = session;//暂时先放这里
    }
}
