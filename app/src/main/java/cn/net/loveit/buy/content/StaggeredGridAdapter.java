package cn.net.loveit.buy.content;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.net.loveit.R;

public class StaggeredGridAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<String> list;
    public static final int STATIC_RES=0;
    public static final int SALE_ITEM=1;
    private OnItemClickListener listener;

    public StaggeredGridAdapter(Context context, List<String> list) {
        this.context=context;
        this.list=list;
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0){
            return STATIC_RES;
        }
        return SALE_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==STATIC_RES){
            View view = LayoutInflater.from(context).inflate(R.layout.sale_static_item, null);
            return new BuyStaticHolder(view);
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.buy_list_item, null);
            return new BuyListHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        if (itemViewType==STATIC_RES){
            BuyStaticHolder staticHolder= (BuyStaticHolder) holder;

        }else{
            BuyListHolder listHolder= (BuyListHolder) holder;
            listHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null){
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size()+1;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
}
