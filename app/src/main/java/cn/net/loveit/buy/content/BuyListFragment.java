package cn.net.loveit.buy.content;

import android.os.Bundle;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.alibaba.android.arouter.launcher.ARouter;

import java.util.ArrayList;
import java.util.List;

import cn.net.base.BaseFragment;
import cn.net.base.constant.RoutePath;
import cn.net.loveit.R;

public class BuyListFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private List<String> list=new ArrayList<>();
    private StaggeredGridAdapter adapter;

    public static BaseFragment getFragment(int i) {
        BaseFragment listFragment=new BuyListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position",i);
        listFragment.setArguments(bundle);
        return listFragment;
    }

    @Override
    protected void initIntentDatas() {
        Bundle arguments = getArguments();
        int position = arguments.getInt("position");
        initData();
    }

    private void initData() {
        list.clear();
        list.add("aaaa");
        list.add("aaaa");
        list.add("aaaa");
        list.add("aaaa");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_buy_list;
    }

    public void loadMoreNotify(){
        int start = list.size();
//        list.addAll(newItems);
//        adapter.notifyItemInserted(start, list.size());
        adapter.notifyItemInserted(start);
    }

    public void pullRefresh(){
        list.clear();
//        list.addAll(newList);
        adapter.notifyItemRangeChanged(0, list.size());
    }



    @Override
    protected void initViews() {
        recyclerView = getRootView().findViewById(R.id.buy_list_recyclerview);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerView.setItemAnimator(null);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new StaggeredDividerItemDecoration(getContext(), 9));
        adapter = new StaggeredGridAdapter(getContext(), list);
        adapter.setOnItemClickListener(new StaggeredGridAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String s = list.get(position - 1);
                ARouter.getInstance().build(RoutePath.ACTIVITY_GOOD_DETAIL).navigation();
            }
        });
        recyclerView.setAdapter(adapter);
        int spanCount = 2;
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                int[] first = new int[spanCount];
                manager.findFirstCompletelyVisibleItemPositions(first);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && (first[0] == 1 || first[1] == 1)) {
                    manager.invalidateSpanAssignments();
                }
            }
        });
    }
}
