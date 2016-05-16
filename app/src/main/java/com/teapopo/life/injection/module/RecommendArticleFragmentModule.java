package com.teapopo.life.injection.module;

import android.content.Context;

import com.teapopo.life.injection.scope.PerActivity;
import com.teapopo.life.model.category.CategoryModel;
import com.teapopo.life.model.recommendarticle.RecommendArticleModel;
import com.teapopo.life.model.toparticle.TopArticleModel;
import com.teapopo.life.view.adapter.recyclerview.RecommendArticleAdapter;
import com.teapopo.life.viewModel.RecomendArticleViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by louiszgm-pc on 2016/5/2.
 */
@Module
public class RecommendArticleFragmentModule {
    private Context mContext;

    public RecommendArticleFragmentModule(Context context){
        this.mContext = context;
    }


    @Provides
    @PerActivity
    RecomendArticleViewModel provideRecommendArticleViewModel(RecommendArticleModel recommendArticleModel,TopArticleModel topArticleModel,CategoryModel categoryModel){
        return new RecomendArticleViewModel(mContext,recommendArticleModel,topArticleModel,categoryModel);
    }

    @Provides
    @PerActivity
    RecommendArticleModel provideRecommendArticleModel(){
        return new RecommendArticleModel(mContext);
    }
    @Provides
    @PerActivity
    TopArticleModel provideTopArticleModel(){
        return new TopArticleModel(mContext);
    }
    @Provides
    @PerActivity
    CategoryModel provideCategoryModel(){
        return new CategoryModel(mContext);
    }
}
