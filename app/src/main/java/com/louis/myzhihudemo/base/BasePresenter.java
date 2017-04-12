package com.louis.myzhihudemo.base;

import android.content.Context;

/**
 * Created by louis on 17-4-11.
 */

public abstract class BasePresenter<M, V> {
    protected M mModel;
    protected V mView;
    protected Context mContext;

    protected void attachVM(M model, V view) {
        mModel = model;
        mView = view;
    }

    protected void setContext(Context context) {
        mContext = context;
    }


}
