package com.teapopo.life.injection.component;

import com.teapopo.life.injection.scope.PerActivity;
import com.teapopo.life.view.fragment.Home.RecommendArticleFragment;

import dagger.Subcomponent;

/**
 * Created by louiszgm on 2016/4/28.
 */
@Subcomponent
@PerActivity
public interface RecommendArticleFragmentComponent {

    void inject(RecommendArticleFragment recommendArticleFragment);
}
