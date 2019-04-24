package com.example.pornographic.util;

import com.example.pornographic.App;
import com.example.pornographic.weight.toast.ToastUtils;
import com.payelves.sdk.EPay;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;

import cn.bmob.v3.Bmob;
import cn.jpush.android.api.JPushInterface;

/**
 * @author Xuyijie
 */
public class ApplicationInitial {
    public ApplicationInitial initBmob() {
        Bmob.initialize(App.getInstance(), "2a33abea32080fbb53b6103a504347ec");
        return this;
    }
    public ApplicationInitial initToast() {
        ToastUtils.init(App.getInstance());
        return this;
    }
    public ApplicationInitial initPayment() {
        EPay.getInstance(App.getInstance()).init("wAwS4BHkB","1b0ccf51458c4053ae2931772fbbfb97",
               "7778897507581955", "baidu");
        return this;
    }
    public ApplicationInitial initJpush() {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(App.getInstance());
        return this;
    }
    public ApplicationInitial initBugly() {
        Beta.autoInit = true;
        Beta.autoCheckUpgrade = true;
        Bugly.init(App.getInstance(), "c02bea3990", false);

        return this;
    }
}
