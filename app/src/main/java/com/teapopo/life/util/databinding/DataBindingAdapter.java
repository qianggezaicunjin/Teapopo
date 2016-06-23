package com.teapopo.life.util.databinding;

import android.databinding.BindingAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;
import com.teapopo.life.data.remote.NetWorkService;
import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.view.customView.HackyViewPager;
import com.teapopo.life.view.customView.HtmlTextView.HtmlTextView;

import java.util.List;

import timber.log.Timber;

/**
 * Created by louiszgm on 2016/5/4.
 */
public class DataBindingAdapter {
    @BindingAdapter({"html_temp"})
    public static void setHtmlText(HtmlTextView textView,String html){
        Timber.d("加载的文章信息为:%s",html);
        if (html==null){
            textView.setHtmlFromString("正在加载....",false);
        }else {
            textView.setHtmlFromString(html,false);
        }

    }
    @BindingAdapter({"adapter","data"})
    public static void setNineImageAdapter(NineGridImageView imageView, NineGridImageViewAdapter adapter,List data){
        imageView.setAdapter(adapter);
        imageView.setImagesData(data);
    }

    @BindingAdapter({"onMenuItemClick"})
    public static void onMenuItemClick(Toolbar toolbar, Toolbar.OnMenuItemClickListener listener) {
        toolbar.setOnMenuItemClickListener(listener);
    }

    //ImageVie 设置网络图片
    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView iv, String imageUrl) {
        if(imageUrl!=null){
            //如果传过来的参数时拼接好的图片地址，则直接使用，如果不是，则自行拼凑
            String tag = imageUrl.substring(0,4);
            if(tag.equals("http")){
                Picasso.with(iv.getContext()).load(imageUrl).into(iv);
            }else {
                String imagurl = NetWorkService.IMAGE_ENDPOINT+imageUrl+NetWorkService.IMAGE_EXT;
                Picasso.with(iv.getContext()).load(imagurl).into(iv);
            }
        }else {
            Picasso.with(iv.getContext()).load(imageUrl).into(iv);
        }
    }
    //SwipeRefreshLayout 设置loading状态
    @BindingAdapter({"isLoading"})
    public static void isLoading(SwipeRefreshLayout swipeRefreshLayout, boolean isLoading) {
        swipeRefreshLayout.setRefreshing(isLoading);
    }

    //HackyViewPager 设置data 并通知数据集改变
    @BindingAdapter({"data"})
    public static void setViewPagerData(HackyViewPager viewPager, List<BaseEntity> data) {
        Timber.d("notifyDataSetChanged viewPager");
        viewPager.notifyDataSetChanged();
    }
}
