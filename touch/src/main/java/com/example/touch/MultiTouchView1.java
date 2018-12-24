package com.example.touch;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author: Chris on 2018/12/24 15:06
 * @description: 抢夺型
 */
public class MultiTouchView1 extends View{

    private static final float IMAGE_WIDTH = Utils.dpToPixel(200);

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;

    float downX;// 初始x值，移动的时候要减去这个值
    float downY;// 初始y值，移动的时候要减去这个值
    float offsetX;
    float offsetY;
    float originalOffsetX;
    float originalOffsetY;
    int trackingPointerId;

    public MultiTouchView1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bitmap = Utils.getAvatar(getResources(), (int) IMAGE_WIDTH);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int actionIndex;
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:

                trackingPointerId = event.getPointerId(0);// 0
                downX = event.getX();
                downY = event.getY();
                originalOffsetX = offsetX;
                originalOffsetY = offsetY;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                // 判断手指
                actionIndex = event.getActionIndex();
                trackingPointerId = event.getPointerId(actionIndex);

                downX = event.getX(actionIndex);// 当前手指
                downY = event.getY(actionIndex);
                originalOffsetX = offsetX;
                originalOffsetY = offsetY;
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("chris","ACTION_MOVE");
//                // 判断手指
//                actionIndex = event.getActionIndex();
//                trackingPointerId = event.getPointerId(actionIndex);

                int index = event.findPointerIndex(trackingPointerId);// 要是当前活动的手指

                Log.e("chris","originalOffsetX==" + originalOffsetX);
                Log.e("chris","downX==" + downX);
                offsetX = originalOffsetX + event.getX(index) - downX;
                Log.e("chris","offsetX==" + offsetX);
                offsetY = originalOffsetY + event.getY(index) - downY;
                invalidate();
                break;
            case MotionEvent.ACTION_POINTER_UP:
                actionIndex = event.getActionIndex();
                int pointerId = event.getPointerId(actionIndex);
                if (pointerId == trackingPointerId) {// 只有活跃的手指才需要考虑，如果不是活跃的手指就没有影响
                    // 修改当前使用的手指
                    int newIndex;
                    if (actionIndex == event.getPointerCount() - 1) {
                        newIndex = event.getPointerCount() - 2;
                    } else {
                        newIndex = event.getPointerCount() - 1;
                    }
                    Log.e("chris","newIndex---==" + newIndex);
                    trackingPointerId = event.getPointerId(newIndex);
                    // 赋值，跟按下是一个逻辑
                    downX = event.getX(actionIndex);// 因为是活跃的手指，松开后，下次移动的时候要减去这个点的位置
                    Log.e("chris","downX---==" + downX);
                    downY = event.getY(actionIndex);
                    originalOffsetX = offsetX;// 初始位置是松开这个点的偏移
                    Log.e("chris","originalOffsetX---==" + originalOffsetX);
                    originalOffsetY = offsetY;
                }else{
                    Log.e("chris","else---==");
                }
                break;
        }

//        return super.onTouchEvent(event);
        return true;// 自己处理
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap, offsetX, offsetY, paint);
    }

}
