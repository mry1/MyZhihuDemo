package com.louis.myzhihudemo.ui.video.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.louis.myzhihudemo.adapter.VideoListAdapter;
import com.louis.myzhihudemo.api.bean.VideoInfo;
import com.louis.myzhihudemo.base.BaseFragment;
import com.louis.myzhihudemo.injector.components.DaggerVideoListComponent;
import com.louis.myzhihudemo.injector.modules.VideoListModule;
import com.louis.myzhihudemo.ui.R;
import com.louis.myzhihudemo.utils.RecyclerViewHelper;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class VideoListFragment extends BaseFragment<VideoListPresent> {
    private static final String VIDEO_ID_KEY = "VideoIdKey";

    @BindView(R.id.rv_photo_list)
    protected RecyclerView rv_photo_list;
    private String mVideoId;

    @Inject
    VideoListAdapter mAdapter;

    public static VideoListFragment newInstance(String VideoId) {
        VideoListFragment videoListFragment = new VideoListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(VIDEO_ID_KEY, VideoId);
        videoListFragment.setArguments(bundle);
        return videoListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mVideoId = bundle.getString(VIDEO_ID_KEY);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_video_list;
    }

    @Override
    protected void initInjector() {
        DaggerVideoListComponent.builder()
                .applicationComponent(getAppComponent())
                .videoListModule(new VideoListModule(this, mVideoId))
                .build()
                .inject(this);
    }

    @Override
    protected void initViews() {
        RecyclerViewHelper.initRecyclerView(mContext, rv_photo_list, true, mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                VideoInfo item = (VideoInfo) adapter.getItem(position);

            }
        });
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.getMoreData();
            }
        }, rv_photo_list);
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        mPresenter.getData(isRefresh);
    }

    public void loadData(List<VideoInfo> data) {
//        System.out.println(data.toString());
        mAdapter.setNewData(data);

    }

    /**
     * 加载失败
     */
    public void loadMoreFail() {
        mAdapter.loadMoreFail();
    }

    /**
     * 加载更多
     *
     * @param data
     */
    public void loadMoreData(List<VideoInfo> data) {
        mAdapter.loadMoreComplete();
        mAdapter.addData(data);
    }

    /**
     * 加载结束，暂无更多数据
     */
    public void loadMoreEnd() {
        mAdapter.loadMoreEnd();
    }
}
