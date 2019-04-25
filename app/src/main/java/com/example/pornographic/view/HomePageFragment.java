package com.example.pornographic.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pornographic.R;
import com.example.pornographic.adapter.BannerViewHolder;
import com.example.pornographic.adapter.HomePagerAdapter;
import com.example.pornographic.base.BaseFragment;
import com.example.pornographic.gson.AVStarts;
import com.example.pornographic.gson.Banner;
import com.example.pornographic.weight.DecoratorViewPager;
import com.example.pornographic.weight.ViewPageIndicator;
import com.example.pornographic.weight.ViewPagerForScrollView;
import com.example.pornographic.weight.toast.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * @author Xuyijie
 */
public class HomePageFragment extends BaseFragment {

    @BindView(R.id.mz_home)
    MZBannerView mzHome;
    Unbinder unbinder;
    @BindView(R.id.vi_home)
    ViewPageIndicator viHome;
    @BindView(R.id.vp_home)
    ViewPager vpHome;
    Unbinder unbinder1;
    @BindView(R.id.sml_home)
    SmartRefreshLayout smlHome;
    @BindView(R.id.iv_hot)
    TextView ivHot;
    @BindView(R.id.iv_select)
    TextView ivSelect;
    @BindView(R.id.iv_member)
    TextView ivMember;
    @BindView(R.id.iv_picture)
    TextView ivPicture;
    @BindView(R.id.iv_sign)
    TextView ivSign;

    @Override
    public int initLayout() {
        return R.layout.fragment_home_page;
    }

    @Override
    public void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void initData() {
        // 设置数据
        smlHome.autoRefresh();
        smlHome.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                initAVStart();
                initBanner();
            }
        });
    }

    private void initAVStart() {
        final List<Fragment> fragments = new ArrayList<>();
        BmobQuery<AVStarts> avStartGsonBmobQuery = new BmobQuery<>();
        avStartGsonBmobQuery.findObjects(new FindListener<AVStarts>() {
            @Override
            public void done(List<AVStarts> list, BmobException e) {
                if (e == null) {
                    for (int i = 0; i < list.size(); i++) {
                        HomeAVStartFragment e1 = new HomeAVStartFragment();
                        e1.setTitle(list.get(i).getName());
                        fragments.add(e1);
                    }
                    viHome.setTitles(list);
                    HomePagerAdapter test1 = new HomePagerAdapter(getChildFragmentManager(), fragments);
                    vpHome.setAdapter(test1);

                    viHome.setOnItenClickListener(new ViewPageIndicator.OnItenClickListener() {
                        @Override
                        public void setOnClickListener(String title) {
                            Log.i(TAG, "setOnClickListener: "+title);
                        }
                    });
                    viHome.setViewPager(vpHome);
                }
                smlHome.finishRefresh();
            }
        });

    }

    private void initBanner() {
        BmobQuery<Banner> query = new BmobQuery<>();
        query.setLimit(5)
                .findObjects(new FindListener<Banner>() {
                    @Override
                    public void done(List<Banner> list, BmobException e) {
                        if (e == null) {
                            mzHome.setPages(list, new MZHolderCreator<BannerViewHolder>() {
                                @Override
                                public BannerViewHolder createViewHolder() {
                                    return new BannerViewHolder();
                                }
                            });
                            mzHome.start();
                        } else {
                            Log.i(TAG, "done: " + e.getMessage());
                        }
                        smlHome.finishRefresh();
                    }
                });
    }

    private static final String TAG = "HomePageFragment";

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

    @OnClick({R.id.iv_hot, R.id.iv_select, R.id.iv_member, R.id.iv_picture, R.id.iv_sign})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_hot:
                break;
            case R.id.iv_select:
                startActivity(new Intent(getContext(),VideoTopicActivity.class));
                break;
            case R.id.iv_member:
                startActivity(new Intent(getContext(), MemberActivity.class));
                break;
            case R.id.iv_picture:

                break;
            case R.id.iv_sign:
                ToastUtils.show("待开发");
                break;
        }
    }
}
