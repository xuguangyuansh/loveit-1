package cn.net.home.holder;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import cn.net.home.R;

public class RecommandHolder extends RecyclerView.ViewHolder {

    public ViewPager recommand_item_viewpager;
    public TextView recommand_item_des;
    public TextView recommand_item_name;
    public ImageView recommand_item_photo;
    public LinearLayout recommand_item_tags;
    public TextView recommand_item_time;
    public LinearLayout recommand_share;
    public LinearLayout recommand_collection;
    public LinearLayout recommand_like;
    public LinearLayout recommand_send;

    public RecommandHolder(@NonNull View itemView) {
        super(itemView);
        recommand_item_viewpager = itemView.findViewById(R.id.recommand_item_viewpager);
        recommand_item_des = itemView.findViewById(R.id.recommand_item_des);
        recommand_item_name = itemView.findViewById(R.id.recommand_item_name);
        recommand_item_photo = itemView.findViewById(R.id.recommand_item_photo);
        recommand_item_tags = itemView.findViewById(R.id.recommand_item_tags);
        recommand_item_time = itemView.findViewById(R.id.recommand_item_time);
        recommand_share = itemView.findViewById(R.id.recommand_share);
        recommand_collection = itemView.findViewById(R.id.recommand_collection);
        recommand_like = itemView.findViewById(R.id.recommand_like);
        recommand_send = itemView.findViewById(R.id.recommand_send);
        
    }
}
