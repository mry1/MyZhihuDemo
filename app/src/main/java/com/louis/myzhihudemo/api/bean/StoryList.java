package com.louis.myzhihudemo.api.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Louis on 2017/5/13.
 */

public class StoryList {

    public int color;
    public String name;
    public String description;
    public String image;
    public String image_source;
    public String background;
    public List<Editor> editors;
    public List<Story> stories;

    public static class Editor {
        public int id;
        public String name;
        public String bio;
        public String avatar;
        public String url;

        @Override
        public String toString() {
            return "Editor{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", bio='" + bio + '\'' +
                    ", avatar='" + avatar + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }

    public static class Story {
        public int id;
        public int type;
        public String title;
        public String image;//top_stories用的字段
        public String ga_prefix;// 供 Google Analytics 使用
        public ArrayList<String> images;//其他story用的字段

        @Override
        public String toString() {
            return "Story{" +
                    "id=" + id +
                    ", type=" + type +
                    ", title='" + title + '\'' +
                    ", image='" + image + '\'' +
                    ", ga_prefix='" + ga_prefix + '\'' +
                    ", images=" + images +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "StoryList{" +
                "color=" + color +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", image_source='" + image_source + '\'' +
                ", background='" + background + '\'' +
                ", editors=" + editors +
                ", stories=" + stories +
                '}';
    }
}
