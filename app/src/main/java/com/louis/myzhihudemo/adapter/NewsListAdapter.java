package com.louis.myzhihudemo.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.louis.myzhihudemo.api.bean.StoryList;
import com.louis.myzhihudemo.ui.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Louis on 2017/5/14.
 */

public class NewsListAdapter extends BaseQuickAdapter<StoryList.Story, BaseViewHolder> {

    private Context mContext;

    public NewsListAdapter(Context context, @Nullable List<StoryList.Story> data) {
        super(R.layout.item_nomal_story, data);
        mContext = context;
    }

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
//    public NewsListAdapter(Context context, List<StoryList.Story> data) {
//        super(data);
//        mContext = context;
//        addItemType(StoryMultiItem.ITEM_TYPE_HEADER, R.layout.item_header_story);
//        addItemType(StoryList.Story.ITEM_TYPE_NORMAL, R.layout.item_nomal_story);
//    }
    @Override
    protected void convert(BaseViewHolder helper, StoryList.Story item) {
//        switch (item.getItemType()) {
//            case StoryList.Story.ITEM_TYPE_HEADER:
//                handlerHeaderStory(helper, item.mStory);
//                break;
//            case StoryList.Story.ITEM_TYPE_NORMAL:
        handlerNormalStory(helper, item);
//                break;
//
//        }

    }

    /**
     * 处理头部大图item
     *
     * @param holder
     * @param story
     */
    private void handlerHeaderStory(BaseViewHolder holder, StoryList story) {
        ImageView themeThumbnail = holder.getView(R.id.iv_theme_thumbnail);
        Picasso.with(mContext).load(story.background).into(themeThumbnail);
        holder.setText(R.id.tv_theme_desc, story.description);

    }

    /**
     * 处理正常item
     *
     * @param holder
     * @param story
     */
    private void handlerNormalStory(BaseViewHolder holder, StoryList.Story story) {
        holder.setText(R.id.tv_title, story.title);
        ImageView images = holder.getView(R.id.image_view);
        if (story.images != null && story.images.size() > 0) {
            Picasso.with(mContext).load(story.images.get(0)).into(images);
        }else{
            images.setVisibility(View.GONE);
        }

    }
}
