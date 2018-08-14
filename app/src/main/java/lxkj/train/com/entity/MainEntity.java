package lxkj.train.com.entity;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import io.realm.RealmObject;
import lxkj.train.com.R;
import lxkj.train.com.entity.base.BaseEntity;
import lxkj.train.com.overall.OverallData;

/**
 * Created by dell on 2018/6/11.
 */

public class MainEntity extends RealmObject {
    private int image;
    private String text;
    private int tag;

    public void setKeyImage(TextView tv,ImageView iv) {
        if (text.equals("T")||text.equals("G")||text.equals("D")||text.equals("C")) {
            tv.setTextColor(ContextCompat.getColor(OverallData.app,R.color.base_txt_color3));
        }else {
            tv.setTextColor(ContextCompat.getColor(OverallData.app,R.color.gray_33));
        }
        if (text.equals("-1")) {//删除图标
            iv.setVisibility(View.VISIBLE);
            iv.setImageResource(R.mipmap.icon_del);
            tv.setVisibility(View.GONE);
        }else if (text.equals("-2")) {//隐藏图标
            iv.setVisibility(View.VISIBLE);
            iv.setImageResource(R.mipmap.ic_arrow_down);
            tv.setVisibility(View.GONE);
        }else {
            iv.setVisibility(View.GONE);
            tv.setVisibility(View.VISIBLE);
        }
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }


    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    public void setImage(ImageView iv){
        iv.setImageResource(image);
    }
}
