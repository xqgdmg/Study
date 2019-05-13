package com.example.thinkpad.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.thinkpad.myapplication.Utils;

/**
 * Created by Chris on 2018/12/5.
 * 类似抽奖地显示省份的名称
 */
public class ProvinceView extends View{

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    String province = "";

    public ProvinceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        paint.setColor(Color.parseColor("#55ff0000"));
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(Utils.dp2px(35));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(province,getWidth()/2,getHeight()/2,paint);
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
        invalidate();
    }
}
