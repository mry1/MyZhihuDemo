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
    private String imgsrc;
    private String pixel;
    private String docid;
    private String title;
    // 喜欢
    private boolean isLove;
    // 点赞
    private boolean isPraise;
    // 下载
    private boolean isDownload;
    @Generated(hash = 215652306)
    public BeautyPhotoInfo(String imgsrc, String pixel, String docid, String title,
            boolean isLove, boolean isPraise, boolean isDownload) {
        this.imgsrc = imgsrc;
        this.pixel = pixel;
        this.docid = docid;
        this.title = title;
        this.isLove = isLove;
        this.isPraise = isPraise;
        this.isDownload = isDownload;
    }

    @Generated(hash = 827125854)
    public BeautyPhotoInfo() {
    }
    public String getImgsrc() {
        return this.imgsrc;
    }
    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }
    public String getPixel() {
        return this.pixel;
    }
    public void setPixel(String pixel) {
        this.pixel = pixel;
    }
    public String getDocid() {
        return this.docid;
    }
    public void setDocid(String docid) {
        this.docid = docid;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
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
