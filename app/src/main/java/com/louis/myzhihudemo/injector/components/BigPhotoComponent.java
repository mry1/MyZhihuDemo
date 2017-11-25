package com.louis.myzhihudemo.injector.components;

import com.louis.myzhihudemo.injector.PerFragment;
import com.louis.myzhihudemo.injector.modules.BigPhotoModule;
import com.louis.myzhihudemo.ui.photo.bigphoto.BigPhotoActivity;

import dagger.Component;

/**
 * Created by louis on 17-11-25.
 */

@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = BigPhotoModule.class)
public interface BigPhotoComponent {
    void inject(BigPhotoActivity bigPhotoActivity);
}
