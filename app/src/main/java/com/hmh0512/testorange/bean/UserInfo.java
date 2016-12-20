package com.hmh0512.testorange.bean;

import java.io.Serializable;

public class UserInfo implements Serializable {

    private static final long serialVersionUID = -899216449824840169L;

    private int id;
    private String password;// 账号密码
    private String nickName;// 用户昵称,方便显示用
    private String headPic;// 用户头像,方便显示用
    private String sex = "";// 性别
    private String email;// 电子邮件
    private String mobile;// mobile
    private String papersNum;// 证件号码
    private String companyName;// 公司名称
    private String address;// 联系地址
    private String postCode;// 邮编

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPapersNum() {
        return papersNum;
    }

    public void setPapersNum(String papersNum) {
        this.papersNum = papersNum;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
}
