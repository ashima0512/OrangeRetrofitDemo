package com.hmh0512.orangeretrofit.http;

import com.hmh0512.orangeretrofit.http.eventbus.ProgressEvent;
import com.hmh0512.orangeretrofit.OrangeRetrofit;
import com.hmh0512.orangeretrofit.util.FormatUtils;
import com.hmh0512.orangeretrofit.util.LogUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * Created by 00012927 on 2016/12/14.
 */

public class FileRequestBody extends RequestBody {

    private RequestBody mOriginalRequestBody;//实际请求体
    private ECallback mCallback;//上传回调接口
    private BufferedSink bufferedSink;//包装完成的BufferedSink

    public FileRequestBody(RequestBody originalRequestBody, ECallback callback) {
        this.mOriginalRequestBody = originalRequestBody;
        this.mCallback = callback;
    }

    @Override
    public MediaType contentType() {
        return mOriginalRequestBody.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        return mOriginalRequestBody.contentLength();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        if (bufferedSink == null) {
            //包装
            bufferedSink = Okio.buffer(sink(sink));
        }
        //写入
        mOriginalRequestBody.writeTo(bufferedSink);
        //必须调用flush，否则最后一部分数据可能不会被写入
        bufferedSink.flush();
    }

    long startTime = 0L;

    private Sink sink(Sink sink) {
        return new ForwardingSink(sink) {
            //当前写入字节数
            long bytesWritten = 0L;
            //总字节长度，避免多次调用contentLength()方法
            long contentLength = 0L;

            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);
                if (contentLength == 0) {
                    //获得contentLength的值，后续不再调用
                    contentLength = contentLength();
                }
                //增加当前写入的字节数
                bytesWritten += byteCount;
                long currentTime = System.currentTimeMillis();
                if (currentTime - startTime > OrangeRetrofit.UPLOAD_PROGRESS_INTERVAL || bytesWritten == contentLength) {
                    startTime = currentTime;
                    LogUtils.d("uploaded " + bytesWritten + " of " + contentLength + ", startTime=" + FormatUtils.toDate(startTime) + ", endTime=" + FormatUtils.toDate(currentTime));
                    EventBus.getDefault().post(new ProgressEvent(bytesWritten, contentLength));
//                    //回调 要切换到主线程
//                    OrangeRetrofit.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            mCallback.onProgressOnUi(bytesWritten, contentLength);
//                        }
//                    });
                }
            }
        };
    }
}
