package com.louis.myzhihudemo.injector.components;

import com.louis.myzhihudemo.injector.PerFragment;
import com.louis.myzhihudemo.injector.modules.BeautyListModule;
import com.louis.myzhihudemo.ui.photo.beauty.BeautyListFragment;

import dagger.Component;

/**
 * Created by louis on 17-11-23.
 */

@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = BeautyListModule.class)
public interface BeautyListComponent {

    void inject(BeautyListFragment beautyListFragment);

}
