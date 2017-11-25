package com.louis.myzhihudemo.ui.photo.bigphoto;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dl7.drag.DragSlopLayout;
import com.louis.myzhihudemo.adapter.PhotoPagerAdapter;
import com.louis.myzhihudemo.base.BaseActivity;
import com.louis.myzhihudemo.injector.components.DaggerBigPhotoComponent;
import com.louis.myzhihudemo.injector.modules.BigPhotoModule;
import com.louis.myzhihudemo.local.table.BeautyPhotoInfo;
import com.louis.myzhihudemo.ui.R;
import com.louis.myzhihudemo.utils.NavUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by louis on 17-11-24.
 */

public class BigPhotoActivity extends BaseActivity<BigPhotoPresent> {
    @BindView(R.id.vp_photo)
    ViewPager mVpPhoto;
    @BindView(R.id.iv_favorite)
    ImageView mIvFavorite;
    @BindView(R.id.iv_download)
    ImageView mIvDownload;
    @BindView(R.id.iv_praise)
    ImageView mIvPraise;
    @BindView(R.id.iv_share)
    ImageView mIvShare;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.drag_layout)
    DragSlopLayout mDragLayout;
    @BindView(R.id.ll_layout)
    LinearLayout mLlLayout;
    @Inject
    PhotoPagerAdapter mAdapter;

    private static final String BIG_PHOTO_KEY = "BigPhotoKey";
    private static final String PHOTO_INDEX_KEY = "PhotoIndexKey";
    private ArrayList<BeautyPhotoInfo> mPhotoList;
    private int mIndex;

    public static void launch(Context context, ArrayList<BeautyPhotoInfo> datas, int index) {
        Intent intent = new Intent(context, BigPhotoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(BIG_PHOTO_KEY, datas);
        intent.putExtras(bundle);
        intent.putExtra(PHOTO_INDEX_KEY, index);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.expand_vertical_entry, R.anim.hold);

    }

    @Override
    protected int getResId() {
        return R.layout.activity_big_photo;
    }

    @Override
    protected void initInjector() {
        mPhotoList = getIntent().getParcelableArrayListExtra(BIG_PHOTO_KEY);
        mIndex = getIntent().getIntExtra(PHOTO_INDEX_KEY, 0);
        DaggerBigPhotoComponent.builder()
                .applicationComponent(getAppComponent())
                .bigPhotoModule(new BigPhotoModule(this, mPhotoList))
                .build()
                .inject(this);

    }

    @Override
    protected void initView() {
        initToolbar(mToolbar, true, "");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 空出底部导航的高度，因为 NavigationBar 是透明的
            mLlLayout.setPadding(0, 0, 0, NavUtils.getNavigationBarHeight(this));
        }
        mVpPhoto.setAdapter(mAdapter);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void updateViews(boolean isRefresh) {
        mPresenter.getData(isRefresh);

    }

    public void loadData(List<BeautyPhotoInfo> beautyPhotoDatas) {
        mAdapter.updateData(beautyPhotoDatas);
    }
}
