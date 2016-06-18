package com.teapopo.life.viewModel.home;

import android.content.Context;
import android.content.Intent;
import android.databinding.Bindable;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.teapopo.life.databinding.FragmentRecommendarticleBinding;
import com.teapopo.life.databinding.ItemHomeHottagsBinding;
import com.teapopo.life.databinding.ItemRecyclerviewToparticleBinding;
import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.model.Tag.Tag;
import com.teapopo.life.model.article.Article;
import com.teapopo.life.model.article.categoryarticle.CategoryArticle;
import com.teapopo.life.model.article.categoryarticle.RecommendArticleModel;
import com.teapopo.life.model.toparticle.TopArticle;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.util.CustomToast;
import com.teapopo.life.util.navigator.Navigator;
import com.teapopo.life.view.activity.ArticleDetailActivity;
import com.teapopo.life.view.adapter.recyclerview.base.BaseRecyclerViewAdapter;
import com.teapopo.life.view.adapter.recyclerview.HotTagsAdapter;
import com.teapopo.life.view.adapter.recyclerview.RecommendArticleAdapter;
import com.teapopo.life.view.adapter.viewpager.TopArticleAdapter;
import com.teapopo.life.view.customView.RecyclerView.OnPageListener;
import com.teapopo.life.view.customView.RequestView;
import com.teapopo.life.BR;
import com.teapopo.life.view.fragment.ArticleInfoFragment;
import com.teapopo.life.view.fragment.Home.RecommendArticleFragment;
import com.teapopo.life.viewModel.BaseRecyclerViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import me.yokeyword.fragmentation.SupportActivity;
import timber.log.Timber;

/**
 * Created by Administrator on 2016/4/8 0008.
 */
public class RecomendArticleViewModel extends BaseRecyclerViewModel<BaseEntity> implements RequestView<ModelAction> {

    private RecommendArticleModel mRecommendArticleModel;
    private RecommendArticleFragment mView;


    public RecomendArticleViewModel(Fragment view, RecommendArticleModel recommendArticleModel){
       mView = (RecommendArticleFragment) view;
        this.mRecommendArticleModel = recommendArticleModel;
        mRecommendArticleModel.setView(this);
    }


    @Override
    public void requestData() {
        super.requestData();
        mRecommendArticleModel.getArticle("发现");
    }
    public void getTopArticle(){
        mRecommendArticleModel.getTopArticle("index");
    }
    public void getHotTags(){
        mRecommendArticleModel.getHotTags();
    }
    @Override
    public void onRequestSuccess(ModelAction data) {
        synchronized (this){
            //如果数据源是头部轮播文章
            if (data.action == Action.RecommendArticleModel_GetTopArticle){
                Timber.d("onRequestSuccess  TopArticle");
                List<TopArticle> list = (List<TopArticle>)data.t;
                mView.refreshTopArticle(list);
            }
            //如果数据源是热门标签
            if(data.action == Action.RecommendArticleModel_GetHotTags){
                Timber.d("onRequestSuccess  Tag");
                List<Tag> list = (List<Tag>) data.t;
                mView.refreshHotTags(list);
            }
            //如果数据源是文章列表内容
            if(data.action == Action.CategoryArticleModel_GetArticle){
                Timber.d("onRequestSuccess  RecommendArticle");
               List<CategoryArticle> list = (List<CategoryArticle>) data.t;
                mView.refreshArticle(list);
                //加载文章数据完成
                loading = false;
                notifyPropertyChanged(BR.loading);
                mView.refreshWhenLoadingDone();
            }
        }
    }



    @Override
    public void onRequestErroInfo(String erroinfo) {
        Timber.e(erroinfo);
    }

    @Override
    public void onRequestFinished() {

    }


}
