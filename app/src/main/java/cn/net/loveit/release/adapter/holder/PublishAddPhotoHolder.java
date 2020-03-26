package cn.net.loveit.release.adapter.holder;

import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import cn.net.loveit.R;

public class PublishAddPhotoHolder extends RecyclerView.ViewHolder {

    public LinearLayout add_photo_ll;

    public PublishAddPhotoHolder(@NonNull View itemView) {
        super(itemView);
        add_photo_ll = itemView.findViewById(R.id.add_photo_ll);
    }
}
