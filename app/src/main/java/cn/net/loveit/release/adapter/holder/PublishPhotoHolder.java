package cn.net.loveit.release.adapter.holder;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import cn.net.loveit.R;

public class PublishPhotoHolder extends RecyclerView.ViewHolder {

    public ImageView item_photo_iv;

    public PublishPhotoHolder(@NonNull View itemView) {
        super(itemView);
        item_photo_iv = itemView.findViewById(R.id.item_photo_iv);
    }
}
