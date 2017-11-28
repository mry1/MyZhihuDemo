package com.louis.myzhihudemo.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.ybq.android.spinkit.SpinKitView;
import com.louis.myzhihudemo.local.table.BeautyPhotoInfo;
import com.louis.myzhihudemo.ui.R;
import com.louis.myzhihudemo.utils.ImageLoader;

import java.util.Collections;
import java.util.List;

/**
 * Created by louis on 17-11-25.
 */

public class PhotoPagerAdapter extends PagerAdapter {
    private Context mContext;
    private List<BeautyPhotoInfo> mImgList;
    private OnTapListener mTapListener;

    public PhotoPagerAdapter(Context context) {
        this.mContext = context;
        mImgList = Collections.emptyList();
    }


    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_photo_view, null, false);
        final PhotoView photo = (PhotoView) view.findViewById(R.id.iv_photo);
        final SpinKitView loadingView = (SpinKitView) view.findViewById(R.id.loading_view);
        final TextView tvReload = (TextView) view.findViewById(R.id.tv_reload);

        final RequestListener listener = new RequestListener() {
            @Override
            public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {
                loadingView.setVisibility(View.GONE);
                tvReload.setVisibility(View.VISIBLE);
                return false;
            }

            @Override
            public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache, boolean isFirstResource) {
                loadingView.setVisibility(View.GONE);
                tvReload.setVisibility(View.GONE);
                photo.setImageDrawable((Drawable) resource);
                return false;
            }
        };
        ImageLoader.loadCenterCrop(mContext, mImgList.get(position).getUrl(), photo, listener);
        photo.setOnPhotoTapListener(new OnPhotoTapListener() {
            @Override
            public void onPhotoTap(ImageView imageView, float v, float v1) {
                if (mTapListener != null) {
                    mTapListener.onPhotoClick();
                }
            }
        });

        tvReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvReload.setVisibility(View.GONE);
                loadingView.setVisibility(View.VISIBLE);
                ImageLoader.loadCenterCrop(mContext, mImgList.get(position % mImgList.size()).getUrl(), photo, listener);

            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mImgList == null ? 0 : mImgList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public void updateData(List<BeautyPhotoInfo> imgList) {
        this.mImgList = imgList;
        notifyDataSetChanged();
    }

    public void setTapListener(OnTapListener listener) {
        mTapListener = listener;
    }

    public interface OnTapListener {
        void onPhotoClick();
    }

}
