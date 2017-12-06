package com.louis.myzhihudemo.ui.manage.photo;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.louis.myzhihudemo.adapter.BeautyListAdapter;
import com.louis.myzhihudemo.base.BaseFragment;
import com.louis.myzhihudemo.injector.components.DaggerLovePhotoComponent;
import com.louis.myzhihudemo.injector.modules.LovePhotoModule;
import com.louis.myzhihudemo.local.table.BeautyPhotoInfo;
import com.louis.myzhihudemo.ui.R;
import com.louis.myzhihudemo.ui.manage.love.LovePhotoPresent;
import com.louis.myzhihudemo.ui.photo.bigphoto.BigPhotoActivity;
import com.louis.myzhihudemo.utils.DialogHelper;
import com.louis.myzhihudemo.utils.GlobalConst;
import com.louis.myzhihudemo.utils.RecyclerViewHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by louis on 17-12-4.
 */

public class LovePhotoFragment extends BaseFragment<LovePhotoPresent> implements BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.rv_love_list)
    RecyclerView mRvPhotoList;
    @BindView(R.id.default_bg)
    TextView mDefaultBg;
    @Inject
    BeautyListAdapter mAdapter;

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_love_list;
    }

    @Override
    protected void initInjector() {
        DaggerLovePhotoComponent.builder()
                .applicationComponent(getAppComponent())
                .lovePhotoModule(new LovePhotoModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initViews() {
        RecyclerViewHelper.initRecyclerViewSV(mContext, mRvPhotoList, false, mAdapter, 2);
        mAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                DialogHelper.deleteDialog(mContext, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // TODO: 17-12-4 删除

                    }
                });
                return false;// TODO: 17-12-4
            }
        });
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        mPresenter.getData(isRefresh);

    }

    public void loadNoData() {
        mDefaultBg.setVisibility(View.VISIBLE);
    }

    public void loadData(List<BeautyPhotoInfo> beautyPhotoInfos) {
        if (mDefaultBg.getVisibility() == View.VISIBLE) {
            mDefaultBg.setVisibility(View.GONE);
        }
        mAdapter.setNewData(beautyPhotoInfos);

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        BigPhotoActivity.launchForResult(BigPhotoActivity.LAUNCH_TAG_FROM_LOVE, this, (ArrayList<BeautyPhotoInfo>) adapter.getData(), position);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GlobalConst.REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            final boolean[] delLove = data.getBooleanArrayExtra(GlobalConst.RESULT_KEY);
            // 延迟 500MS 做删除操作，不然退回来看不到动画效果
            for (int i = delLove.length - 1; i >= 0; i--) {
                if (delLove[i]) {
                    mAdapter.remove(i);
                }
            }
            if (mAdapter.getData().size() == 0) {
                mDefaultBg.setVisibility(View.VISIBLE);
            }

        }

    }
}
