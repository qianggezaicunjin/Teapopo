package com.teapopo.life.view.adapter.viewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.teapopo.life.R;

import java.util.List;

/**
 * Created by lhq on 2016/6/27.
 */

public class WelfareAdapter extends PagerAdapter {
    int[] ImageId= new int[]{R.drawable.background_splash, R.drawable.icon_home};//图片资源数组
    private Context mContext;
    private List<ImageView> maoist;
    public WelfareAdapter(Context context){
        mContext=context;
    }
    @Override
    public int getCount() {
        return ImageId.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return maoist.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(maoist.get(position));
    }
}

