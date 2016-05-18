package com.teapopo.life.viewModel.home;

import android.content.Context;
import android.databinding.Bindable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;

import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.model.category.Category;
import com.teapopo.life.model.category.CategoryModel;
import com.teapopo.life.model.recommendarticle.RecommendArticleModel;
import com.teapopo.life.model.toparticle.TopArticle;
import com.teapopo.life.model.toparticle.TopArticleModel;
import com.teapopo.life.view.adapter.recyclerview.CategoryAdapter;
import com.teapopo.life.view.adapter.recyclerview.RecommendArticleAdapter;
import com.teapopo.life.view.adapter.viewpager.TabFragmentAdapter;
import com.teapopo.life.view.adapter.viewpager.TopArticleAdapter;
import com.teapopo.life.view.customView.RecyclerView.OnPageListener;
import com.teapopo.life.view.customView.RequestView;
import com.teapopo.life.BR;
import com.teapopo.life.viewModel.BaseRecyclerViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by Administrator on 2016/4/8 0008.
 */
public class RecomendArticleViewModel extends BaseRecyclerViewModel<BaseEntity> implements RequestView<List<BaseEntity>> {
    private Context mContext;

    private RecommendArticleAdapter mAdapter;//文章内容的adapter
    private TopArticleAdapter topArticleAdapter;//顶部文章轮播viewpager的adapter
    private CategoryAdapter categoryAdapter;
    private RecommendArticleModel mRecommendArticleModel;
    private TopArticleModel mTopArticleModel;
    private CategoryModel mCategoryModel;

    @Bindable
    public List<BaseEntity> articles = new ArrayList<>();
    @Bindable
    public List<BaseEntity> categories = new ArrayList<>();


    public RecomendArticleViewModel(Context context, RecommendArticleModel recommendArticleModel, TopArticleModel topArticleModel,CategoryModel categoryModel){

        this.mContext = context;

        this.mRecommendArticleModel = recommendArticleModel;
        mRecommendArticleModel.setView(this);

        this.mTopArticleModel = topArticleModel;
        mTopArticleModel.setView(this);

        this.mCategoryModel = categoryModel;
        mCategoryModel.setView(this);

        mAdapter = new RecommendArticleAdapter(mContext,getData());
        topArticleAdapter = new TopArticleAdapter(mContext,articles);
        categoryAdapter = new CategoryAdapter(mContext,categories);

        requestData();
        mTopArticleModel.getContens("index");
        categoryModel.getContents();
    }

    public TopArticleAdapter getTopArticleAdapter(){
        return topArticleAdapter;
    }
    public RecommendArticleAdapter getAdapter(){
        return mAdapter;
    }
    public CategoryAdapter getCategoryAdapter(){
        return categoryAdapter;
    }

    @Override
    public void requestData() {
        super.requestData();
        mRecommendArticleModel.getContents();
    }

    @Override
    public void onRequestSuccess(List<BaseEntity> list) {
        super.onRequestSuccess(list);
        //如果数据源是头部轮播文章
        if (list.get(0) instanceof TopArticle){
            Timber.d("onRequestSuccess  TopArticle");
            articles.addAll(list);
            notifyPropertyChanged(BR.articles);
        }
        //如果数据源是分类标签
        if(list.get(0) instanceof Category){
            Timber.d("onRequestSuccess  Category");
            categories.addAll(list);
            notifyPropertyChanged(BR.categories);
        }
    }

    @Override
    public void onRequestErroInfo(String erroinfo) {

    }

    /**
     * 首页面的下拉刷新监听器
     * @return
     */
    public SwipeRefreshLayout.OnRefreshListener getOnRefreshListener(){
        return new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Timber.d("onRefresh");
                data.clear();
                requestData();
            }
        };
    }

    /**
     * 首页面的翻页监听器
     * @return
     */
    public OnPageListener getOnPageListener() {
        return new OnPageListener() {
            @Override
            public void onPage() {
                requestData();
            }
        };
    }
}
