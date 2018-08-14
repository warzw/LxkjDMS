package lxkj.train.com.entity.base;

import android.support.v4.content.ContextCompat;

import io.realm.RealmObject;
import lxkj.train.com.R;
import lxkj.train.com.overall.OverallData;

/**
 * Created by dell on 2018/6/8.
 */

public class BaseEntity {
    private String content; //为了较少实体类，多添加的属性
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    private int textSize12 = R.dimen.text_size_12;
    private int color333 = ContextCompat.getColor(OverallData.app,R.color.gray_33);
    private int colorWhite = ContextCompat.getColor(OverallData.app,R.color.white);
    public int getColorWhite() {
        colorWhite = ContextCompat.getColor(OverallData.app,R.color.white);
        return colorWhite;
    }

    public void setColorWhite(int colorWhite) {
        this.colorWhite = colorWhite;

    }


    public int getTextSize12() {
        textSize12 = R.dimen.text_size_12;
        return textSize12;
    }

    public void setTextSize12(int textSize12) {
        this.textSize12 = textSize12;
    }

    public int getColor333() {
        color333 = ContextCompat.getColor(OverallData.app,R.color.gray_33);
        return color333;
    }

    public void setColor333(int color333) {
        this.color333 = color333;
    }

}
