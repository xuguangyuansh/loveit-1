package cn.net.view.network;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.net.view.R;

public class NoNetWorkView extends LinearLayout {

    private View view;
    private TextView top_text;
    private TextView bottom_text;

    public NoNetWorkView(Context context) {
        super(context);
        init(null);
    }

    public NoNetWorkView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public NoNetWorkView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.view_no_network, this);
        top_text = view.findViewById(R.id.top_text);
        bottom_text = view.findViewById(R.id.bottom_text);
    }

    public void setTopText(int resId){
        if (top_text!=null){
            if (resId!=0) {
                top_text.setText(resId);
                top_text.setVisibility(VISIBLE);
            }else{
                top_text.setVisibility(GONE);
            }
        }
    }


    public void setBottomText(int resId){
        if (bottom_text!=null){
            if (resId!=0) {
                bottom_text.setVisibility(VISIBLE);
                bottom_text.setText(resId);
            }else{
                bottom_text.setVisibility(GONE);
            }
        }
    }

    public void setCloseClickListener(View.OnClickListener listener){
        if (view!=null&&listener!=null){
            view.setOnClickListener(listener);
        }
    }




}
