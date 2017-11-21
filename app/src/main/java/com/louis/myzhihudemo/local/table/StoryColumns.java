package com.louis.myzhihudemo.local.table;

import android.support.annotation.IntDef;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import static com.louis.myzhihudemo.local.table.StoryColumns.BOOKED_STORY;
import static com.louis.myzhihudemo.local.table.StoryColumns.UNBOOKED_STORY;

/**
 * Created by Louis on 2017/5/21.
 */

@Entity
public class StoryColumns {
    public static final int BOOKED_STORY = 1;

    public static final int UNBOOKED_STORY = 0;
    /**
     * The unique ID for a row.
     * <P>Type: INTEGER (long)</P>
     */
//    public long _ID;

    /**
     * The count of rows in a directory.
     * <P>Type: INTEGER</P>
     */
//    public String _COUNT = "_count";
    @Id(autoincrement = true)
    public long STORY_ID;
    //    public String TYPE;
//    public String TITLE;
//    public String IMAGE;
//    public String BODY;
//    public String TIME;
    //    public long THEME_ID;//对应着哪个主题下的story
    @IfBooekd
    public int BOOKMARK;


    @Generated(hash = 728984494)
    public StoryColumns(long STORY_ID, int BOOKMARK) {
        this.STORY_ID = STORY_ID;
        this.BOOKMARK = BOOKMARK;
    }


    @Generated(hash = 911395981)
    public StoryColumns() {
    }


    public long getSTORY_ID() {
        return this.STORY_ID;
    }


    public void setSTORY_ID(long STORY_ID) {
        this.STORY_ID = STORY_ID;
    }


    public int getBOOKMARK() {
        return this.BOOKMARK;
    }


    public void setBOOKMARK(@IfBooekd int BOOKMARK) {
        this.BOOKMARK = BOOKMARK;
    }


    @IntDef({BOOKED_STORY, UNBOOKED_STORY})
    public @interface IfBooekd {
    }
}

