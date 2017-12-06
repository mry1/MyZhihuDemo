package com.louis.myzhihudemo.ui.photo.bigphoto;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dl7.drag.DragSlopLayout;
import com.jakewharton.rxbinding.view.RxView;
import com.louis.myzhihudemo.adapter.PhotoPagerAdapter;
import com.louis.myzhihudemo.base.BaseActivity;
import com.louis.myzhihudemo.injector.components.DaggerBigPhotoComponent;
import com.louis.myzhihudemo.injector.modules.BigPhotoModule;
import com.louis.myzhihudemo.local.table.BeautyPhotoInfo;
import com.louis.myzhihudemo.ui.R;
import com.louis.myzhihudemo.ui.home.HomeActivity;
import com.louis.myzhihudemo.ui.manage.photo.LovePhotoFragment;
import com.louis.myzhihudemo.utils.AnimateHelper;
import com.louis.myzhihudemo.utils.DownloadUtils;
import com.louis.myzhihudemo.utils.GlobalConst;
import com.louis.myzhihudemo.utils.NavUtils;
import com.louis.myzhihudemo.utils.ToastUtils;
import com.louis.myzhihudemo.widget.PhotoViewPager;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * Created by louis on 17-11-24.
 */

public class BigPhotoActivity extends BaseActivity<BigPhotoPresent> {
    @BindView(R.id.vp_photo)
    PhotoViewPager mVpPhoto;
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
    private boolean mIsInteract = false;
    public static final int LAUNCH_TAG_FROM_LIST = 1;
    public static final int LAUNCH_TAG_FROM_LOVE = 2;
    private static int mTag = 0;

    /**
     * 保存被删除的收藏项
     */
    private boolean[] mIsDelLove;

    /**
     * 是否隐藏Toolbar
     */
    private boolean mIsHideToolbar = false;
    private RxPermissions mRxPermissions;
    /**
     * adapter当前位置
     */
    private int mCurPosition;

    @IntDef({LAUNCH_TAG_FROM_LIST, LAUNCH_TAG_FROM_LOVE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LaunchTag {
    }

    public static void launch(@LaunchTag int tag, Context context, ArrayList<BeautyPhotoInfo> datas, int index) {
        mTag = tag;
        Intent intent = new Intent(context, BigPhotoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(BIG_PHOTO_KEY, datas);
        intent.putExtras(bundle);
        intent.putExtra(PHOTO_INDEX_KEY, index);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.expand_vertical_entry, R.anim.hold);

    }

    public static void launchForResult(@LaunchTag int tag, Fragment fragment, ArrayList<BeautyPhotoInfo> datas, int index) {
        mTag = tag;
        Intent intent = new Intent(fragment.getContext(), BigPhotoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(BIG_PHOTO_KEY, datas);
        intent.putExtras(bundle);
        intent.putExtra(PHOTO_INDEX_KEY, index);
        fragment.startActivityForResult(intent, GlobalConst.REQUEST_CODE);
        fragment.getActivity().overridePendingTransition(R.anim.expand_vertical_entry, R.anim.hold);

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
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BigPhotoActivity.this.finish();
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 空出底部导航的高度，因为 NavigationBar 是透明的
            mLlLayout.setPadding(0, 0, 0, NavUtils.getNavigationBarHeight(this));
        }
        mVpPhoto.setAdapter(mAdapter);
        // 设置是否和viewpager联动、动画
        mDragLayout.interactWithViewPager(mIsInteract);
        mDragLayout.setAnimatorMode(DragSlopLayout.MODE_DRAG_OUTSIDE);
        mAdapter.setTapListener(new PhotoPagerAdapter.OnTapListener() {
            @Override
            public void onPhotoClick() {
                mIsHideToolbar = !mIsHideToolbar;
                if (mIsHideToolbar) {
                    mDragLayout.startOutAnim();
                    mToolbar.animate().translationY(-mToolbar.getBottom()).setDuration(300);

                } else {
                    mDragLayout.startInAnim();
                    mToolbar.animate().translationY(0).setDuration(300);
                }

            }
        });

        mVpPhoto.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mCurPosition = position;
                // 设置图标状态
                BeautyPhotoInfo data = mAdapter.getData(position);
                mIvFavorite.setSelected(mAdapter.isLoved(position));
                mIvDownload.setSelected(mAdapter.isDownload(position));

                mIvPraise.setSelected(mAdapter.isPraise(position));
            }
        });

