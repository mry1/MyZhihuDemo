package com.louis.myzhihudemo.local.table;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by louis on 17-11-23.
 */
@Entity
public class BeautyPhotoInfo implements Parcelable {
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
    // 喜欢
    private boolean isLove;
    // 点赞
    private boolean isPraise;
    // 下载
    private boolean isDownload;

    @Generated(hash = 2131726483)
    public BeautyPhotoInfo(String id, String createdAt, String desc, String publishedAt,
                           String source, String type, String url, boolean used, String who, String pixel,
                           boolean isLove, boolean isPraise, boolean isDownload) {
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
        this.isLove = isLove;
        this.isPraise = isPraise;
        this.isDownload = isDownload;
    }

    @Generated(hash = 827125854)
    public BeautyPhotoInfo() {
    }

    protected BeautyPhotoInfo(Parcel in) {
        id = in.readString();
        createdAt = in.readString();
        desc = in.readString();
        publishedAt = in.readString();
        source = in.readString();
        type = in.readString();
        url = in.readString();
        used = in.readByte() != 0;
        who = in.readString();
        pixel = in.readString();
        isLove = in.readByte() != 0;
        isPraise = in.readByte() != 0;
        isDownload = in.readByte() != 0;

    }

    public static final Creator<BeautyPhotoInfo> CREATOR = new Creator<BeautyPhotoInfo>() {
        @Override
        public BeautyPhotoInfo createFromParcel(Parcel in) {

            return new BeautyPhotoInfo(in);
        }

        @Override
        public BeautyPhotoInfo[] newArray(int size) {
            return new BeautyPhotoInfo[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(createdAt);
        parcel.writeString(desc);
        parcel.writeString(publishedAt);
        parcel.writeString(source);
        parcel.writeString(type);
        parcel.writeString(url);
        parcel.writeByte((byte) (used ? 1 : 0));
        parcel.writeString(who);
        parcel.writeString(pixel);
        parcel.writeByte((byte) (isLove ? 1 : 0));
        parcel.writeByte((byte) (isPraise ? 1 : 0));
        parcel.writeByte((byte) (isDownload ? 1 : 0));
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BeautyPhotoInfo)) {
            return false;
        }
        BeautyPhotoInfo other = (BeautyPhotoInfo) o;
        if (url.equals(other.getUrl())) {
            return true;
        }
        return false;
    }

    public boolean getIsLove() {
        return this.isLove;
    }

    public void setIsLove(boolean isLove) {
        this.isLove = isLove;
    }

    public boolean getIsPraise() {
        return this.isPraise;
    }

    public void setIsPraise(boolean isPraise) {
        this.isPraise = isPraise;
    }

    public boolean getIsDownload() {
        return this.isDownload;
    }

    public void setIsDownload(boolean isDownload) {
        this.isDownload = isDownload;
    }

}
