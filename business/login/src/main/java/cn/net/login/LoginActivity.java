package cn.net.login;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import java.util.HashMap;

import cn.net.base.BaseActivity;
import cn.net.base.BaseResult;
import cn.net.base.constant.RoutePath;
import cn.net.base.utils.SpUtil;
import cn.net.base.utils.ToastHelper;
import cn.net.login.code.CodeView;
import cn.net.login.forget_pwd.ForgetPwdView;
import cn.net.login.net.LoginPresenter;
import cn.net.login.pwd.PwdView;
import cn.net.model.login.LoginBean;
import cn.net.net.listener.PresenterCallback;
import cn.net.third.share.WeiBoShare;
import cn.net.third.sms.SmsUtil;
import cn.net.view.titleView.TitleView;

@Route(path = RoutePath.LOGIN_INDEX)
public class LoginActivity extends BaseActivity implements View.OnClickListener, SmsUtil.VerificationListener {


    public static final int LOGIN_WITH_CODE=1;
    public static final int LOGIN_WITH_PWD=2;
    public static final int FORGET_PWD=3;
    private int loginType;
    private TextView login_type;
    private TextView login_type_desc;
    private LinearLayout login_view;
    private TextView login_user_agreement;
    private TextView login_privacy_agreement;
    private TextView login_bt;
    private TextView jump_other_login;
    private boolean isFinish=false;
    private PwdView pwdView;
    private ForgetPwdView forgetPwdView;
    private CodeView codeView;
    private LoginPresenter loginPresenter;
    private String phoneText;
    private String newPassword;

//    public static void jump2Login(int loginType){
//        ARouter.getInstance().build(RoutePath.LOGIN_INDEX).withInt("loginType",loginType).navigation();
//    }

    @Override
    protected void initPresenter() {
        SmsUtil.getInstance().registerSmsHandler();
        SmsUtil.getInstance().setVerificationListener(this);
//        WeiBoShare.getInstance(this).installWeiBo();
//        WeiBoShare.getInstance(this).registerApp();
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("text","test");
//        WeiBoShare.getInstance(this).sendMessage(map);
        if (loginPresenter==null)
            loginPresenter = new LoginPresenter(this, null);
        loginPresenter.setPresenterCallback(new PresenterCallback() {
            @Override
            public void onCallBack(boolean isSuccess, Object obj) {
                if (isSuccess){
                    if (obj instanceof LoginBean){
                        LoginBean loginBean= (LoginBean) obj;
                        Log.e("zz","login="+loginBean.toString());
                        SpUtil.getInstance(LoginActivity.this).addString(SpUtil.KEY_TOKEN,loginBean.getToken());
                        if (loginBean.isNewUser()) {
                            ARouter.getInstance().build(RoutePath.LOGIN_SELECT_TAG).navigation();
                        }else{
                            ARouter.getInstance().build(RoutePath.ACTIVITY_MAIN_URI).navigation();
                        }
                        finish();
                    }
                }else{
                    if (obj instanceof BaseResult){
                        BaseResult result= (BaseResult) obj;
                        ToastHelper.showToast(result.getMsg());
                    }
                }
            }
        });
    }

