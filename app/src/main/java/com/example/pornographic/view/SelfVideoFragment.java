package com.example.pornographic.view;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pornographic.R;
import com.example.pornographic.adapter.ShortVideoAdapter;
import com.example.pornographic.base.BaseFragment;
import com.example.pornographic.gson.HotVideo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class SelfVideoFragment extends BaseFragment {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ry_shortvideo)
    RecyclerView ryShortvideo;
    Unbinder unbinder;
    @BindView(R.id.sml_video)
    SmartRefreshLayout smlVideo;
    Unbinder unbinder1;
    private ShortVideoAdapter shortVideoAdapter;
    private List<HotVideo> hotVideos = new ArrayList<>();

    @Override
    public int initLayout() {
        return R.layout.fragment_self_video;
    }

    @Override
    public void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    private int page = 1;

    @Override
    public void initData() {
        shortVideoAdapter = new ShortVideoAdapter(null,getContext());
        final BmobQuery<HotVideo> bmobQuery = new BmobQuery<>();
        bmobQuery.setLimit(10);
        bmobQuery.setSkip(0);
        smlVideo.autoRefresh();
        ryShortvideo.setLayoutManager(new GridLayoutManager(getContext(), 2));
        ryShortvideo.setAdapter(shortVideoAdapter);
        smlVideo.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                hotVideos.clear();
                page =1;
                bmobQuery.setSkip(0);
                bmobQuery.findObjects(new FindListener<HotVideo>() {
                    @Override
                    public void done(List<HotVideo> list, BmobException e) {
                        hotVideos.addAll(list);
                        shortVideoAdapter.replaceData(list);
                        smlVideo.finishRefresh();
                    }
                });
            }
        });

        smlVideo.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                page++;
                bmobQuery.setSkip(hotVideos.size());
                bmobQuery.findObjects(new FindListener<HotVideo>() {
                    @Override
                    public void done(List<HotVideo> list, BmobException e) {
                        hotVideos.addAll(list);
                        shortVideoAdapter.addData(list);
                        smlVideo.finishLoadMore();
                    }
                });
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
