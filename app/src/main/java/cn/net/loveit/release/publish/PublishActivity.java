package cn.net.loveit.release.publish;


import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import cn.net.base.BaseActivity;
import cn.net.base.constant.RoutePath;
import cn.net.loveit.R;
import cn.net.loveit.release.adapter.PublishPhotoAdapter;
import cn.net.view.decoration.SpacesItemDecoration;
import cn.net.view.utils.GlideUtil;
import cn.net.view.utils.UIUtil;


@Route(path=RoutePath.ACTIVITY_PUBLISH_URI)
public class PublishActivity extends BaseActivity {
    private LinearLayout photo_image;
    private LinearLayout photo_continue;
//    private ImageView show_image;
    private String imageUrl;
    private RecyclerView photo_list;
    public static int PHOTO_COUNT=3;

    private List<String> photos=new ArrayList<>();
    private PublishPhotoAdapter adapter;
    private EditText et_topic;
    private TextView tv_topic_size;
    private EditText et_tag;
    private LinearLayout ll_position;
    private EditText et_position;

    @Override
    protected boolean hideTitle() {
        return true;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initIntentDatas() {
        Intent intent = getIntent();
        imageUrl = intent.getStringExtra("url");
    }

    @Override
    protected void initStatusBar() {

    }

    @Override
    protected void initView() {
        photo_image = findViewById(R.id.photo_image);
        photo_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (!TextUtils.isEmpty(imageUrl)&&photos.size()<PHOTO_COUNT){
            photos.add(imageUrl);
        }

        photo_continue = findViewById(R.id.photo_continue);
        photo_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

//        show_image = findViewById(R.id.show_image);
        photo_list = findViewById(R.id.photo_list);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, PHOTO_COUNT);
        gridLayoutManager.setSmoothScrollbarEnabled(true);
        photo_list.addItemDecoration(new SpacesItemDecoration(UIUtil.dip2px(this, 8), PHOTO_COUNT));
        photo_list.setLayoutManager(gridLayoutManager);

        adapter = new PublishPhotoAdapter(this, photos);

        adapter.setPublishPhotoListener(new PublishPhotoAdapter.PublishPhotoListener() {
            @Override
            public void onRemove(int position) {
                photos.remove(position);
                if (adapter!=null)
                    adapter.notifyDataSetChanged();
            }
        });

        photo_list.setAdapter(adapter);


//        if (!TextUtils.isEmpty(imageUrl)){
//            GlideUtil.getInstance(this).skipMemoryCache(true).setDiskCacheStrategy(DiskCacheStrategy.NONE).loadImage(imageUrl, show_image);
//        }
        et_topic = findViewById(R.id.et_topic);
        tv_topic_size = findViewById(R.id.tv_topic_size);
        et_tag = findViewById(R.id.et_tag);
        ll_position = findViewById(R.id.ll_position);
        et_position = findViewById(R.id.et_position);

        et_topic.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString();
                tv_topic_size.setText(str.length()+"/20");
            }
        });

        et_tag.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        ll_position.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_publish;
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String imageUrl = intent.getStringExtra("url");
        if (!TextUtils.isEmpty(imageUrl)&&photos.size()<PHOTO_COUNT){
            photos.add(imageUrl);
            if (adapter!=null){
                adapter.notifyDataSetChanged();
            }
        }
    }
}
