package com.example.taglayout;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 2018/12/7.
 */
public class TagLayout extends ViewGroup {

     // 保存每个孩子的矩形框
    List<Rect> childrenBounds = new ArrayList<>();

    public TagLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
         // 自己测量，不调用父类的测量方法
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int size = MeasureSpec.getSize(widthMeasureSpec);
        int mode = MeasureSpec.getMode(widthMeasureSpec);

        int widthUsed = 0;
        int heightUsed = 0;
        int lineMaxHeight = 0; // 单行最大高度
        int lineWidthUsed = 0; // 单行已使用宽度

         // 测量每个孩子
        for (int i = 0; i < getChildCount(); i++) {
             // 测量孩子
            View child = getChildAt(i);
            // 两个0表示父控件给最大空间，至于孩子的矩形框是自己通过 childBound.set 算出来的
            measureChildWithMargins(child,widthMeasureSpec,0,heightMeasureSpec,heightUsed);

             // 折行
            if (mode != MeasureSpec.UNSPECIFIED && size < lineWidthUsed + child.getMeasuredWidth()){  // UNSPECIFIED 的值是 0
                 // 重新测量 子View
                lineWidthUsed = 0; // 单行已使用宽度
                heightUsed += lineMaxHeight; // 总使用高度，先保存才能 lineMaxHeight = 0;
                lineMaxHeight = 0; // 单行已使用高度
                measureChildWithMargins(child,widthMeasureSpec,0,heightMeasureSpec,heightUsed);
            }

             // 初始化每个孩子的边框
            Rect childBound;
            if (i >= childrenBounds.size()){
                childBound = new Rect();
                childrenBounds.add(childBound);
            }else{
                childBound = childrenBounds.get(i);
            }
            // 保存每个孩子的矩形框
            childBound.set(lineWidthUsed,heightUsed,lineWidthUsed+child.getMeasuredWidth(),heightUsed + child.getMeasuredHeight());

            // 计算单行宽度
            lineWidthUsed += child.getMeasuredWidth();
            // 计算单行最大高度
            lineMaxHeight = Math.max(lineMaxHeight,child.getMeasuredHeight());
            // 计算子View占用的宽高
            widthUsed = Math.max(widthUsed, lineWidthUsed);
        }

         // 计算父控件测量好的自己的宽高
        int measureWidth = widthUsed;
        int measureHeight = heightUsed + lineMaxHeight;
         // 保存测量结果
        setMeasuredDimension(measureWidth,measureHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
         // 自己布局，不调用父类的方法
//        super.onLayout(changed, left, top, right, bottom);

         // 根据测量的值布局每个孩子
        for (int i = 0; i < getChildCount(); i++) {
            Rect rect = childrenBounds.get(i);
            getChildAt(i).layout(rect.left,rect.top,rect.right,rect.bottom);
        }
    }

    /*
     * measureChildWithMargins 方法需要获取到待 Margin 的 LayoutParams
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }
    //    @Override
//    protected LayoutParams generateLayoutParams(LayoutParams p) {
//        return super.generateLayoutParams(p);
//    }

}
