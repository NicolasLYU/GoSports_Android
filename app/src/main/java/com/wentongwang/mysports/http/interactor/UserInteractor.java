package com.wentongwang.mysports.http.interactor;

import com.google.gson.Gson;
import com.wentongwang.mysports.http.InteractorCallback;
import com.wentongwang.mysports.http.RetrofitManager;
import com.wentongwang.mysports.http.RetrofitResult;
import com.wentongwang.mysports.http.services.UserService;
import com.wentongwang.mysports.model.module.LoginResponse;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wwtco on 2017/10/3.
 */

public class UserInteractor {

    public void login(String userName, String userPassWord, final InteractorCallback<LoginResponse> callback) {
        UserService userService = RetrofitManager.getRetrofit().create(UserService.class);

        Observable<RetrofitResult> observable = userService.login(userName, userPassWord);
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.immediate())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> callback.onSuccess(result.getObject(LoginResponse.class)),
                        e -> callback.onFailed(e.getMessage()));
    }

    public void signUpAndLogin(Map<String, String> userInfo, final InteractorCallback<LoginResponse> callback) {
        final UserService userService = RetrofitManager.getRetrofit().create(UserService.class);

        Observable.just(userInfo)
                .flatMap(user -> userService.userNameExisted(user.get("user_name")))
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> {
                    if (result.isSuccess()) {
                        return new Gson().toJson(userInfo);
                    } else {
                        callback.onFailed(result.getMessage());
                        return null;
                    }
                })
                .filter(str -> str != null)
                .observeOn(Schedulers.io())
                .flatMap(userService::signUp)
                .flatMap(response -> {
                    LoginResponse loginResponse = response.getObject(LoginResponse.class);

                    if (loginResponse != null) {
                        String userName = loginResponse.getUserName();
                        String userPwd = loginResponse.getUserPassword();

                        return userService.login(userName, userPwd);
                    } else {
                        return null;
                    }
                }).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.immediate())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> callback.onSuccess(result.getObject(LoginResponse.class)),
                        e -> callback.onFailed(e.getMessage()));

    }

    public void updateSportsLike(String userId, String sportsLike, final InteractorCallback<String> callback) {
        UserService userService = RetrofitManager.getRetrofit().create(UserService.class);

        Observable<RetrofitResult> observable = userService.updateSportsLike(userId, sportsLike);
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.immediate())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> callback.onSuccess(result.getResult()),
                        e -> callback.onFailed(e.getMessage()));
    }


}
