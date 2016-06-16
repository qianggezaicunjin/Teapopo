package com.teapopo.life.view.fragment.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.data.rx.RxBus;
import com.teapopo.life.databinding.FragmentRecommendarticleBinding;
import com.teapopo.life.databinding.ItemHomeHottagsBinding;
import com.teapopo.life.databinding.ItemRecyclerviewToparticleBinding;
import com.teapopo.life.injection.component.fragment.MainFragmentComponent;
import com.teapopo.life.injection.module.fragment.MainFragmentModule;
import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.model.Tag.Tag;
import com.teapopo.life.model.article.Article;
import com.teapopo.life.model.article.categoryarticle.CategoryArticle;
import com.teapopo.life.model.toparticle.TopArticle;
import com.teapopo.life.util.navigator.Navigator;
import com.teapopo.life.view.activity.ArticleDetailActivity;
import com.teapopo.life.view.activity.MainActivity;
import com.teapopo.life.view.adapter.recyclerview.HotTagsAdapter;
import com.teapopo.life.view.adapter.recyclerview.RecommendArticleAdapter;
import com.teapopo.life.view.adapter.recyclerview.base.BaseRecyclerViewAdapter;
import com.teapopo.life.view.adapter.viewpager.TopArticleAdapter;
import com.teapopo.life.view.customView.RecyclerView.OnPageListener;
import com.teapopo.life.view.fragment.BaseFragment;
import com.teapopo.life.viewModel.home.RecomendArticleViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by Administrator on 2016/4/7 0007.
 */
public class RecommendArticleFragment extends BaseFragment implements BaseRecyclerViewAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, OnPageListener {


    @Inject
    RecomendArticleViewModel mRecomendArticleViewModel;
    @Inject
    RxBus mRxBus;
    private MainFragmentComponent mComponent;
    private FragmentRecommendarticleBinding mBinding;//文章列表内容的binding对象
    private ItemRecyclerviewToparticleBinding toparticleBinding;
    private ItemHomeHottagsBinding hotTagsBinding;

    private RecommendArticleAdapter mAdapter;//文章内容的adapter
    private TopArticleAdapter topArticleAdapter;//顶部文章轮播viewpager的adapter
    private HotTagsAdapter hotTagsAdapter;

    private List<BaseEntity> articleList = new ArrayList<>();
    private List<BaseEntity> topArticleList = new ArrayList<>();
    private List<Tag> tagList = new ArrayList<>();

    public static RecommendArticleFragment newInstance() {
        return new RecommendArticleFragment();
    }

    @Override
    public void onCreateBinding() {
        if(getActivity() instanceof MainActivity){
            mComponent = ((MainActivity)getActivity()).getMainActivityComponent().mainFragmentComponent(new MainFragmentModule(this));
            mComponent.inject(this);
        }
    }
    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Timber.d("getContentView");
        mBinding = FragmentRecommendarticleBinding.inflate(inflater);
        toparticleBinding = ItemRecyclerviewToparticleBinding.inflate(inflater);
        hotTagsBinding = ItemHomeHottagsBinding.inflate(inflater);
        toparticleBinding.setRecommendArticleViewModel(mRecomendArticleViewModel);
        hotTagsBinding.setRecommendArticleViewModel(mRecomendArticleViewModel);
        mBinding.setRecommendArticleViewModel(mRecomendArticleViewModel);
        return mBinding.getRoot();
    }
    @Override
    public void setUpView() {
        setUpArticle();
        setUpTopArticle();
        setUpHotTags();

    }

    private void setUpHotTags() {

        hotTagsAdapter = new HotTagsAdapter(_mActivity,tagList);
        hotTagsBinding.rvCategory.setAdapter(hotTagsAdapter);
        hotTagsBinding.rvCategory.setOrientation(RecyclerView.HORIZONTAL);
        //请求热门标签的数据
        mRecomendArticleViewModel.getHotTags();
    }

    private void setUpArticle() {
        mAdapter = new RecommendArticleAdapter(_mActivity,articleList);
        mAdapter.setOnItemClickListener(this);
        mBinding.swipeRefreshWidget.setOnRefreshListener(this);
        mBinding.rvRecommendarticle.setOnPageListener(this);
        mBinding.rvRecommendarticle.setAdapter(mAdapter);
        mBinding.rvRecommendarticle.addHeader(toparticleBinding.getRoot());
        mBinding.rvRecommendarticle.addHeader(hotTagsBinding.getRoot());
        //请问文章数据
        mRecomendArticleViewModel.requestData();
    }

    private void setUpTopArticle() {
        topArticleAdapter = new TopArticleAdapter(_mActivity,topArticleList);
        toparticleBinding.viewpagerToparticle.setAdapter(topArticleAdapter);
        //请求头部文章的数据
        mRecomendArticleViewModel.getTopArticle();
    }

    public void refreshArticle(List<CategoryArticle> data){
        articleList.addAll(data);
        mBinding.rvRecommendarticle.notifyDataSetChanged();
    }
    public void refreshTopArticle(List<TopArticle> data){
        topArticleList.addAll(data);
        toparticleBinding.viewpagerToparticle.notifyDataSetChanged();
    }
    public void refreshHotTags(List<Tag> data){
        tagList.addAll(data);
        hotTagsBinding.rvCategory.notifyDataSetChanged();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onItemClick(View view, int position) {
        //recyclerview正在加载数据的时候,点击item不做任何跳转
        if(mBinding.swipeRefreshWidget.isRefreshing()){

        }else {
            Article article = (Article) articleList.get(position);
            //跳转到文章详情页
            Intent intent = ArticleDetailActivity.getStartIntent(_mActivity);
            intent.putExtra("articleId",article.articleId);
            Navigator.getInstance().start(_mActivity,intent);
        }
    }

    //下拉刷新监听
    @Override
    public void onRefresh() {
        Timber.d("onRefresh");
        articleList.clear();
        mRecomendArticleViewModel.requestData();
    }

    @Override
    public void onPage() {
        mRecomendArticleViewModel.requestData();
    }
}
