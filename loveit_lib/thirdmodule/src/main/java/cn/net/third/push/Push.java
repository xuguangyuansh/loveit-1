package cn.net.third.push;

import android.content.Context;
import android.util.Log;

import com.mob.pushsdk.MobPush;
import com.mob.pushsdk.MobPushCallback;
import com.mob.pushsdk.MobPushCustomMessage;
import com.mob.pushsdk.MobPushNotifyMessage;
import com.mob.pushsdk.MobPushReceiver;

public class Push {
//    private PushListener listener;

    private Push(){

    }

    public static Push push;

    public static Push getInstance(){
        if (push==null){
            push=new Push();
        }
        return push;
    }

    private MobPushReceiver receiver=new MobPushReceiver() {
        @Override
        public void onCustomMessageReceive(Context context, MobPushCustomMessage message) {
            //接收自定义消息
        }
        @Override
        public void onNotifyMessageReceive(Context context, MobPushNotifyMessage message) {
            //接收通知消息
            Log.e("zz","收到了消息"+message.getTitle());
        }

        @Override
        public void onNotifyMessageOpenedReceive(Context context, MobPushNotifyMessage message) {
            //接收通知消息被点击事件
        }
        @Override
        public void onTagsCallback(Context context, String[] tags, int operation, int errorCode) {
            //接收tags的增改删查操作
        }
        @Override
        public void onAliasCallback(Context context, String alias, int operation, int errorCode) {
            //接收alias的增改删查操作
        }
    };
//MobPushReceiver receiver
    public void registerPushReceiver(){
        MobPush.addPushReceiver(receiver);
    }

    public void removePushReceiver(){
        MobPush.removePushReceiver(receiver);
    }

    public void showBadge(){
        MobPush.setShowBadge(true); //默认是关闭的，设置true为打开显示角标，反之则为关闭显示角标
    }

    public void setAlias(String alias){
        MobPush.setAlias(alias);//设置别名
    }

    public void setShowBadge(){
        MobPush.setShowBadge(true); //默认是关闭的，设置true为打开显示角标，反之则为关闭显示角标
    }
    public void setTags(String[] tags){
        MobPush.addTags(tags);//设置标签
    }

    public void getRegistrationId(PushListener listener){
        MobPush.getRegistrationId(new MobPushCallback<String>() {

            @Override

            public void onCallback(String rid){
                if (listener!=null){
                    listener.getRegistrationId(rid);
                }
                System.out.println("RegistrationId:" + rid);

            }

        });
    }
//    public void setPushListener(PushListener listener){
//        this.listener=listener;
//    }
}
