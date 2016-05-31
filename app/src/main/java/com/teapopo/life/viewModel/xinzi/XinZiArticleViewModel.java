package com.teapopo.life.viewModel.xinzi;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Toast;

import com.teapopo.life.BR;
import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.model.article.Article;
import com.teapopo.life.model.article.categoryarticle.XinZiArticleModel;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.util.CustomToast;
import com.teapopo.life.view.adapter.recyclerview.XinZiArticleAdapter;
import com.teapopo.life.view.customView.RecyclerView.OnPageListener;
import com.teapopo.life.view.customView.RequestView;
import com.teapopo.life.viewModel.BaseRecyclerViewModel;

import java.util.List;

import timber.log.Timber;

/**
 * Created by louiszgm on 2016/5/31.
 */
public class XinZiArticleViewModel extends BaseRecyclerViewModel<BaseEntity> implements RequestView<ModelAction> {

    private XinZiArticleAdapter mAdapter;
    private XinZiArticleModel mModel;
    private Context mContext;
    public XinZiArticleViewModel(Context context, XinZiArticleModel model){
        mContext = context;
        mModel = model;
        mModel.setView(this);
        mAdapter = new XinZiArticleAdapter(mContext,getData());
        requestData();
    }

    @Override
    public void requestData() {
        super.requestData();
        mModel.getArticle("新滋");
    }

    public XinZiArticleAdapter getAdapter(){
        return mAdapter;
    }


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
            super.data.addAll(articles);
            notifyPropertyChanged(BR.data);
        }
    }

    @Override
    public void onRequestErroInfo(String erroinfo) {
        CustomToast.makeText(mContext,erroinfo, Toast.LENGTH_SHORT).show();
    }
}
