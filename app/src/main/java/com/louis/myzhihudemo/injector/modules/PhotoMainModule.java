package com.louis.myzhihudemo.injector.modules;

import com.louis.myzhihudemo.adapter.ViewPagerAdapter;
import com.louis.myzhihudemo.injector.PerFragment;
import com.louis.myzhihudemo.local.table.DaoSession;
import com.louis.myzhihudemo.ui.photo.main.PhotoMainFragment;
import com.louis.myzhihudemo.ui.photo.main.PhotoMainPresent;

import dagger.Module;
import dagger.Provides;

/**
 * Created by louis on 17-11-22.
 */

@Module
public class PhotoMainModule {

    private PhotoMainFragment mView;

    public PhotoMainModule(PhotoMainFragment view) {
        mView = view;
    }

    @PerFragment
    @Provides
    public PhotoMainPresent providePhotoMainPresent(DaoSession daoSession) {
        return new PhotoMainPresent(mView);
    }

    @PerFragment
    @Provides
    public ViewPagerAdapter provideViewPagerAdapter() {

        return new ViewPagerAdapter(mView.getChildFragmentManager());
    }

}
