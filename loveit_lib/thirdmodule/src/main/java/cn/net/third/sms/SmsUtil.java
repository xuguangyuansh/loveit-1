package cn.net.third.sms;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class SmsUtil {
    private VerificationListener listener;

    // 在尝试读取通信录时以弹窗提示用户（可选功能）
    //SMSSDK.setAskPermisionOnReadContact(true);

    private SmsUtil(){

    }

    private static SmsUtil smsUtil;

    public static SmsUtil getInstance(){
        if (smsUtil==null){
            smsUtil=new SmsUtil();
        }
        return smsUtil;
    }

    private  EventHandler eventHandler = new EventHandler() {
        public void afterEvent(int event, int result, Object data) {
            // afterEvent会在子线程被调用，因此如果后续有UI相关操作，需要将数据发送到UI线程
            Message msg = new Message();
            msg.arg1 = event;
            msg.arg2 = result;
            msg.obj = data;
            new Handler(Looper.getMainLooper(), new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    int event = msg.arg1;
                    int result = msg.arg2;
                    Object data = msg.obj;
                    if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            // TODO 处理成功得到验证码的结果
                            Log.e("zz","send success");
                            // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                        } else {
                            // TODO 处理错误的结果
                            ((Throwable) data).printStackTrace();
                            Log.e("zz","send fail"+((Throwable) data).getMessage());
                        }
                    } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            // TODO 处理验证码验证通过的结果
                            Log.e("zz","verification success");
                            if (listener!=null)
                                listener.onVerifySuccess();
                        } else {
                            // TODO 处理错误的结果
                            ((Throwable) data).printStackTrace();
                            Log.e("zz","verification fail");
                            if (listener!= null)
                                listener.onVerifyFail();

                        }
                    }
                    // TODO 其他接口的返回结果也类似，根据event判断当前数据属于哪个接口
                    return false;
                }
            }).sendMessage(msg);
        }
    };

    public void registerSmsHandler(){
        // 注册一个事件回调，用于处理SMSSDK接口请求的结果
        SMSSDK.registerEventHandler(eventHandler);
    }

    public void getSmsCode(String country,String phone){
        // 请求验证码，其中country表示国家代码，如“86”；phone表示手机号码，如“13800138000”
        SMSSDK.getVerificationCode(country, phone);
    }

    public void setVerificationListener(VerificationListener listener){
        this.listener=listener;
    }

    public void commitSmsCode(String country,String phone,String code){
        // 提交验证码，其中的code表示验证码，如“1357”
        SMSSDK.submitVerificationCode(country, phone, code);
    }

    public void onDestory(){
        if (eventHandler!=null) {
            SMSSDK.unregisterEventHandler(eventHandler);
            eventHandler=null;
        }
    }

    public interface VerificationListener{
        void onVerifySuccess();
        void onVerifyFail();
    }
}
