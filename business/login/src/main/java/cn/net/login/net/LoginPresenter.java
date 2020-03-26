package cn.net.login.net;

import android.content.Context;
import android.view.View;

import java.util.HashMap;

import cn.net.login.net.service.LoginService;
import cn.net.net.BasePresenter;
import cn.net.net.NetClient;
import cn.net.net.subscribers.BaseSubscriber;
import rx.Observable;

public class LoginPresenter extends BasePresenter {
    public LoginPresenter(Context context, View root_view) {
        super(context, root_view);
    }

    @Override
    public void initView() {

    }

    @Override
    public void refresh() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onLoadingComplete() {

    }

    public void login(String loginType,String phone,String pwd){
        HashMap<String,String> map = new HashMap<>();
        map.put("loginType",loginType);
        map.put("phone",phone);
        map.put("password",pwd);
        mySubscriber = new BaseSubscriber(myNextListener);
        Observable o = NetClient.getInstance().createApi(LoginService.class).login(map);
        NetClient.requestData(o,mySubscriber,null);
    }

    public void restPwd(String loginType,String phone,String pwd){
        HashMap<String,String> map = new HashMap<>();
        map.put("loginType",loginType);
        map.put("phone",phone);
        map.put("password",pwd);
        mySubscriber = new BaseSubscriber(myNextListener);
        Observable o = NetClient.getInstance().createApi(LoginService.class).restPwd(map);
        NetClient.requestData(o,mySubscriber,null);
    }
}
