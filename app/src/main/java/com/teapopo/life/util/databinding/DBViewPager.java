package com.teapopo.life.util.databinding;

import android.databinding.BindingAdapter;

import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.view.adapter.viewpager.ArticleInfoImageAdapter;
import com.teapopo.life.view.customView.HackyViewPager;
import com.teapopo.life.view.customView.RecyclerView.SuperRecyclerView;

import java.util.List;

import timber.log.Timber;

/**
 * Created by louiszgm-pc on 2016/6/19.
 */
public class DBViewPager {

    @BindingAdapter({"articleInfo"})
    public static void bindImage(HackyViewPager viewPager, List<String> data) {
        Timber.d("notifyDataSetChanged data");
        ((ArticleInfoImageAdapter)viewPager.getAdapter()).imageUrls.addAll(data);
        viewPager.notifyDataSetChanged();
    }
}
