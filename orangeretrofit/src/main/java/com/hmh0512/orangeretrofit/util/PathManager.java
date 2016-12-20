package com.hmh0512.orangeretrofit.util;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.hmh0512.orangeretrofit.OrangeRetrofit;

import java.io.File;

/**
 * Created by 00012927 on 2016/12/1.
 */

public class PathManager {

    private static final String APK_PATH_NAME = "apk";
    private static final String DOWNLOAD_PATH_NAME = "download";
    private static final String APP_NAME = ApkUtils.getAppName();
    private File apkPath;
    private File downloadPath;
    private static PathManager instance = new PathManager();

    private PathManager() {
        initDirs(OrangeRetrofit.appContext);
    }

    private void initDirs(Context context) {
        apkPath = new File(getDiskSaveDir(context), APK_PATH_NAME);
        if (!apkPath.exists()) {
            apkPath.mkdirs();
        }

        downloadPath = new File(getDiskSaveDir(context), DOWNLOAD_PATH_NAME);
        if (!downloadPath.exists()) {
            downloadPath.mkdirs();
        }
    }

    public static PathManager getInstance() {
        return instance;
    }

    public File getDiskSaveDir() {
        return getDiskSaveDir(OrangeRetrofit.appContext);
    }

    /**
     * 用户数据保存目录，不会随着应用的删除而删除(对用户可见)
     *
     * @param context
     * @return
     */
    public File getDiskSaveDir(Context context) {

        File cachePath = null;
        if (TextUtils.equals(Environment.MEDIA_MOUNTED, Environment.getExternalStorageState())) {
            // /mnt/sdcard
            cachePath = Environment.getExternalStorageDirectory();
            cachePath = new File(cachePath, APP_NAME);
        } else {
            // /data/data/com.zhd/cache
            cachePath = context.getCacheDir();
        }
        return cachePath;
    }

    public File getApkPath() {

        if (!apkPath.exists()) {
            apkPath.mkdirs();
        }
        return this.apkPath;
    }


    public File getDownloadDir() {
        if (!downloadPath.exists()) {
            downloadPath.mkdirs();
        }
        return downloadPath;
    }
}
