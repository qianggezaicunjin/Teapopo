package com.teapopo.life.viewModel.publisharticle;

import android.databinding.BaseObservable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.teapopo.life.model.article.publisharticle.PublishArticleData;
import com.teapopo.life.model.article.publisharticle.PublishArticleModel;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.view.customView.RequestView;
import com.teapopo.life.view.fragment.PublishArticle.PublishArticleFragment;
import com.teapopo.life.viewModel.BaseViewModel;

import java.util.List;

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

        Action action = data.action;
        if(action == Action.PublishArticleModel_PublishArticle){
            showMaskingView(false);
            setErroInfo("发布文章成功");
        }
    }

    public void publishArticle(PublishArticleData publishArticleData) {
        showMaskingView(true);
        if (TextUtils.isEmpty(publishArticleData.content)) {
            setErroInfo("输入的文章内容不为空");
        }
        mModel.publishArticle(publishArticleData.title, publishArticleData.content, castImagesListToArray(publishArticleData.images), getTagStringDivideByDot(publishArticleData.tags));
    }

    private String[] castImagesListToArray(List<String> imageIds){
        return imageIds.toArray(new String[imageIds.size()]);
    }
    /**
     * 将标签用  , 分隔开
     * @param tagArray
     * @return
     */
    private String getTagStringDivideByDot(String[] tagArray){
        StringBuilder tags = new StringBuilder();
        //将tag用逗号分隔
        for (int i = 0; i < tagArray.length; i++) {
            if (i == 0) {
                tags.append(tagArray[i]);
            } else {
                tags.append(",").append(tagArray[i]);
            }
        }
        return tags.toString();
    }
}
