package com.teapopo.life.viewModel.xinzi;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Toast;

import com.teapopo.life.BR;
import com.teapopo.life.injection.component.ComponentHolder;
import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.model.article.Article;
import com.teapopo.life.model.article.categoryarticle.XinZiArticleModel;
import com.teapopo.life.model.toparticle.TopArticle;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.util.CustomToast;
import com.teapopo.life.view.adapter.recyclerview.XinZiArticleAdapter;
import com.teapopo.life.view.customView.RecyclerView.OnPageListener;
import com.teapopo.life.view.customView.RequestView;
import com.teapopo.life.view.fragment.xinzi.XinZiFragment;
import com.teapopo.life.viewModel.BaseRecyclerViewModel;

import java.util.List;

import timber.log.Timber;

/**
 * Created by louiszgm on 2016/5/31.
 */
public class XinZiArticleViewModel extends BaseRecyclerViewModel<BaseEntity> implements RequestView<ModelAction> {

    private XinZiArticleModel mModel;
    private XinZiFragment mView;
    public XinZiArticleViewModel(Fragment view, XinZiArticleModel model){
        mView = (XinZiFragment) view;
        mModel = model;
        mModel.setView(this);
        requestData();
    }

    @Override
    public void requestData() {
        super.requestData();
        mModel.getArticle("新滋");
    }

    public void getTopArticle(){
        mModel.getTopArticle("xinzi");
    }
    public OnPageListener getOnPageListener() {
        return new OnPageListener() {
            @Override
            public void onPage() {
                requestData();
            }
        };
    }
    @Override
    public void onRequestFinished() {

    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        Action action = data.action;
        if(action == Action.CategoryArticleModel_GetArticle){
            List<Article> articles = (List<Article>) data.t;
            mView.refreshArticleContent(articles);
        }else if(action == Action.XinZiArticleModel_GetTopArticle){
            List<TopArticle> topArticles = (List<TopArticle>) data.t;
            mView.refreshTopArticle(topArticles);
        }
    }

    @Override
    public void onRequestErroInfo(String erroinfo) {
        mView.handleErroMsg(erroinfo);
    }
}
