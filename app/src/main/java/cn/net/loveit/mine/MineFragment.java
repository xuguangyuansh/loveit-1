package cn.net.loveit.mine;

import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import cn.net.base.BaseFragment;
import cn.net.base.constant.RoutePath;
import cn.net.loveit.R;
import cn.net.view.mine.MineHeader;
import cn.net.view.mine.MineItem;

@Route(path = RoutePath.FRAGMENT_MINE_URI)
public class MineFragment extends BaseFragment {

    private LinearLayout mine_notification;
    private MineHeader mine_header;
    private MineItem mine_item_make;
    private MineItem mine_item_sale;
    private MineItem mine_item_buy;
    private MineItem mine_item_get_buy;
    private MineItem mine_item_collection;
    private MineItem mine_item_star;
    private MineItem mine_item_money;
    private MineItem mine_item_setting;

    @Override
    protected void initIntentDatas() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initViews() {
        mine_notification = getRootView().findViewById(R.id.mine_notification);
        mine_header = getRootView().findViewById(R.id.mine_header);
        mine_item_make = getRootView().findViewById(R.id.mine_item_make);
        mine_item_sale = getRootView().findViewById(R.id.mine_item_sale);
        mine_item_buy = getRootView().findViewById(R.id.mine_item_buy);
//        mine_item_get_buy = getRootView().findViewById(R.id.mine_item_get_buy);
        mine_item_collection = getRootView().findViewById(R.id.mine_item_collection);
        mine_item_star = getRootView().findViewById(R.id.mine_item_star);
//        mine_item_money = getRootView().findViewById(R.id.mine_item_money);
        mine_item_setting = getRootView().findViewById(R.id.mine_item_setting);

        mine_header.setHeaderViewClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(RoutePath.ACTIVITY_MINE_CHANGE_INFO_URI).navigation();
            }
        });

        mine_header.setFriendsClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(RoutePath.ACTIVITY_MINE_MY_FRIENDS_URI).navigation();
            }
        });

        mine_header.setFocusClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(RoutePath.ACTIVITY_MINE_MY_FOCUS_URI).navigation();
            }
        });

        mine_header.setFansClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(RoutePath.ACTIVITY_MINE_MY_FANS_URI).navigation();
            }
        });

        mine_item_make.setItemClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(RoutePath.ACTIVITY_MINE_MY_MAKE_URI).navigation();
            }
        });
        mine_item_sale.setItemClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(RoutePath.ACTIVITY_MINE_MY_SALE_URI).navigation();
            }
        });
        mine_item_buy.setItemClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(RoutePath.ACTIVITY_MINE_MY_BUY_URI).navigation();
            }
        });
//        mine_item_get_buy.setItemClick(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ARouter.getInstance().build(RoutePath.ACTIVITY_MINE_MY_BUY_URI).navigation();
//            }
//        });
        mine_item_collection.setItemClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mine_item_star.setItemClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

//        mine_item_money.setItemClick(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        mine_item_setting.setItemClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(RoutePath.ACTIVITY_MINE_SETTING_URI).navigation();
            }
        });


    }
}
