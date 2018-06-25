package com.louis.myzhihudemo.api.bean;


import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;


@Entity
public class VideoInfo implements Parcelable {
    /* sizeHD: 11844,
     mp4Hd_url: null,
     description: "",
     title: "看一波守门员的精彩操作Top.10",
     mp4_url: "http://flv3.bn.netease.com/tvmrepo/2018/6/H/9/EDJTRBEH9/SD/EDJTRBEH9-mobile.mp4",
     vid: "VDJTRBP5I",
     cover: "http://vimg1.ws.126.net/image/snapshot/2018/6/K/K/VDJTRBNKK.jpg",
     sizeSHD: 0,
     playersize: 1,
     ptime: "2018-06-21 19:11:08",
     m3u8_url: "http://flv.bn.netease.com/tvmrepo/2018/6/H/9/EDJTRBEH9/SD/movie_index.m3u8",
     topicImg: "http://vimg2.ws.126.net/image/snapshot/2017/2/S/T/VCCUNO3ST.jpg",
     votecount: 12,
     length: 168,
     videosource: "新媒体",
     m3u8Hd_url: null,
     sizeSD: 7308,
     topicSid: "VCCUNO3SQ",
     playCount: 0,
     replyCount: 15,
     replyBoard: "video_bbs",
     replyid: "DJTRBP5I008535RB",
     topicName: "短视频",
     sectiontitle: "",
     topicDesc: "芒果TV视频"
 */
    @Id
    public String vid;
    public long sizeHD;
    public String mp4Hd_url;
    public String description;
    public String title;
    public String mp4_url;
    public String cover;
    public int sizeSHD;
    public int playersize;
    public String ptime;
    public String m3u8_url;
    public String topicImg;
    public int votecount;
    public int length;
    public String videosource;
    public String m3u8Hd_url;
    public int sizeSD;
    public String topicSid;
    public int playCount;
    public int replyCount;
    public String replyBoard;
    public String replyid;
    public String topicName;
    public String sectiontitle;
    public String topicDesc;

    protected VideoInfo(Parcel in) {
        vid = in.readString();
        sizeHD = in.readLong();
        mp4Hd_url = in.readString();
        description = in.readString();
        title = in.readString();
        mp4_url = in.readString();
        cover = in.readString();
        sizeSHD = in.readInt();
        playersize = in.readInt();
        ptime = in.readString();
        m3u8_url = in.readString();
        topicImg = in.readString();
        votecount = in.readInt();
        length = in.readInt();
        videosource = in.readString();
        m3u8Hd_url = in.readString();
        sizeSD = in.readInt();
        topicSid = in.readString();
        playCount = in.readInt();
        replyCount = in.readInt();
        replyBoard = in.readString();
        replyid = in.readString();
        topicName = in.readString();
        sectiontitle = in.readString();
        topicDesc = in.readString();
    }

    @Generated(hash = 615150130)
    public VideoInfo(String vid, long sizeHD, String mp4Hd_url, String description, String title,
            String mp4_url, String cover, int sizeSHD, int playersize, String ptime, String m3u8_url,
            String topicImg, int votecount, int length, String videosource, String m3u8Hd_url,
            int sizeSD, String topicSid, int playCount, int replyCount, String replyBoard,
            String replyid, String topicName, String sectiontitle, String topicDesc) {
        this.vid = vid;
        this.sizeHD = sizeHD;
        this.mp4Hd_url = mp4Hd_url;
        this.description = description;
        this.title = title;
        this.mp4_url = mp4_url;
        this.cover = cover;
        this.sizeSHD = sizeSHD;
        this.playersize = playersize;
        this.ptime = ptime;
        this.m3u8_url = m3u8_url;
        this.topicImg = topicImg;
        this.votecount = votecount;
        this.length = length;
        this.videosource = videosource;
        this.m3u8Hd_url = m3u8Hd_url;
        this.sizeSD = sizeSD;
        this.topicSid = topicSid;
        this.playCount = playCount;
        this.replyCount = replyCount;
        this.replyBoard = replyBoard;
        this.replyid = replyid;
        this.topicName = topicName;
        this.sectiontitle = sectiontitle;
        this.topicDesc = topicDesc;
    }

    @Generated(hash = 296402066)
    public VideoInfo() {
    }

