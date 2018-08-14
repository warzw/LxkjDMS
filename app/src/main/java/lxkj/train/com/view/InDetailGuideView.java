package lxkj.train.com.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import lxkj.train.com.R;
import lxkj.train.com.utils.DensityUtil;
import lxkj.train.com.utils.LogUtils;
import lxkj.train.com.utils.ScreenUtils;

/**
 * Created by dell on 2018/7/3.
 */

public class InDetailGuideView extends View {
    private Paint mPaint;
    private Paint mPaint2;
    private int screenW;
    private int screenH;
    private int x;
    private int y;
    private int mWidth;
    private int y2;
    private Paint mPaint3;

    public InDetailGuideView(Context context) {
        super(context);
        initScreen();
        initPaint();
    }

    public InDetailGuideView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initScreen();
        initPaint();
    }

    public InDetailGuideView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initScreen();
        initPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(x, y, mWidth, y, mPaint); //上横线
        canvas.drawLine(mWidth / 4, y, mWidth / 4 + mWidth / 16, y - dip2px(60), mPaint);  //左边上斜线
        canvas.drawLine(mWidth / 4 + mWidth / 16 - dip2px(1), y - dip2px(59), mWidth * 3 / 4, y - dip2px(59), mPaint);//上顶线
        canvas.drawLine(mWidth * 3 / 4 - dip2px(1), y - dip2px(60), mWidth * 3 / 4 + mWidth / 16, y, mPaint);//右边上斜线

        canvas.drawLine(x, y2, mWidth, y2, mPaint); //下横线
        canvas.drawLine(mWidth / 4, y2, mWidth / 4 + mWidth / 16, y2 + dip2px(60), mPaint);  //左边下斜线
        canvas.drawLine(mWidth / 4 + mWidth / 16, y2 + dip2px(60), mWidth * 3 / 4, y2 + dip2px(60), mPaint);//下顶线
        canvas.drawLine(mWidth * 3 / 4, y2 + dip2px(60), mWidth * 3 / 4 + mWidth / 16, y2 , mPaint);//右边下斜线

        //设置画笔颜色
        mPaint2.setColor(ContextCompat.getColor(getContext(), R.color.gray_fff666));
        canvas.drawLine(mWidth/8, y-dip2px(10), mWidth/8+dip2px(20), y-dip2px(10), mPaint3);
        canvas.drawLine(mWidth/8+dip2px(10), y-dip2px(10), mWidth/8+dip2px(10), y-dip2px(30), mPaint3);
        canvas.drawCircle(mWidth/8+dip2px(10),y-dip2px(35) ,dip2px(10),mPaint2); //左边黄色圆球


        //设置画笔颜色
        mPaint2.setColor(ContextCompat.getColor(getContext(), R.color.gray_c61f26));
        canvas.drawLine(mWidth * 3 / 4-dip2px(50), y - dip2px(69), mWidth * 3 / 4-dip2px(30), y - dip2px(69), mPaint3);
        canvas.drawLine(mWidth * 3 / 4-dip2px(40), y - dip2px(69), mWidth * 3 / 4-dip2px(40), y - dip2px(89), mPaint3);
        canvas.drawCircle(mWidth * 3 / 4-dip2px(40),y - dip2px(95) ,dip2px(10),mPaint2); //右边红色色圆球

        //白色线部分
        //设置画笔颜色
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.white));
        canvas.drawLine(mWidth/8+dip2px(10), y, mWidth / 4+dip2px(1), y, mPaint); //上横线
        canvas.drawLine(mWidth / 4-dip2px(1), y, mWidth / 4 + mWidth / 16, y - dip2px(60), mPaint);  //上斜线
        canvas.drawLine(mWidth / 4 + mWidth / 16-dip2px(1), y - dip2px(59), mWidth * 3 / 4-dip2px(40), y - dip2px(59), mPaint);//上顶线
    }

    private void initScreen() {
        ScreenUtils.initScreen((Activity) getContext());
        screenW = ScreenUtils.getScreenW();
        screenH = ScreenUtils.getScreenH();
        LogUtils.i("screenWscreenH", screenW * 4 / 10 + " --- " + screenH);
        x = 0;
        y = screenH / 2 - dip2px(45);
        y2 = screenH / 2;
        mWidth = screenW * 6 / 10;
    }

    private void initPaint() {
        initPaint2();
        initPaint3();
        mPaint = new Paint();
        //设置画笔颜色
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.gray_2f97e3));
        //STROKE                //描边
        //FILL                  //填充
        //FILL_AND_STROKE       //描边加填充
        //设置画笔模式
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true); //消除锯齿
        //设置画笔宽度为30px
        mPaint.setStrokeWidth(dip2px(4));
        mPaint.setTextSize(dip2px(14));
    }
    private void initPaint2() { //画圆圈
        mPaint2 = new Paint();
        //设置画笔颜色
        mPaint2.setColor(ContextCompat.getColor(getContext(), R.color.gray_fff666));
        //STROKE                //描边
        //FILL                  //填充
        //FILL_AND_STROKE       //描边加填充
        //设置画笔模式
        mPaint2.setAntiAlias(true); //消除锯齿
        mPaint2.setStyle(Paint.Style.FILL);
        //设置画笔宽度为30px
        mPaint2.setStrokeWidth(dip2px(3));
        mPaint2.setTextSize(dip2px(14));
    }
    private void initPaint3() {
        mPaint3 = new Paint();
        //设置画笔颜色
        mPaint3.setColor(ContextCompat.getColor(getContext(), R.color.gray_2f97e3));
        //STROKE                //描边
        //FILL                  //填充
        //FILL_AND_STROKE       //描边加填充
        //设置画笔模式
        mPaint3.setStyle(Paint.Style.FILL);
        mPaint3.setAntiAlias(true); //消除锯齿
        //设置画笔宽度为30px
        mPaint3.setStrokeWidth(dip2px(1));
        mPaint3.setTextSize(dip2px(14));
    }



    private int dip2px(int xy) {//dp转化为px
        return DensityUtil.dip2px(getContext(), xy);
    }
}
