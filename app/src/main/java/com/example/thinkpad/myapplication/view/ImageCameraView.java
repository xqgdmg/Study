package com.example.thinkpad.myapplication.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.thinkpad.myapplication.R;

/**
 * Created by Chris on 2018/12/3.
 * 使用Camera三维变换
 */
public class ImageCameraView extends View {


    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Bitmap avatar;
    private Camera camera;

    public ImageCameraView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    {
        avatar = getAvatar(400);
        camera = new Camera();
        camera.rotateX(25);
        camera.setLocation(0,0,8 * Resources.getSystem().getDisplayMetrics().density);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(200,200);
        camera.applyToCanvas(canvas);
        canvas.translate(-200,-200);
        canvas.drawBitmap(avatar,200,200,paint);

    }

    /*
     * 获取指定大小的bitmap
     */
    Bitmap getAvatar(int width) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.iv_avatar, options);

        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;
        options.inTargetDensity = width;
        return BitmapFactory.decodeResource(getResources(), R.drawable.iv_avatar, options);
    }

}