    public static final Creator<VideoInfo> CREATOR = new Creator<VideoInfo>() {
        @Override
        public VideoInfo createFromParcel(Parcel in) {
            return new VideoInfo(in);
        }

        @Override
        public VideoInfo[] newArray(int size) {
            return new VideoInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "VideoInfo{" +
                "vid='" + vid + '\'' +
                ", sizeHD=" + sizeHD +
                ", mp4Hd_url='" + mp4Hd_url + '\'' +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                ", mp4_url='" + mp4_url + '\'' +
                ", cover='" + cover + '\'' +
                ", sizeSHD=" + sizeSHD +
                ", playersize=" + playersize +
                ", ptime='" + ptime + '\'' +
                ", m3u8_url='" + m3u8_url + '\'' +
                ", topicImg='" + topicImg + '\'' +
                ", votecount=" + votecount +
                ", length=" + length +
                ", videosource='" + videosource + '\'' +
                ", m3u8Hd_url='" + m3u8Hd_url + '\'' +
                ", sizeSD=" + sizeSD +
                ", topicSid='" + topicSid + '\'' +
                ", playCount=" + playCount +
                ", replyCount=" + replyCount +
                ", replyBoard='" + replyBoard + '\'' +
                ", replyid='" + replyid + '\'' +
                ", topicName='" + topicName + '\'' +
                ", sectiontitle='" + sectiontitle + '\'' +
                ", topicDesc='" + topicDesc + '\'' +
                '}';
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(vid);
        dest.writeLong(sizeHD);
        dest.writeString(mp4Hd_url);
        dest.writeString(description);
        dest.writeString(title);
        dest.writeString(mp4_url);
        dest.writeString(cover);
        dest.writeInt(sizeSHD);
        dest.writeInt(playersize);
        dest.writeString(ptime);
        dest.writeString(m3u8_url);
        dest.writeString(topicImg);
        dest.writeInt(votecount);
        dest.writeInt(length);
        dest.writeString(videosource);
        dest.writeString(m3u8Hd_url);
        dest.writeInt(sizeSD);
        dest.writeString(topicSid);
        dest.writeInt(playCount);
        dest.writeInt(replyCount);
        dest.writeString(replyBoard);
        dest.writeString(replyid);
        dest.writeString(topicName);
        dest.writeString(sectiontitle);
        dest.writeString(topicDesc);
    }

    public String getVid() {
        return this.vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public long getSizeHD() {
        return this.sizeHD;
    }

    public void setSizeHD(long sizeHD) {
        this.sizeHD = sizeHD;
    }

    public String getMp4Hd_url() {
        return this.mp4Hd_url;
    }

    public void setMp4Hd_url(String mp4Hd_url) {
        this.mp4Hd_url = mp4Hd_url;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMp4_url() {
        return this.mp4_url;
    }

    public void setMp4_url(String mp4_url) {
        this.mp4_url = mp4_url;
    }

    public String getCover() {
        return this.cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getSizeSHD() {
        return this.sizeSHD;
    }

    public void setSizeSHD(int sizeSHD) {
        this.sizeSHD = sizeSHD;
    }

    public int getPlayersize() {
        return this.playersize;
    }

    public void setPlayersize(int playersize) {
        this.playersize = playersize;
    }

    public String getPtime() {
        return this.ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public String getM3u8_url() {
        return this.m3u8_url;
    }

    public void setM3u8_url(String m3u8_url) {
        this.m3u8_url = m3u8_url;
    }

    public String getTopicImg() {
        return this.topicImg;
    }

    public void setTopicImg(String topicImg) {
        this.topicImg = topicImg;
    }

    public int getVotecount() {
        return this.votecount;
    }

    public void setVotecount(int votecount) {
        this.votecount = votecount;
    }

    public int getLength() {
        return this.length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getVideosource() {
        return this.videosource;
    }

    public void setVideosource(String videosource) {
        this.videosource = videosource;
    }

    public String getM3u8Hd_url() {
        return this.m3u8Hd_url;
    }

    public void setM3u8Hd_url(String m3u8Hd_url) {
        this.m3u8Hd_url = m3u8Hd_url;
    }

    public int getSizeSD() {
        return this.sizeSD;
    }

    public void setSizeSD(int sizeSD) {
        this.sizeSD = sizeSD;
    }

    public String getTopicSid() {
        return this.topicSid;
    }

    public void setTopicSid(String topicSid) {
        this.topicSid = topicSid;
    }

    public int getPlayCount() {
        return this.playCount;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }

    public int getReplyCount() {
        return this.replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public String getReplyBoard() {
        return this.replyBoard;
    }

    public void setReplyBoard(String replyBoard) {
        this.replyBoard = replyBoard;
    }

    public String getReplyid() {
        return this.replyid;
    }

    public void setReplyid(String replyid) {
        this.replyid = replyid;
    }

    public String getTopicName() {
        return this.topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getSectiontitle() {
        return this.sectiontitle;
    }

    public void setSectiontitle(String sectiontitle) {
        this.sectiontitle = sectiontitle;
    }

    public String getTopicDesc() {
        return this.topicDesc;
    }

    public void setTopicDesc(String topicDesc) {
        this.topicDesc = topicDesc;
    }
}
