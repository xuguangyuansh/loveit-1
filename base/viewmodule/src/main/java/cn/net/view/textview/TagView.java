package cn.net.view.textview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import cn.net.view.R;
import cn.net.view.utils.UIUtil;

public class TagView extends TextView {

    public TagView(Context context) {
        super(context);
        init();
    }

    public TagView(Context context,  AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setTextSize(11);
        setTextColor(getContext().getResources().getColor(R.color.color_text_black4));
        int left=UIUtil.dip2px(getContext(),6);
        int top=UIUtil.dip2px(getContext(),3);
        int bottom=UIUtil.dip2px(getContext(),2);
        setPadding(left,top,left,bottom);
        setBackgroundResource(R.drawable.tag_backgroud_circle);
    }

}
