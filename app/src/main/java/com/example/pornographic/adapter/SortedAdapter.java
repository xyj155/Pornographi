package com.example.pornographic.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.pornographic.R;
import com.example.pornographic.gson.SortedBean;
import com.example.pornographic.util.GlideUtil;
import com.example.pornographic.view.VideoListActivity;

import java.util.List;

public class SortedAdapter extends BaseQuickAdapter<SortedBean, BaseViewHolder> {
    private Context context;

    public SortedAdapter(@Nullable List<SortedBean> data, Context context) {
        super(R.layout.sorted_item, data);
        this.context = context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final SortedBean item) {
//        GlideUtil.loadRoundCornerImage(item.getRes(),(ImageView) helper.getView(R.id.iv_icon));
        TextView view = helper.getView(R.id.tv_title);
        Drawable drawableLeft = context.getResources().getDrawable(
                item.getRes());

        view.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                null, null, null);
        view.setCompoundDrawablePadding(4);
        helper.setText(R.id.tv_title, item.getTitle())
                .setOnClickListener(R.id.tv_title, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (helper.getPosition()) {
                            case 0:
                            case 1:
                            case 2:
                            case 3:
                            case 4:
                                Intent intent = new Intent(context, VideoListActivity.class);
                                intent.putExtra("kind", item.getTitle());
                                context.startActivity(intent);
                                break;
                            case 5:
                                Toast.makeText(context, "待开发", Toast.LENGTH_SHORT).show();
                                break;
                            case 6:
                                Toast.makeText(context, "待开发", Toast.LENGTH_SHORT).show();
                                break;
                        }

                    }
                });
    }
}
