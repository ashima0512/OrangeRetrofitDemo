package com.hmh0512.orangeretrofit.http;

/**
 * Created by 00012927 on 2016/12/9.
 */

public interface ECallback<T> {
    //业务成功 code=1
    void onBizSuccessOnUi(T model);

    //业务失败 code=2
    void onBizFailureOnUi(T model);

    //除失败以外的其他业务错误，code>=3 具体code见HttpBaseModel中的定义
    void onBizOtherErrorsOnUi(int bizErrorCode, String bizErrorMsg);

    //网络层失败
    void onNetErrorOnUi(int netErrorCode, String requestUrl, String paramStr);

    //进度回调
    void onProgressOnUi(long progress, long total);
}
