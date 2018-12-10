package com.louis.myzhihudemo.test;

import java.util.ArrayList;
import java.util.List;

public class ProcessRunner<T, E extends Exception> extends ArrayList<Processor<T, E>> {

    List<T> processAll() throws E {
        ArrayList<T> resultCollector = new ArrayList<>();
        for (Processor<T, E> processor : this) {
            processor.process(resultCollector);
        }
        return resultCollector;
    }

}
