package com.time_bt.administrator.bt_timing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author iimT
 * @date 2017/12/22
 */

public class MyCanvas extends View {
    private Paint mPaint = new Paint();
    public MyCanvas(Context context) {
        super(context);
    }
    public MyCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyCanvas(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //绘制最外层大圆
        mPaint.setColor(Color.BLACK);//设置画笔颜色为黑色
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);//设置画笔style实心
        mPaint.setTextSize(40);
        RectF rect = new RectF(getWidth() / 2 - getHeight() / 2,
                0, getWidth() / 2 + getHeight() / 2, getHeight());//圆弧的外接矩形
        canvas.drawText("❀文字",100,100,mPaint);
        mPaint.setStyle(Paint.Style.FILL);
    }
}
