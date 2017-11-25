package com.louis.myzhihudemo.injector.modules;

import com.louis.myzhihudemo.adapter.PhotoPagerAdapter;
import com.louis.myzhihudemo.injector.PerFragment;
import com.louis.myzhihudemo.local.table.BeautyPhotoInfo;
import com.louis.myzhihudemo.local.table.DaoSession;
import com.louis.myzhihudemo.ui.photo.bigphoto.BigPhotoActivity;
import com.louis.myzhihudemo.ui.photo.bigphoto.BigPhotoPresent;

import java.util.List;

import dagger.Module;
import dagger.Provides;

/**
 * Created by louis on 17-11-25.
 */
@Module
public class BigPhotoModule {
    BigPhotoActivity mView;
    List<BeautyPhotoInfo> datas;

    public BigPhotoModule(BigPhotoActivity view, List<BeautyPhotoInfo> datas) {
        this.datas = datas;
        mView = view;
    }

    @PerFragment
    @Provides
    public BigPhotoPresent provideBigPhotoPresent(DaoSession daoSession) {

        return new BigPhotoPresent(mView, datas, daoSession.getBeautyPhotoInfoDao());
    }

    @PerFragment
    @Provides
    public PhotoPagerAdapter providePhotoPagerAdapter() {
        return new PhotoPagerAdapter(mView);
    }

}
