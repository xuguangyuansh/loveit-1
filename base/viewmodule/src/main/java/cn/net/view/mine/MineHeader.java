package cn.net.view.mine;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import cn.net.view.R;
import cn.net.view.utils.GlideUtil;
import cn.net.view.utils.UIUtil;

public class MineHeader extends LinearLayout {

    private ImageView mine_photo;
    private TextView mine_name;
    private TextView mine_vip;
    private LinearLayout mine_ll_tags;
    private LinearLayout mine_header_next;
    private LinearLayout mine_fensi_ll;
    private TextView mine_fensi_count;
    private TextView mine_focus_count;
    private LinearLayout mine_focus_ll;
    private TextView mine_friend_count;
    private LinearLayout mine_friend_ll;
    private View view;

    public MineHeader(Context context) {
        super(context);
        init();
    }

    public MineHeader(Context context,  AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MineHeader(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        view = LayoutInflater.from(getContext()).inflate(R.layout.view_mine_header, this);
        mine_photo = view.findViewById(R.id.mine_photo);
        mine_name = view.findViewById(R.id.mine_name);
        mine_vip = view.findViewById(R.id.mine_vip);
        mine_ll_tags = view.findViewById(R.id.mine_ll_tags);
        mine_header_next = view.findViewById(R.id.mine_header_next);
        mine_fensi_ll = view.findViewById(R.id.mine_fensi_ll);
        mine_fensi_count = view.findViewById(R.id.mine_fensi_count);
        mine_focus_count = view.findViewById(R.id.mine_focus_count);
        mine_focus_ll = view.findViewById(R.id.mine_focus_ll);
        mine_friend_count = view.findViewById(R.id.mine_friend_count);
        mine_friend_ll = view.findViewById(R.id.mine_friend_ll);

    }

    public void setMinePhoto(String url){
        if (mine_photo!=null){
            if (TextUtils.isEmpty(url)){
                GlideUtil.getInstance(getContext()).setCircleCrop().loadImage(R.mipmap.ic_launcher,mine_photo);
            }else{
                GlideUtil.getInstance(getContext()).setCircleCrop().loadImage(url,mine_photo);
            }
        }
    }
    public void setMineName(String name){
        if (mine_name!=null){
            mine_name.setText(name);
        }
    }
    public void setVipText(String vip){
        if (mine_vip!=null){
            mine_vip.setText(vip);
        }
    }

    public void setTags(List<String> tags){
        if (mine_ll_tags!=null){
            mine_ll_tags.removeAllViews();
            for (int i = 0; i < tags.size(); i++) {
                String tag = tags.get(i);
                TextView textView = new TextView(getContext());
                textView.setText(tag);
                textView.setTextSize(10);
                textView.setTextColor(getContext().getResources().getColor(R.color.color_text_gray));
                if (i!=0){
                    LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.leftMargin= UIUtil.dip2px(getContext(),10);
                    textView.setLayoutParams(params);
                }
                mine_ll_tags.addView(textView);
            }
        }
    }
    public void setOnMineHeaderNextClick(View.OnClickListener listener){
        if (listener!=null&&mine_header_next!=null){
            mine_header_next.setOnClickListener(listener);
        }
    }

    public void setFriendCount(String count){
        if (mine_friend_count!=null){
           if (TextUtils.isEmpty(count)){
               mine_friend_count.setText("0");
           }else{
               mine_friend_count.setText(count);
           }
        }
    }

    public void setFansCount(String count){
        if (mine_fensi_count!=null){
            if (TextUtils.isEmpty(count)){
                mine_fensi_count.setText("0");
            }else{
                mine_fensi_count.setText(count);
            }
        }
    }

    public void setFocusCount(String count){
        if (mine_focus_count!=null){
            if (TextUtils.isEmpty(count)){
                mine_focus_count.setText("0");
            }else{
                mine_focus_count.setText(count);
            }
        }
    }

    public void setFocusClickListener(View.OnClickListener listener){
        if (mine_focus_ll!=null&&listener!=null){
            mine_focus_ll.setOnClickListener(listener);
        }
    }

    public void setFansClickListener(View.OnClickListener listener){
        if (mine_fensi_ll!=null&&listener!=null){
            mine_fensi_ll.setOnClickListener(listener);
        }
    }

    public void setFriendsClickListener(View.OnClickListener listener){
        if (mine_friend_ll!=null&&listener!=null){
            mine_friend_ll.setOnClickListener(listener);
        }
    }

    public void setHeaderViewClick(View.OnClickListener listener){
        if (view!=null&&listener!=null){
            view.setOnClickListener(listener);
        }
    }
}
