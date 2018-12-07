package com.example.thinkpad.myapplication.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.thinkpad.myapplication.Utils;

/**
 * Created by Chris on 2018/12/5.
 */
public class FlipView extends View {

    private static final float PADDING = Utils.dp2px(100);
    private static final float IMAGE_WIDTH = Utils.dp2px(200);

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Camera camera = new Camera();
    private  Bitmap bitmap;

    float topFlip = 0;
    float bottomFlip = 0;
    float flipRotation = 0;

    public FlipView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }



    {
        camera.setLocation(0,0,Utils.getZForCamera());
        bitmap = Utils.getAvatar(getResources(), (int) IMAGE_WIDTH);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

         // 上半部分
        canvas.save();
        canvas.translate(PADDING+IMAGE_WIDTH/ 2,PADDING+IMAGE_WIDTH/ 2);
        canvas.rotate(-flipRotation);// ???
        camera.save();
        camera.rotateX(topFlip);
        camera.applyToCanvas(canvas);
        camera.restore();
        canvas.clipRect(- IMAGE_WIDTH, - IMAGE_WIDTH, IMAGE_WIDTH, 0);
        canvas.rotate(flipRotation);// ???
        canvas.translate(-(PADDING+IMAGE_WIDTH/ 2),-(PADDING+IMAGE_WIDTH/ 2));
        canvas.drawBitmap(bitmap,PADDING,PADDING,paint);
        canvas.restore();

        // 下半部分
        canvas.save();
        canvas.translate(PADDING+IMAGE_WIDTH/ 2,PADDING+IMAGE_WIDTH/ 2);
        canvas.rotate(-flipRotation);// ???
        camera.save();
        camera.rotateX(bottomFlip);
        camera.applyToCanvas(canvas);
        camera.restore();
        canvas.clipRect(- IMAGE_WIDTH, 0, IMAGE_WIDTH, IMAGE_WIDTH);
        canvas.rotate(flipRotation);// ???
        canvas.translate(-(PADDING+IMAGE_WIDTH/ 2),-(PADDING+IMAGE_WIDTH/ 2));
        canvas.drawBitmap(bitmap,PADDING,PADDING,paint);
        canvas.restore();

        /*canvas.save();
        canvas.translate(PADDING + IMAGE_WIDTH / 2, PADDING + IMAGE_WIDTH / 2);
        canvas.rotate(-flipRotation);
        camera.save();
        camera.rotateX(bottomFlip);
        camera.applyToCanvas(canvas);
        camera.restore();
        canvas.clipRect(- IMAGE_WIDTH, 0, IMAGE_WIDTH, IMAGE_WIDTH);
        canvas.rotate(flipRotation);
        canvas.translate(- (PADDING + IMAGE_WIDTH / 2), - (PADDING + IMAGE_WIDTH / 2));
        canvas.drawBitmap(Utils.getAvatar(getResources(), (int) IMAGE_WIDTH), PADDING, PADDING, paint);
        canvas.restore();*/

    }

    public float getTopFlip() {
        return topFlip;
    }

    public void setTopFlip(float topFlip) {
        this.topFlip = topFlip;
        Log.e("chris","bottomFlip==" + bottomFlip);
        Log.e("chris","flipRotation==" + flipRotation);
        invalidate();
    }

    public float getBottomFlip() {
        return bottomFlip;
    }

    public void setBottomFlip(float bottomFlip) {
        this.bottomFlip = bottomFlip;
        invalidate();
    }

    public float getFlipRotation() {
        return flipRotation;
    }

    public void setFlipRotation(float flipRotation) {
        this.flipRotation = flipRotation;
        invalidate();
    }
}
