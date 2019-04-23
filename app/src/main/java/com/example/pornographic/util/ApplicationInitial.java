package com.example.pornographic.util;

import com.example.pornographic.App;

import cn.bmob.v3.Bmob;

/**
 * @author Xuyijie
 */
public class ApplicationInitial {
    public ApplicationInitial initBmob() {
        Bmob.initialize(App.getInstance(), "2a33abea32080fbb53b6103a504347ec");
        return this;
    }
}
