package com.louis.myzhihudemo.injector.components;

import com.louis.myzhihudemo.injector.PerFragment;
import com.louis.myzhihudemo.injector.modules.PhotoMainModule;
import com.louis.myzhihudemo.ui.photo.main.PhotoMainFragment;

import dagger.Component;

/**
 * Created by louis on 17-11-22.
 */

@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = PhotoMainModule.class)
public interface PhotoMainComponent {

    void inject(PhotoMainFragment photoMainFragment);

}
