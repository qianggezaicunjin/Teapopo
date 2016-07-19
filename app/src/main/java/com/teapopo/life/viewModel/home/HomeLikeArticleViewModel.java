package com.teapopo.life.viewModel.home;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Toast;

import com.teapopo.life.BR;
import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.model.article.likearticle.HomeLikeArticle;
import com.teapopo.life.model.article.likearticle.HomeLikeArticleModel;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.util.CustomToast;
import com.teapopo.life.view.adapter.recyclerview.RecommendArticleAdapter;
import com.teapopo.life.view.customView.RecyclerView.OnPageListener;
import com.teapopo.life.view.customView.RequestView;
import com.teapopo.life.viewModel.BaseRecyclerViewModel;

import java.util.List;

import timber.log.Timber;

/**
 * Created by louiszgm on 2016/5/30.
 */
public class HomeLikeArticleViewModel extends BaseRecyclerViewModel  {

    private HomeLikeArticleModel mModel;

    public HomeLikeArticleViewModel(HomeLikeArticleModel model){
        mModel = model;
        mModel.setView(this);
    }

    @Override
    public void requestData() {
        super.requestData();
        mModel.getArticle(true);
    }


    @Override
    public void onRequestSuccess(ModelAction data) {
        Action action = data.action;
            if(action == Action.HomeLikeArticleModel_GetArticle){
                Timber.d("Action.HomeLikeArticleModel_GetArticle");
                List<HomeLikeArticle> list = (List<HomeLikeArticle>) data.t;
                super.data.addAll(list);
                notifyPropertyChanged(BR.data);
                //通知加载文章内容完毕，更新loading状态
                loading = false;
                notifyPropertyChanged(BR.loading);
            }

    }

}
