package com.teapopo.life.util.databinding;

import android.databinding.BindingAdapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;

import com.teapopo.life.MyApplication;
import com.teapopo.life.data.rx.RxBus;
import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.model.event.AddHeaderEvent;
import com.teapopo.life.view.customView.HackyViewPager;
import com.teapopo.life.view.customView.RecyclerView.SuperRecyclerView;

import java.util.List;

import timber.log.Timber;

/**
 * Created by louiszgm on 2016/5/4.
 */
public class DataBindingAdapter {
    @BindingAdapter({"isLoading"})
    public static void isLoading(SwipeRefreshLayout swipeRefreshLayout, boolean isLoading) {
        swipeRefreshLayout.setRefreshing(isLoading);
    }
    @BindingAdapter({"onRefreshListener"})
    public static void setsetOnRefreshListener(SwipeRefreshLayout swipeRefreshLayout, SwipeRefreshLayout.OnRefreshListener listener){
        swipeRefreshLayout.setOnRefreshListener(listener);
    }
    @BindingAdapter({"adapter"})
    public static void setViewPagerAdapter(HackyViewPager viewPager, PagerAdapter adapter) {
        Timber.d("setViewPagerAdapter");
        viewPager.setAdapter(adapter);

    }
    @BindingAdapter({"fragments","titles"})
    public static void setViewPagerData(HackyViewPager viewPager, List<Fragment> fragments,List<String> titles) {
        Timber.d("setViewPagerData");
        viewPager.notifyDataSetChanged();
        if(fragments.size()>0){
            RxBus rxBus = MyApplication.get(viewPager.getContext()).getComponent().rxbus();
            rxBus.postEvent(new AddHeaderEvent());
        }
    }
    @BindingAdapter({"articles"})
    public static void setViewPagerData(HackyViewPager viewPager, List<BaseEntity> articles) {
        Timber.d("setViewPagerData");
        viewPager.notifyDataSetChanged();
        if(articles.size()>0){
            RxBus rxBus = MyApplication.get(viewPager.getContext()).getComponent().rxbus();
            rxBus.postEvent(new AddHeaderEvent());
        }
    }
}
