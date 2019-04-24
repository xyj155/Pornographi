package com.example.pornographic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.example.pornographic.R;
import com.example.pornographic.gson.Banner;
import com.example.pornographic.util.GlideUtil;
import com.zhouwei.mzbanner.holder.MZViewHolder;

public  class BannerViewHolder implements MZViewHolder<Banner> {
        private ImageView mImageView;
        @Override
        public View createView(Context context) {
            // 返回页面布局
            View view = LayoutInflater.from(context).inflate(R.layout.banner_item,null);
            mImageView = (ImageView) view.findViewById(R.id.banner_image);
            return view;
        }

        @Override
        public void onBind(Context context, int position, Banner data) {
            // 数据绑定
//            mImageView.setImageResource(data);
            GlideUtil.loadRoundCornerAvatarImage(data.getBanner(),mImageView,10);
        }
    }