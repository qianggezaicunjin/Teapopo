package com.teapopo.life.view.customView;

import android.content.Context;
import android.graphics.PointF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import timber.log.Timber;

/**
 * Hacky fix for Issue #4 and
 * http://code.google.com/p/android/issues/detail?id=18990
 * 
 * ScaleGestureDetector seems to mess up the touch events, which means that
 * ViewGroups which make use of onInterceptTouchEvent throw a lot of
 * IllegalArgumentException: pointerIndex out of range.
 * 
 * There's not much I can do in my code for now, but we can mask the result by
 * just catching the problem and ignoring it.
 * 
 * @author Chris Banes
 */
public class HackyViewPager extends ViewPager {

	private static final String TAG = "HackyViewPager";

	/** 触摸时按下的点 **/
	PointF downP = new PointF();
	/** 触摸时当前的点 **/
	PointF curP = new PointF();
	private boolean mScrollable=true;
	private boolean mIsCostTheEvent = false;
	public HackyViewPager(Context context) {
		super(context);
	}

	public HackyViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}




	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		try {
			return super.onInterceptTouchEvent(ev);
		} catch (IllegalArgumentException e) {
			// 不理会
			Log.e(TAG, "hacky viewpager error1");
			return false;
		} catch (ArrayIndexOutOfBoundsException e) {
			// 不理会
			Log.e(TAG, "hacky viewpager error2");
			return false;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if(mIsCostTheEvent){
			//每次进行onTouch事件都记录当前的按下的坐标
			if(getChildCount()<=1)
			{
				return super.onTouchEvent(ev);
			}
			curP.x = ev.getX();
			curP.y = ev.getY();

			if(ev.getAction() == MotionEvent.ACTION_DOWN)
			{
				//记录按下时候的坐标
				//切记不可用 downP = curP ，这样在改变curP的时候，downP也会改变
				downP.x = ev.getX();
				downP.y = ev.getY();
				//此句代码是为了通知他的父ViewPager现在进行的是本控件的操作，不要对我的操作进行干扰
				getParent().requestDisallowInterceptTouchEvent(true);
			}

			if(ev.getAction() == MotionEvent.ACTION_MOVE){
				//此句代码是为了通知他的父ViewPager现在进行的是本控件的操作，不要对我的操作进行干扰
				getParent().requestDisallowInterceptTouchEvent(true);
			}

			if(ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_CANCEL){
				//在up时判断是否按下和松手的坐标为一个点
				//如果是一个点，将执行点击事件，这是我自己写的点击事件，而不是onclick
				getParent().requestDisallowInterceptTouchEvent(false);
				if(downP.x==curP.x && downP.y==curP.y){

					return true;
				}
			}
			super.onTouchEvent(ev); //注意这句不能 return super.onTouchEvent(arg0); 否则触发parent滑动
			return true;
		}else {
			if (!mScrollable) {
				return false;
			}
		}
		return super.onTouchEvent(ev);
	}

	public void  notifyDataSetChanged(){
		this.getAdapter().notifyDataSetChanged();
	}
	public boolean isScrollble() {
		return mScrollable;
	}

	/**
	 * 设置viewpager是否能手势滑动
	 * @param scrollble
	 */
	public void setScrollble(boolean scrollble) {
		this.mScrollable = scrollble;
	}

	public void setIsCostTheEvent(boolean isCostTheEvent){
		this.mIsCostTheEvent = isCostTheEvent;
	}
}
