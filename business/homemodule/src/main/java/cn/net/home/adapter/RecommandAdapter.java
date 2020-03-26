package cn.net.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import cn.net.base.utils.ToastHelper;
import cn.net.home.R;
import cn.net.home.holder.RecommandHolder;

import java.util.List;

public class RecommandAdapter extends RecyclerView.Adapter<RecommandHolder> {
    private Context ctx;
    private List list;
    private OnItemClick listener;

    public  RecommandAdapter(List list, Context ctx){
        this.list=list;
        this.ctx=ctx;

    }
    @NonNull
    @Override
    public RecommandHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.recyclerview_recommand_item, null);
        RecommandHolder recommandHolder = new RecommandHolder(view);
        return recommandHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecommandHolder viewHolder, final int position) {
        ImageAdapter imageAdapter = new ImageAdapter(ctx);
        viewHolder.recommand_item_viewpager.setId(position);
        viewHolder.recommand_item_viewpager.setAdapter(imageAdapter);

        viewHolder.recommand_item_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastHelper.showToast("个人主页");
            }
        });

        viewHolder.recommand_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastHelper.showToast("分享");
            }
        });

        viewHolder.recommand_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastHelper.showToast("收藏");
            }
        });

        viewHolder.recommand_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastHelper.showToast("喜欢");
            }
        });

        viewHolder.recommand_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastHelper.showToast("发送");
            }
        });
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null)
                    listener.onItemClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnItemClickListener(OnItemClick listener){
        this.listener=listener;
    }

    public interface OnItemClick{
        void onItemClick(int position);
    }
}
