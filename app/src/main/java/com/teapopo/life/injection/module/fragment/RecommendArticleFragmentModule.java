package com.teapopo.life.injection.module.fragment;

import android.content.Context;

import com.teapopo.life.injection.scope.PerActivity;
import com.teapopo.life.model.category.CategoryModel;
import com.teapopo.life.model.recommendarticle.RecommendArticleModel;
import com.teapopo.life.model.toparticle.TopArticleModel;
import com.teapopo.life.viewModel.home.RecomendArticleViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by louiszgm-pc on 2016/5/2.
 */
@Module
public class RecommendArticleFragmentModule {
    @Provides
    @PerActivity
    RecomendArticleViewModel provideRecommendArticleViewModel(Context context,RecommendArticleModel recommendArticleModel,TopArticleModel topArticleModel,CategoryModel categoryModel){
        return new RecomendArticleViewModel(context,recommendArticleModel,topArticleModel,categoryModel);
    }

    @Provides
    @PerActivity
    RecommendArticleModel provideRecommendArticleModel(Context context){
        return new RecommendArticleModel(context);
    }
    @Provides
    @PerActivity
    TopArticleModel provideTopArticleModel(Context context){
        return new TopArticleModel(context);
    }
    @Provides
    @PerActivity
    CategoryModel provideCategoryModel(Context context){
        return new CategoryModel(context);
    }
}
