package lxkj.train.com.entity.realm;

import io.realm.RealmObject;

/**
 * Created by dell on 2018/6/25.
 */

public class AnnouncementRealm extends RealmObject {

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
