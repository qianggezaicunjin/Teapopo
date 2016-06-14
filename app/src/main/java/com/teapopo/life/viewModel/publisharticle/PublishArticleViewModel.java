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
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.util.DataUtils;
import com.teapopo.life.view.customView.RequestView;

import java.lang.ref.WeakReference;
import java.util.List;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import timber.log.Timber;

/**
 * Created by louiszgm on 2016/6/8.
 */
public class PublishArticleViewModel extends BaseObservable implements RequestView<ModelAction> {

    private Context mContext;
    public PublishArticleModel mModel;

    private FragmentPublisharticleBinding mBinding;

    private List<PhotoInfo> mPhotoInfo;
    public PublishArticleViewModel(Context context, PublishArticleModel model, ViewDataBinding binding){
        mContext = context;
        mBinding = (FragmentPublisharticleBinding) binding;
        mModel = model;
        mModel.setView(this);
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
        mBinding.btnPublishArticle.setProgress(50);
        //获取文章标题
        String title = "帖子标题啊";
        //获取文章内容
        String content = "发布帖子啊";
        //获取要上传的图片
        //获取该文章的标签
        String tags = "tags1,tags2,tags3";
        mModel.publishArticle(title,content,mPhotoInfo,tags);
    }

    private void addPublishPhoto() {
        //弱引用 ,防止内存泄漏
        WeakReference<CoreConfig> coreConfigWeakReference = new WeakReference<CoreConfig>(GalleryFinal.getCoreConfig());
        WeakReference<GalleryFinal.OnHanlderResultCallback> callback = new WeakReference<GalleryFinal.OnHanlderResultCallback>(new GalleryFinal.OnHanlderResultCallback() {
            @Override
            public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                mPhotoInfo = resultList;
                PickPhotoListResult pickPhotoListResult = new PickPhotoListResult();
                pickPhotoListResult.photoInfoList = resultList;
                ComponentHolder.getAppComponent().rxbus().post(pickPhotoListResult);
            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {

            }
        });
        GalleryFinal.openGalleryMuti(1, coreConfigWeakReference.get().getFunctionConfig(),callback.get());
    }

    @Override
    public void onRequestFinished() {

    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        int countUpload = 0;
        Action action = data.action;
        if(action == Action.PublishArticleModel_PublishWithoutImage){
            mBinding.btnPublishArticle.setProgress(100);
        }else if(action == Action.PublishArticleModel_PublishWithImage){
            countUpload += 1;
            Timber.d("成功上传的图片个数:%d",countUpload);
        }
    }

    @Override
    public void onRequestErroInfo(String erroinfo) {

    }
}
