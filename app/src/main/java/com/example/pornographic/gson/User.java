package com.example.pornographic.gson;

import cn.bmob.v3.BmobUser;

public class User extends BmobUser {
    private String IME;
    private String NickName;
    private String MemerTime;
    private String MemerRank;

    public String getMemerRank() {
        return MemerRank;
    }

    public void setMemerRank(String memerRank) {
        MemerRank = memerRank;
    }

    public String getIME() {
        return IME;
    }

    public void setIME(String IME) {
        this.IME = IME;
    }

    public String getMemerTime() {
        return MemerTime;
    }

    public void setMemerTime(String memerTime) {
        MemerTime = memerTime;
    }

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
