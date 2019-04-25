package com.example.pornographic.view;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pornographic.R;
import com.example.pornographic.base.BaseActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HotVideoActivity extends BaseActivity {


    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_menu)
    TextView tvMenu;
    @BindView(R.id.tb_common)
    RelativeLayout tbCommon;
    @BindView(R.id.ry_hot)
    RecyclerView ryHot;
    @BindView(R.id.sml_hot)
    SmartRefreshLayout smlHot;

    @Override
    public int initActivityLayout() {
        return R.layout.activity_hot_video;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        initToolbar("热播榜单");
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
