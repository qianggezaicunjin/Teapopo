package com.teapopo.life.viewModel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.teapopo.life.R;
import com.teapopo.life.databinding.HeaderLogoBinding;
import com.teapopo.life.databinding.ItemRecyclerviewHeaderBinding;
import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.model.recommendarticle.ArticleAuthorInfo;
import com.teapopo.life.model.recommendarticle.RecommendArticle;
import com.teapopo.life.model.recommendarticle.RecommendArticleModel;
import com.teapopo.life.model.recommendarticle.RecommendData;
import com.teapopo.life.util.DataUtils;
import com.teapopo.life.view.adapter.recyclerview.RecommendArticleAdapter;
import com.teapopo.life.view.adapter.viewpager.TabFragmentAdapter;
import com.teapopo.life.view.customView.RecyclerView.OnPageListener;
import com.teapopo.life.view.customView.RecyclerView.OnTopPageListener;
import com.teapopo.life.view.customView.RequestView;
import com.teapopo.life.view.fragment.Home.TopArticleFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by Administrator on 2016/4/8 0008.
 */
public class RecomendArticleViewModel extends BaseRecyclerViewModel<BaseEntity> implements RequestView<BaseEntity> {
    private Context mContext;

    private RecommendArticleAdapter mAdapter;

    private RecommendArticleModel mRecommendArticleModel;

    @Inject
    public RecomendArticleViewModel(Context context,RecommendArticleModel recommendArticleModel){
        this.mContext = context;
        this.mRecommendArticleModel = recommendArticleModel;
        mRecommendArticleModel.setView(this);

    }

    public RecommendArticleAdapter getAdapter(){
        mAdapter = new RecommendArticleAdapter(mContext,getData());
        requestData();
        return mAdapter;
    }

    @Override
    public void requestData() {
        super.requestData();
        mRecommendArticleModel.getContents();
    }

    /**
     * Recyclerview 添加头布局
     * @return
     */
    public View getHeaders(){
        List<View> headers = new ArrayList<View>();
        ItemRecyclerviewHeaderBinding binding=ItemRecyclerviewHeaderBinding.inflate(LayoutInflater.from(mContext));
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new TopArticleFragment());
        List<String> titles = new ArrayList<>();
        titles.add("头部轮播文章");
        TabFragmentAdapter adapter = new TabFragmentAdapter(((AppCompatActivity)mContext).getSupportFragmentManager(),fragments,titles);
        binding.viewpagerToparticle.setAdapter(adapter);
        binding.indicatorCircleViewpager.setViewPager(binding.viewpagerToparticle);

        return binding.getRoot();
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
