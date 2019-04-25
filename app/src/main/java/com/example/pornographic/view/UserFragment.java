package com.example.pornographic.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pornographic.R;
import com.example.pornographic.base.BaseFragment;
import com.example.pornographic.gson.User;
import com.example.pornographic.gson.UserInvite;
import com.example.pornographic.util.SharePreferenceUtil;
import com.example.pornographic.weight.CircleImageView;
import com.example.pornographic.weight.toast.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tencent.bugly.beta.Beta;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

import static com.chad.library.adapter.base.listener.SimpleClickListener.TAG;

public class UserFragment extends BaseFragment {
    @BindView(R.id.iv_setting)
    TextView ivSetting;
    @BindView(R.id.iv_avatar)
    CircleImageView ivAvatar;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.tv_user_information)
    TextView tvUserInformation;
    @BindView(R.id.tv_acount)
    TextView tvAcount;
    @BindView(R.id.tv_user_score)
    TextView tvUserScore;
    @BindView(R.id.tv_user_invite)
    TextView tvUserInvite;
    @BindView(R.id.tv_user_password)
    TextView tvUserPassword;
    @BindView(R.id.tv_user_service)
    TextView tvUserService;
    @BindView(R.id.tv_user_version)
    TextView tvUserVersion;
    Unbinder unbinder;
    @BindView(R.id.sml_user)
    SmartRefreshLayout smlUser;
    Unbinder unbinder1;
    @BindView(R.id.tv_invite_user)
    TextView tvInviteUser;

    @Override
    public int initLayout() {
        return R.layout.fragment_user;
    }

    @Override
    public void onResume() {
        super.onResume();
        BmobQuery<UserInvite> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("InviteId", SharePreferenceUtil.getUser("id", "String"));
        bmobQuery.count(UserInvite.class, new CountListener() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (e == null) {
                    tvInviteUser.setText(integer + "");
                } else {
                    ToastUtils.show("查询出错");
                }
                smlUser.finishRefresh();
            }
        });
    }

    @Override
    public void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        String user = (String) SharePreferenceUtil.getUser("nikeName", "String");
        tvUsername.setText(user);
        smlUser.autoRefresh();
        smlUser.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                BmobQuery<UserInvite> bmobQuery = new BmobQuery<>();
                bmobQuery.addWhereEqualTo("UserId", SharePreferenceUtil.getUser("id", "String"));
                bmobQuery.count(UserInvite.class, new CountListener() {
                    @Override
                    public void done(Integer integer, BmobException e) {
                        if (e == null) {
                            tvInviteUser.setText(integer + "");
                        } else {
                            ToastUtils.show("查询出错");
                        }
                        smlUser.finishRefresh();
                    }
                });
            }
        });
    }

    private void showLoginDialog() {
        final AlertDialog customizeDialog = new AlertDialog.Builder(getContext()).create();
        final View dialogView = LayoutInflater.from(getContext())
                .inflate(R.layout.dialog_user_invite, null);
        customizeDialog.setTitle("邀请码填写");
        customizeDialog.setView(dialogView);
        customizeDialog.show();
        final EditText etUsername = dialogView.findViewById(R.id.et_code);
        TextView btnRegister = dialogView.findViewById(R.id.btn_login);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etUsername.getText().toString().isEmpty()) {
                    ToastUtils.show("邀请码不可为空");
                } else {
                    final BmobQuery<User> userBmobQuery = new BmobQuery<>();
                    userBmobQuery.addWhereEqualTo("objectId", etUsername.getText().toString());
                    userBmobQuery.findObjects(new FindListener<User>() {
                        @Override
                        public void done(List<User> list, BmobException e) {
                            if (e == null) {
                                if (list.size() == 0) {
                                    ToastUtils.show("邀请码错误");
                                } else {
                                    BmobQuery<UserInvite> bmobQuery = new BmobQuery<>();
                                    bmobQuery.addWhereEqualTo("UserId", SharePreferenceUtil.getUser("id", "String"));
                                    bmobQuery.findObjects(new FindListener<UserInvite>() {
                                        @Override
                                        public void done(List<UserInvite> list, BmobException e) {
                                            if (e == null) {
                                                Log.i(TAG, "done: " + list.size());
                                                if (list.size() == 0) {
                                                    UserInvite userInvite = new UserInvite();
                                                    userInvite.setUserId((String) SharePreferenceUtil.getUser("id", "String"));
                                                    userInvite.setInviteId(etUsername.getText().toString());
                                                    userInvite.save(new SaveListener<String>() {
                                                        @Override
                                                        public void done(String s, BmobException e) {
                                                            if (e == null) {
                                                                ToastUtils.show("邀请码填写成功");
                                                            } else {
                                                                ToastUtils.show("系统错误");
                                                            }
                                                            customizeDialog.dismiss();
                                                        }
                                                    });
                                                } else {
                                                    ToastUtils.show("你已填写邀请码");
                                                    customizeDialog.dismiss();
                                                }
                                            } else {
                                                ToastUtils.show("系统出错");
                                                customizeDialog.dismiss();
                                            }

                                        }
                                    });
                                }
                            } else {
                                ToastUtils.show("系统出错");
                                customizeDialog.dismiss();
                            }

                        }
                    });
                }

            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_user_information, R.id.tv_acount, R.id.tv_user_score, R.id.tv_user_invite, R.id.tv_user_password, R.id.tv_user_service, R.id.tv_user_version})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_user_information:
                showLoginDialog();
                break;
            case R.id.tv_acount:
                startActivity(new Intent(getContext(), BindUserAccountActivity.class));
                break;
            case R.id.tv_user_score:
//                ToastUtils.show("待开发");
                startActivity(new Intent(getContext(), UserScoreActivity.class));
                break;
            case R.id.tv_user_invite:
                startActivity(new Intent(getContext(), InviteUserActivity.class));
                break;
            case R.id.tv_user_password:
                startActivity(new Intent(getContext(), PasswordChangeActivity.class));
                break;
            case R.id.tv_user_service:
                String url = "mqqwpa://im/chat?chat_type=wpa&uin=2934046679";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                break;
            case R.id.tv_user_version:
                Beta.checkUpgrade();
                break;
        }
    }
}
