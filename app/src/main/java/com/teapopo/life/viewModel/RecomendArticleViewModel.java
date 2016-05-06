package com.teapopo.life.viewModel;

import android.content.Context;
import android.databinding.Bindable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;

import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.model.recommendarticle.RecommendArticleModel;
import com.teapopo.life.model.toparticle.TopArticle;
import com.teapopo.life.model.toparticle.TopArticleModel;
import com.teapopo.life.view.adapter.recyclerview.RecommendArticleAdapter;
import com.teapopo.life.view.adapter.viewpager.TabFragmentAdapter;
import com.teapopo.life.view.adapter.viewpager.TopArticleAdapter;
import com.teapopo.life.view.customView.RecyclerView.OnPageListener;
import com.teapopo.life.view.customView.RequestView;
import com.teapopo.life.BR;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by Administrator on 2016/4/8 0008.
 */
public class RecomendArticleViewModel extends BaseRecyclerViewModel<BaseEntity> implements RequestView<BaseEntity> {
    private Context mContext;

    private RecommendArticleAdapter mAdapter;//文章内容的adapter
    private TabFragmentAdapter tabFragmentAdapter;//顶部文章轮播viewpager的adapter
    private TopArticleAdapter topArticleAdapter;

    private RecommendArticleModel mRecommendArticleModel;
    private TopArticleModel mTopArticleModel;


    @Bindable
    public List<Fragment> fragments = new ArrayList<>();
    @Bindable
    public List<String> titles = new ArrayList<>();
    @Bindable
    public List<BaseEntity> articles = new ArrayList<>();
    @Inject
    public RecomendArticleViewModel(Context context, RecommendArticleModel recommendArticleModel, TopArticleModel topArticleModel){

        this.mContext = context;

        this.mRecommendArticleModel = recommendArticleModel;
        mRecommendArticleModel.setView(this);

        this.mTopArticleModel = topArticleModel;
        mTopArticleModel.setView(this);

        mAdapter = new RecommendArticleAdapter(mContext,getData());
//        tabFragmentAdapter = new TabFragmentAdapter(((AppCompatActivity)mContext).getSupportFragmentManager(),fragments,titles);
        topArticleAdapter = new TopArticleAdapter(mContext,articles);
        requestData();
        mTopArticleModel.getContens("index");
    }

    public TopArticleAdapter getTopArticleAdapter(){
        return topArticleAdapter;
    }
    public RecommendArticleAdapter getAdapter(){
        return mAdapter;
    }

    public TabFragmentAdapter getTabFragmentAdapter(){
        return tabFragmentAdapter;
    }

    public List<BaseEntity> getArticles(){
        return articles;
    }
    public void setArticles(List<BaseEntity> articles){
        this.articles = articles;
    }
    public List<Fragment> getFragments(){
        return fragments;
    }
    public void setFragments(List<Fragment> fragmentList){
        this.fragments = fragmentList;
    }
    public List<String> getTitles(){
        return titles;
    }
    public void setTitles(List<String> stringList){
        this.titles = stringList;
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
//            for(int i = 0;i<list.size();i++){
//                TopArticle topArticle = (TopArticle) list.get(i);
//               fragments.add(TopArticleFragment.newInstance(topArticle));
//                titles.add(topArticle.title);
//            }
//            notifyPropertyChanged(BR.fragments);
//            notifyPropertyChanged(BR.titles);
            articles.addAll(list);
            notifyPropertyChanged(BR.articles);
        }
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
                getData().clear();
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
