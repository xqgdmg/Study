package com.example.thinkpad.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.thinkpad.myapplication.Utils;

/**
 * Created by Chris on 2018/11/29.
 */
public class DashboardTest extends View{

    private static final int angle = 120;
    private static final float radius = Utils.dp2px(150);
    private static final float length = Utils.dp2px(100);
    private Paint paint;
    private final Path pathShape;
    private final PathEffect pathEffect;

    public DashboardTest(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

         // 画笔
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(Utils.dp2px(2));

         // 刻度
        pathShape = new Path();
        pathShape.addRect(0,0,Utils.dp2px(2),Utils.dp2px(10), Path.Direction.CW);

         // 测量圆弧长度
        Path path = new Path();
        path.addArc(getWidth()/2 -radius,getHeight()/2-radius,getWidth()/2+radius,getHeight()/2+radius ,
                90+angle/2,360-angle);
        PathMeasure pathMeasure = new PathMeasure(path,false);
        float arcLength = pathMeasure.getLength();

        pathEffect = new PathDashPathEffect(pathShape,(arcLength-Utils.dp2px(2))/20,0,PathDashPathEffect.Style.ROTATE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

         // 画弧线
        canvas.drawArc(getWidth()/2 -radius,getHeight()/2-radius,getWidth()/2+radius,getHeight()/2+radius ,
                90+angle/2,360-angle,false,paint);

         // 画刻度
        paint.setPathEffect(pathEffect);
        canvas.drawArc(getWidth()/2 -radius,getHeight()/2-radius,getWidth()/2+radius,getHeight()/2+radius ,
                90+angle/2,360-angle,false,paint);
        paint.setPathEffect(null);


         // 画指针
        canvas.drawLine(getWidth()/2,getHeight()/2
                ,(float)Math.cos(Math.toRadians(getAngleFromMark(5)))*length + getWidth()/2
                ,(float)Math.sin(Math.toRadians(getAngleFromMark(5)))*length + getHeight()/2
                ,paint);


    }

     // 计算第几个刻度的角度
     int getAngleFromMark(int mark) {
         return (int) (
                 90 + (float) angle / 2  // 起点
                         + (360 - (float) angle) / 20 * mark  // 占据多少份
         );
     }

}
