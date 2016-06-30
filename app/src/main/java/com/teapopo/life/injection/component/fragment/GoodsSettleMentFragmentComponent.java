package com.teapopo.life.injection.component.fragment;

import com.teapopo.life.injection.module.fragment.ArticleDetailFragmentModule;
import com.teapopo.life.injection.module.fragment.GoodsSettleMentFragmentModule;
import com.teapopo.life.injection.scope.PerActivity;
import com.teapopo.life.view.fragment.welfare.GoodsSettleMentFragment;

import dagger.Subcomponent;

/**
 * Created by louiszgm on 2016/6/30.
 */
@PerActivity
@Subcomponent(modules = GoodsSettleMentFragmentModule.class)
public interface GoodsSettleMentFragmentComponent {
    void inject(GoodsSettleMentFragment fragment);
}
