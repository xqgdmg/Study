package com.example.scalable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Chris on 2018/12/10.
 */
public class ScalableImageView extends View{

    private static final float IMAGE_WIDTH = Utils.dpToPixel(300);
    private static final float OVER_SCALE_FACTOR = 1.5f;

    Bitmap bitmap;
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    float offsetX;
    float offsetY;
    float originalOffsetX;
    float originalOffsetY;
    float smallScale;
    float bigScale;
    boolean big;
    float currentScale;

    GestureDetector.SimpleOnGestureListener simpleOnGestureListener = new MyOnGestureListener();
    GestureDetectorCompat gestureDetector;


    public ScalableImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bitmap = Utils.getAvatar(getResources(), (int) IMAGE_WIDTH);

         // 第二个参数可以传 SimpleOnGestureListener，少实现几个没有用的方法。SimpleOnGestureListener 实现了
         // SimpleOnGestureListener implements OnGestureListener, OnDoubleTapListener
         // 这个构造方法有这个方法：
        // if (listener instanceof OnDoubleTapListener) {
        //    setOnDoubleTapListener((OnDoubleTapListener) listener);
        // }
        gestureDetector = new GestureDetectorCompat(context, simpleOnGestureListener);

         // 这个就不用设置
        /*gestureDetector.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                return false;
            }
        });*/
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        originalOffsetX = (getWidth()-IMAGE_WIDTH)/2;
        originalOffsetY = (getHeight()-IMAGE_WIDTH)/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(bitmap,originalOffsetX,originalOffsetY,paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        return super.onTouchEvent(event);
        return gestureDetector.onTouchEvent(event);
    }


    class MyOnGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return true; // 必须返回true
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

         // 单击抬起，不会判断双击，双击也会触发
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

         // 移动
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

         // 惯性滑动
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }

         // 单击确认（设置了双击用这个）
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return super.onSingleTapConfirmed(e);
        }

         // 双击
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            return super.onDoubleTap(e);
        }

         // 双击，
        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            return super.onDoubleTapEvent(e);
        }
    }



}
