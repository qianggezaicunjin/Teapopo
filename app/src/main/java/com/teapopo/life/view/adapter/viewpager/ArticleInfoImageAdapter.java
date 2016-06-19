package com.teapopo.life.view.adapter.viewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.databinding.ItemViewpagerArticleinfoImageBinding;
import com.teapopo.life.databinding.ItemViewpagerToparticleBinding;
import com.teapopo.life.model.toparticle.TopArticle;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Created by louiszgm on 2016/6/2.
 */
public class ArticleInfoImageAdapter extends PagerAdapter {
    public List<String> imageUrls;
    List<View> mViews = new ArrayList<>();
    private Context mContext;

    public ArticleInfoImageAdapter(Context context,List<String> imageUrls){
        this.imageUrls = imageUrls;
        this.mContext = context;
    }
    @Override
    public int getCount() {
        return imageUrls.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
       ItemViewpagerArticleinfoImageBinding binding = ItemViewpagerArticleinfoImageBinding.inflate(LayoutInflater.from(mContext));
        String url = imageUrls.get(position);
        binding.setImageurl(url);
        container.addView(binding.getRoot());
        mViews.add(binding.getRoot());
        return binding.getRoot();
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if(imageUrls.size()!=0&&mViews.size()!=0){
            container.removeView(mViews.get(position));
        }else {
            container.removeView((View) object);
        }
    }

    private int mChildCount = 0;

    @Override
    public void notifyDataSetChanged() {
        mChildCount = getCount();
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object)   {
        if ( mChildCount > 0) {
            mChildCount --;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);

    }
}
