package com.louis.myzhihudemo.ui.video.main;

import com.louis.myzhihudemo.base.BaseFragment;
import com.louis.myzhihudemo.ui.R;

import butterknife.OnClick;

/**
 * Created by louis on 17-11-22.
 * 视频主界面
 */

public class VideoMainFragment extends BaseFragment<VideoMainPresent> {


    @Override
    protected int attachLayoutRes() {

        return R.layout.fragment_video_main;
    }

    @Override
    protected void initInjector() {

//        DaggerPhotoMainComponent.builder()
//                .applicationComponent(getAppComponent())
//                .photoMainModule(new PhotoMainModule(this))
//                .build()
//                .inject(this);


    }


    @Override
    protected void initViews() {
//        initToolbar(mToolbar, true, "图片");
        setHasOptionsMenu(true);//设置menu生效

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        System.out.println("updateViews:" + isRefresh);
        mPresenter.getData(isRefresh);

    }


    @OnClick(R.id.fl_layout)
    public void onClick() {

    }


}
