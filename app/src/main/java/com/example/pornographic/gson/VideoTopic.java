package com.example.pornographic.gson;

import cn.bmob.v3.BmobObject;

public class VideoTopic {
    private String title;
    private String avatar;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public VideoTopic(String title, String avatar) {
        this.title = title;
        this.avatar = avatar;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
