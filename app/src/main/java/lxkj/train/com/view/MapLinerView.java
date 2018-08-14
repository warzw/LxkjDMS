package lxkj.train.com.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.NumberKeyListener;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import lxkj.train.com.R;
import lxkj.train.com.utils.DensityUtil;
import lxkj.train.com.utils.EditTextLimitUtil;


/**
 * 创建人: 龙哥 on 2016/8/4
 * 主要功能: 一行的布局，像map一样 键对应值
 */
public class MapLinerView extends LinearLayout {

    private String textLeft = "";
    private String textRight = "";
    private String etRight = "";
    private TextView tv_text;
    private EditText et_text;
    private boolean isShow;
    private boolean isPhone;
    private boolean isPhoneNumber;
    private boolean isBackImage;
    private int rightColor;
    private int leftcolor;
    private int textLength;
    private TextView tv;
    private ImageView iv_1;
    private boolean isShowLine = true;
    private LinearLayout layout;
    private boolean isnNumberAZ;
    private float leftSize;
    private float rightSize;
    private float leftMargin;
    private String data="0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public MapLinerView(Context context) {
        super(context);
        initLayoutView();
    }

    public MapLinerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MapLinerView);
        textLeft = a.getString(R.styleable.MapLinerView_textLeft);
        textRight = a.getString(R.styleable.MapLinerView_textRight);
        etRight = a.getString(R.styleable.MapLinerView_etRight);
        isShow = a.getBoolean(R.styleable.MapLinerView_isShow, false);
        isPhone = a.getBoolean(R.styleable.MapLinerView_isPhone, false);
        isPhoneNumber = a.getBoolean(R.styleable.MapLinerView_isPhoneNumber, false);
        isBackImage = a.getBoolean(R.styleable.MapLinerView_isBackImage, false);
        rightColor = a.getColor(R.styleable.MapLinerView_textRightColor, getResources().getColor(R.color.gray_33));
        leftcolor = a.getColor(R.styleable.MapLinerView_textLeftColor, getResources().getColor(R.color.gray_33));
        textLength = a.getInt(R.styleable.MapLinerView_textLength, 100);
        isShowLine = a.getBoolean(R.styleable.MapLinerView_isShowLine, false);
        isnNumberAZ = a.getBoolean(R.styleable.MapLinerView_isnNumberAZ, false);
        leftSize =a.getDimension(R.styleable.MapLinerView_leftSize, DensityUtil.dip2px(getContext(),13));
        rightSize = a.getDimension(R.styleable.MapLinerView_rightSize,DensityUtil.dip2px(getContext(),13));
        leftMargin =a.getDimension(R.styleable.MapLinerView_leftMargin,DensityUtil.dip2px(getContext(),30));
        initLayoutView();
    }

    public MapLinerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayoutView();

    }

    public void initLayoutView() {
        View view = View.inflate(getContext(), R.layout.map_liner_view, this);
        layout = (LinearLayout) view.findViewById(R.id.layout);
        tv = (TextView) view.findViewById(R.id.tv);
        tv_text = (TextView) view.findViewById(R.id.tv_text);
        et_text = (EditText) view.findViewById(R.id.et_text);
        iv_1 = (ImageView) view.findViewById(R.id.iv_1);
        View line_1 = view.findViewById(R.id.line_1);
        if (!TextUtils.isEmpty(textRight)) {
            tv.setVisibility(View.VISIBLE);
            et_text.setVisibility(View.GONE);
            tv.setText(textRight);
        }
        //设置右边rightTextSize
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, rightSize);
        LayoutParams lp = (LayoutParams) tv.getLayoutParams();
        lp.setMargins((int) leftMargin, 0, 0, 0);
        tv.setLayoutParams(lp);
        tv_text.setText(textLeft);
        tv_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, leftSize);
        if (!TextUtils.isEmpty(textLeft)) {
            tv_text.setVisibility(View.VISIBLE);
            tv_text.setText(textLeft);
        }
        if (isShow) {
            tv.setVisibility(View.GONE);
            et_text.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(etRight)) {
            et_text.setHint(etRight);
        }
        if (isPhone) {
            et_text.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
        if (isPhoneNumber) {
            et_text.setInputType(InputType.TYPE_CLASS_PHONE);
            et_text.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
        }
        if (isBackImage) {
            iv_1.setVisibility(View.VISIBLE);
            et_text.setVisibility(View.GONE);
            tv.setVisibility(View.VISIBLE);
        }
        if (rightColor != 0) {
            tv.setTextColor(rightColor);
        }
        if (leftcolor != 0) {
            tv_text.setTextColor(leftcolor);
        }
        if (textLength != 0) {
            et_text.setFilters(new InputFilter[]{new InputFilter.LengthFilter(textLength)});
        }
        if (!isShowLine) {
            line_1.setVisibility(View.GONE);
        } else {
            line_1.setVisibility(View.VISIBLE);
        }
        if (isnNumberAZ) {
            et_text.setKeyListener(new NumberKeyListener() {
                @Override
                protected char[] getAcceptedChars() {
                    char[] chs=data.toCharArray();
                    return chs;
                }

                @Override
                public int getInputType() {
                    return InputType.TYPE_CLASS_TEXT;
                }
            });
        }
            EditTextLimitUtil.setEditTextInhibitInputSpeChat(et_text);
            EditTextLimitUtil.setEditTextInhibitInputSpace(et_text);
        }
    public void isNumberAZ(){
        et_text.setKeyListener(new NumberKeyListener() {
            @Override
            protected char[] getAcceptedChars() {
                char[] chs=data.toCharArray();
                return chs;
            }

            @Override
            public int getInputType() {
                return InputType.TYPE_CLASS_TEXT;
            }
        });
    }
    public void setRightText(String rightText) {
        tv.setVisibility(View.VISIBLE);
        et_text.setVisibility(View.GONE);
        tv.setText(rightText);

    }

    public void setRightTextColor(int color) {
        tv.setVisibility(View.VISIBLE);
        et_text.setVisibility(View.GONE);
        tv.setTextColor(ContextCompat.getColor(getContext(), color));

    }

    public String getRightText() {
        return tv.getText().toString().trim();
    }

    public TextView getRightTextView() {
        return tv;
    }

    public void setRightEditText(String rightEdit) {
        tv.setVisibility(View.GONE);
        et_text.setVisibility(View.VISIBLE);
        et_text.setText(rightEdit);
    }

    public void setRightEditHint(String rightHint) {
        et_text.setText(null);
        et_text.setHint(rightHint);
    }

    public EditText getEtTextView() {
        return et_text;
    }

    public String getEdString() {
        return et_text.getText().toString().trim();
    }

    public String getTextLeft() {
        return tv_text.getText().toString().trim();
    }

    public void setTextLeft(String textLeft) {
        tv_text.setText(textLeft);
    }

    public void showBackImage(boolean isShow) {
        if (isShow) {
            iv_1.setVisibility(View.VISIBLE);
            et_text.setVisibility(View.GONE);
            tv.setVisibility(View.VISIBLE);
        }
    }
    public ImageView getBackImage(){
        iv_1.setVisibility(View.VISIBLE);
        return iv_1;
    }
    public void setBackColor(int id) {
        layout.setBackgroundResource(id);
    }

}
