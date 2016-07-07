package com.teapopo.life.view.customView.TextView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.teapopo.life.view.customView.Interface.DrawableClickListener;

import timber.log.Timber;

/**
 * Created by louiszgm on 2016/6/30.
 */
public class DrawableClickAbleTextView extends TextView implements Target{
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

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        Timber.d("加载图片成功");
        Drawable drawable = new BitmapDrawable(null,bitmap);
        drawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
        setCompoundDrawables(drawable,null,null,null);

    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
        Timber.d("加载图片失败");
//        errorDrawable.setBounds(0,0,width,heigth);
        setCompoundDrawables(errorDrawable,null,null,null);
    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {
        Timber.d("准备加载图片");
//        placeHolderDrawable.setBounds(0,0,width,heigth);
        setCompoundDrawables(placeHolderDrawable,null,null,null);
    }
}
