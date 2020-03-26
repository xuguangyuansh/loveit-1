package cn.net.base.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;

public abstract class BaseDialog extends Dialog {
    protected OnYesClickListener onYesClickListener;
    protected OnNoClickListener onNoClickListener;
    private View view;

    public BaseDialog(@NonNull Context context) {
        super(context);
        createDialog();
    }

    public BaseDialog createDialog(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            create();
        }
        return this;
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        createDialog();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        view = LayoutInflater.from(getContext()).inflate(getDialogLayout(), null);
        setContentView(view);
        setSize();
        initView();
    }

    protected View getRootView(){
        return view;
    }


    protected abstract void setSize();

    protected abstract void initView();

    public abstract int getDialogLayout();


    /*
     * 设置取消对话框的点击事件的监听接口
     */
    public interface OnNoClickListener {
        public void cancel();

    }

    /*
     * 设置确认对话框的点击事件的监听接口
     */
    public interface OnYesClickListener {
        public void confirm();
    }

    /*
     * 为外部设置监听部分
     */
    public void setOnYesClick(OnYesClickListener onYesClickListener) {
        this.onYesClickListener = onYesClickListener;
    }

    public void setOnNoClick(OnNoClickListener onNoClickListener) {
        this.onNoClickListener = onNoClickListener;
    }
}
