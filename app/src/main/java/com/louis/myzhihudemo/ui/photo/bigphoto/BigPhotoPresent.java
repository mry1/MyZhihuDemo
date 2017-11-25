package com.louis.myzhihudemo.ui.photo.bigphoto;

import com.louis.myzhihudemo.base.BasePresenter;
import com.louis.myzhihudemo.local.table.BeautyPhotoInfo;
import com.louis.myzhihudemo.local.table.BeautyPhotoInfoDao;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by louis on 17-11-24.
 */

public class BigPhotoPresent extends BasePresenter {
    BigPhotoActivity mView;
    List<BeautyPhotoInfo> mDatas;
    private final BeautyPhotoInfoDao mDbDao;
    private List<BeautyPhotoInfo> mDbLovedData;

    public BigPhotoPresent(BigPhotoActivity view, List<BeautyPhotoInfo> datas, BeautyPhotoInfoDao dao) {
        this.mView = view;
        this.mDbDao = dao;
        this.mDatas = datas;
        mDbLovedData = mDbDao.queryBuilder().list();

    }

    @Override
    public void getData(boolean isRefresh) {

        Observable.from(mDatas)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.showLoading();
                    }
                })
                .compose(mTransformer)
                .subscribe(new Subscriber<List<BeautyPhotoInfo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<BeautyPhotoInfo> beautyPhotoInfos) {
                        mView.loadData(beautyPhotoInfos);

                    }
                });
    }

    @Override
    public void getMoreData() {

    }

    private Observable.Transformer<BeautyPhotoInfo, List<BeautyPhotoInfo>> mTransformer = new Observable.Transformer<BeautyPhotoInfo, List<BeautyPhotoInfo>>() {
        @Override
        public Observable<List<BeautyPhotoInfo>> call(Observable<BeautyPhotoInfo> beautyPhotoInfoObservable) {
            return beautyPhotoInfoObservable
                    .doOnNext(new Action1<BeautyPhotoInfo>() {

                        private BeautyPhotoInfo bean;

                        @Override
                        public void call(BeautyPhotoInfo beautyPhotoInfo) {
                            // 判断数据库是否有数据，有则设置对应参数
                            if (mDbLovedData.contains(beautyPhotoInfo)) {
                                bean = mDbLovedData.get(mDbLovedData.indexOf(beautyPhotoInfo));
                                beautyPhotoInfo.setIsLove(bean.getIsLove());
                                beautyPhotoInfo.setIsPraise(bean.getIsPraise());
                                beautyPhotoInfo.setIsDownload(bean.getIsDownload());
                            }
                        }
                    })
                    .toList();
        }
    };
}
