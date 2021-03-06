package com.wentongwang.mysports.views.activity.login;


import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.wentongwang.mysports.R;
import com.wentongwang.mysports.base.BaseActivity;
import com.wentongwang.mysports.utils.ActivityManager;
import com.wentongwang.mysports.views.activity.home.HomeActivity;
import com.wentongwang.mysports.views.activity.signup.SignUpActivity;

import butterknife.BindView;

/**
 * login layout
 * Created by Wentong WANG on 2016/8/18.
 */
public class LoginActivity extends BaseActivity implements LoginView {

    @BindView(R.id.login_user_name)
    EditText userName;
    @BindView(R.id.login_user_pwd)
    EditText userPwd;
    @BindView(R.id.btn_sign_in)
    Button sign_in;
    @BindView(R.id.login_sign_up)
    TextView sign_up;

    @BindView(R.id.auto_login_ckeck)
    CheckBox autoLoginCheckBox;

    @BindView(R.id.progress_view)
    View progressView;

    private LoginPresenter mPresenter = new LoginPresenter();

    @Override
    protected boolean noTitle() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_layout;
    }

    @Override
    protected void initPresenter() {
        mPresenter.setView(this);
        mPresenter.init(LoginActivity.this);
    }

    @Override
    protected void initDatesAndViews() {
        //initial dates

    }

    @Override
    protected void initEvents() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        sign_up.setOnClickListener(v -> mPresenter.goToSignUp());

        sign_in.setOnClickListener(v -> mPresenter.login());
    }

    //callbacks after onCreate() write there


    @Override
    public void showProgressBar() {
        progressView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressView.setVisibility(View.GONE);
    }


    @Override
    public void goToSignUp() {
        Intent it = new Intent();
        it.setClass(LoginActivity.this, SignUpActivity.class);
        startActivity(it);
    }

    @Override
    public String getUserName() {
        return userName.getText().toString();
    }

    @Override
    public String getUserPwd() {
        return userPwd.getText().toString();
    }

    @Override
    public void goToHomeActivity() {
        Intent it = new Intent();
        it.setClass(LoginActivity.this, HomeActivity.class);
        startActivity(it);
    }

    @Override
    public boolean autoLoginSelected() {
        return autoLoginCheckBox.isChecked();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityManager.getInstance().exit();
    }
}