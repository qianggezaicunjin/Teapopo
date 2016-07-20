package com.teapopo.life.viewModel.publisharticle;

import android.databinding.Bindable;

import com.teapopo.life.BR;
import com.teapopo.life.model.imageselect.Image;
import com.teapopo.life.viewModel.BaseViewModel;

import timber.log.Timber;

/**
 * Created by louiszgm on 2016/7/15.
 */
public class ItemSelectImageViewModel extends BaseViewModel {

    @Bindable
    public Image image;

    public void changeImageState(Image clickImage){
        Timber.d("changeImageState");
        if(image.path.equals(clickImage.path)){
            image.isSelected = !image.isSelected;
            notifyPropertyChanged(BR.image);
        }
    }
}
