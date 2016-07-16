package com.teapopo.life.model.imageselect;

import android.content.Context;


import com.teapopo.life.view.customView.ImageView.RecyclableImageView;

import java.io.Serializable;

/**
 * ImageLoader
 * Created by Yancy on 2015/12/6.
 */
public interface ImageLoader extends Serializable {
    void displayImage(Context context, String path, RecyclableImageView imageView, int width, int height);
}