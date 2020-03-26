package cn.net.gallery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.net.gallery.R;
import cn.net.gallery.callback.OnItemClickListener;
import cn.net.model.gallery.Image;
import cn.net.view.utils.GlideUtil;
import cn.net.view.utils.UIUtil;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryHolder> {
    private Context context;
    private List<Image> list;
    private OnItemClickListener listener;

    public GalleryAdapter(Context context, List<Image> list){
        this.list=list;
        this.context=context;
    }

    @NonNull
    @Override
    public GalleryHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.gallery_item, null);
        GalleryHolder holder = new GalleryHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryHolder gralleryHolder, final int position) {
        Image image = list.get(position);
        String url = image.getUrl();
        GlideUtil.getInstance(context)./*setPlaceHolder(R.mipmap.ic_launcher).*/loadImage(url,gralleryHolder.gallery_item_iv);
        gralleryHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null)
                    listener.onItemClick(position);
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
