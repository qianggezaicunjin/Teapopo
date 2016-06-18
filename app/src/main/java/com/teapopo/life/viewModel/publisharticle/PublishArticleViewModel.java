package com.teapopo.life.viewModel.publisharticle;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ViewDataBinding;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.teapopo.life.R;
import com.teapopo.life.databinding.FragmentPublisharticleBinding;
import com.teapopo.life.injection.component.ComponentHolder;
import com.teapopo.life.model.article.publisharticle.PublishArticleModel;
import com.teapopo.life.model.event.PickPhotoListResult;
import com.teapopo.life.util.BitmapUtils;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.util.DataUtils;
import com.teapopo.life.view.customView.MultiLineViewGroup;
import com.teapopo.life.view.customView.RequestView;
import com.teapopo.life.view.fragment.PublishArticle.PublishArticleFragment;

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

    private PublishArticleModel mModel;

    private PublishArticleFragment mView;

    public PublishArticleViewModel(Fragment view,PublishArticleModel model){
        mView = (PublishArticleFragment) view;
        mModel = model;
        mModel.setView(this);
    }


    @Override
    public void onRequestFinished() {

    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        int countUpload = 0;
        Action action = data.action;
        if(action == Action.PublishArticleModel_PublishWithoutImage){

        }else if(action == Action.PublishArticleModel_PublishWithImage){
            Timber.d("成功上传的图片个数:%d",countUpload);
        }
    }

    @Override
    public void onRequestErroInfo(String erroinfo) {

    }

    public void publishArticle(String title, String content, List<PhotoInfo> mPhotoInfoList, String[] tagArray) {
        StringBuilder tags = new StringBuilder();
        //将tag用逗号分隔
        for (int i = 0; i < tagArray.length; i++) {
            if (i == 0) {
                tags.append(tagArray[i]);
            } else {
                tags.append(",").append(tagArray[i]);
            }
        }
        if (TextUtils.isEmpty(content)) {
            mView.toastErroMsg("输入的文章内容不为空");
        }
        mModel.publishArticle(title, content, mPhotoInfoList, tags.toString());
    }
}
