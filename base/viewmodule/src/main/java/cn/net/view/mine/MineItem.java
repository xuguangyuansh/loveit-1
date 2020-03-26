package cn.net.view.mine;

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

public class MineItem extends LinearLayout {

    private ImageView mine_item_icon;
    private TextView mine_item_text;
    private View view;
    private View mine_item_line;

    public MineItem(Context context) {
        super(context);
        init(null);
    }

    public MineItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MineItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.view_mine_item, this);
        mine_item_icon = view.findViewById(R.id.mine_item_icon);
        mine_item_text = view.findViewById(R.id.mine_item_text);
        mine_item_line = view.findViewById(R.id.mine_item_line);
        if (attrs!=null){
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MineItemStyle);
            String text = typedArray.getString(R.styleable.MineItemStyle_mine_item_text);
            int imageId = typedArray.getResourceId(R.styleable.MineItemStyle_mine_item_icon, 0);
            boolean showLine = typedArray.getBoolean(R.styleable.MineItemStyle_mine_item_show_line, true);
            if (imageId!=0){
                mine_item_icon.setImageResource(imageId);
            }else{
                mine_item_icon.setImageResource(R.mipmap.ic_me_creation);
            }
            if (!TextUtils.isEmpty(text)){
                mine_item_text.setText(text);
            }else{
                mine_item_text.setText("");
            }
            if (showLine){
                mine_item_line.setVisibility(View.VISIBLE);
            }else{
                mine_item_line.setVisibility(View.INVISIBLE);
            }
        }
    }


    public void setItemClick(View.OnClickListener listener){
        if (view!=null&&listener!=null){
            view.setOnClickListener(listener);
        }
    }


}
