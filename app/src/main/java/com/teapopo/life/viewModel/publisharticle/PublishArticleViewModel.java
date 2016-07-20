package com.teapopo.life.viewModel.publisharticle;

import android.databinding.BaseObservable;
import android.support.v4.app.Fragment;

import com.teapopo.life.model.article.publisharticle.PublishArticleModel;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.view.customView.RequestView;
import com.teapopo.life.view.fragment.PublishArticle.PublishArticleFragment;
import com.teapopo.life.viewModel.BaseViewModel;

/**
 * Created by louiszgm on 2016/6/8.
 */
public class PublishArticleViewModel extends BaseViewModel {

    private PublishArticleModel mModel;



    public PublishArticleViewModel(PublishArticleModel model){
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

        }
    }

    //发布文章
//    public void publishArticle(String title, String content, List<PhotoInfo> mPhotoInfoList, String[] tagArray) {
//        StringBuilder tags = new StringBuilder();
//        //将tag用逗号分隔
//        for (int i = 0; i < tagArray.length; i++) {
//            if (i == 0) {
//                tags.append(tagArray[i]);
//            } else {
//                tags.append(",").append(tagArray[i]);
//            }
//        }
//        if (TextUtils.isEmpty(content)) {
//            mView.toastErroMsg("输入的文章内容不为空");
//        }
//        mModel.publishArticle(title, content, mPhotoInfoList, tags.toString());
//    }
}
