package cn.net.loveit.release.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import cn.net.base.constant.RoutePath;
import cn.net.base.dialog.BaseDialog;
import cn.net.base.dialog.OptionsDialog;
import cn.net.loveit.R;
import cn.net.loveit.release.adapter.holder.PublishAddPhotoHolder;
import cn.net.loveit.release.adapter.holder.PublishPhotoHolder;
import cn.net.view.utils.GlideUtil;

import static cn.net.loveit.release.publish.PublishActivity.PHOTO_COUNT;

public class PublishPhotoAdapter extends RecyclerView.Adapter {
    private List<String> data;
    private Context ctx;
    public static int TYPE_PHOTO_ITEM=0;
    public static int TYPE_ADD_ITEM=1;
    private PublishPhotoListener listener;

    public PublishPhotoAdapter(Context ctx, List<String> data){
        this.ctx=ctx;
        this.data=data;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=null;
        RecyclerView.ViewHolder holder=null;
        if (viewType==TYPE_PHOTO_ITEM){
            view= LayoutInflater.from(ctx).inflate(R.layout.publish_item_photo,null);
            holder=new PublishPhotoHolder(view);
        }else if (viewType==TYPE_ADD_ITEM){
            view= LayoutInflater.from(ctx).inflate(R.layout.publish_item_add_photo,null);
            holder=new PublishAddPhotoHolder(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PublishPhotoHolder){
            PublishPhotoHolder photoHolder= (PublishPhotoHolder) holder;
            GlideUtil.getInstance(ctx).skipMemoryCache(true).setDiskCacheStrategy(DiskCacheStrategy.NONE).loadImage(data.get(position),photoHolder.item_photo_iv);
            photoHolder.item_photo_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //弹出对话框判断是否要删除
                    new OptionsDialog(ctx).setDialogTitle("删除").setContent("删除图片，重新选择？").setLeftButton("取消", new BaseDialog.OnNoClickListener() {
                        @Override
                        public void cancel() {

                        }
                    }).setRightButton("确定", new BaseDialog.OnYesClickListener() {
                        @Override
                        public void confirm() {
                            if (listener!=null){
                                listener.onRemove(position);
                            }
                        }
                    }).show();
                }
            });
        }else if (holder instanceof PublishAddPhotoHolder){
            ((PublishAddPhotoHolder) holder).add_photo_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ARouter.getInstance().build(RoutePath.ACTIVITY_RELEASE_PHOTO_URI).navigation();
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (data.size()<PHOTO_COUNT){
            if ((position+1)>data.size()){
                return TYPE_ADD_ITEM;
            }else{
                return TYPE_PHOTO_ITEM;
            }
        }
        return TYPE_PHOTO_ITEM;
    }

    @Override
    public int getItemCount() {
        if (data.size()<PHOTO_COUNT){
            return data.size()+1;
        }
        return PHOTO_COUNT;
    }

    public void setPublishPhotoListener(PublishPhotoListener listener){
        this.listener=listener;
    }


    public interface PublishPhotoListener{
        void onRemove(int position);
    }
}
