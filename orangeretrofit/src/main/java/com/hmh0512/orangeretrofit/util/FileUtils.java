package com.hmh0512.orangeretrofit.util;

import android.webkit.MimeTypeMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;

/**
 * Created by 00012927 on 2016/12/12.
 */

public class FileUtils {

    public static boolean saveFile(InputStream inputStream, File fileDir, String fileName) {
        return saveFile(inputStream, fileDir, fileName, true);
    }

    public static boolean saveFile(InputStream inputStream, File fileDir, String fileName, boolean recreate) {
        OutputStream outputStream = null;
        try {
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            File targetFile = new File(fileDir, fileName);
            if (targetFile.exists() && recreate) {
                targetFile.delete();
            }
            try {
                byte[] fileReader = new byte[4096];
                outputStream = new FileOutputStream(targetFile);
                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                }
                outputStream.flush();
                return true;
            } catch (FileNotFoundException e) {//文件不存在
                LogUtils.d(e.getMessage());
                return false;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

    public static String getSuffix(File file) {
        if (file == null || !file.exists() || file.isDirectory()) {
            return null;
        }
        String fileName = file.getName();
        if (fileName.equals("") || fileName.endsWith(".")) {
            return null;
        }
        int index = fileName.lastIndexOf(".");
        if (index != -1) {
            return fileName.substring(index + 1).toLowerCase(Locale.US);
        } else {
            return null;
        }
    }

    public static String getMimeType(File file) {
        String suffix = getSuffix(file);
        if (suffix == null) {
            return "file/*";
        }
        String type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(suffix);
        if (type != null || !type.isEmpty()) {
            return type;
        }
        return "file/*";
    }
}
