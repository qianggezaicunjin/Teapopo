package com.teapopo.life.view.customView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by louiszgm on 2016/6/30.
 */
public class ClickAbleTextView extends TextView {
    private DrawableClickListener mdrawableClickListener;
    public static final int LeftDrawAble = 1;
    public static final int RightDrawAble = 2;
    public ClickAbleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }
    public ClickAbleTextView(Context context,  AttributeSet attrs) {
        super(context, attrs);
    }
    public void setDrawableClickListener(DrawableClickListener listener){
        mdrawableClickListener = listener;
    }
    public static interface DrawableClickListener {
        /**
         * textview的drawable被点击了
         * @param type LeftDrawAble  RightDrawAble
         */
        void drawableClick(int type);
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
        if(event.getX()<getWidth()-getPaddingLeft()-left.getIntrinsicWidth()){
            mdrawableClickListener.drawableClick(LeftDrawAble);
            return true;
        }
        return super.onTouchEvent(event);
    }
}
