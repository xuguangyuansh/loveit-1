package cn.net.login.count;

import android.os.CountDownTimer;

import cn.net.base.utils.LogUtils;

public class Counter extends CountDownTimer {

    private CountListner listener;

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public Counter(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    @Override
    public void onTick(long millisUntilFinished) {
        LogUtils.e("zz",millisUntilFinished+"");
        if ((int)(millisUntilFinished/1000)==0){
            if (listener!=null)
                listener.onFinish();
            cancel();
            return;
        }
        if (listener!=null)
            listener.onCount((int) (millisUntilFinished/1000));
    }

    @Override
    public void onFinish() {
        LogUtils.e("zz","finish");
        if (listener!=null)
            listener.onFinish();
    }

    public void setCounterListener(CountListner listener){
        this.listener=listener;
    }
}
