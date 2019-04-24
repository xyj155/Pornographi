package com.example.pornographic.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pornographic.R;
import com.example.pornographic.adapter.SortedAdapter;
import com.example.pornographic.base.BaseFragment;
import com.example.pornographic.gson.SortedBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SortedFragment extends BaseFragment {


    @BindView(R.id.ry_kind)
    RecyclerView ryKind;
    Unbinder unbinder;
    private SortedAdapter sortedAdapter;

    @Override
    public int initLayout() {
        return R.layout.fragment_sorted;
    }

    @Override
    public void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        ryKind.setLayoutManager(new LinearLayoutManager(getContext()));
        List<SortedBean> stringList = new ArrayList<>();
        stringList.add(new SortedBean("大陆",R.mipmap.xihuan));
        stringList.add(new SortedBean("日韩",R.mipmap.ziyuan_5));
        stringList.add(new SortedBean("欧美",R.mipmap.ziyuan));
        stringList.add(new SortedBean("动画",R.mipmap.xihuan));
        stringList.add(new SortedBean("三级",R.mipmap.ziyuan_3));
        stringList.add(new SortedBean("约炮秘籍",R.mipmap.ziyuan_2));
        stringList.add(new SortedBean("美图",R.mipmap.zuji));
        sortedAdapter = new SortedAdapter(stringList, getActivity());
        ryKind.setAdapter(sortedAdapter);
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
