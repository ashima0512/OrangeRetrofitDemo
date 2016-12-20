package com.hmh0512.testorange.bean;


import com.hmh0512.orangeretrofit.http.HttpBaseModel;

/**
 * Created by 00012927 on 2016/12/9.
 */

public class LoginModel extends HttpBaseModel {

    private UserInfo data;

    public UserInfo getData() {
        return data;
    }

    public void setData(UserInfo data) {
        this.data = data;
    }
}
