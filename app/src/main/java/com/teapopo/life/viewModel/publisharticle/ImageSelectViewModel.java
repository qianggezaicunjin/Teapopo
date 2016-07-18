package com.teapopo.life.viewModel.publisharticle;

import android.app.Activity;
import android.databinding.Bindable;
import android.os.Parcelable;
import android.view.View;
import android.widget.Toast;

import com.teapopo.life.BR;
import com.teapopo.life.injection.component.ComponentHolder;
import com.teapopo.life.model.event.OpenCameraEvent;
import com.teapopo.life.model.imageselect.Folder;
import com.teapopo.life.model.imageselect.Image;
import com.teapopo.life.model.imageselect.ImageConfig;
import com.teapopo.life.model.imageselect.ImageSelectModel;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.view.adapter.gridview.ImageAdapter;
import com.teapopo.life.view.adapter.recyclerview.base.BaseRecyclerViewAdapter;
import com.teapopo.life.view.fragment.PublishArticle.FolderListFragment;
import com.teapopo.life.view.fragment.PublishArticle.ImageSelectorFragment;
import com.teapopo.life.viewModel.BaseRecyclerViewModel;
import com.teapopo.life.viewModel.BaseViewModel;

import java.io.File;
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
    @Bindable
    public String leftSelectImage = data.size()+" / 9";
    public ImageSelectViewModel(ImageSelectModel model){
        mModel = model;
        mModel.setView(this);
    }

    public void getImageData(){
        mModel.getPhoneImageData();
    }

    public void getFolderList() {
        mModel.getFolderList();
    }

    public void selectImageFromGrid(int position, ImageConfig config) {
        if (config.isShowCamera()) {
            if (position == 0) {
                ComponentHolder.getAppComponent().rxbus().post(new OpenCameraEvent());
            } else {
                Image image = imageList.get(position-1);
                doImageMultiSelect(image,config.getMaxSize());
            }
        } else {
            // 正常操作
            Image image = imageList.get(position);
            doImageMultiSelect(image,config.getMaxSize());
        }
    }

    private void doImageMultiSelect(Image image,int maxsize) {
        if (data.contains(image)) {
            data.remove(image);
            image.isSelected = false;
            ComponentHolder.getAppComponent().rxbus().post(image);
        } else {
            if (maxsize == data.size()) {
                setErroInfo("选择图片已超过上限!");
                return;
            }
            data.add(image);
            image.isSelected = true;
            ComponentHolder.getAppComponent().rxbus().post(image);
        }
        leftSelectImage = data.size()+" / 9";

        notifyPropertyChanged(BR.leftSelectImage);
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
            ArrayList<Parcelable> folderArrayList = (ArrayList<Parcelable>) data.t;
            Timber.d("图片文件夹有%d",folderArrayList.size());
            navToFragment(FolderListFragment.newInstance(folderArrayList));
        }
    }


    public void handleTakePhotoDone(int requestCode, int resultCode, File tempFile) {
        if (requestCode == ImageSelectorFragment.REQUEST_CAMERA) {
            if (resultCode == Activity.RESULT_OK) {
                if (tempFile != null) {

                }
            } else {
                if (tempFile != null && tempFile.exists()) {
                    tempFile.delete();
                }
            }
        }
    }


}
