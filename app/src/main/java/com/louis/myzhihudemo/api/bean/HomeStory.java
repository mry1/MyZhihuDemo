package com.louis.myzhihudemo.api.bean;

import java.util.List;

/**
 * Created by Louis on 2017/5/16.
 */

public class HomeStory {

    public String date;
    public List<HomeStories> stories;
    public List<HomeStories> top_stories;


    public static class HomeStories {

        public String ga_prefix;
        public long id;
        public String title;
        public int type;
        /**
         * 首页轮播图
         */
        public List<String> images;
        /**
         * 首页item里面的图片
         */
        public String image;

        @Override
        public String toString() {
            return "HomeStories{" +
                    "ga_prefix='" + ga_prefix + '\'' +
                    ", id=" + id +
                    ", title='" + title + '\'' +
                    ", type=" + type +
                    ", images=" + images +
                    ", image='" + image + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "HomeStory{" +
                "date='" + date + '\'' +
                ", stories=" + stories +
                ", top_stories=" + top_stories +
                '}';
    }
}
