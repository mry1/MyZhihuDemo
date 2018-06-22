package com.louis.myzhihudemo.base;

import rx.Subscriber;

public interface IBaseRxBusPresent {

    <T> void registerRxBus(Class<T> clazz, Subscriber<T> subscriber);

    void unRegisterRxBus();

}
