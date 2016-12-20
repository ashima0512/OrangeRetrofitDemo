package com.hmh0512.orangeretrofit.http.request;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.hmh0512.orangeretrofit.OrangeRetrofit;
import com.hmh0512.orangeretrofit.http.AbsCallback;
import com.hmh0512.orangeretrofit.http.ApiService;
import com.hmh0512.orangeretrofit.http.ECallback;
import com.hmh0512.orangeretrofit.http.HttpBaseModel;
import com.hmh0512.orangeretrofit.http.ServiceGenerator;
import com.hmh0512.orangeretrofit.util.ApkUtils;
import com.hmh0512.orangeretrofit.util.LogUtils;
import com.hmh0512.orangeretrofit.util.SessionUtils;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Joshua on 2016/12/18.
 */

public class HttpRequest {

    public static ApiService sApiService = ServiceGenerator.createService(ApiService.class);

    private Builder mBuilder;

    private Call<ResponseBody> mCall;

    public HttpRequest(Builder builder) {
        this.mBuilder = builder;
    }

    public static HttpRequest builder(Builder builder) {
        return builder.create();
    }

    public HttpRequest startCall() {

        mBuilder.defaultHeaders();
        final String paramJson = mBuilder.paramsToJson();
        mCall = mBuilder.generateCall();
        if (null == mCall) {//如果call对象为null，提前结束
            return this;
        }
        mCall.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                LogUtils.d("[Request]\nurl:" + call.request().url() + ",\nheaders:" + call.request().headers() + "param json:" + paramJson);
                LogUtils.d("[Response raw]\n" + response.raw().toString());
                final ResponseBody responseBody = response.body();
                if (null == responseBody) {
                    mBuilder.onNetError(AbsCallback.NetErrorCode.UNKNOWN_ERROR);
                    return;
                }
                if (response.isSuccessful()) {
                    mBuilder.onRequestSuccess(response);
                } else {
                    mBuilder.onNetError(AbsCallback.NetErrorCode.REQUEST_FAILED);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (call.isCanceled()) {
                    mBuilder.onNetError(AbsCallback.NetErrorCode.REQUEST_CANCELLED);
                } else {
                    t.printStackTrace();
                    LogUtils.d(t.getMessage());
                    mBuilder.onNetError(AbsCallback.NetErrorCode.NETWORK_UNCONNECTED);
                }
            }
        });
        return this;
    }

    public void cancel() {
        this.mCall.cancel();
    }

    public boolean isEmpty() {
        return (null == this.mCall);
    }

    public interface IBuilder {

        Call<ResponseBody> generateCall();

        void onRequestSuccess(Response<ResponseBody> response);
    }

    public static abstract class Builder implements IBuilder {

        protected String mUrl;
        protected Map<String, String> mHeaderMap = new HashMap<>();
        protected Map<String, String> mParamMap = new HashMap<>();
        protected Type mModelClazz = HttpBaseModel.class;//拿到json形式的response body后转化成bean所需要指定的类型
        protected ECallback mCallback;//回调函数
        protected String mParamStr;
        protected Gson gson = new Gson();

        public Builder url(String url) {
            this.mUrl = url.startsWith("http") ? url : OrangeRetrofit.BASE_URL + url;
            return this;
        }

        public void sameHeaders() {
            mHeaderMap.put("Accept", "application/json");
            mHeaderMap.put("RequestSource", "Android");
            mHeaderMap.put("sysModel", android.os.Build.MODEL);
            mHeaderMap.put("sysVersion", android.os.Build.VERSION.RELEASE);
            String versionName = ApkUtils.getVersionName();
            mHeaderMap.put("versionName", versionName);
        }

        public Builder sessionHeader() {
            //更新JSESSIONID
            mHeaderMap.put(OrangeRetrofit.SESSION_KEY, SessionUtils.getSessionValue());
            return this;
        }

        public Builder defaultHeaders() {
            sameHeaders();
            sessionHeader();
            return this;
        }

        public Builder header(String headerKey, String headerValue) {
            if (!TextUtils.isEmpty(headerKey) && !TextUtils.isEmpty(headerValue)) {
                mHeaderMap.put(headerKey, headerValue);
            }
            return this;
        }

        public Builder headers(HashMap<String, String> headers) {
            if (null != headers && !headers.isEmpty()) {
                mHeaderMap.putAll(headers);
            }
            return this;
        }

        public Builder param(String paramKey, String paramValue) {
            if (!TextUtils.isEmpty(paramKey) && !TextUtils.isEmpty(paramValue)) {
                mParamMap.put(paramKey, paramValue);
            }
            return this;
        }

        public Builder params(HashMap<String, String> params) {
            if (null != params && !params.isEmpty()) {
                mParamMap.putAll(params);
            }
            return this;
        }

        public Builder modelClazz(Type modelClazz) {
            mModelClazz = modelClazz;
            return this;
        }

        public Builder callback(ECallback callback) {
            mCallback = callback;
            return this;
        }

        public HttpRequest create() {
            return new HttpRequest(this);
        }

        public void onNetError(int netErrorCode) {
            mCallback.onNetErrorOnUi(netErrorCode, mUrl, mParamStr);
        }

        /**
         * request with json 时和 uploadd时都执行这里，download时需override
         *
         * @param response
         */
        @Override
        public void onRequestSuccess(Response<ResponseBody> response) {

            ResponseBody responseBody = response.body();
            try {
                String bodyStr = responseBody.string();
                LogUtils.d("response body:" + bodyStr);
                HttpBaseModel model = gson.fromJson(bodyStr, mModelClazz);
                if (1 == model.code) {
                    //取出response中的header，找到JSESSIONID字段，把值覆写到文件中
                    List<String> sessionHeaders = response.headers().values(OrangeRetrofit.SESSION_KEY);
                    if (null != sessionHeaders && !sessionHeaders.isEmpty()) {
                        SessionUtils.setSession(sessionHeaders.get(0));
                    }
                    mCallback.onBizSuccessOnUi(model);
                } else if (2 == model.code) {
                    mCallback.onBizFailureOnUi(model);
                } else {
                    mCallback.onBizOtherErrorsOnUi(model.code, model.msg);
                }
            } catch (JsonSyntaxException e) {
                onNetError(AbsCallback.NetErrorCode.JSON_PARSE_ERROR);
            } catch (Exception e) {
                onNetError(AbsCallback.NetErrorCode.UNKNOWN_ERROR);
                responseBody.close();
            }
        }

        public String paramsToJson() {
            return mParamStr = gson.toJson(mParamMap);
        }
    }
}
