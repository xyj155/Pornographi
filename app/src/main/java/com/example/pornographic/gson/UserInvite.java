package com.example.pornographic.gson;

import cn.bmob.v3.BmobObject;

public class UserInvite extends BmobObject {
    private String InviteId;

    public String getInviteId() {
        return InviteId;
    }

    public void setInviteId(String inviteId) {
        InviteId = inviteId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    private String UserId;
}
