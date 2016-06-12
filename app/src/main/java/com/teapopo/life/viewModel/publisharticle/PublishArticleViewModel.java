package com.teapopo.life.viewModel.publisharticle;

import android.content.Context;
import android.databinding.BaseObservable;
import android.view.View;

import com.teapopo.life.R;
import com.teapopo.life.injection.component.ComponentHolder;
import com.teapopo.life.model.article.publisharticle.PublishArticleModel;
import com.teapopo.life.model.event.PickPhotoListResult;

import java.util.List;

import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * Created by louiszgm on 2016/6/8.
 */
public class PublishArticleViewModel extends BaseObservable {

    private Context mContext;
    private PublishArticleModel mModel;
    public PublishArticleViewModel(Context context, PublishArticleModel model){
        mContext = context;
        mModel = model;
    }

    public View.OnClickListener getOnClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btn_addpublishphoto:
                        addPublishPhoto();
                        break;
                    case R.id.img_publishimage:

                        break;
                }

            }
        };
    }

    private void addPublishPhoto() {
        GalleryFinal.openGalleryMuti(1, GalleryFinal.copyGlobalFuncationConfig(), new GalleryFinal.OnHanlderResultCallback() {
            @Override
            public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                PickPhotoListResult pickPhotoListResult = new PickPhotoListResult();
                pickPhotoListResult.photoInfoList = resultList;
                ComponentHolder.getAppComponent().rxbus().post(pickPhotoListResult);
            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {

            }
        });
    }
}
