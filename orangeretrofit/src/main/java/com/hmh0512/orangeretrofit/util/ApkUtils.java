package com.hmh0512.orangeretrofit.util;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.hmh0512.orangeretrofit.OrangeRetrofit;

import java.util.Calendar;

public class ApkUtils {

    public static String getAppName() {
        return getAppName(OrangeRetrofit.appContext);
    }

    /**
     * 获取应用程序名称
     */
    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getVersionName() {
        return getVersionName(OrangeRetrofit.appContext);
    }

    /**
     * [获取应用程序版本名称信息]<BR>
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return "v" + packageInfo.versionName;
        } catch (NameNotFoundException e) {
            return "";
        }
    }

    public static int getVersionCode(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pinfo = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
            return pinfo.versionCode;
        } catch (NameNotFoundException e) {
        }
        return -1;
    }

    public static PackageInfo getPackageInfo(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            return packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取当前进程名称
     */
    public static String getAppProcessName(Context appContext) {
        int pid = android.os.Process.myPid();
        android.app.ActivityManager activityManager = (android.app.ActivityManager) appContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (android.app.ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return "";
    }

    /**
     * 构建设备唯一标示，DEVICE_ID取前4位+ANDROID_ID取前6位=10位设备id
     */
    public static String generateDeviceId(Context appContext) {
        TelephonyManager tm = (TelephonyManager) appContext.getSystemService(Context.TELEPHONY_SERVICE);
        String DEVICE_ID = tm.getDeviceId();
        String ANDROID_ID = Settings.System.getString(appContext.getContentResolver(), Settings.Secure.ANDROID_ID);
        return DEVICE_ID.substring(0, 4) + ANDROID_ID.substring(0, 6);
    }

    /**
     * hour:时,minute:分,id:闹钟的id
     */
    public static void setAlarm(Context context, int hour, int minute, int id, String action) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        long intervalMillis = 24 * 3600 * 1000;
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                hour, minute, 10);
        long time = 0;
        if (calendar.getTimeInMillis() > System.currentTimeMillis()) {
            time = calendar.getTimeInMillis();
        } else {
            time = calendar.getTimeInMillis() + intervalMillis;
        }
        Intent intent = new Intent(action);
        PendingIntent sender = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setAlarmTime(am, time, intervalMillis, sender);
        } else {
            am.setRepeating(AlarmManager.RTC_WAKEUP, time, intervalMillis, sender);
        }
    }

    @SuppressLint("NewApi")
    public static void setAlarmTime(AlarmManager am, long time, long intervalMillis, PendingIntent sender) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            am.setWindow(AlarmManager.RTC_WAKEUP, time, intervalMillis, sender);
        }
    }

}
