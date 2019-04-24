package com.example.pornographic.view;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pornographic.R;
import com.example.pornographic.adapter.ShortVideoAdapter;
import com.example.pornographic.base.BaseActivity;
import com.example.pornographic.gson.HotVideo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class VideoListActivity extends BaseActivity {

    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_menu)
    TextView tvMenu;
    @BindView(R.id.tb_common)
    RelativeLayout tbCommon;
    @BindView(R.id.ry_video)
    RecyclerView ryVideo;
    @BindView(R.id.sml_video)
    SmartRefreshLayout smlVideo;
    private ShortVideoAdapter shortVideoAdapter;
    private List<HotVideo> hotVideos = new ArrayList<>();

    @Override
    public int initActivityLayout() {
        return R.layout.activity_video_list;
    }

    private int page = 1;

    @Override
    public void initView() {
        ButterKnife.bind(this);
        initToolbar(getIntent().getStringExtra("kind"));
        shortVideoAdapter = new ShortVideoAdapter(null, VideoListActivity.this);
        final BmobQuery<HotVideo> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("Kind",getIntent().getStringExtra("kind"));
        bmobQuery.setSkip(0);
        bmobQuery.setLimit(10);
        smlVideo.autoRefresh();
        ryVideo.setLayoutManager(new GridLayoutManager(VideoListActivity.this, 2));
        ryVideo.setAdapter(shortVideoAdapter);
        smlVideo.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                hotVideos.clear();
                page = 1;

                bmobQuery.findObjects(new FindListener<HotVideo>() {
                    @Override
                    public void done(List<HotVideo> list, BmobException e) {
                        hotVideos.addAll(list);
                        shortVideoAdapter.addData(hotVideos);
                        smlVideo.finishRefresh();
                        Log.i(TAG, "done:1 "+list.size());
                    }
                });
            }
        });

        smlVideo.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                page++;
                bmobQuery.findObjects(new FindListener<HotVideo>() {
                    @Override
                    public void done(List<HotVideo> list, BmobException e) {
                        hotVideos.addAll(list);
                        Log.i(TAG, "done: 2"+list.size());
                        shortVideoAdapter.replaceData(hotVideos);
                        smlVideo.finishLoadMore();
                    }
                });
            }
        });
    }

    @Override
    public void initData() {

    }

    private static final String TAG = "VideoListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
