package com.example.scalable;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.OverScroller;

/**
 * Created by Chris on 2018/12/10.
 */
public class ScalableImageView extends View{

    private static final float IMAGE_WIDTH = Utils.dpToPixel(300);
    private static final float OVER_SCALE_FACTOR = 2.0f;

    Bitmap bitmap;
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    float offsetX;
    float offsetY;
    float originalOffsetX;
    float originalOffsetY;
    float smallScale;
    float bigScale;
    boolean isBig;
    float currentScale;

    ObjectAnimator scaleAnimator;

    GestureDetector.SimpleOnGestureListener simpleOnGestureListener = new MyOnGestureListener();
    GestureDetectorCompat gestureDetector;
    OverScroller scroller;


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

        scroller = new OverScroller(context);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        originalOffsetX = (getWidth()-bitmap.getWidth())/2f;
        originalOffsetY = (getHeight()-bitmap.getHeight())/2f;

        if (bitmap.getWidth()/bitmap.getHeight() > getWidth()/getHeight()){ // 图片在控件中，宽大于高，所以纵向是大的
            bigScale = (float)getHeight()/bitmap.getHeight() * OVER_SCALE_FACTOR;
            smallScale = (float)getWidth()/bitmap.getWidth();
        }else {
            smallScale = (float)getHeight()/bitmap.getHeight();
            bigScale = (float)getWidth()/bitmap.getWidth() * OVER_SCALE_FACTOR;
        }
        currentScale = smallScale;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
         // 手指移动图片
//        canvas.translate(offsetX,offsetY);// 这样写会造成放大后在缩小的时候，原来小图的位置也跟着变
         // 修正放大后移动在缩小后 位置偏移的问题
        float scaleFraction = (currentScale - smallScale) / (bigScale - smallScale); // 由缩放的比例计算位移的比例
        canvas.translate(offsetX * scaleFraction, offsetY * scaleFraction);
         // 双击放大图片
        canvas.scale(currentScale,currentScale, getWidth() / 2f, getHeight() / 2f); // 注意要传后面的两个缩放中心的参数
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

         // 移动 distanceX ,distanceY 的值正负号和坐标系相反
        @Override
        public boolean onScroll(MotionEvent down, MotionEvent event, float distanceX, float distanceY) {
            Log.e("chris","distanceX==" + distanceX);
            Log.e("chris","distanceY==" + distanceY);
             // 计算位移
            if (isBig){
                offsetX = offsetX - distanceX;
                offsetY = offsetY - distanceY;
                fixOffsets();
                invalidate();
            }

            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

         // 惯性滑动
        @Override
        public boolean onFling(MotionEvent down, MotionEvent move, float velocityX, float velocityY) {
            if (isBig){
                 // 填参数
                scroller.fling((int)offsetX,(int)offsetY,(int)velocityX,(int)velocityY,
                        // 边界物理模型
                        (int)-(bitmap.getWidth() * bigScale - getWidth()) / 2,
                        (int)(bitmap.getWidth() * bigScale - getWidth()) / 2,
                        (int)-(bitmap.getHeight() * bigScale - getHeight()) / 2,
                        (int)(bitmap.getHeight() * bigScale - getHeight()) / 2
                );

                 // 计算
                // 这个方法要api 16才有效，老版本用post
                postOnAnimation(new Runnable() {
                    @Override
                    public void run() {
                        boolean computeResult = scroller.computeScrollOffset();//计算
                        if (computeResult){ // computeResult== true 表示动画未完成
                            // 赋值
                            offsetX = scroller.getCurrX();
                            offsetY = scroller.getCurrY();
                            invalidate();

                            // 继续执行下一个
                            postOnAnimation(this);
                        }
                    }
                });
            }

            return false;
        }

         // 单击确认（设置了双击用这个）
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return super.onSingleTapConfirmed(e);

        }

         // 双击 300ms，手抖两次间隔小于40ms，不会响应
         // 只响应 down
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            isBig = !isBig;
//            invalidate();
            if (isBig){
                getScaleAnimator().start();
            }else{
                getScaleAnimator().reverse();
            }
            return super.onDoubleTap(e);
        }



         // 双击，早期谷歌地图调地图角度，现在地图是多点触控的
         // 响应 down, move, and up
        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            return super.onDoubleTapEvent(e);
        }
    }

    /*
     * 限制手指移动的时候，图片可以移动的边界
     * 注意最小最大值的正负号
     */
    private void fixOffsets() {
         // 正向取最小值
       offsetX = Math.min(offsetX,(bitmap.getWidth()* bigScale - getWidth())/2);
        // 反向取最大值
       offsetX = Math.max(offsetX,-(bitmap.getWidth()* bigScale - getWidth())/2);
       offsetY = Math.min(offsetY,(bitmap.getHeight()* bigScale - getHeight())/2);
       offsetY = Math.max(offsetY,-(bitmap.getHeight()* bigScale - getHeight())/2);

    }

    /*
     * 获取动画
     */
    private ObjectAnimator getScaleAnimator() {
        if (scaleAnimator == null){
            scaleAnimator = ObjectAnimator.ofFloat(this,"currentScale",smallScale,bigScale);
        }
        return scaleAnimator;
    }

    public float getCurrentScale() {
        return currentScale;
    }

    public void setCurrentScale(float currentScale) {
        this.currentScale = currentScale;
        invalidate();
    }
}
