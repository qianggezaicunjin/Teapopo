package com.teapopo.life.viewModel.publisharticle;

import com.teapopo.life.model.imageselect.ImageSelectModel;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.viewModel.BaseViewModel;

/**
 * Created by louiszgm on 2016/7/14.
 */
public class ImageSelectViewModel extends BaseViewModel {

    ImageSelectModel mModel;
    public ImageSelectViewModel(ImageSelectModel model){
        mModel = model;
        mModel.setView(this);
    }

    public void getImageData(){
        mModel.getPhoneImageData();
    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        Action action = data.action;
        if(action == Action.ImageSelectModel_GetImageData){

        }else if(action == Action.ImageSelectModel_GetFolderList){

        }
    }
}
