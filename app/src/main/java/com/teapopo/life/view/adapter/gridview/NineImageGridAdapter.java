package com.teapopo.life.view.adapter.gridview;

import android.content.Context;
import android.widget.ImageView;

import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;
import com.teapopo.life.data.remote.NetWorkService;

import java.util.List;

import timber.log.Timber;

/**
 * Created by louiszgm on 2016/5/26.
 */
public class NineImageGridAdapter<T> extends NineGridImageViewAdapter {


    @Override
    protected void onDisplayImage(Context context, ImageView imageView, Object o) {
        String image = (String) o;
        String url= NetWorkService.IMAGE_ENDPOINT+image+"_300x300"+NetWorkService.IMAGE_EXT;
        Timber.d("加载图片的地址为:%s",url);
        ImageLoader.getInstance().displayImage(url, imageView);
//        Picasso.with(context).load(url).into(imageView);
    }
}
