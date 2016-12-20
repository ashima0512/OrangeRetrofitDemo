package com.hmh0512.testorange.common;

/**
 * Created by 00012927 on 2016/12/9.
 */

public interface ApiUrl {
    String BASE_URL_INNER = "http://192.168.1.19/app/";

    String LOGIN = "authapp/login";//登录
    String WALLET_ACCOUNT = "wallet/getWalletAccountInfo";//钱包账户
    String UPLOAD_AVATAR = "authapp/uploadHeadImg";
    String APK_DOWNLOAD = "http://a.wdjcdn.com/release/files/phoenix/5.24.2.12070/wandoujia-wandoujia-web_direct_binded_5.24.2.12070.apk?remove=2&append=%8C%00eyJhcHBEb3dubG9hZCI6eyJkb3dubG9hZFR5cGUiOiJkb3dubG9hZF9ieV9wYWNrYWdlX25hbWUiLCJwYWNrYWdlTmFtZSI6ImNvbS5jZWx0LmVzdGF0aW9uIn19Wdj01B00007c5522";
}
