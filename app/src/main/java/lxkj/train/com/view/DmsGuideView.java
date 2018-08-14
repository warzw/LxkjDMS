package lxkj.train.com.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;
import java.util.Map;

import lxkj.train.com.R;
import lxkj.train.com.entity.DmsDrawEntity;
import lxkj.train.com.overall.OverallData;
import lxkj.train.com.utils.DensityUtil;
import lxkj.train.com.utils.LogUtils;
import lxkj.train.com.utils.MillerCoordinate;
import lxkj.train.com.utils.ScreenUtils;

/**
 * Created by dell on 2018/6/29.
 */

public class DmsGuideView extends View {

    private Paint mPaint;
    private Paint mPaint2;
    List<DmsDrawEntity> datas;
    private Paint mPaint3;

    public DmsGuideView(Context context) {
        super(context);
        initPaint();
    }

    public DmsGuideView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public DmsGuideView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (datas == null) {
            return;
        }
        //绘制一条直线(两点确定一线) ,下面两个时基线x,y
        for (int i = 0; i <datas.size()-1 ; i++) {
            canvas.drawLine(datas.get(i).getX(), datas.get(i).getY(), datas.get(i+1).getX(), datas.get(i+1).getY(), mPaint);
            canvas.drawCircle(datas.get(i).getX(), datas.get(i).getY(),dip2px(4),mPaint2);
            canvas.drawCircle(datas.get(i+1).getX(), datas.get(i+1).getY(),dip2px(4),mPaint2);
        }
        for (int i = 0; i < datas.size(); i++) {
            if (i%2==0) {
                canvas.drawText(datas.get(i).getName(),datas.get(i).getX()+dip2px(10), datas.get(i).getY(),mPaint);
            }else {
                canvas.drawText(datas.get(i).getName(),datas.get(i).getX()-dip2px(50),datas.get(i).getY()+dip2px(10),mPaint);
            }
        }
        canvas.drawCircle(datas.get(4).getX()+dip2px(55), datas.get(4).getY()+dip2px(100),dip2px(8),mPaint3);
        mPaint.setStrokeWidth(dip2px(1));
        canvas.drawLine(datas.get(4).getX()+dip2px(62), datas.get(4).getY()+dip2px(100), datas.get(4).getX()+dip2px(150), datas.get(4).getY(), mPaint);
        mPaint.setStrokeWidth(dip2px(5));
        canvas.drawText("当前位置",datas.get(4).getX()+dip2px(150), datas.get(4).getY(),mPaint);
    }
    public void dataDraws(List<DmsDrawEntity> datas){
        this.datas = datas;
        invalidate();
    }


    private void initPaint() {
        initPaint2();
        initPaint3();
        mPaint = new Paint();
        //设置画笔颜色
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.gray_33));
        //STROKE                //描边
        //FILL                  //填充
        //FILL_AND_STROKE       //描边加填充
        //设置画笔模式
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true); //消除锯齿
        //设置画笔宽度为30px
        mPaint.setStrokeWidth(dip2px(5));
        mPaint.setTextSize(dip2px(14));
    }
    private void initPaint2() { //画圆圈
        mPaint2 = new Paint();
        //设置画笔颜色
        mPaint2.setColor(ContextCompat.getColor(getContext(), R.color.gray_e84e4e));
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
    private void initPaint3() { //画圆圈
        mPaint3 = new Paint();
        //设置画笔颜色
        mPaint3.setColor(ContextCompat.getColor(getContext(), R.color.gray_33cc66));
        //STROKE                //描边
        //FILL                  //填充
        //FILL_AND_STROKE       //描边加填充
        //设置画笔模式
        mPaint3.setAntiAlias(true); //消除锯齿
        mPaint3.setStyle(Paint.Style.FILL);
        //设置画笔宽度为30px
        mPaint3.setStrokeWidth(dip2px(3));
        mPaint3.setTextSize(dip2px(14));
    }
    private int dip2px(int xy){//dp转化为px
        return DensityUtil.dip2px(getContext(), xy);
    }
}
