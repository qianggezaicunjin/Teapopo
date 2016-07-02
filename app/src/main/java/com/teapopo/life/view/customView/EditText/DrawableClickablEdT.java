package com.teapopo.life.view.customView.EditText;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

/**
 * Created by louiszgm on 2016/7/2.
 */
public class DrawableClickablEdT extends EditText {
    public DrawableClickablEdT(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    private DrawableClickListener mdrawableClickListener;
    public static final int LeftDrawAble = 1;
    public static final int RightDrawAble = 2;

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
        if(event.getX()<left.getIntrinsicWidth()){
            mdrawableClickListener.drawableClick(LeftDrawAble);
            return true;
        }
        return super.onTouchEvent(event);
    }
}
