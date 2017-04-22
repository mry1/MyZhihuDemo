package com.louis.myzhihudemo.api.bean;

import java.util.List;

/**
 * Created by Louis on 2017/4/22.
 */

public class ThemeInfo {

    public int limit;
    public List<ThemeBean> others;
    public List<ThemeBean> subscribed;

    public static class ThemeBean{
        public int id;
        public int color;
        public int order;//展示在首页的顺序，0表示不展示在首页
        public String name;
        public String description;
        public String thumbnail;

        @Override
        public String toString() {
            return "ThemeBean{" +
                    "id=" + id +
                    ", color=" + color +
                    ", order=" + order +
                    ", name='" + name + '\'' +
                    ", description='" + description + '\'' +
                    ", thumbnail='" + thumbnail + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ThemeInfo{" +
                "limit=" + limit +
                ", others=" + others +
                ", subscribed=" + subscribed +
                '}';
    }
}
