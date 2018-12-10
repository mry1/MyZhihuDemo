package com.louis.myzhihudemo.ui.photo.news;

import com.louis.myzhihudemo.base.BaseFragment;
import com.louis.myzhihudemo.ui.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by louis on 17-11-23.
 */

public class PhotoNewsFragment extends BaseFragment {
    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_beauty_list;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }
    static class Pet{}
    static class Cat extends Pet{}
    static class Dog extends Pet{}

    static void oldStyleMethod(List probablyDogs) {
        probablyDogs.add(new Cat());
    }
    public static void main(String[] args){
        ArrayList<Dog> dogs1 = new ArrayList<>();
        oldStyleMethod(dogs1);

        List<Dog> dogs2 = Collections.checkedList(new ArrayList<Dog>(), Dog.class);
        oldStyleMethod(dogs2);
    }

}
