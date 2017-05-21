package com.louis.myzhihudemo.local.table;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Louis on 2017/5/21.
 */
@Entity
public class ThemeColumns {

    /**
     * The unique ID for a row.
     * <P>Type: INTEGER (long)</P>
     */
    @Id(autoincrement = true)
    public long _ID;

    /**
     * The count of rows in a directory.
     * <P>Type: INTEGER</P>
     */
    public String _count;

    public long themeId;
    public String color;
    public String name;
    public String description;
    public String thumbnail;
    public String showInHome;//显示在首页的顺序
    @Generated(hash = 1720511606)
    public ThemeColumns(long _ID, String _count, long themeId, String color,
            String name, String description, String thumbnail, String showInHome) {
        this._ID = _ID;
        this._count = _count;
        this.themeId = themeId;
        this.color = color;
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
        this.showInHome = showInHome;
    }
    @Generated(hash = 1673896204)
    public ThemeColumns() {
    }
    public long get_ID() {
        return this._ID;
    }
    public void set_ID(long _ID) {
        this._ID = _ID;
    }
    public String get_count() {
        return this._count;
    }
    public void set_count(String _count) {
        this._count = _count;
    }
    public long getThemeId() {
        return this.themeId;
    }
    public void setThemeId(long themeId) {
        this.themeId = themeId;
    }
    public String getColor() {
        return this.color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getThumbnail() {
        return this.thumbnail;
    }
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
    public String getShowInHome() {
        return this.showInHome;
    }
    public void setShowInHome(String showInHome) {
        this.showInHome = showInHome;
    }
}
