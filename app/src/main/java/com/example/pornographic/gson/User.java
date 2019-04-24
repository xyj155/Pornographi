package com.example.pornographic.gson;

import cn.bmob.v3.BmobUser;

public class User extends BmobUser {
private String IME;
private String NickName;

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public String getPhoneIME() {
        return IME;
    }

    public void setPhoneIME(String phoneIME) {
        IME = phoneIME;
    }
}
