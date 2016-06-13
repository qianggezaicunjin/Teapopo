package com.teapopo.life.viewModel.publisharticle;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ViewDataBinding;
import android.view.View;

import com.teapopo.life.R;
import com.teapopo.life.databinding.FragmentPublisharticleBinding;
import com.teapopo.life.injection.component.ComponentHolder;
import com.teapopo.life.model.article.publisharticle.PublishArticleModel;
import com.teapopo.life.model.event.PickPhotoListResult;
import com.teapopo.life.util.DataUtils;

import java.lang.ref.WeakReference;
import java.util.List;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import timber.log.Timber;

/**
 * Created by louiszgm on 2016/6/8.
 */
public class PublishArticleViewModel extends BaseObservable {

    private Context mContext;
    private PublishArticleModel mModel;

    private FragmentPublisharticleBinding mBinding;
    public PublishArticleViewModel(Context context, PublishArticleModel model, ViewDataBinding binding){
        mContext = context;
        mBinding = (FragmentPublisharticleBinding) binding;
        mModel = model;
    }

    public View.OnClickListener getOnClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.linearlayout_addpublishphoto:
                        addPublishPhoto();
                        break;
                    case R.id.btn_publishArticle:
                        publishArticle();
                        break;
                }

            }
        };
    }

    private void publishArticle() {
        //获取文章标题
        //获取文章内容
        //获取要上传的图片
        //选择封面
        //获取该文章的标签
    }

    private void addPublishPhoto() {
        WeakReference<CoreConfig> coreConfigWeakReference = new WeakReference<CoreConfig>(GalleryFinal.getCoreConfig());
        GalleryFinal.openGalleryMuti(1, coreConfigWeakReference.get().getFunctionConfig(), new GalleryFinal.OnHanlderResultCallback() {
            @Override
            public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                PickPhotoListResult pickPhotoListResult = new PickPhotoListResult();
                pickPhotoListResult.photoInfoList = resultList;
                ComponentHolder.getAppComponent().rxbus().post(pickPhotoListResult);

                for(PhotoInfo photoInfo:resultList){
                    Timber.d("图片路径为:%s",photoInfo.getPhotoPath());
                    Timber.d("图片名称为:%s",photoInfo.getImageName());
                }
            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {

            }
        });
    }
}
