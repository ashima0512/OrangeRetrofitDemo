package com.hmh0512.orangeretrofit.util;

import android.widget.Toast;

import com.hmh0512.orangeretrofit.OrangeRetrofit;

/**
 * Created by 00012927 on 2016/11/28.
 */

public class ToastUtils {

    public static void showShort(String msg) {
        Toast.makeText(OrangeRetrofit.appContext, msg, Toast.LENGTH_SHORT).show();
    }
}
