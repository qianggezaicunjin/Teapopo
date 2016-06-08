package com.teapopo.life.viewModel.home;

import android.content.Context;
import android.content.Intent;
import android.databinding.Bindable;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
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
import com.teapopo.life.viewModel.BaseRecyclerViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import me.yokeyword.fragmentation.SupportActivity;
import timber.log.Timber;

/**
 * Created by Administrator on 2016/4/8 0008.
 */
public class RecomendArticleViewModel extends BaseRecyclerViewModel<BaseEntity> implements RequestView<ModelAction>,BaseRecyclerViewAdapter.OnItemClickListener {
    private FragmentRecommendarticleBinding mBinding;
    private Context mContext;
    private boolean isFirstTime = true;
    private RecommendArticleAdapter mAdapter;//文章内容的adapter
    private TopArticleAdapter topArticleAdapter;//顶部文章轮播viewpager的adapter
    private HotTagsAdapter hotTagsAdapter;
    private RecommendArticleModel mRecommendArticleModel;


    @Bindable
    public List<BaseEntity> articles = new ArrayList<>();
    @Bindable
    public List<BaseEntity> tags = new ArrayList<>();


    public RecomendArticleViewModel(Context context, RecommendArticleModel recommendArticleModel,ViewDataBinding binding){
        this.mBinding = (FragmentRecommendarticleBinding) binding;
        this.mContext = context;
        
        this.mRecommendArticleModel = recommendArticleModel;
        mRecommendArticleModel.setView(this);

        mAdapter = new RecommendArticleAdapter(mContext,getData());
        topArticleAdapter = new TopArticleAdapter(mContext,articles);
        hotTagsAdapter = new HotTagsAdapter(mContext, tags);

        mAdapter.setOnItemClickListener(this);
        mRecommendArticleModel.getTopArticle("index");
        mRecommendArticleModel.getHotTags();
        requestData();

    }

    public TopArticleAdapter getTopArticleAdapter(){
        return topArticleAdapter;
    }
    public RecommendArticleAdapter getAdapter(){
        return mAdapter;
    }
    public HotTagsAdapter getHotTagsAdapter(){
        return hotTagsAdapter;
    }

    @Override
    public void requestData() {
        super.requestData();
        mRecommendArticleModel.getArticle("发现");
    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        synchronized (this){
            //如果数据源是头部轮播文章
            if (data.action == Action.RecommendArticleModel_GetTopArticle){
                Timber.d("onRequestSuccess  TopArticle");
                List<TopArticle> list = (List<TopArticle>)data.t;
                articles.addAll(list);
                notifyPropertyChanged(BR.articles);
            }
            //如果数据源是热门标签
            if(data.action == Action.RecommendArticleModel_GetHotTags){
                Timber.d("onRequestSuccess  Tag");
                tags.addAll((List<Tag>)data.t);
                notifyPropertyChanged(BR.tags);

            }
            //如果数据源是文章列表内容
            if(data.action == Action.CategoryArticleModel_GetArticle){
                Timber.d("onRequestSuccess  RecommendArticle");
                super.data.addAll((List<CategoryArticle>)data.t);
                Timber.d("文章长度为:%d",super.data.size());
                notifyPropertyChanged(BR.data);
                //通知加载文章内容完毕，更新loading状态
                loading = false;
                notifyPropertyChanged(BR.loading);
                //在首次加载文章完成时，添加头部的布局，只添加一次
                if(isFirstTime){
                    addHeader();
                    isFirstTime = false;
                }
            }
        }
    }

    private void addHeader() {
        //头部视图的初始化
        ItemRecyclerviewToparticleBinding toparticleBinding = ItemRecyclerviewToparticleBinding.inflate(LayoutInflater.from(mContext));
        ItemHomeHottagsBinding hotTagsBinding = ItemHomeHottagsBinding.inflate(LayoutInflater.from(mContext));
        hotTagsBinding.rvCategory.setOrientation(RecyclerView.HORIZONTAL);
        toparticleBinding.setRecommendArticleViewModel(this);
        hotTagsBinding.setRecommendArticleViewModel(this);
        mBinding.rvRecommendarticle.addHeader(toparticleBinding.getRoot());
        mBinding.rvRecommendarticle.addHeader(hotTagsBinding.getRoot());
        toparticleBinding.viewpagerToparticle.setCurrentItem(Integer.MAX_VALUE/2);
    }

    @Override
    public void onRequestErroInfo(String erroinfo) {
        CustomToast.makeText(mContext,erroinfo, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestFinished() {

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

    @Override
    public void onItemClick(View view, int position) {
        //recyclerview正在加载数据的时候,点击item不做任何跳转
        if(loading){

        }else {
            Article article = (Article) data.get(position);
            //跳转到文章详情页
            Intent intent = ArticleDetailActivity.getStartIntent(mContext);
            intent.putExtra("articleId",article.articleId);
            Navigator.getInstance().start(mContext,intent);
        }
    }
}
