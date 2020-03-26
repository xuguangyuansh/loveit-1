package cn.net.user.net;

import android.content.Context;
import android.view.View;

import java.util.HashMap;
import java.util.List;

import cn.net.login.net.service.LoginService;
import cn.net.net.BasePresenter;
import cn.net.net.NetClient;
import cn.net.net.subscribers.BaseSubscriber;
import cn.net.user.net.service.TabService;
import rx.Observable;

public class TabPresenter extends BasePresenter {
    public TabPresenter(Context context, View root_view) {
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

    public void getTagList(){
        HashMap<String,String> map = new HashMap<>();
        mySubscriber = new BaseSubscriber(myNextListener);
        Observable o = NetClient.getInstance().createApi(TabService.class).getTagList(map);
        NetClient.requestData(o,mySubscriber,null);
    }

    public void bindTag(List<String> selectedTagList){
        HashMap<String,Object> map = new HashMap<>();
        map.put("tag",selectedTagList);
        mySubscriber = new BaseSubscriber(myStringNextListener);
        Observable o = NetClient.getInstance().createApi(TabService.class).bindTag(map);
        NetClient.requestData(o,mySubscriber,null);
    }
}
