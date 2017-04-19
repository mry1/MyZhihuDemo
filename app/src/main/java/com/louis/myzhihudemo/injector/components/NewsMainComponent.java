package com.louis.myzhihudemo.injector.components;

import com.louis.myzhihudemo.injector.PerFragment;
import com.louis.myzhihudemo.injector.modules.NewsMainModule;
import com.louis.myzhihudemo.ui.news.main.NewsMainFragment;

import dagger.Component;

/**
 * Created by louis on 17-4-19.
 */
@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = NewsMainModule.class)
public interface NewsMainComponent {

    void inject(NewsMainFragment newsMainFragment);

}
