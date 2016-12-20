package com.hmh0512.orangeretrofit.http;

import com.hmh0512.orangeretrofit.util.LogUtils;
import com.hmh0512.orangeretrofit.util.ToastUtils;

import static com.hmh0512.orangeretrofit.http.AbsCallback.BizErrorCode.ACCOUNT_NOT_EXISTED;
import static com.hmh0512.orangeretrofit.http.AbsCallback.BizErrorCode.NO_ACCOUNT_INFO;
import static com.hmh0512.orangeretrofit.http.AbsCallback.NetErrorCode.JSON_PARSE_ERROR;
import static com.hmh0512.orangeretrofit.http.AbsCallback.NetErrorCode.NETWORK_UNCONNECTED;
import static com.hmh0512.orangeretrofit.http.AbsCallback.NetErrorCode.REQUEST_CANCELLED;
import static com.hmh0512.orangeretrofit.http.AbsCallback.NetErrorCode.REQUEST_FAILED;
import static com.hmh0512.orangeretrofit.http.AbsCallback.NetErrorCode.UNKNOWN_ERROR;

/**
 * Created by 00012927 on 2016/12/6.
 */

public abstract class AbsCallback<T extends HttpBaseModel> implements ECallback<T> {
    public static final class BizErrorCode {
        //        public static final int SUCCESS = 1;//成功
//        public static final int FAILURE = 2;//失败
        public static final int ACCOUNT_NOT_EXISTED = 3;//JSON解析错误
        public static final int NO_ACCOUNT_INFO = 8;//JSESSIONID验证不通过，没有用户信息
        // other error codes
    }

    public static final class NetErrorCode {
        public static final int REQUEST_CANCELLED = 0x01;//请求被取消
        public static final int NETWORK_UNCONNECTED = 0x02;//网络无连接
        public static final int JSON_PARSE_ERROR = 0x03;//JSON解析错误
        public static final int REQUEST_FAILED = 0x04;//请求失败
        public static final int UNKNOWN_ERROR = 0x05;//其他未知错误
    }


    //其他业务错误，code>=3 具体code见HttpBaseModel中的定义
    @Override
    public void onBizOtherErrorsOnUi(int bizErrorCode, String bizErrorMsg) {
        switch (bizErrorCode) {
            case ACCOUNT_NOT_EXISTED:
                ToastUtils.showShort("用户不存在");
                break;
            case NO_ACCOUNT_INFO:
                ToastUtils.showShort("没有用户信息，请登录后重试");
                break;
            default:
                //直接Toast提示服务器返回的错误信息
                if (null != bizErrorMsg) {
                    ToastUtils.showShort(bizErrorMsg);
                }
                break;
        }
    }

    //进度回调
    @Override
    public void onProgressOnUi(long progress, long total) {
        LogUtils.d("finished " + progress + " of " + total);
    }

    //网络层失败
    @Override
    public void onNetErrorOnUi(int netErrorCode, String requestUrl, String paramStr) {
        //统一处理，比如弹框提示联网
        switch (netErrorCode) {
            case REQUEST_CANCELLED:
                LogUtils.d("request cancelled.requestUrl=[" + requestUrl + "], paramStr=" + paramStr);
                break;
            case NETWORK_UNCONNECTED:
                ToastUtils.showShort("无法连接到服务器，请开启网络后重试");
                break;
            case JSON_PARSE_ERROR:
                ToastUtils.showShort("JSON解析错误");
                break;
            case REQUEST_FAILED:
                ToastUtils.showShort("请求失败");
                break;
            case UNKNOWN_ERROR:
                ToastUtils.showShort("未知错误");
                break;
            default:
                ToastUtils.showShort("未知错误");
                break;
        }


    }


    public static final AbsCallback DEFAULT = new AbsCallback<HttpBaseModel>() {
        @Override
        public void onBizSuccessOnUi(HttpBaseModel model) {

        }

        @Override
        public void onBizFailureOnUi(HttpBaseModel model) {

        }
    };
}
