package com.example.touch;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author: Chris on 2018/12/25 14:52
 * @description: 多指画板，互不干扰型
 */
public class MultiTouchView3 extends View {

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    SparseArray<Path> paths = new SparseArray<>();

    public MultiTouchView3(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(Utils.dpToPixel(4));
        paint.setStrokeCap(Paint.Cap.ROUND);// 圆头
        paint.setStrokeJoin(Paint.Join.ROUND);// 圆的连接线
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int actionIndex = event.getActionIndex();
        int pointerId = event.getPointerId(actionIndex);

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                Path path = new Path();
                path.moveTo(event.getX(actionIndex),event.getY(actionIndex));
                paths.append(pointerId, path);// key==pointerId,value==path
                invalidate();
                break;

            case MotionEvent.ACTION_MOVE:
                for (int pointIndex = 0; pointIndex < event.getPointerCount(); pointIndex++) {
                    int pointerId1 = event.getPointerId(pointIndex);
                    Path path1 = paths.get(pointerId1);
                    path1.lineTo(event.getX(pointIndex),event.getY(pointIndex));
                    invalidate();
                }
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                paths.remove(pointerId);
                invalidate();
                break;
        }

//        return super.onTouchEvent(event);
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < paths.size(); i++) {
            canvas.drawPath(paths.valueAt(i),paint);
        }

    }

}
