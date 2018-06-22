package com.louis.myzhihudemo.ui.photo.beauty;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.louis.myzhihudemo.adapter.BeautyListAdapter;
import com.louis.myzhihudemo.base.BaseFragment;
import com.louis.myzhihudemo.injector.components.DaggerBeautyListComponent;
import com.louis.myzhihudemo.injector.modules.BeautyListModule;
import com.louis.myzhihudemo.local.table.BeautyPhotoInfo;
import com.louis.myzhihudemo.ui.R;
import com.louis.myzhihudemo.ui.photo.bigphoto.BigPhotoActivity;
import com.louis.myzhihudemo.utils.RecyclerViewHelper;
import com.louis.myzhihudemo.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by louis on 17-11-23.
 * 美图页面
 */

public class BeautyListFragment extends BaseFragment<BeautyListPresent> implements BaseQuickAdapter.OnItemClickListener {
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
        RecyclerViewHelper.initRecyclerViewSV(mContext, mRvPhotoList, false, mAdapter, 2);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.getMoreData();
            }
        }, mRvPhotoList);
        mAdapter.setOnItemClickListener(this);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        mPresenter.getData(isRefresh);
    }


    public void loadData(List<BeautyPhotoInfo> data) {
        mAdapter.setNewData(data);
    }

    /**
     * 加载更多
     *
     * @param data
     */
    public void loadMoreData(List<BeautyPhotoInfo> data) {
        mAdapter.loadMoreComplete();
        mAdapter.addData(data);

    }

    /**
     * 加载失败
     */
    public void loadMoreFail() {
        mAdapter.loadMoreFail();
    }

    /**
     * 暂无更多数据
     */
    public void loadMoreEnd() {
        mAdapter.loadMoreEnd();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        // 进入大图页面
        ToastUtils.showMessage("item:" + position);
        BigPhotoActivity.launch(BigPhotoActivity.LAUNCH_TAG_FROM_LIST, mContext, (ArrayList<BeautyPhotoInfo>) adapter.getData(), position);

    }
}
