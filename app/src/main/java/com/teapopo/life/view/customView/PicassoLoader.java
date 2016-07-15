package com.teapopo.life.view.customView;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.teapopo.life.R;
import com.yancy.imageselector.ImageLoader;
import com.yancy.imageselector.customview.*;
import com.yancy.imageselector.customview.RecyclableImageView;

import java.io.File;

import timber.log.Timber;

/**
 * Created by louiszgm on 2016/7/14.
 */
public class PicassoLoader implements ImageLoader {

    @Override
    public void displayImage(Context context, String path, RecyclableImageView imageView, int width, int height) {
        Picasso.with(context)
                .load(new File(path))
                .placeholder(R.drawable.default_picture)
                .config(Bitmap.Config.RGB_565)
                .resize(width, height)
                .centerInside()
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .into(imageView);
    }
}