        if (mTag == LAUNCH_TAG_FROM_LIST) { // 收藏界面不需要加载更多
            mAdapter.setLoadMoreListener(new PhotoPagerAdapter.OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    mPresenter.getMoreData();

                }
            });
        } else {
            mIsDelLove = new boolean[mPhotoList.size()];
        }
        mRxPermissions = new RxPermissions(this);
        RxView.clicks(mIvDownload)
                .compose(mRxPermissions.ensure(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE))
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean granted) {
                        if (granted) {
                            //  分配了权限，开始下载图片
                            DownloadUtils.downloadOrDeletePhoto(BigPhotoActivity.this, mAdapter.getData(mCurPosition).getUrl(),
                                    mAdapter.getData(mCurPosition).getId(), new DownloadUtils.OnCompletedListener() {
                                        @Override
                                        public void onCompleted(String url) {
                                            BeautyPhotoInfo data = mAdapter.getData(url);
                                            Log.d(GlobalConst.TAG_BIG_PHOTO, "data::" + data.toString());
                                            data.setIsDownload(true);
                                            mIvDownload.setSelected(true);
                                            mPresenter.insert(data);

                                        }

                                        @Override
                                        public void onDeleted(String url) {
                                            BeautyPhotoInfo data = mAdapter.getData(url);
                                            data.setIsDownload(false);
                                            mIvDownload.setSelected(false);
                                            mPresenter.delete(data);
                                        }

                                    });


                        } else {
                            ToastUtils.showSnackBar(BigPhotoActivity.this, "授权失败", false);
                        }
                    }
                });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_animate, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        item.setChecked(true);
        switch (item.getItemId()) {
            case R.id.slide_bottom:
                mDragLayout.setAnimatorMode(DragSlopLayout.SLIDE_BOTTOM);
                return true;
            case R.id.slide_left:
                mDragLayout.setAnimatorMode(DragSlopLayout.SLIDE_LEFT);
                return true;
            case R.id.slide_right:
                mDragLayout.setAnimatorMode(DragSlopLayout.SLIDE_RIGHT);
                return true;
            case R.id.slide_fade:
                mDragLayout.setAnimatorMode(DragSlopLayout.FADE);
                return true;
            case R.id.slide_flip_x:
                mDragLayout.setAnimatorMode(DragSlopLayout.FLIP_X);
                return true;
            case R.id.slide_flip_y:
                mDragLayout.setAnimatorMode(DragSlopLayout.FLIP_Y);
                return true;
            case R.id.slide_zoom:
                mDragLayout.setAnimatorMode(DragSlopLayout.ZOOM);
                return true;
            case R.id.slide_zoom_left:
                mDragLayout.setAnimatorMode(DragSlopLayout.ZOOM_LEFT);
                return true;
            case R.id.slide_zoom_right:
                mDragLayout.setAnimatorMode(DragSlopLayout.ZOOM_RIGHT);
                return true;

            case R.id.item_interact:
                mIsInteract = !mIsInteract;
                item.setChecked(mIsInteract);
                mDragLayout.interactWithViewPager(mIsInteract);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.iv_favorite, R.id.iv_praise, R.id.iv_share})
    public void onClick(View view) {
        boolean selected = !view.isSelected();
        BeautyPhotoInfo data = mAdapter.getData(mCurPosition);
        switch (view.getId()) {
            case R.id.iv_favorite:
                data.setIsLove(selected);
                if (mTag == LAUNCH_TAG_FROM_LOVE) {
                    mIsDelLove[mCurPosition] = mIvFavorite.isSelected();
                    System.out.println("=====" + Arrays.toString(mIsDelLove));
                }
                break;
            case R.id.iv_praise:
                data.setIsPraise(selected);
                break;
            case R.id.iv_share:
                // TODO: 17-11-29 美图模块分享功能
                ToastUtils.showToast("还没加这个功能");

                break;
        }

        // 除分享外都做动画和数据库处理
        if (view.getId() != R.id.iv_share) {
            view.setSelected(selected);
            AnimateHelper.doHeartBeat(view, 500);

            if (selected) {
                mPresenter.insert(data);
            } else {
                mPresenter.delete(data);
            }

        }

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
        mVpPhoto.setCurrentItem(mIndex);
        if (mIndex == 0) {
            //  为0不会回调addOnPageSelectedListener，所以这里要处理下
            mIvFavorite.setSelected(mAdapter.isLoved(0));
            mIvDownload.setSelected(mAdapter.isDownload(0));
            mIvPraise.setSelected(mAdapter.isPraise(0));
        }

    }

    public void loadMoreData(List<BeautyPhotoInfo> beautyPhotoInfos) {
        mAdapter.addData(beautyPhotoInfos);
        mAdapter.startUpdate(mVpPhoto);

    }


    @Override
    public void finish() {
        if (mTag == LAUNCH_TAG_FROM_LOVE) {
            Intent intent = new Intent();
            intent.putExtra(GlobalConst.RESULT_KEY, mIsDelLove);
            setResult(RESULT_OK, intent);
        }
        super.finish();
        overridePendingTransition(R.anim.hold, R.anim.zoom_out_exit);

    }
}
