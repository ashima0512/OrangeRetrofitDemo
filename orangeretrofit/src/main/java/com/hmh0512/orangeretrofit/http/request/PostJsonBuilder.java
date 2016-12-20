package com.hmh0512.orangeretrofit.http.request;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Joshua on 2016/12/18.
 */

public class PostJsonBuilder extends HttpRequest.Builder {

    @Override
    public Call<ResponseBody> generateCall() {
        RequestBody paramBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), mParamStr);//将参数包装成RequestBody
        return HttpRequest.sApiService.postWithJson(mUrl, mHeaderMap, paramBody);
    }

    @Override
    public void onRequestSuccess(Response<ResponseBody> response) {
        super.onRequestSuccess(response);
    }
}
