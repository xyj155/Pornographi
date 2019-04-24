package com.example.pornographic.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.pornographic.R;
import com.example.pornographic.gson.HotVideo;
import com.example.pornographic.util.GlideUtil;
import com.example.pornographic.view.VideoPlayerActivity;

import java.util.List;

public class ShortVideoAdapter extends BaseQuickAdapter<HotVideo, BaseViewHolder> {
    private Context context;

    public ShortVideoAdapter(@Nullable List<HotVideo> data, Context context) {
        super(R.layout.shortvideo_item, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final HotVideo item) {
        helper.setText(R.id.tv_title, item.getVideoName())
                .setText(R.id.tv_time, item.getDate())
                .setOnClickListener(R.id.ll_item, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, VideoPlayerActivity.class);
                        intent.putExtra("url", item.getVideoUrl());
                        intent.putExtra("title", item.getVideoName());
                        context.startActivity(intent);
                    }
                });
        GlideUtil.loadRoundCornerAvatarImage(item.getPicture(), (ImageView) helper.getView(R.id.iv_cover), 10);
    }
}
