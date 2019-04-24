package com.example.pornographic.view;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pornographic.R;
import com.example.pornographic.adapter.VerticalAVAdapterAdapter;
import com.example.pornographic.base.BaseFragment;
import com.example.pornographic.gson.ComicAV;
import com.example.pornographic.weight.toast.ToastUtils;
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

public class HomeComicFragment extends BaseFragment {
    @BindView(R.id.ry_comic)
    RecyclerView ryComic;
    @BindView(R.id.sml_comic)
    SmartRefreshLayout smlComic;
    Unbinder unbinder;
    private VerticalAVAdapterAdapter verticalAVAdapterAdapter;
    private List<ComicAV> comicAVList = new ArrayList<>();

    @Override
    public int initLayout() {
        return R.layout.fragment_home_comic;
    }

    @Override
    public void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        verticalAVAdapterAdapter = new VerticalAVAdapterAdapter(null, getContext());
        ryComic.setLayoutManager(new GridLayoutManager(getContext(), 3));
        final BmobQuery<ComicAV> comicAVBmobQuery = new BmobQuery<>();
        comicAVBmobQuery.setLimit(20);
        smlComic.autoRefresh();
        smlComic.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                comicAVBmobQuery.setSkip(1);
                comicAVBmobQuery.findObjects(new FindListener<ComicAV>() {
                    @Override
                    public void done(List<ComicAV> list, BmobException e) {
                        if (e == null) {
                            comicAVList.clear();
                            comicAVList.addAll(list);
                            verticalAVAdapterAdapter.replaceData(comicAVList);
                        } else {
                            ToastUtils.show("查询出错");
                        }
                        smlComic.finishRefresh();
                    }
                });
            }
        });
        smlComic.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                comicAVBmobQuery.setSkip(comicAVList.size());
                comicAVBmobQuery.findObjects(new FindListener<ComicAV>() {
                    @Override
                    public void done(List<ComicAV> list, BmobException e) {
                        if (e == null) {
                            comicAVList.addAll(list);
                            verticalAVAdapterAdapter.addData(comicAVList);

                        } else {
                            ToastUtils.show("查询出错");
                        }
                        smlComic.finishLoadMore();
                    }
                });
            }
        });


        ryComic.setAdapter(verticalAVAdapterAdapter);
    }

    @Override
    public void initData() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
