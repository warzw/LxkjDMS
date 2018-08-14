package lxkj.train.com.entity;

import android.support.v4.content.ContextCompat;
import android.view.View;

import io.realm.RealmObject;
import lxkj.train.com.R;
import lxkj.train.com.overall.OverallData;

/**
 * Created by dell on 2018/6/25.
 */

public class AnnouncementEntity {

    private String title;
    private String content;
    private String time;
    private String isShow ;
    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
