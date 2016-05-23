package com.teapopo.life.util.databinding;

import android.databinding.BindingAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.teapopo.life.data.remote.NetWorkService;
import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.view.customView.HackyViewPager;

import java.util.List;

import timber.log.Timber;

/**
 * Created by louiszgm on 2016/5/4.
 */
public class DataBindingAdapter {

    @BindingAdapter({"onMenuItemClick"})
    public static void onMenuItemClick(Toolbar toolbar, Toolbar.OnMenuItemClickListener listener) {
        toolbar.setOnMenuItemClickListener(listener);
    }
    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView iv, String imageUrl) {
        if(imageUrl!=null){
            //如果传过来的参数时拼接好的图片地址，则直接使用，如果不是，则自行拼凑
            String tag = imageUrl.substring(0,4);
            if(tag.equals("http")){
                ImageLoader.getInstance().displayImage(imageUrl,iv);
            }else {
                String imagurl = NetWorkService.IMAGE_ENDPOINT+imageUrl+NetWorkService.IMAGE_EXT;
                ImageLoader.getInstance().displayImage(imagurl,iv);
            }
        }else {
            ImageLoader.getInstance().displayImage(imageUrl,iv);
        }
    }
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
    @BindingAdapter({"viewpagerdata"})
    public static void setViewPagerData(HackyViewPager viewPager, List<BaseEntity> articles) {
        Timber.d("notifyDataSetChanged viewPager");
        viewPager.notifyDataSetChanged();
//        if(articles.size()>0){
//            RxBus rxBus = ComponentHolder.getAppComponent().rxbus();
//            rxBus.postEvent(new AddHeaderEvent());
//        }
    }
}
