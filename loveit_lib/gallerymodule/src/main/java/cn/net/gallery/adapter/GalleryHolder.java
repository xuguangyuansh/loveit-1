package cn.net.gallery.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import cn.net.gallery.R;

class GalleryHolder extends RecyclerView.ViewHolder {

    public ImageView gallery_item_iv;

    public GalleryHolder(@NonNull View itemView) {
        super(itemView);
        gallery_item_iv = itemView.findViewById(R.id.gallery_item_iv);
    }
}
