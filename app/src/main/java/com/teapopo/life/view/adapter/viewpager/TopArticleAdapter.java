package com.teapopo.life.view.adapter.viewpager;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.databinding.ItemViewpagerToparticleBinding;
import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.model.toparticle.TopArticle;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Created by louiszgm-pc on 2016/5/6.
 */
public class TopArticleAdapter extends PagerAdapter {

    List<BaseEntity> articles;
    List<View> mViews = new ArrayList<>();
    private Context mContext;

    public TopArticleAdapter(Context context,List<BaseEntity> topArticles){
        this.articles = topArticles;
        this.mContext = context;
    }
    @Override
    public int getCount() {
        if(articles!=null){
            return Integer.MAX_VALUE;
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ItemViewpagerToparticleBinding binding = ItemViewpagerToparticleBinding.inflate(LayoutInflater.from(mContext));
        if(articles.size()!=0){
            TopArticle topArticle = (TopArticle) articles.get(position%articles.size());
            binding.setTopArticle(topArticle);
            container.addView(binding.getRoot());
            mViews.add(binding.getRoot());
        }

        return binding.getRoot();
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Timber.d("articles的大小为:%d",articles.size());
        if(articles.size()!=0&&mViews.size()!=0){
            container.removeView(mViews.get(position%articles.size()));
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
