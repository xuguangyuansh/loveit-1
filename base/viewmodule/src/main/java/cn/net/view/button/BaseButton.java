package cn.net.view.button;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

import cn.net.view.R;

public class BaseButton extends AppCompatButton {
    public BaseButton(Context context) {
        super(context);
        init();
    }

    public BaseButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setBackgroundResource(R.drawable.bg_button_selector_normal);
        setTextColor(getResources().getColor(R.color.color_white));
        setTextSize(16);
    }
}
