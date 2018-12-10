package com.louis.myzhihudemo.test;

import java.util.List;

public interface Processor<T, E extends Exception> {
    void process(List<T> resultCollector) throws E;
}
