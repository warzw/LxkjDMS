package lxkj.train.com.entity;

import android.widget.ImageView;

import lxkj.train.com.R;

/**
 * Created by dell on 2018/6/25.
 */

public class DriveGuideItemEntity  {
    private String textTitle;
    private boolean isCheck;
    private String textContent;
    public String getTextTitle() {
        return textTitle;
    }

    public void setTextTitle(String textTitle) {
        this.textTitle = textTitle;
    }
    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public void setCheckState(ImageView imageView){
        if (isCheck) {
            imageView.setImageResource(R.mipmap.pay_success_icon);
        }else {
            imageView.setImageResource(R.mipmap.pay_failed_icon);
        }
    }
}
