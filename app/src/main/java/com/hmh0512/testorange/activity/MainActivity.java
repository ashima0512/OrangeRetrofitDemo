package com.hmh0512.testorange.activity;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.hmh0512.orangeretrofit.http.AbsCallback;
import com.hmh0512.orangeretrofit.http.HttpBaseModel;
import com.hmh0512.orangeretrofit.http.request.DownloadBuilder;
import com.hmh0512.orangeretrofit.http.request.HttpRequest;
import com.hmh0512.orangeretrofit.http.request.PostJsonBuilder;
import com.hmh0512.orangeretrofit.http.request.UploadBuilder;
import com.hmh0512.orangeretrofit.util.FormatUtils;
import com.hmh0512.orangeretrofit.util.LogUtils;
import com.hmh0512.orangeretrofit.util.Md5;
import com.hmh0512.orangeretrofit.util.PathManager;
import com.hmh0512.orangeretrofit.util.ToastUtils;
import com.hmh0512.testorange.R;
import com.hmh0512.testorange.base.BaseActivity;
import com.hmh0512.testorange.bean.LoginModel;
import com.hmh0512.testorange.bean.UserInfo;
import com.hmh0512.testorange.bean.WalletAccountModel;
import com.hmh0512.testorange.common.ApiUrl;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.btn_login)
    Button mBtnLogin;
    @BindView(R.id.btn_wallet_my_account)
    Button mBtnWalletAccount;
    @BindView(R.id.btn_download)
    Button mBtnDownload;
    @BindView(R.id.btn_upload)
    Button mBtnUpload;
    @BindView(R.id.prog_download)
    ProgressBar mProgDownload;
    @BindView(R.id.prog_upload)
    ProgressBar mProgUpload;

    @Override
    public void initView() {
        setContentView(R.layout.activity_main);
    }

    @OnClick(R.id.btn_login)
    public void onLogin() {
        HttpRequest.builder(
                new PostJsonBuilder()
                        .url(ApiUrl.LOGIN)
                        .defaultHeaders()
                        .param("mobile", "15818703208")
                        .param("password", Md5.md5Toword("123456789"))
                        .modelClazz(LoginModel.class)
                        .callback(new AbsCallback<LoginModel>() {
                            @Override
                            public void onBizSuccessOnUi(LoginModel model) {
                                UserInfo info = model.getData();
                                ToastUtils.showShort("登录成功");
                                mBtnLogin.setText("登录成功");
                            }

                            @Override
                            public void onBizFailureOnUi(LoginModel model) {
                                ToastUtils.showShort("登录失败");
                            }
                        })).startCall();
    }

    @OnClick(R.id.btn_wallet_my_account)
    public void onWalletMyAccount() {
        new PostJsonBuilder()
                .url(ApiUrl.WALLET_ACCOUNT)
                .modelClazz(WalletAccountModel.class)
                .callback(new AbsCallback<WalletAccountModel>() {
                    @Override
                    public void onBizSuccessOnUi(WalletAccountModel model) {
                        mBtnWalletAccount.setText(model.getData().getTotalMoney() + "");
                    }

                    @Override
                    public void onBizFailureOnUi(WalletAccountModel model) {
                        mBtnWalletAccount.setText("--");
                    }
                }).create().startCall();
    }

    @OnClick(R.id.btn_download)
    public void onDownload() {
        mProgDownload.setVisibility(View.VISIBLE);
        mProgDownload.setProgress(0);
        LogUtils.d("点击下载按钮 " + FormatUtils.currentTime());

        new DownloadBuilder("xxx.apk")
                .url(ApiUrl.APK_DOWNLOAD)
                .callback(new AbsCallback<HttpBaseModel>() {

                    @Override
                    public void onBizSuccessOnUi(HttpBaseModel model) {
                        mProgDownload.setVisibility(View.INVISIBLE);
                        mBtnDownload.setText("下载成功");
                        ToastUtils.showShort("下载成功");
                        LogUtils.d("回调：下载成功 " + FormatUtils.currentTime());
                    }

                    @Override
                    public void onBizFailureOnUi(HttpBaseModel model) {
                        mProgDownload.setVisibility(View.INVISIBLE);
                        mBtnDownload.setText("下载失败");
                        ToastUtils.showShort(model.msg);
                        LogUtils.d("回调：下载失败 " + FormatUtils.currentTime());
                    }

                    @Override
                    public void onProgressOnUi(long progress, long total) {
//                super.onProgressOnUi(progress, total);
                        double per = progress * 1.0 / total * 100;
                        mProgDownload.setProgress((int) per);
                        LogUtils.d("回调：下载进度 progress=" + progress + ", total=" + total + " " + FormatUtils.currentTime());
                        LogUtils.d("--------------------------------------------------------------------------------------------");
                    }
                }).create().startCall();
    }

    @OnClick(R.id.btn_upload)
    public void onUpload() {

        File dir = PathManager.getInstance().getDiskSaveDir();
        File file = new File(dir, "avatar1.jpg");
        LogUtils.d("点击上传按钮 " + FormatUtils.currentTime());
        HttpRequest upload = HttpRequest.builder(
                new UploadBuilder(file)
                        .url(ApiUrl.UPLOAD_AVATAR)
                        .callback(new AbsCallback<HttpBaseModel>() {
                            @Override
                            public void onBizSuccessOnUi(HttpBaseModel model) {
                                ToastUtils.showShort(model.msg);
                                mProgUpload.setVisibility(View.INVISIBLE);
                            }

                            @Override
                            public void onBizFailureOnUi(HttpBaseModel model) {
                                ToastUtils.showShort(model.msg);
                                mProgUpload.setVisibility(View.INVISIBLE);
                            }

                            @Override
                            public void onProgressOnUi(long progress, long total) {
                                super.onProgressOnUi(progress, total);
                                mProgUpload.setProgress((int) (progress * 1.0 / total * 100));
                            }
                        })).startCall();
        if (!upload.isEmpty()) {
            mProgUpload.setVisibility(View.VISIBLE);
            mProgUpload.setProgress(0);
        }
    }
}
