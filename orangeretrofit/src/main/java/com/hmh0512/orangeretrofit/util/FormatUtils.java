package com.hmh0512.orangeretrofit.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 00012927 on 2016/12/14.
 */

public class FormatUtils {
    static SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

    public static String toDate(long milli) {

        return sFormat.format(new Date(milli));
    }

    public static String currentTime() {
        return toDate(System.currentTimeMillis());
    }
}
