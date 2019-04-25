package com.example.pornographic.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pornographic.R;
import com.example.pornographic.adapter.UserInviteAdapter;
import com.example.pornographic.base.BaseActivity;
import com.example.pornographic.gson.User;
import com.example.pornographic.gson.UserInvite;
import com.example.pornographic.util.SharePreferenceUtil;
import com.example.pornographic.weight.toast.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class BindUserAccountActivity extends BaseActivity {


    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_menu)
    TextView tvMenu;
    @BindView(R.id.tb_common)
    RelativeLayout tbCommon;
    @BindView(R.id.ry_user)
    RecyclerView ryUser;
    @BindView(R.id.sml_user)
    SmartRefreshLayout smlUser;
    private UserInviteAdapter userInviteAdapter;

    @Override
    public int initActivityLayout() {
        return R.layout.activity_bind_user_account;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        initToolbar("下线用户");
        userInviteAdapter = new UserInviteAdapter(null);
        smlUser.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                userArrayList.clear();
                queryBindUser(0);
            }
        });
        smlUser.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                queryBindUser(userArrayList.size());
            }
        });
    }

    private List<User> userArrayList = new ArrayList<>();

    @Override
    public void initData() {
        smlUser.autoRefresh();
        ryUser.setLayoutManager(new LinearLayoutManager(BindUserAccountActivity.this));
        ryUser.setAdapter(userInviteAdapter);
    }

    private static final String TAG = "BindUserAccountActivity";

    private void queryBindUser(int page) {
        BmobQuery<UserInvite> userInviteBmobQuery = new BmobQuery<>();
        userInviteBmobQuery.setSkip(page);
        userInviteBmobQuery.setLimit(20);
        userInviteBmobQuery.addWhereEqualTo("UserId", SharePreferenceUtil.getUser("id", "String"));
        userInviteBmobQuery.findObjects(new FindListener<UserInvite>() {
            @Override
            public void done(List<UserInvite> list, BmobException e) {
                if (e == null) {
                    Log.i(TAG, "done: "+list.size());
                    for (int i = 0; i < list.size(); i++) {
                        BmobQuery<User> userBmobQuery = new BmobQuery<>();
                        userBmobQuery.addWhereEqualTo("objectId", list.get(i).getInviteId());
                        userBmobQuery.findObjects(new FindListener<User>() {
                            @Override
                            public void done(List<User> list, BmobException e) {
                                if (e == null) {
                                    userArrayList.add(list.get(0));
                                    userInviteAdapter.replaceData(userArrayList);
                                }
                            }
                        });
                    }

                } else {
                    ToastUtils.show("查询出错");
                }
                smlUser.finishRefresh();
                smlUser.finishLoadMore();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

    }
}
