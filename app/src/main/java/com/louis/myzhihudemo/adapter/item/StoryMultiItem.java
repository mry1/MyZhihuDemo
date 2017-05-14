package com.louis.myzhihudemo.adapter.item;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.louis.myzhihudemo.api.bean.StoryList;

/**
 * Created by Louis on 2017/5/14.
 */

public class StoryMultiItem implements MultiItemEntity {
    public static final int ITEM_TYPE_NORMAL = 1;
    public static final int ITEM_TYPE_HEADER = 2;
    private int itemType;
    public StoryList.Story mStory;

    public StoryMultiItem(int itemType, StoryList.Story story) {
        this.itemType = itemType;
        mStory = story;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
