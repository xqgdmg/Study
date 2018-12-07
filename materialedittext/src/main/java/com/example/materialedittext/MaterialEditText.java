package com.example.materialedittext;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Chris on 2018/12/6.
 */
public class MaterialEditText extends AppCompatEditText {

    private static final float TEXT_SIZE = Utils.dpToPixel(12);
    private static final float TEXT_MARGIN = Utils.dpToPixel(8);
     // 绘制文字距离屏幕上方的距离
    private static final int TEXT_VERTICAL_OFFSET = (int) Utils.dpToPixel(22);
    // 绘制文字距离屏幕左边的距离
    private static final int TEXT_HORIZONTAL_OFFSET = (int) Utils.dpToPixel(5);
    private static final int TEXT_ANIMATION_OFFSET = (int) Utils.dpToPixel(6);

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    boolean floatingLabelShown;
    float floatingLabelFraction;
    ObjectAnimator animator;
    boolean useFloatingLabel;
    Rect backgroundPadding = new Rect();

    public MaterialEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        // 打印attrs
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            Log.e("chris","attrsName==" + attrs.getAttributeName(i) + ",attrsValue==" + attrs.getAttributeValue(i));
        }

        // 获取attrs
         // 本质
//        TypedArray typedArray = context.obtainStyledAttributes(attrs, new int[]{R.attr.MaterialEditText_useFloatingLabel});
//        useFloatingLabel = typedArray.getBoolean(0, false);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MaterialEditText);
        useFloatingLabel = typedArray.getBoolean(R.styleable.MaterialEditText_MaterialEditText_useFloatingLabel, false);
        Log.e("chris","useFloatingLabel==" + useFloatingLabel);
        typedArray.recycle();

        paint.setTextSize(TEXT_SIZE);

         // 设置绘制输入框的上边距
        if (useFloatingLabel){
            setPadding(getPaddingLeft(), (int) (backgroundPadding.top+TEXT_SIZE + TEXT_MARGIN),getPaddingRight(),getPaddingBottom());
        }

         // 获取背景的padding
        getBackground().getPadding(backgroundPadding);

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (useFloatingLabel){
                    if (floatingLabelShown && TextUtils.isEmpty(s)){ // 文字从有到无
                        floatingLabelShown = false;
                        getAnimator().start();
                        // 设置绘制文字的间距
                        setPadding(getPaddingLeft(), backgroundPadding.top,getPaddingRight(),getPaddingBottom());
                    }else if (!floatingLabelShown && !TextUtils.isEmpty(s)){ // 文字从无到有
                        floatingLabelShown = true;
                        getAnimator().reverse();
                        // 设置绘制文字的间距
                        setPadding(getPaddingLeft(), (int) (backgroundPadding.top+TEXT_SIZE + TEXT_MARGIN),getPaddingRight(),getPaddingBottom());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setAlpha((int) (0xff * floatingLabelFraction)); // // 0xff 是 255，是透明度的满值

        float extraOffset = TEXT_ANIMATION_OFFSET * (1 - floatingLabelFraction);
        canvas.drawText(getHint().toString(),TEXT_HORIZONTAL_OFFSET,TEXT_VERTICAL_OFFSET + extraOffset,paint);
    }




    @NonNull
    private ObjectAnimator getAnimator() {
        if (animator == null){
            animator = ObjectAnimator.ofFloat(MaterialEditText.this, "floatingLabelFraction", 1, 0);
            animator.setDuration(1500);
        }
        return animator;
    }

    public float getFloatingLabelFraction() {
        return floatingLabelFraction;
    }

    public void setFloatingLabelFraction(float floatingLabelFraction) {
        this.floatingLabelFraction = floatingLabelFraction;
        invalidate();
    }


}
