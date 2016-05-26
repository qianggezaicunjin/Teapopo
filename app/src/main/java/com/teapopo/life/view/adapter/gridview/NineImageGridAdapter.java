package com.teapopo.life.view.adapter.gridview;

import android.content.Context;
import android.widget.ImageView;

import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.teapopo.life.data.remote.NetWorkService;

import java.util.List;

import timber.log.Timber;

/**
 * Created by louiszgm on 2016/5/26.
 */
public class NineImageGridAdapter<T> extends NineGridImageViewAdapter {


    @Override
    protected void onDisplayImage(Context context, ImageView imageView, Object o) {
        String url= NetWorkService.IMAGE_ENDPOINT+o+NetWorkService.IMAGE_EXT;

        ImageLoader.getInstance().displayImage(url, imageView);
    }
}
