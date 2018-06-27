package com.louis.myzhihudemo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.louis.myzhihudemo.api.bean.VideoInfo;
import com.louis.myzhihudemo.ui.R;
import com.louis.myzhihudemo.utils.ImageLoader;
import com.louis.myzhihudemo.utils.imageloader.ImageLoader2;
import com.louis.myzhihudemo.utils.imageloader.LoaderOptions;
import com.louis.myzhihudemo.utils.imageloader.PicassoLoader;

import java.util.List;

public class VideoListAdapter extends BaseQuickAdapter<VideoInfo, BaseViewHolder> {
    private Context mContext;

    public VideoListAdapter(Context context, @Nullable List<VideoInfo> data) {
        super(R.layout.item_video_list, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoInfo item) {
        helper.setText(R.id.tv_title, item.title);
        ImageView iv_photo = helper.getView(R.id.iv_photo);
        ImageLoader2.getInstance()
                .with(mContext)
                .load(item.getCover())
                .angle(3)
                .resize(400, 600)
                .centerCrop()
                .diskCacheStrategy(LoaderOptions.DiskCacheStrategy.ALL)
                .into(iv_photo);

    }
}
