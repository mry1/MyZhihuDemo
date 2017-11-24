package com.louis.myzhihudemo.local.table;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by louis on 17-11-23.
 */
@Entity
public class BeautyPhotoInfo {
    @Id
    private String id;
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;
    // 保存图片宽高
    private String pixel;

    @Generated(hash = 512096145)
    public BeautyPhotoInfo(String id, String createdAt, String desc,
                           String publishedAt, String source, String type, String url,
                           boolean used, String who, String pixel) {
        this.id = id;
        this.createdAt = createdAt;
        this.desc = desc;
        this.publishedAt = publishedAt;
        this.source = source;
        this.type = type;
        this.url = url;
        this.used = used;
        this.who = who;
        this.pixel = pixel;
    }

    @Generated(hash = 827125854)
    public BeautyPhotoInfo() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublishedAt() {
        return this.publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean getUsed() {
        return this.used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return this.who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public String getPixel() {
        return this.pixel;
    }

    public void setPixel(String pixel) {
        this.pixel = pixel;
    }

    @Override
    public String toString() {
        return "BeautyPhotoInfo{" +
                "id='" + id + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", desc='" + desc + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                ", source='" + source + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", used=" + used +
                ", who='" + who + '\'' +
                ", pixel='" + pixel + '\'' +
                '}';
    }
}
