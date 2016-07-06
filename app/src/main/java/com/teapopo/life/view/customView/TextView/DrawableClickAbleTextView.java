package com.teapopo.life.view.customView.TextView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

import com.teapopo.life.view.customView.Interface.DrawableClickListener;

/**
 * Created by louiszgm on 2016/6/30.
 */
public class DrawableClickAbleTextView extends TextView {
    private DrawableClickListener mdrawableClickListener;
    public static final int LeftDrawAble = 1;
    public static final int RightDrawAble = 2;
    public DrawableClickAbleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }
    public DrawableClickAbleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setDrawableClickListener(DrawableClickListener listener){
        mdrawableClickListener = listener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Drawable left = getCompoundDrawables()[0];
        Drawable right = getCompoundDrawables()[2];
        if(event.getAction() == MotionEvent.ACTION_UP){
            return super.onTouchEvent(event);
        }
        if(left==null||right==null){
            return super.onTouchEvent(event);
        }
        if(event.getX()>getWidth()-getPaddingRight()-right.getIntrinsicWidth()){
            mdrawableClickListener.drawableClick(RightDrawAble);
            return true;
        }
        if(event.getX()<left.getIntrinsicWidth()){
            mdrawableClickListener.drawableClick(LeftDrawAble);
            return true;
        }
        return super.onTouchEvent(event);
    }
}
