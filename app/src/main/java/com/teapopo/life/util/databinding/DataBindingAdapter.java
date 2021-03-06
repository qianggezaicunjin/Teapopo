package com.teapopo.life.util.databinding;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.teapopo.life.R;
import com.teapopo.life.data.remote.NetWorkService;
import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.model.imageselect.Image;
import com.teapopo.life.view.adapter.LBaseAdapter;
import com.teapopo.life.view.adapter.gridview.ImageAdapter;
import com.teapopo.life.view.customView.FlexBox.FlexBoxWithAdapter;
import com.teapopo.life.view.customView.HackyViewPager;
import com.teapopo.life.view.customView.HtmlTextView.HtmlTextView;
import com.teapopo.life.view.customView.ImageView.ImageSelectorImageView;
import com.teapopo.life.view.customView.TextView.DrawableClickAbleTextView;
import com.yancy.imageselector.utils.DeviceUtils;

import java.io.File;
import java.util.List;

import me.yokeyword.fragmentation.SupportActivity;
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
    @BindingAdapter({"data"})
    public static void setFlexBoxWithAdapterData(FlexBoxWithAdapter flexBoxWithAdapter, List<BaseEntity> data){
        Timber.d("setFlexBoxWithAdapterData  data大小为:%d",data.size());
        LBaseAdapter adapter = (LBaseAdapter) flexBoxWithAdapter.getAdapter();
        adapter.notifyDataSetChanged();
    }
    @BindingAdapter({"compoundDrawables"})
    public static void setTextViewDrawables(DrawableClickAbleTextView textView, String url) {
        Timber.d("setTextViewDrawables:%s",url);
        final int width;
        final int heigth;
        //如果传过来的参数时拼接好的图片地址，则直接使用，如果不是，则自行拼凑
        if(url != null){
            String tag = url.substring(0,4);
            //提取出url里面_width x height的宽高
            //为了设置正在加载图片和图片加载错误时候的占位图的大小
            String[] part1= url.split("_");
            String[] part2 = part1[1].split("x");
             width = Integer.parseInt(part2[0]);
            if(!tag.equals("http")){
                url = NetWorkService.IMAGE_ENDPOINT+url+NetWorkService.IMAGE_EXT;
                heigth = Integer.parseInt(part2[1]);
            }else {
                int size  = part2[1].length();
                String tag1 = part2[1].substring(0,size-4);
                Timber.d("tag1为:%s",tag1);
                heigth = Integer.parseInt(tag1);
            }
            Timber.d("要加载图片的大小为:%s %s",width,heigth);
            //加载图片
            Picasso.with(textView.getContext()).load(url).placeholder(R.drawable.default_picture).error(R.drawable.default_picture).into(textView);
        }

    }

    //ImageVie 设置网络图片
    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView iv, String imageUrl) {
        Timber.d("imageUrl:%s",imageUrl);
        if(iv instanceof ImageSelectorImageView){
            Timber.d("加载的图片路径为:%s",imageUrl);
                Picasso.with(iv.getContext())
                        .load(new File(imageUrl))
                        .placeholder(R.drawable.default_picture)
                        .config(Bitmap.Config.RGB_565)
                        .resize(300,300)
                        .centerCrop()
//                        .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                        .into(iv);
        }else {
            if(imageUrl!=null){
                //如果传过来的参数时拼接好的图片地址，则直接使用，如果不是，则自行拼凑
                String tag = imageUrl.substring(0,4);
                if(!tag.equals("http")){
                    imageUrl = NetWorkService.IMAGE_ENDPOINT+imageUrl+NetWorkService.IMAGE_EXT;
                }
            }
            Picasso.with(iv.getContext()).load(imageUrl).placeholder(R.drawable.default_picture).error(R.drawable.default_picture).into(iv);
        }
    }
    //SwipeRefreshLayout 设置loading状态
    @BindingAdapter({"isLoading"})
    public static void isLoading(SwipeRefreshLayout swipeRefreshLayout, boolean isLoading) {
        Timber.d("set SwipeRefreshLayout loading:%s",isLoading);
        swipeRefreshLayout.setRefreshing(isLoading);
    }

    //HackyViewPager 设置data 并通知数据集改变
    @BindingAdapter({"data"})
    public static void setViewPagerData(HackyViewPager viewPager, List<BaseEntity> data) {
        Timber.d("notifyDataSetChanged viewPager");
        viewPager.notifyDataSetChanged();
    }

    @BindingAdapter({"imageList"})
    public static void setGridViewData(GridView gridView,List<Image> data){
        ImageAdapter adapter = (ImageAdapter) gridView.getAdapter();
        adapter.notifyDataSetChanged();
    }
    @BindingAdapter({"position"})
    public static void setPage(HackyViewPager viewPager,int position){
        viewPager.setCurrentItem(position);
    }
}
