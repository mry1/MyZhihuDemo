package com.louis.myzhihudemo.injector.components;

import com.louis.myzhihudemo.injector.PerFragment;
import com.louis.myzhihudemo.injector.modules.LovePhotoModule;
import com.louis.myzhihudemo.ui.manage.photo.LovePhotoFragment;

import dagger.Component;

/**
 * Created by louis on 17-12-4.
 */
@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = LovePhotoModule.class)
public interface LovePhotoComponent {

    public void inject(LovePhotoFragment lovePhotoFragment);

}
