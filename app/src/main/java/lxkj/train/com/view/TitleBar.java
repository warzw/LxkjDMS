package lxkj.train.com.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import lxkj.train.com.R;


/**
 * Created by dhc on 2017/4/6.
 */

public class TitleBar extends LinearLayout implements View.OnClickListener {
    Drawable leftImageDrawble;
    Drawable rightImageDrawble;
    private String contentTitle = "";
    private String backtrack;
    private ImageView left_iv;
    private ImageView right_iv;
    private TextView tv_context;
    private TextView backtrack_tv;
    private View view;
    private LinearLayout left_layout;
    private Activity activity = (Activity) getContext();
    private RelativeLayout layout_background;
    private View line;
    private TextView right_tv;
    private LinearLayout layout_content;
    private LinearLayout right_layout;

    public TitleBar(Context context) {
        super(context);
        initLayoutView();

    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);
        leftImageDrawble = a.getDrawable(R.styleable.TitleBar_leftTitleImage);
        backtrack = a.getString(R.styleable.TitleBar_backtrack);
        contentTitle = a.getString(R.styleable.TitleBar_contentTitle);
        rightImageDrawble = a.getDrawable(R.styleable.TitleBar_rightTitleImage);
        initLayoutView();
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayoutView();
    }

    public void initLayoutView() {
        view = View.inflate(getContext(), R.layout.title_bar, this);
        tv_context =  view.findViewById(R.id.tv_context);
        layout_content =  view.findViewById(R.id.layout_content);
        backtrack_tv =  view.findViewById(R.id.backtrack_tv);
        left_iv =  view.findViewById(R.id.left_iv);
        right_iv =  view.findViewById(R.id.right_iv);
        left_layout =  view.findViewById(R.id.left_layout);
        layout_background =  view.findViewById(R.id.layout_background);
        line = view.findViewById(R.id.line);
        right_tv =  view.findViewById(R.id.right_tv);
        right_layout =  view.findViewById(R.id.right_layout);
        left_layout.setOnClickListener(this);
        if (!TextUtils.isEmpty(contentTitle)) {
            tv_context.setText(contentTitle);
        }
        if (rightImageDrawble != null) {
            right_iv.setVisibility(View.VISIBLE);
            right_iv.setImageDrawable(rightImageDrawble);
        }
    }

    public View getleft_layout() {
        return view.findViewById(R.id.left_layout);
    }

    public ImageView getRightImage() {
        return right_iv;
    }

    @Override
    public void onClick(View v) {
        activity.onBackPressed();
    }

    public void setLeftText(String leftText) {
        if (leftText != null) {
            backtrack_tv.setText(leftText);
            backtrack_tv.setVisibility(View.VISIBLE);
        }
    }

    public void setCentreText(String centreText) {
        if (!TextUtils.isEmpty(centreText)) {
            tv_context.setText(centreText);
            tv_context.setVisibility(View.VISIBLE);
        }
        layout_content.setVisibility(View.GONE);
    }

    public void setLeftImage(int id) {
        left_iv.setVisibility(View.VISIBLE);
        if (0 == id) {
            left_iv.setVisibility(View.GONE);
        }else {
            left_iv.setImageResource(id);
        }

    }

    public void setRightImage(int rightImage) {
        if (rightImage != 0) {
            right_iv.setImageResource(rightImage);
            right_iv.setVisibility(View.VISIBLE);
        }
    }

    public void showLine(boolean isShow) {
        if (!isShow) {
            line.setVisibility(View.GONE);
        }

    }

    public void setLayouBackGround(int color) {
        layout_background.setBackgroundResource(color);
    }

    public void setRight_tv(String right_text) {
        if (!TextUtils.isEmpty(right_text)) {
            right_tv.setVisibility(View.VISIBLE);
            right_tv.setText("" + right_text);
        }else {
            right_tv.setVisibility(View.GONE);
        }

    }

    public TextView getRight_tv() {
        return right_tv;
    }

    public void addContentView(View view) {
        layout_content.addView(view);
        tv_context.setVisibility(View.GONE);
        layout_content.setVisibility(View.VISIBLE);
    }
    public void addRightLayout(View view) {
        right_layout.setVisibility(View.VISIBLE);
        right_layout.addView(view);

    }
}
