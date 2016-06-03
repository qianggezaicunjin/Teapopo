package com.teapopo.life.view.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import timber.log.Timber;

/**
 * Created by louiszgm on 2016/6/3.
 */
public class MultiLineViewGroup extends ViewGroup {

    private final static String TAG = "MyViewGroup";

    private final static int VIEW_MARGIN = 2;

    public MultiLineViewGroup(Context context) {
        super(context);

    }
    public MultiLineViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        Log.d(TAG, "widthMeasureSpec = " + MeasureSpec.getSize(widthMeasureSpec)
                + " heightMeasureSpec" + MeasureSpec.getMode(heightMeasureSpec));
        int childswidth = 0; //子view的总宽度
        int childwidth = 0; //子view单个的宽度
        for (int index = 0; index < getChildCount(); index++) {

            final View child = getChildAt(index);
                childwidth = child.getMeasuredWidth();
            // measure

            child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
            childswidth+=child.getMeasuredWidth();
        }
        //计算一共有几行
        int row = 1;
        int parentwidth = MeasureSpec.getSize(widthMeasureSpec);
        if(childswidth>parentwidth){
            if(childswidth%parentwidth==0){
                row = childswidth/parentwidth;
            }else {
                row = childswidth/parentwidth+1;
            }
        }
        int top = getTop()-childwidth;
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(row*childwidth+top,MeasureSpec.EXACTLY));

    }

    @Override
    protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {

        Log.d(TAG, "changed = " + arg0 + " left = " + arg1 + " top = " + arg2
                + " right = " + arg3 + " botom = " + arg4);

        final int count = getChildCount();

        int row = 0;// which row lay you view relative to parent

        int lengthX = arg1; // right position of child relative to parent

        int lengthY = arg2; // bottom position of child relative to parent

        for (int i = 0; i < count; i++) {

            final View child = this.getChildAt(i);

            int width = child.getMeasuredWidth();

            int height = child.getMeasuredHeight();

            lengthX += width + VIEW_MARGIN;

            lengthY = row * (height + VIEW_MARGIN) + VIEW_MARGIN + height+arg2;

            // if it can't drawing on a same line , skip to next line

            if (lengthX > arg3) {

                lengthX = width + VIEW_MARGIN + arg1;

                row++;

                lengthY = row * (height + VIEW_MARGIN) + VIEW_MARGIN + height
                        + arg2;

            }

            child.layout(lengthX - width, lengthY - height-height, lengthX, lengthY-height);

        }

    }

}
