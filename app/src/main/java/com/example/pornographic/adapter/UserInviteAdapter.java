package com.example.pornographic.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.pornographic.R;
import com.example.pornographic.gson.User;

import java.util.List;

public class UserInviteAdapter extends BaseQuickAdapter<User, BaseViewHolder> {
    public UserInviteAdapter(@Nullable List<User> data) {
        super(R.layout.user_invite_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, User item) {
        helper.setText(R.id.tv_username, item.getNickName())
        .setText(R.id.tv_time,item.getCreatedAt());
    }
}
