package cn.net.view.titleView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.net.view.R;

public class TitleView extends RelativeLayout {

    private ImageView iv_back;
    private View title_line;
    private TextView title_text;
    private LinearLayout iv_back_ll;

    public TitleView(Context context) {
        super(context);
        init(null);
    }

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_title_view, this);
        iv_back = view.findViewById(R.id.iv_back);
        iv_back.setVisibility(View.GONE);
        title_line = view.findViewById(R.id.title_line);
        title_text = view.findViewById(R.id.title_text);
        iv_back_ll = view.findViewById(R.id.iv_back_ll);
        title_text.setVisibility(View.GONE);

    }

    public void setBackImageRes(int resId){
        if (iv_back!=null){
            if (resId!=0) {
                iv_back.setImageResource(resId);
                iv_back.setVisibility(View.VISIBLE);
            }else{
                iv_back.setVisibility(View.GONE);
            }
        }
    }

    public void setBackPressListener(View.OnClickListener listener){
        if (iv_back_ll!=null){
            if (listener!=null) {
                iv_back_ll.setVisibility(View.VISIBLE);
                iv_back_ll.setOnClickListener(listener);
            }else{
                iv_back_ll.setVisibility(View.GONE);
            }
        }
    }

    public void showTitleLine(boolean show){
        if (title_line!=null){
            if (show){
                title_line.setVisibility(VISIBLE);
            }else{
                title_line.setVisibility(GONE);
            }
        }
    }

    public void setTitle(String title){
        if (title_text!=null){
            title_text.setText(title);
            title_text.setVisibility(VISIBLE);
        }
    }

    public void setTitle(int titleId){
        if (title_text!=null&&titleId!=0){
            title_text.setText(titleId);
            title_text.setVisibility(VISIBLE);
        }
    }

}
