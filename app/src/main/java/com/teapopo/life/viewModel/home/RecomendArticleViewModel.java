package com.teapopo.life.viewModel.home;

import android.content.Context;
import android.databinding.Bindable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.teapopo.life.databinding.FragmentRecommendarticleBinding;
import com.teapopo.life.databinding.ItemHomeCategoryBinding;
import com.teapopo.life.databinding.ItemRecyclerviewToparticleBinding;
import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.model.category.Category;
import com.teapopo.life.model.recommendarticle.RecommendArticle;
import com.teapopo.life.model.recommendarticle.RecommendArticleModel;
import com.teapopo.life.model.toparticle.TopArticle;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.util.CustomToast;
import com.teapopo.life.view.adapter.recyclerview.CategoryAdapter;
import com.teapopo.life.view.adapter.recyclerview.RecommendArticleAdapter;
import com.teapopo.life.view.adapter.viewpager.TopArticleAdapter;
import com.teapopo.life.view.customView.RecyclerView.OnPageListener;
import com.teapopo.life.view.customView.RequestView;
import com.teapopo.life.BR;
import com.teapopo.life.viewModel.BaseRecyclerViewModel;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Created by Administrator on 2016/4/8 0008.
 */
public class RecomendArticleViewModel extends BaseRecyclerViewModel<BaseEntity> implements RequestView<ModelAction> {
    private FragmentRecommendarticleBinding mBinding;
    private Context mContext;
    private boolean isFirstTime = true;
    private RecommendArticleAdapter mAdapter;//文章内容的adapter
    private TopArticleAdapter topArticleAdapter;//顶部文章轮播viewpager的adapter
    private CategoryAdapter categoryAdapter;
    private RecommendArticleModel mRecommendArticleModel;


    @Bindable
    public List<BaseEntity> articles = new ArrayList<>();
    @Bindable
    public List<BaseEntity> categories = new ArrayList<>();


    public RecomendArticleViewModel(Context context, RecommendArticleModel recommendArticleModel,FragmentRecommendarticleBinding binding){
        this.mBinding = binding;
        this.mContext = context;
        
        this.mRecommendArticleModel = recommendArticleModel;
        mRecommendArticleModel.setView(this);

        mAdapter = new RecommendArticleAdapter(mContext,getData());
        topArticleAdapter = new TopArticleAdapter(mContext,articles);
        categoryAdapter = new CategoryAdapter(mContext,categories);

        mRecommendArticleModel.getTopArticle("index");
        mRecommendArticleModel.getCategory();
        requestData();

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
    public void onRequestSuccess(ModelAction data) {
        super.onRequestSuccess(data);
        synchronized (this){
            //如果数据源是头部轮播文章
            if (data.action == Action.RecommendArticleModel_GetTopArticle){
                Timber.d("onRequestSuccess  TopArticle");
                List<TopArticle> list = (List<TopArticle>)data.t;
                articles.addAll(list);
                notifyPropertyChanged(BR.articles);
            }
            //如果数据源是分类标签
            if(data.action == Action.RecommendArticleModel_GetCategory){
                Timber.d("onRequestSuccess  Category");
                categories.addAll((List<Category>)data.t);
                notifyPropertyChanged(BR.categories);

            }
            //如果数据源是文章列表内容
            if(data.action == Action.RecommendArticleModel_GetContents){
                Timber.d("onRequestSuccess  RecommendArticle");
                super.data.addAll((List<RecommendArticle>)data.t);
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
        ItemHomeCategoryBinding categoryBinding = ItemHomeCategoryBinding.inflate(LayoutInflater.from(mContext));
        categoryBinding.rvCategory.setOrientation(RecyclerView.HORIZONTAL);
        toparticleBinding.setRecommendArticleViewModel(this);
        categoryBinding.setRecommendArticleViewModel(this);
        mBinding.rvRecommendarticle.addHeader(toparticleBinding.getRoot());
        mBinding.rvRecommendarticle.addHeader(categoryBinding.getRoot());
        toparticleBinding.viewpagerToparticle.setCurrentItem(Integer.MAX_VALUE/2);
        //填充数据
//        mRecommendArticleModel.getTopArticle("index");
//        mRecommendArticleModel.getCategory();
    }

    @Override
    public void onRequestErroInfo(String erroinfo) {
        CustomToast.makeText(mContext,erroinfo, Toast.LENGTH_SHORT).show();
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
