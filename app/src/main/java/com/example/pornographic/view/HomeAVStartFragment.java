package com.example.pornographic.view;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pornographic.R;
import com.example.pornographic.adapter.HomeAVStartAdapter;
import com.example.pornographic.adapter.ShortVideoAdapter;
import com.example.pornographic.base.BaseFragment;
import com.example.pornographic.gson.HotVideo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class HomeAVStartFragment extends BaseFragment {
    private static final String TAG = "HomeAVStartFragment";
    @BindView(R.id.ry_av)
    RecyclerView ryAv;
    Unbinder unbinder;
    private HomeAVStartAdapter shortVideoAdapter;
    private List<HotVideo> hotVideos = new ArrayList<>();

   private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private int page = 1;

    @Override
    public int initLayout() {
        return R.layout.fragment_home_av_start;
    }

    @Override
    public void initView(View view) {
        unbinder = ButterKnife.bind(this, view);

    }

    @Override
    public void initData() {
        shortVideoAdapter = new HomeAVStartAdapter( getContext(),null);
        LinearLayoutManager layout = new LinearLayoutManager(getContext());
        layout.setOrientation(LinearLayoutManager.HORIZONTAL);
        ryAv.setLayoutManager(layout);
        ryAv.setNestedScrollingEnabled(false);
        final BmobQuery<HotVideo> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("Start",getTitle());
        bmobQuery.setLimit(15);
        bmobQuery.findObjects(new FindListener<HotVideo>() {
            @Override
            public void done(List<HotVideo> list, BmobException e) {
                shortVideoAdapter.replaceData(list);
                ryAv.setAdapter(shortVideoAdapter);
                Log.i(TAG, "done:1 "+list.size());
            }
        });
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
