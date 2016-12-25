package com.hmh0512.orangeretrofit.http.request;

import com.hmh0512.orangeretrofit.http.eventbus.ProgressEvent;
import com.hmh0512.orangeretrofit.OrangeRetrofit;
import com.hmh0512.orangeretrofit.http.FileRequestBody;
import com.hmh0512.orangeretrofit.util.LogUtils;
import com.hmh0512.orangeretrofit.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Joshua on 2016/12/18.
 */

public class UploadBuilder extends HttpRequest.Builder {

    private File mUploadedFile;

    public UploadBuilder(File uploadedFile) {
        this.mUploadedFile = uploadedFile;
        EventBus.getDefault().register(this);
    }

    @Override
    public Call<ResponseBody> generateCall() {
        if (!mUploadedFile.exists()) {
            ToastUtils.showShort("待上传的文件不存在");
            LogUtils.d("file to upload not existed: " + mUploadedFile.getAbsolutePath());
            return null;
        }
        return upload(OrangeRetrofit.UPLOAD_DEFAULT_KEY);
    }

    public Call<ResponseBody> upload(String fileKey) {

        //可以使用默认的MediaType.parse("multipart/form-data")，也可以使用准确MediaType
//        MediaType mediaType = MediaType.parse(FileUtils.getMimeType(file));
        MediaType mediaType = MediaType.parse("multipart/form-data");
        RequestBody originalRequestBody = RequestBody.create(mediaType, mUploadedFile);
        FileRequestBody fileRequestBody = new FileRequestBody(originalRequestBody, mCallback);//把回调传进去，实现进度回调
        Map<String, RequestBody> partMap = new HashMap<String, RequestBody>();
        /**
         * 下面这里没有为什么，在不带进度的上传接口（使用@Multipart）调试成功后，用fiddler抓包获取request的正确格式
         * Content-Disposition: form-data; name="file"; filename="avatar1.jpg"
         */
        fileKey += "\"; filename=\"" + mUploadedFile.getName();
        partMap.put(fileKey, fileRequestBody);

        return HttpRequest.sApiService.uploadWithProgress(mUrl, mHeaderMap, partMap);
    }

    @Override
    public void onRequestSuccess(Response<ResponseBody> response) {
        super.onRequestSuccess(response);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ProgressEvent event) {
        mCallback.onProgressOnUi(event.getProgress(), event.getTotal());
    }
}
