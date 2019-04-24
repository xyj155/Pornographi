package com.example.pornographic.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dueeeke.videocontroller.StandardVideoController;
import com.dueeeke.videoplayer.player.IjkVideoView;
import com.example.pornographic.R;
import com.example.pornographic.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoPlayerActivity extends BaseActivity {

    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_menu)
    TextView tvMenu;
    @BindView(R.id.tb_common)
    RelativeLayout tbCommon;
    @BindView(R.id.player)
    IjkVideoView player;



    @Override
    public int initActivityLayout() {
        return R.layout.activity_video_player;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        initToolbar("视频播放");
        player.setUrl(getIntent().getStringExtra("url")); //设置视频地址
        StandardVideoController controller = new StandardVideoController(this);
        controller.setTitle(getIntent().getStringExtra("title")); //设置视频标题
        player.setVideoController(controller); //设置控制器，如需定制可继承BaseVideoController
        player.start(); //开始播放，不调用则不自动播放
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onPause() {
        super.onPause();
        player.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        player.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();
    }


    @Override
    public void onBackPressed() {
        if (!player.onBackPressed()) {
            super.onBackPressed();
        }
    }
}
