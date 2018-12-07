package com.example.thinkpad.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.thinkpad.myapplication.Utils;

/**
 * Created by Chris on 2018/12/3.
 */
public class SportsView extends View {

    private static final float RING_WIDTH = Utils.dp2px(20);
    private static final float RADIUS = Utils.dp2px(150);
    private static final int CIRCLE_COLOR = Color.parseColor("#90A4AE");
    private static final int HIGHLIGHT_COLOR = Color.parseColor("#FF4081");

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint.FontMetrics fontMetrics = new Paint.FontMetrics();

    public SportsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 绘制环
        paint.setColor(CIRCLE_COLOR);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(RING_WIDTH);
        canvas.drawCircle(getWidth()/2,getHeight()/2,RADIUS,paint);

        // 绘制进度条
        paint.setColor(HIGHLIGHT_COLOR);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(RING_WIDTH);
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawArc(getWidth()/2-RADIUS,getHeight()/2-RADIUS,getWidth()/2+RADIUS,getHeight()/2+RADIUS,
                0,120,false,paint);

        // 绘制文字
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(Utils.dp2px(35));
        paint.setTextAlign(Paint.Align.CENTER);
        float fontMetricsd = paint.getFontMetrics(this.fontMetrics);
        Log.e("chris","fontMetricsd==" + fontMetricsd);
        Log.e("chris","fontMetrics.ascent==" + fontMetrics.ascent);
        Log.e("chris","fontMetrics.descent==" + fontMetrics.descent);
        Log.e("chris","getHeight()/2==" + getHeight()/2);
        canvas.drawText("heihei",getWidth()/2,getHeight()/2- (fontMetrics.ascent+fontMetrics.descent)/2,paint);

    }
}
