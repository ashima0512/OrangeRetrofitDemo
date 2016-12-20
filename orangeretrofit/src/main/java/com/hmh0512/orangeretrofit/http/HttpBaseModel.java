package com.hmh0512.orangeretrofit.http;

import java.io.Serializable;

public class HttpBaseModel implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 返回的错误码
     * 0 未知状态
     * 1 成功
     * 2 失败
     *
     * 3 以后是与后台约定的和业务逻辑相关的错误代码
     *
     * 3 账号不存在
     * 4 用户名或密码为空
     * 5 您输入的密码错误，请核实输入是否正确
     * 6 由于多次提供密码错误，用户暂时冻结
     * 7 密码已过期
     * 8 没有用户信息，不能操作请求
     * 9 当前用户角色无此权限
     * 10 当前角色无此操作权限
     * 11 验证码失效
     * 12 账号未启用
     * 13 账号已禁用
     */
    public int code = 1;

    /**
     * 消息
     */
    public String msg;
}