package com.example.thinkpad.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.thinkpad.myapplication.Utils;

/**
 * Created by Chris on 2018/11/30.
 */
public class PieChart extends View{

    private static final int RADIUS = (int) Utils.dp2px(150);
    private static final int LENGTH = (int) Utils.dp2px(20);
    private static final int PULLED_OUT_INDEX = 2;

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    RectF bounds = new RectF();
    float[] angles = {60,80, 100, 120};
    int[] colors = {Color.parseColor("#55ff0000"), Color.parseColor("#5500ff00"),
            Color.parseColor("#550000ff"), Color.parseColor("#009688")};

    public PieChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bounds.set(getWidth() / 2 - RADIUS, getHeight() / 2 - RADIUS, getWidth() / 2 + RADIUS, getHeight() / 2 + RADIUS);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float currentAngle = 0;
        for (int i = 0; i < angles.length; i++) {
            paint.setColor(colors[i]);

            canvas.save(); // 保存画布
            if (i == PULLED_OUT_INDEX){
                canvas.translate((float)Math.cos(Math.toRadians(currentAngle + angles[i]/2))*LENGTH , (float)Math.sin(Math.toRadians(currentAngle + angles[i]/2))*LENGTH );
            }
            canvas.drawArc(getWidth()/2-RADIUS,getHeight()/2-RADIUS,getWidth()/2+RADIUS,getHeight()/2+RADIUS
                    ,currentAngle,angles[i],true,paint);
            canvas.restore(); // 恢复画布

            currentAngle += angles[i];
        }

    }

}