    @Override
    public void initNewIntent(Intent intent) {
        super.initNewIntent(intent);
        WeiBoShare.getInstance(this).handleResult(intent);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        WeiBoShare.getInstance(this).handleResult(data);
//    }

    @Override
    protected void initIntentDatas() {
        Intent intent = getIntent();
        loginType=intent.getIntExtra("loginType",LOGIN_WITH_CODE);
        isFinish=intent.getBooleanExtra("isFinish",false);
    }

    @Override
    protected void initStatusBar() {
    }

    @Override
    protected void initView() {
        TitleView titleView = getTitleView();
        if (titleView!=null){
            titleView.showTitleLine(false);
            titleView.setBackPressListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finishLogin();
                }
            });
        }

        login_type = findViewById(R.id.login_type);
        login_type_desc = findViewById(R.id.login_type_desc);
        login_view = findViewById(R.id.login_view);
        login_user_agreement = findViewById(R.id.login_user_agreement);
        login_privacy_agreement = findViewById(R.id.login_privacy_agreement);
        login_bt = findViewById(R.id.login_bt);
        jump_other_login = findViewById(R.id.jump_other_login);
        jump_other_login.setVisibility(View.GONE);

        login_bt.setOnClickListener(this);

        //协议
        initBottom();

        if (loginType==LOGIN_WITH_CODE){
            //验证码初始化
            initCodeView();
        }else if (loginType==LOGIN_WITH_PWD){
            //密码初始化
            initPwdView();
        }else if (loginType==FORGET_PWD){
            //忘记密码初始化
            initForgetPwdView();
        }

    }

    private void initBottom() {
        login_privacy_agreement.setOnClickListener(this);
        login_user_agreement.setOnClickListener(this);
    }

    private void initForgetPwdView() {
        login_type.setText(R.string.look_up_pwd);
        login_type_desc.setText(R.string.look_up_pwd_desc);
        if (isFinish) {
            login_view.removeAllViews();
        }
        if (forgetPwdView==null) {
            forgetPwdView = new ForgetPwdView(this);
            login_view.addView(forgetPwdView);
        }
        forgetPwdView.setVisibility(View.VISIBLE);
        if (codeView!=null){
            codeView.setVisibility(View.GONE);
        }
        if (pwdView!=null){
            pwdView.setVisibility(View.GONE);
        }
        jump_other_login.setVisibility(View.GONE);
    }

    private void initPwdView() {
        login_type.setText(R.string.pwd_login);
        login_type_desc.setText(R.string.login_desc);
        if (isFinish){
            login_view.removeAllViews();
        }
        if (pwdView==null) {
            pwdView = new PwdView(this);
            login_view.addView(pwdView);
        }
        pwdView.setVisibility(View.VISIBLE);
        if (codeView!=null){
            codeView.setVisibility(View.GONE);
        }
        if (forgetPwdView!=null){
            forgetPwdView.setVisibility(View.GONE);
        }
        jump_other_login.setVisibility(View.VISIBLE);
        jump_other_login.setText(R.string.phone_login);
        jump_other_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(RoutePath.LOGIN_INDEX).withInt("loginType",LOGIN_WITH_CODE).navigation();
            }
        });
    }

    private void initCodeView() {
        login_type.setText(R.string.phone_login);
        login_type_desc.setText(R.string.login_desc);
        if (isFinish) {
            login_view.removeAllViews();
        }
        if (codeView==null) {
            codeView = new CodeView(this);
            login_view.addView(codeView);
        }
        codeView.setVisibility(View.VISIBLE);
        if (pwdView!=null){
            pwdView.setVisibility(View.GONE);
        }
        if (forgetPwdView!=null){
            forgetPwdView.setVisibility(View.GONE);
        }
        jump_other_login.setVisibility(View.VISIBLE);
        jump_other_login.setText(R.string.pwd_login);
        jump_other_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(RoutePath.LOGIN_INDEX).withInt("loginType",LOGIN_WITH_PWD).navigation();
            }
        });
    }

    private void finishLogin() {
        if (isFinish){
            finish();
            return;
        }
        if (loginType==LOGIN_WITH_CODE){
            //退出
            finish();
        }else if (loginType==LOGIN_WITH_PWD){
            //返回验证码
            ARouter.getInstance().build(RoutePath.LOGIN_INDEX).withInt("loginType",LOGIN_WITH_CODE).navigation();
        }else if (loginType==FORGET_PWD){
            //返回密码
            ARouter.getInstance().build(RoutePath.LOGIN_INDEX).withInt("loginType",LOGIN_WITH_PWD).navigation();
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        finishLogin();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

//    @Override
//    public boolean isToolBar() {
//        return false;
//    }

    @Override
    protected boolean hideTitle() {
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e("zz","onNewIntent");
        loginType=intent.getIntExtra("loginType",LOGIN_WITH_CODE);
        isFinish=intent.getBooleanExtra("isFinish",false);
        initView();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.login_privacy_agreement==id){
            //隐私协议

        }else if (R.id.login_user_agreement==id){
            //用户协议

        }else if (R.id.login_bt==id){
            if (loginType==LOGIN_WITH_CODE){
                if (codeView!=null){
                    String codeText = codeView.getCodeText();
                    phoneText = codeView.getPhoneText();
                    if (TextUtils.isEmpty(phoneText)){
                        ToastHelper.showToast(getString(R.string.phone_empty));
                        return;
                    }
                    if (TextUtils.isEmpty(codeText)){
                        ToastHelper.showToast(getString(R.string.code_empty));
                        return;
                    }

                    SmsUtil.getInstance().commitSmsCode("86", phoneText,codeText);
                }
            }else if (loginType==LOGIN_WITH_PWD){
                if (pwdView!=null){
                    String phoneText = pwdView.getPhoneText();
                    String passwordText = pwdView.getPasswordText();
                    if (TextUtils.isEmpty(phoneText)){
                        ToastHelper.showToast(getString(R.string.phone_empty));
                        return;
                    }
                    if (TextUtils.isEmpty(passwordText)){
                        ToastHelper.showToast(getString(R.string.pwd_empty));
                        return;
                    }
                    loginPresenter.login("1",phoneText,passwordText);
                }
            }else if (loginType==FORGET_PWD){
                if (forgetPwdView!=null){
                    String codeText = forgetPwdView.getCodeText();
                    newPassword = forgetPwdView.getNewPassword();
                    phoneText = forgetPwdView.getPhoneText();
                    if (TextUtils.isEmpty(phoneText)){
                        ToastHelper.showToast(getString(R.string.phone_empty));
                        return;
                    }
                    if (TextUtils.isEmpty(codeText)){
                        ToastHelper.showToast(getString(R.string.code_empty));
                        return;
                    }
                    if (TextUtils.isEmpty(newPassword)){
                        ToastHelper.showToast(getString(R.string.new_pwd_empty));
                        return;
                    }
                    SmsUtil.getInstance().commitSmsCode("86", phoneText,codeText);
                }
            }/*else{
                ARouter.getInstance().build(RoutePath.LOGIN_SELECT_TAG).navigation();
            }*/
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SmsUtil.getInstance().onDestory();
        if (codeView!=null){
            codeView.cancelCounter();
        }
        if (forgetPwdView!=null){
            forgetPwdView.cancelCounter();
        }
    }

    @Override
    public void onVerifySuccess() {
        if (loginType==LOGIN_WITH_CODE){
            loginPresenter.login("2",phoneText,"");
        }else if (loginType==FORGET_PWD){
            loginPresenter.restPwd("3",phoneText,newPassword);
        }
    }

    @Override
    public void onVerifyFail() {
        ToastHelper.showToast(getString(R.string.code_error));
    }
}
