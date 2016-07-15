package com.teapopo.life.viewModel.publisharticle;

import android.databinding.Bindable;
import android.view.View;
import android.widget.Toast;

import com.teapopo.life.BR;
import com.teapopo.life.model.imageselect.Image;
import com.teapopo.life.model.imageselect.ImageConfig;
import com.teapopo.life.model.imageselect.ImageSelectModel;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.view.adapter.gridview.ImageAdapter;
import com.teapopo.life.view.adapter.recyclerview.base.BaseRecyclerViewAdapter;
import com.teapopo.life.viewModel.BaseRecyclerViewModel;
import com.teapopo.life.viewModel.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import timber.log.Timber;

/**
 * Created by louiszgm on 2016/7/14.
 */
public class ImageSelectViewModel extends BaseRecyclerViewModel {

    ImageSelectModel mModel;

    @Bindable
    public List<Image> imageList = new ArrayList<>();
    public void setImageList(List<Image> imageList){
        this.imageList = imageList;
    }
    public List<Image> imageSelectList = new ArrayList<>();
    public ImageSelectViewModel(ImageSelectModel model){
        mModel = model;
        mModel.setView(this);
    }

    public void getImageData(){
        mModel.getPhoneImageData();
    }

    public void selectImageFromGrid(Image image, ImageConfig config) {
        if (image != null) {
            if (config.isMutiSelect()) {
                doImageMultiSelect(image,config.getMaxSize());
            } else {
                doImageSingleSelect();
            }
        }
    }

    private void doImageMultiSelect(Image image,int maxsize) {
        if (data.contains(image)) {
            data.remove(image);
        } else {
            if (maxsize == data.size()) {
                setErroInfo("选择图片已超过上限!");
                return;
            }
            data.add(image);
        }
        notifyPropertyChanged(BR.data);
    }

    private void doImageSingleSelect() {

    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        Action action = data.action;
        if(action == Action.ImageSelectModel_GetImageData){
            List<Image> list = (List<Image>) data.t;
            imageList.addAll(list);
            Timber.d("图片有%d",imageList.size());
            notifyPropertyChanged(BR.imageList);
        }else if(action == Action.ImageSelectModel_GetFolderList){

        }
    }
}
