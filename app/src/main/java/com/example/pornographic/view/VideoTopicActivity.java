package com.example.pornographic.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pornographic.R;
import com.example.pornographic.adapter.HomePagerAdapter;
import com.example.pornographic.base.BaseActivity;
import com.example.pornographic.gson.VideoTopic;
import com.example.pornographic.weight.ViewPageIndicator;
import com.example.pornographic.weight.ViewPageTopicIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoTopicActivity extends BaseActivity {


    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_menu)
    TextView tvMenu;
    @BindView(R.id.tb_common)
    RelativeLayout tbCommon;
    @BindView(R.id.vi_home)
    ViewPageTopicIndicator viHome;
    @BindView(R.id.vp_home)
    ViewPager vpHome;

    @Override
    public int initActivityLayout() {
        return R.layout.activity_video_topic;
    }

    private List<VideoTopic> videoTopicList = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    public void initView() {
        ButterKnife.bind(this);
        initToolbar("精选专题");
        videoTopicList.add(new VideoTopic("亚洲电影","https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=3764126379,2060704259&fm=58&s=F1B51F7403426F51064259CF0300B0B9"));
        videoTopicList.add(new VideoTopic("欧美电影","https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3306766813,1597578038&fm=58&s=9700E5A5CA5253F54E3230A203005023"));
        videoTopicList.add(new VideoTopic("制服丝袜","https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=3239477366,546603218&fm=58&s=BD8AAB57EEB8629415BC49A50300E062"));
        videoTopicList.add(new VideoTopic("强奸乱伦","https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=1525355992,876386508&fm=58"));
        videoTopicList.add(new VideoTopic("国产自拍","https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=2137865195,3434536855&fm=58&s=FFA70AC6CC09CA5D0C0227BA0300C00A"));
        videoTopicList.add(new VideoTopic("变态另类","https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=487088010,1893364182&fm=58&s=8DE7F904C0222515FB0C759903009092"));
        videoTopicList.add(new VideoTopic("经典三级","https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=2256094430,3926784262&fm=58&s=F61B15C7502252BE1435A8FA03008039"));
        videoTopicList.add(new VideoTopic("成人动漫","https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=1143925357,1051122394&fm=58&s=F1B38B703C33E59C301D49CE0300A0A2"));

        for (int i = 0; i < videoTopicList.size(); i++) {
            HomeAVStartFragment e1 = new HomeAVStartFragment();
            e1.setTitle(videoTopicList.get(i).getTitle());
            fragments.add(e1);
        }
        viHome.setTitles(videoTopicList);
        HomePagerAdapter test1 = new HomePagerAdapter(getSupportFragmentManager(), fragments);
        vpHome.setAdapter(test1);
        viHome.setViewPager(vpHome);
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

    }
}
