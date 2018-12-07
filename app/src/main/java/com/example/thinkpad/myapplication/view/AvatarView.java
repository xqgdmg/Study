package com.example.thinkpad.myapplication.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.thinkpad.myapplication.R;
import com.example.thinkpad.myapplication.Utils;

/**
 * Created by Chris on 2018/11/30.
 */
public class AvatarView extends View{

    private static final float WIDTH = Utils.dp2px(300);
    private static final float PADDING = Utils.dp2px(25);
    private static final float EDGE_WIDTH = Utils.dp2px(10);
    private Paint pain;

    private Bitmap bitmap;

    Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    RectF recF ;

    public AvatarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        pain = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    {
        bitmap = getAvatar((int) WIDTH);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        recF = new RectF(PADDING,PADDING,PADDING+WIDTH,PADDING+WIDTH);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawOval(PADDING-EDGE_WIDTH,PADDING-EDGE_WIDTH,PADDING+WIDTH+EDGE_WIDTH,PADDING+WIDTH+EDGE_WIDTH,pain);

        int count = canvas.saveLayer(recF, pain);
        canvas.drawOval(PADDING,PADDING,PADDING+WIDTH,PADDING+WIDTH,pain);
        pain.setXfermode(xfermode);
        canvas.drawBitmap(bitmap,PADDING,PADDING,pain);
        canvas.restoreToCount(count);
        pain.setXfermode(null);


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
