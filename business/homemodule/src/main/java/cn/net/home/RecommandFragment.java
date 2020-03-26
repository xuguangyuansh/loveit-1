package cn.net.home;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.android.arouter.launcher.ARouter;

import cn.net.base.BaseFragment;
import cn.net.base.constant.RoutePath;
import cn.net.home.adapter.RecommandAdapter;

import java.util.ArrayList;
import java.util.List;

public class RecommandFragment extends BaseFragment {

    private SwipeRefreshLayout recommand_refresh;
    private RecyclerView recommend_list;
    private List dataList=new ArrayList();
    private RecommandAdapter adapter;


    public static BaseFragment getFragment(int index) {
        RecommandFragment recommand = new RecommandFragment();
        Bundle bundle = new Bundle();
//        bundle.putString("string", string);
        recommand.setArguments(bundle);
        return recommand;
    }

    @Override
    protected void initIntentDatas() {
        Bundle arguments = getArguments();
        if (arguments != null) {
//            name = arguments.getString("string");
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recommand;
    }

    @Override
    protected void initViews() {
        recommand_refresh = getRootView().findViewById(R.id.recommand_refresh);
        recommend_list = getRootView().findViewById(R.id.recommend_list);
        recommand_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recommand_refresh.setRefreshing(false);
                    }
                },1000);
            }
        });
        dataList.add("aaa");
        dataList.add("bb");
        dataList.add("ccc");
        dataList.add("ccc");
        dataList.add("ccc");
        dataList.add("ccc");
        dataList.add("ccc");
        dataList.add("ccc");
        if (adapter==null)
            adapter = new RecommandAdapter(dataList,getContext());

        adapter.setOnItemClickListener(new RecommandAdapter.OnItemClick() {
            @Override
            public void onItemClick(int position) {
                ARouter.getInstance().build(RoutePath.ACTIVITY_OTHER_PAGE_URI).withString("customerId","111").navigation();
            }
        });

        recommend_list.setLayoutManager(new LinearLayoutManager(getContext()));
        recommend_list.setAdapter(adapter);
        Log.e("RecommandFragment","initview");
    }

}
