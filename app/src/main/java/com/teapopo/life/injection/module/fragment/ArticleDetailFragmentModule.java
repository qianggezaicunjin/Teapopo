package com.teapopo.life.injection.module.fragment;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.v4.app.Fragment;
import android.view.View;

import com.teapopo.life.injection.scope.PerActivity;
import com.teapopo.life.model.articleinfo.ArticleInfoModel;
import com.teapopo.life.viewModel.articleinfo.ArticleInfoViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by louiszgm on 2016/6/2.
 */
@Module
public class ArticleDetailFragmentModule {
    private Fragment mView;
    public ArticleDetailFragmentModule(Fragment fragment){
        mView = fragment;
    }
    //for ArticleInfoFragment
    @Provides
    @PerActivity
    ArticleInfoViewModel provideArticleInfoViewModel( ArticleInfoModel model){
        return new ArticleInfoViewModel(mView,model);
    }
    @Provides
    @PerActivity
    ArticleInfoModel provideArticleInfoModel(Context context){
        return  new ArticleInfoModel(context);
    }
}
