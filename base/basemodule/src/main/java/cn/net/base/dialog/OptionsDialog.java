package cn.net.base.dialog;

import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import cn.net.basemodule.R;

public class OptionsDialog extends BaseDialog {

    private TextView title;
    private TextView content;
    private Button confirm;
    private Button cancel;

    public OptionsDialog(@NonNull Context context) {
        super(context);
    }

    public OptionsDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void setSize() {
        Window window = getWindow();
        // Point point = new Point();
        Display display = getWindow().getWindowManager().getDefaultDisplay();
        int x = display.getWidth();
        int y = display.getHeight();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = (int) (x*0.9);
        layoutParams.height = y / 4;
        window.setAttributes(layoutParams);
    }

    @Override
    protected void initView() {
        title = getRootView().findViewById(R.id.title);
        content = getRootView().findViewById(R.id.content);
        confirm = getRootView().findViewById(R.id.confirm);
        cancel = getRootView().findViewById(R.id.cancel);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onYesClickListener!=null){
                    onYesClickListener.confirm();
                }
                dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onNoClickListener!=null) {
                    onNoClickListener.cancel();
                }
                dismiss();
            }
        });
    }

    @Override
    public int getDialogLayout() {
        return R.layout.dialog_options;
    }

    public OptionsDialog setDialogTitle(String text){
        if (title!=null){
            title.setText(text);
        }
        return this;
    }

    public OptionsDialog setDialogTitle(int textId){
        if (title!=null){
            title.setText(textId);
        }
        return this;
    }

    public OptionsDialog setContent(String text){
        if (content!=null){
            content.setText(text);
        }
        return this;
    }

    public OptionsDialog setContent(int textId){
        if (content!=null){
            content.setText(textId);
        }
        return this;
    }

    public OptionsDialog setLeftButton(String left,OnNoClickListener listener){
        if (cancel!=null){
            cancel.setText(left);
            setOnNoClick(listener);
        }
        return this;
    }

    public OptionsDialog setRightButton(String right,OnYesClickListener listener){
        if (confirm!=null){
            confirm.setText(right);
            setOnYesClick(listener);
        }
        return this;
    }

    public OptionsDialog setLeftButton(int left,OnNoClickListener listener){
        if (cancel!=null){
            cancel.setText(left);
            setOnNoClick(listener);
        }
        return this;
    }

    public OptionsDialog setRightButton(int right,OnYesClickListener listener){
        if (confirm!=null){
            confirm.setText(right);
            setOnYesClick(listener);
        }
        return this;
    }


    public OptionsDialog setSingleButton(String text, OnYesClickListener listener){
        if (cancel!=null){
            cancel.setVisibility(View.GONE);
            setOnNoClick(null);
        }
        if (confirm!=null){
            confirm.setText(text);
            setOnYesClick(listener);
        }
        return this;
    }

    public OptionsDialog setSingleButton(int text,OnYesClickListener listener){
        if (cancel!=null){
            cancel.setVisibility(View.GONE);
            setOnNoClick(null);
        }
        if (confirm!=null){
            confirm.setText(text);
            setOnYesClick(listener);
        }
        return this;
    }


}
