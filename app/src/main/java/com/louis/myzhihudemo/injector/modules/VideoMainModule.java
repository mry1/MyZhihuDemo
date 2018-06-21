package com.louis.myzhihudemo.injector.modules;

import com.louis.myzhihudemo.injector.PerFragment;
import com.louis.myzhihudemo.local.table.DaoSession;
import com.louis.myzhihudemo.ui.video.main.VideoMainFragment;
import com.louis.myzhihudemo.ui.video.main.VideoMainPresent;

import dagger.Module;
import dagger.Provides;

/**
 * @author 刘毅
 * @version 1.0
 * @E-mail 22629411@qq.com
 * @date 创建时间：2018/6/21 22:17
 * @description
 */
@Module
public class VideoMainModule {
    private VideoMainFragment mView;

    public VideoMainModule(VideoMainFragment view) {
        mView = view;
    }

    @PerFragment
    @Provides
    public VideoMainPresent provideVideoMainPresent(DaoSession session) {
        return new VideoMainPresent(mView);
    }

}
