package com.example.pornographic.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dueeeke.videocontroller.StandardVideoController;
import com.dueeeke.videoplayer.player.IjkVideoView;
import com.example.pornographic.R;
import com.example.pornographic.base.BaseActivity;
import com.example.pornographic.util.SharePreferenceUtil;
import com.example.pornographic.weight.MyDialog;
import com.example.pornographic.weight.toast.ToastUtils;

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
        String user = (String) SharePreferenceUtil.getUser("rank", "String");
        if (user.isEmpty()) {
            showMsgDialog("免费获取会员", "你还没有会员哦！你可以通过推广1位以上好友使用，免费获取VIP！", new OnItemClickListener() {
                @Override
                public void onConfirm(MyDialog dialog) {
                    startActivity(new Intent(VideoPlayerActivity.this, UserScoreActivity.class));
                    finish();
                }

                @Override
                public void onCancel(MyDialog dialog) {
                    finish();
                }
            });
        } else {
            player.startFullScreen();
        }

        long currentPosition = player.getCurrentPosition();
        long duration = player.getDuration();
        Log.i(TAG, "initView: getDuration" + duration);
        Log.i(TAG, "initView: getCurrentPosition" + currentPosition);
    }

    private static final String TAG = "VideoPlayerActivity";

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
