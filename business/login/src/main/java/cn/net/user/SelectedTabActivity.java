package cn.net.user;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import cn.net.base.BaseActivity;
import cn.net.base.constant.RoutePath;
import cn.net.base.utils.LogUtils;
import cn.net.base.utils.ToastHelper;
import cn.net.login.R;
import cn.net.model.tag.BindTag;
import cn.net.model.tag.TagBean;
import cn.net.model.tag.TagListBean;
import cn.net.net.listener.PresenterCallback;
import cn.net.user.net.TabPresenter;
import cn.net.view.flowlayout.FlowLayout;
import cn.net.view.flowlayout.TagAdapter;
import cn.net.view.flowlayout.TagFlowLayout;


@Route(path = RoutePath.LOGIN_SELECT_TAG)
public class SelectedTabActivity extends BaseActivity implements View.OnClickListener {

    private TagFlowLayout flowlayout;
    private Button select_commit;
    private TextView select_cancel;
    private List<TagBean> tagList=new ArrayList<>();
    private TabPresenter tabPresenter;

    @Override
    protected void initPresenter() {
        if (tabPresenter==null){
            tabPresenter = new TabPresenter(this, null);
        }
        tabPresenter.setPresenterCallback(new PresenterCallback() {
            @Override
            public void onCallBack(boolean isSuccess, Object obj) {
                if (isSuccess){
                    if (obj instanceof TagListBean){
                        TagListBean data= (TagListBean) obj;
                        List<TagBean> list = data.getList();
                        LogUtils.e("zz",list.toString());
                        tagList.clear();
                        tagList.addAll(list);
                        if (flowlayout!=null){
                            flowlayout.getAdapter().notifyDataChanged();
                        }
                    }else if (obj instanceof BindTag){
                        ARouter.getInstance().build(RoutePath.ACTIVITY_MAIN_URI).navigation();
                        finish();
                    }
                }
            }
        });
        tabPresenter.getTagList();
    }

    @Override
    protected void initIntentDatas() {

    }

    @Override
    protected void initStatusBar() {

    }

    @Override
    protected void initView() {
        select_commit = findViewById(R.id.select_commit);
        select_cancel = findViewById(R.id.select_cancel);
        flowlayout = findViewById(R.id.flowlayout);
        flowlayout.setAdapter(new TagAdapter<TagBean>(tagList) {
            @Override
            public View getView(FlowLayout parent, int position, TagBean s) {
                TextView textView = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.view_tag, null);
                textView.setText(s.getLabelName());
                return textView;
            }
        });



        select_cancel.setOnClickListener(this);
        select_commit.setOnClickListener(this);
    }

    @Override
    protected boolean hideTitle() {
        return true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_selected_tab;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.select_cancel==id){
            ARouter.getInstance().build(RoutePath.ACTIVITY_MAIN_URI).navigation();
            finish();
        }else if (R.id.select_commit==id){
            //提交
            Set<Integer> selectedList = flowlayout.getSelectedList();
            if (selectedList!=null&&selectedList.size()>0){
                List<String> selectedTagList=new ArrayList<>();
                for (int position: selectedList){
                    selectedTagList.add(tagList.get(position).getLabelId());
                }
                tabPresenter.bindTag(selectedTagList);
            }else{
                ToastHelper.showToast(getString(R.string.tag_empty));
            }
        }
    }
}
