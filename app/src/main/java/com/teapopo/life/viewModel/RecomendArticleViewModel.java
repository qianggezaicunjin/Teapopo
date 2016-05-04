package com.teapopo.life.viewModel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.support.v4.widget.SwipeRefreshLayout;

import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.model.recommendarticle.ArticleAuthorInfo;
import com.teapopo.life.model.recommendarticle.RecommendArticle;
import com.teapopo.life.model.recommendarticle.RecommendArticleModel;
import com.teapopo.life.model.recommendarticle.RecommendData;
import com.teapopo.life.util.DataUtils;
import com.teapopo.life.view.adapter.recyclerview.RecommendArticleAdapter;
import com.teapopo.life.view.customView.RecyclerView.OnPageListener;
import com.teapopo.life.view.customView.RecyclerView.OnTopPageListener;
import com.teapopo.life.view.customView.RequestView;

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
        mAdapter = new RecommendArticleAdapter(context,getData());
        requestData();
    }

    public RecommendArticleAdapter getAdapter(){
        return mAdapter;
    }

    @Override
    public void requestData() {
        super.requestData();
        mRecommendArticleModel.getContents();
    }

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
    public OnPageListener getOnPageListener() {
        return new OnPageListener() {
            @Override
            public void onPage() {
                requestData();
            }
        };
    }
}
