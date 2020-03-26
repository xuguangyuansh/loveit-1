package cn.net.view.bottombar;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.net.view.R;

public class BottomBarItem extends LinearLayout {

    private ImageView bottom_bar_image;
    private TextView bottom_bar_title;

    public BottomBarItem(Context context) {
        super(context);
        initView(null);
    }

    public BottomBarItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    public BottomBarItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        setOrientation(VERTICAL);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_bottom_bar_item, this);
        bottom_bar_image = view.findViewById(R.id.bottom_bar_image);
        bottom_bar_title = view.findViewById(R.id.bottom_bar_title);
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.BottomBarItem);
            int imageId = typedArray.getResourceId(R.styleable.BottomBarItem_bottom_bar_image, 0);
            if (imageId != 0) {
                bottom_bar_image.setImageResource(imageId);
                bottom_bar_image.setVisibility(View.VISIBLE);
            } else {
                bottom_bar_image.setVisibility(View.GONE);
            }
            String title = typedArray.getString(R.styleable.BottomBarItem_bottom_bar_title);
            if (!TextUtils.isEmpty(title)) {
                bottom_bar_title.setText(title);
                bottom_bar_title.setVisibility(View.VISIBLE);
            } else {
                bottom_bar_title.setVisibility(View.GONE);
            }
        }

    }

    public void setSelected(boolean selected) {
        if (bottom_bar_image != null) {
            bottom_bar_image.setSelected(selected);
        }
        if (bottom_bar_title!=null)
            bottom_bar_title.setSelected(selected);
    }


}
