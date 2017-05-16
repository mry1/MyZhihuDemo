package com.louis.myzhihudemo.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.louis.myzhihudemo.api.bean.HomeStory;
import com.louis.myzhihudemo.ui.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Louis on 2017/5/16.
 */

public class HomeStoryAdapter extends BaseQuickAdapter<HomeStory.HomeStories, BaseViewHolder> {
    private Context mContext;

    public HomeStoryAdapter(Context context, @Nullable List<HomeStory.HomeStories> data) {
        super(R.layout.item_nomal_story, data);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeStory.HomeStories item) {
        ImageView image = helper.getView(R.id.image_view);
        Picasso.with(mContext).load(item.images.get(0)).into(image);
        helper.setText(R.id.tv_title, item.title);
    }
}
