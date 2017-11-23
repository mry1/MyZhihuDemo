package com.louis.myzhihudemo.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.louis.myzhihudemo.local.table.BeautyPhotoInfo;
import com.louis.myzhihudemo.ui.R;
import com.louis.myzhihudemo.utils.DefIconFactory;
import com.louis.myzhihudemo.utils.StringUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by louis on 17-11-23.
 */

public class BeautyListAdapter extends BaseQuickAdapter<BeautyPhotoInfo, BaseViewHolder> {

    private Context mContext;
    private List<BeautyPhotoInfo> data;
    private final int mPhotoWidth;

    public BeautyListAdapter(Context context, @Nullable List<BeautyPhotoInfo> data) {
        super(R.layout.item_beauty_photos, data);
        this.mContext = context;
        this.data = data;
        int widthPixels = context.getResources().getDisplayMetrics().widthPixels;
        int marginPixels = context.getResources().getDimensionPixelOffset(R.dimen.photo_margin_width);
        mPhotoWidth = widthPixels / 2 - marginPixels;
    }

    @Override
    protected void convert(BaseViewHolder helper, BeautyPhotoInfo item) {
        if (item == null)
            return;
        ImageView ivPhoto = helper.getView(R.id.iv_photo);
        int photoHeight = StringUtils.calcPhotoHeight(item.getPixel(), mPhotoWidth);
        // 接口返回的数据有像素分辨率，根据这个来缩放图片大小
        ViewGroup.LayoutParams layoutParams = ivPhoto.getLayoutParams();
        layoutParams.width = mPhotoWidth;
        layoutParams.height = photoHeight;
        Picasso.with(mContext).load(item.getImgsrc()).placeholder(DefIconFactory.provideIcon()).into(ivPhoto);
        helper.setText(R.id.tv_title, item.getTitle());

    }

}
