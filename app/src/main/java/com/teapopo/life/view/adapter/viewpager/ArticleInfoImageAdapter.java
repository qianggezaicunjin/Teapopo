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
    List<String> imageUrls;
    List<View> mViews = new ArrayList<>();
    private Context mContext;

    public ArticleInfoImageAdapter(Context context,List<String> imageUrls){
        this.imageUrls = imageUrls;
        this.mContext = context;
    }
    @Override
    public int getCount() {
        if(imageUrls !=null){
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
       ItemViewpagerArticleinfoImageBinding binding = ItemViewpagerArticleinfoImageBinding.inflate(LayoutInflater.from(mContext));
        if(imageUrls.size()!=0){
            int position1 = position%imageUrls.size();
            Timber.d("图片的个数为:%d,位置为:%d",imageUrls.size(),position1);
            String url = imageUrls.get(position1);
            Timber.d("图片地址为:%s",url);
            binding.setImageurl(url);
            container.addView(binding.getRoot());
            mViews.add(binding.getRoot());
        }

        return binding.getRoot();
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Timber.d("articles的大小为:%d", imageUrls.size());
        if(imageUrls.size()!=0&&mViews.size()!=0){
            container.removeView(mViews.get(position% imageUrls.size()));
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
