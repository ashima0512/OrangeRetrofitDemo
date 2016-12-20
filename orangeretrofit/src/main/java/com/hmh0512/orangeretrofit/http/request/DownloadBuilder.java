package com.hmh0512.orangeretrofit.http.request;

import com.hmh0512.orangeretrofit.OrangeRetrofit;
import com.hmh0512.orangeretrofit.util.FormatUtils;
import com.hmh0512.orangeretrofit.util.LogUtils;
import com.hmh0512.orangeretrofit.util.PathManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Joshua on 2016/12/18.
 */

public class DownloadBuilder extends HttpRequest.Builder {

    private String mSaveAsName;//下载的文件保存为这个名字
    private File mDestDownloadFile;

    public DownloadBuilder(String saveAsName) {
        this.mSaveAsName = saveAsName;
    }

    @Override
    public Call<ResponseBody> generateCall() {

        File fileDir = PathManager.getInstance().getDownloadDir();
        if (!fileDir.exists()) {//如果目录不存在，则创建目录
            fileDir.mkdirs();
        }
        mDestDownloadFile = new File(fileDir, mSaveAsName);
        if (mDestDownloadFile.exists()) {//如果文件已存在，则删除文件
            mDestDownloadFile.delete();
        }
        return HttpRequest.sApiService.downloadFile(mUrl, mHeaderMap);
    }

    @Override
    public void onRequestSuccess(final Response<ResponseBody> response) {
        //开线程写文件
        new Thread(new Runnable() {
            @Override
            public void run() {
                LogUtils.d("写文件开始：" + FormatUtils.currentTime());
                final boolean writtenToDisk = writeResponseBodyToDisk(response.body(), mDestDownloadFile);
                LogUtils.d("写文件结束：" + FormatUtils.currentTime());
                OrangeRetrofit.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (writtenToDisk) {
                            mCallback.onBizSuccessOnUi(null);
                        } else {
                            mCallback.onBizFailureOnUi(null);
                        }
                    }
                });
            }
        }).start();
    }

    private boolean writeResponseBodyToDisk(ResponseBody responseBody, File destFile) {
        long fileSize = responseBody.contentLength();//文件总长度
        InputStream inputStream = responseBody.byteStream();//文件输入流
        return writeInputStreamToDisk(inputStream, fileSize, destFile);
    }

    private boolean writeInputStreamToDisk(InputStream inputStream, final long fileSize, File destFile) {
        OutputStream outputStream = null;
        try {
            try {
                byte[] fileReader = new byte[4096];
                long fileSizeDownloaded = 0;
                outputStream = new FileOutputStream(destFile);
                long startTime = 0L;
                long endTime;
                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                    endTime = System.currentTimeMillis();
                    final long downloaded = fileSizeDownloaded;
                    if (endTime - startTime > OrangeRetrofit.DOWNLOAD_PROGRESS_INTERVAL || fileSizeDownloaded == fileSize) {
                        LogUtils.d("--------------------------------------------------------------------------------------------");
                        LogUtils.d("每次切换到UI线程前：" + FormatUtils.currentTime());
                        startTime = endTime;
                        LogUtils.d("file download: " + fileSizeDownloaded + " of " + fileSize);
                        OrangeRetrofit.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mCallback.onProgressOnUi(downloaded, fileSize);
                            }
                        });
                    }
                }
                outputStream.flush();
                return true;
            } catch (FileNotFoundException e) {//文件不存在
                LogUtils.d(e.getMessage());
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
}
