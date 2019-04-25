package com.example.pornographic.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pornographic.R;
import com.example.pornographic.base.BaseActivity;
import com.example.pornographic.gson.User;
import com.example.pornographic.gson.UserInvite;
import com.example.pornographic.util.SharePreferenceUtil;
import com.example.pornographic.weight.toast.ToastUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.UpdateListener;

public class UserScoreActivity extends BaseActivity {


    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_menu)
    TextView tvMenu;
    @BindView(R.id.tb_common)
    RelativeLayout tbCommon;
    @BindView(R.id.tv_invite_user)
    TextView tvInviteUser;
    @BindView(R.id.ll_rank1)
    LinearLayout llRank1;
    @BindView(R.id.ll_rank2)
    LinearLayout llRank2;
    @BindView(R.id.ll_rank3)
    LinearLayout llRank3;
    @BindView(R.id.ll_rank4)
    LinearLayout llRank4;

    @Override
    public int initActivityLayout() {
        return R.layout.activity_user_score;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        initToolbar("会员兑换");
        getUserInviteCount();
    }

    private int userCount = -1;

    private void getUserInviteCount() {
        BmobQuery<UserInvite> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("UserId", SharePreferenceUtil.getUser("id", "String"));
        bmobQuery.count(UserInvite.class, new CountListener() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (e == null) {
                    userCount = integer;
                    tvInviteUser.setText(integer + "");
                } else {
                    ToastUtils.show("查询出错");
                }
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

    }

    @OnClick({R.id.ll_rank1, R.id.ll_rank2, R.id.ll_rank3, R.id.ll_rank4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_rank1:
                if (userCount < 500) {
                    ToastUtils.show("邀请人数未达到！");
                } else {
                    exchangeMemberRank("年度会员", 365);
                }
                break;
            case R.id.ll_rank2:
                if (userCount < 200) {
                    ToastUtils.show("邀请人数未达到！");
                } else {
                    exchangeMemberRank("季度会员", 180);
                }
                break;
            case R.id.ll_rank3:
                if (userCount < 50) {
                    ToastUtils.show("邀请人数未达到！");
                } else {
                    exchangeMemberRank("包月会员", 30);
                }
                break;
            case R.id.ll_rank4:
                if (userCount ==0) {
                    ToastUtils.show("邀请人数未达到！");
                } else {
                    exchangeMemberRank("三天体验会员", 3);
                }
                break;
        }
        getUserInviteCount();
    }

    private void exchangeMemberRank(final String rank, final int date) {
//        final UserInvite p = new UserInvite();
//        p.setInviteId((String) SharePreferenceUtil.getUser("id", "String"));
//        p.delete(new UpdateListener() {
//            @Override
//            public void done(BmobException e) {
//                if (e == null) {
                    User user = new User();
                    user.setObjectId((String) SharePreferenceUtil.getUser("id", "String"));
                    user.setMemerRank(rank);
                    int i = (int) (System.currentTimeMillis() / 1000);
                    int z = i + date * 60 * 60 * 24;
                    user.setMemerTime(String.valueOf(z));
                    user.update((String) SharePreferenceUtil.getUser("id", "String"), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Map<String, Object> map = new HashMap<>();
                                map.put("rank",rank);
                                SharePreferenceUtil.saveUser(map);
                                ToastUtils.show("兑换成功");
                            } else {
                                ToastUtils.show("兑换失败"+e.getMessage());
                            }
                        }
                    });
//                } else {
//                    ToastUtils.show("兑换失败"+e.getMessage());
//                }
//            }
//        });
    }
}
