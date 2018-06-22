package com.louis.myzhihudemo.injector.components;

import com.louis.myzhihudemo.injector.PerFragment;
import com.louis.myzhihudemo.injector.modules.VideoMainModule;
import com.louis.myzhihudemo.ui.video.main.VideoMainFragment;

import dagger.Component;

/**
 * @author 刘毅
 * @version 1.0
 * @E-mail 22629411@qq.com
 * @date 创建时间：2018/6/21 22:22
 * @description
 */
@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = VideoMainModule.class)
public interface VideoMainComponent {
    void inject(VideoMainFragment videoMainFragment);
}
