package com.wentongwang.mysports.views.activity.login;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.wentongwang.mysports.R;
import com.wentongwang.mysports.constant.Constant;
import com.wentongwang.mysports.model.bussiness.RxVolleyRequest;
import com.wentongwang.mysports.model.bussiness.VolleyQueueManager;
import com.wentongwang.mysports.model.bussiness.VolleyResponse;
import com.wentongwang.mysports.model.module.LoginResponse;
import com.wentongwang.mysports.utils.Logger;
import com.wentongwang.mysports.utils.SharedPreferenceUtil;
import com.wentongwang.mysports.utils.ToastUtil;
import com.wentongwang.mysports.utils.VolleyUtil;
import com.wentongwang.mysports.model.bussiness.VollyRequestManager;

import java.util.HashMap;
import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Wentong WANG on 2016/9/8.
 */
public class LoginPresenter {

    private LoginView view;
    private Context mContext;
//    private VollyRequestManager vollyRequestManager;

    public LoginPresenter(LoginView view) {
        this.view = view;
    }

    /**
     * initial presenter
     * get Activity context
     * initial VolleyRequestManager
     *
     * @param context
     */
    public void init(Context context) {
        this.mContext = context;
//        vollyRequestManager = new VollyRequestManager(VolleyQueueManager.getRequestQueue());
    }

    public void goToSignUp() {
        view.goToSignUp();
    }

//    public void login() {
//
//
//        String userName = view.getUserName();
//        String userPwd = view.getUserPwd();
//
//        if (TextUtils.isEmpty(userName))
//
//        if (TextUtils.isEmpty(userPwd)) {
//            ToastUtil.show(mContext, "密码不能为空", 1500);
//            return;
//        }
//
//
//        String url = Constant.HOST + Constant.LOGIN_PATH;
//
//        VolleyResponse<LoginResponse> loginResponse = new VolleyResponse<LoginResponse>();
//
//        Map<String, String> params = new HashMap<>();
//        params.put("loginName", userName);
//        params.put("password", userPwd);
//
//        view.showProgressBar();
//        vollyRequestManager.doPost(mContext, url, loginResponse, params, new VollyRequestManager.OnRequestFinishedListener() {
//            @Override
//            public void onSuccess(VolleyResponse response) {
//                Logger.i("Login", response.getMsg());
//                view.hideProgressBar();
//                //存储用户登录信息，cookie之类的
//                LoginResponse result = (LoginResponse) response.getResult(LoginResponse.class);
//                SharedPreferenceUtil.put(mContext, "user_base_info", result);
//                view.goToHomeActivity();
//            }
//
//            @Override
//            public void onFailed(String msg) {
//                view.hideProgressBar();
//                ToastUtil.show(mContext, msg, 1500);
//
//            }
//        });
//
//    }

    public void loginRx() {
        final String userName = view.getUserName();
        final String userPwd = view.getUserPwd();

        if (TextUtils.isEmpty(userName)) {
            ToastUtil.show(mContext, mContext.getString(R.string.user_name_empty_alert), 1500);
            return;
        }

        if (TextUtils.isEmpty(userPwd)) {
            ToastUtil.show(mContext, mContext.getString(R.string.user_password_empty_alert), 1500);
            return;
        }

        String url = Constant.HOST + Constant.LOGIN_PATH;

        Map<String, String> params = new HashMap<>();
        params.put("loginName", userName);
        params.put("password", userPwd);

        view.showProgressBar();

        RxVolleyRequest.getInstance().getRequestObservable(mContext, Request.Method.POST, url, params)
                .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread())// 指定 Subscriber 的回调发生在主线程
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        view.hideProgressBar();
                        if (view.autoLoginSelected())
                            saveUserLoginInfo(userName, userPwd);
                        else
                            clearUserLoginInfo();
                        view.goToHomeActivity();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.hideProgressBar();
                        ToastUtil.show(mContext, mContext.getString(R.string.login_fail), 1500);
                    }

                    @Override
                    public void onNext(String volleyResponse) {
                        VolleyResponse<LoginResponse> loginResponse = new VolleyResponse<LoginResponse>();
                        loginResponse.setMsg(volleyResponse);

                    }
                });
    }

    private void saveUserLoginInfo(String userName, String pwd) {
        SharedPreferenceUtil.put(mContext, Constant.USER_NAME, userName);
        SharedPreferenceUtil.put(mContext, Constant.USER_PASSWORD, pwd);
    }

    private void clearUserLoginInfo() {
        SharedPreferenceUtil.remove(mContext, Constant.USER_NAME);
        SharedPreferenceUtil.remove(mContext, Constant.USER_PASSWORD);
    }
}
