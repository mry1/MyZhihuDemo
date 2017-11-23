package com.louis.myzhihudemo.injector.modules;

import com.louis.myzhihudemo.adapter.BeautyListAdapter;
import com.louis.myzhihudemo.injector.PerFragment;
import com.louis.myzhihudemo.local.table.DaoSession;
import com.louis.myzhihudemo.ui.photo.beauty.BeautyListFragment;
import com.louis.myzhihudemo.ui.photo.beauty.BeautyListPresent;

import dagger.Module;
import dagger.Provides;

/**
 * Created by louis on 17-11-23.
 */

@Module
public class BeautyListModule {

    private BeautyListFragment mView;

    public BeautyListModule(BeautyListFragment view) {
        mView = view;
    }

    @PerFragment
    @Provides
    public BeautyListAdapter provideBeautyListAdapter() {
        return new BeautyListAdapter(mView.getContext(), null);
    }

    @PerFragment
    @Provides
    public BeautyListPresent provideBeautyListPresent(DaoSession daoSession) {

        return new BeautyListPresent(mView);
    }


}
