package com.louis.myzhihudemo.ui.photo.beauty;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.louis.myzhihudemo.adapter.BeautyListAdapter;
import com.louis.myzhihudemo.base.BaseFragment;
import com.louis.myzhihudemo.injector.components.DaggerBeautyListComponent;
import com.louis.myzhihudemo.injector.modules.BeautyListModule;
import com.louis.myzhihudemo.ui.R;
import com.louis.myzhihudemo.utils.RecyclerViewHelper;
import com.louis.myzhihudemo.utils.ToastUtils;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by louis on 17-11-23.
 * 美图页面
 */

public class BeautyListFragment extends BaseFragment<BeautyListPresent> implements BaseQuickAdapter.OnItemChildClickListener {
    @BindView(R.id.frame_layout)
    FrameLayout frame_layout;
    @BindView(R.id.rv_photo_list)
    RecyclerView mRvPhotoList;
    @Inject
    BeautyListAdapter mAdapter;

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_beauty_list;
    }

    @Override
    protected void initInjector() {
        DaggerBeautyListComponent.builder()
                .applicationComponent(getAppComponent())
                .beautyListModule(new BeautyListModule(this))
                .build()
                .inject(this);

    }

    @Override
    protected void initViews() {
        RecyclerViewHelper.initRecyclerView(mContext, mRvPhotoList, false, mAdapter);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.getMoreData();
            }
        }, mRvPhotoList);
        mAdapter.setOnItemChildClickListener(this);
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        mPresenter.getData(isRefresh);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        ToastUtils.showSnackBar(frame_layout, position);
    }

    public void loadData() {

    }
}
