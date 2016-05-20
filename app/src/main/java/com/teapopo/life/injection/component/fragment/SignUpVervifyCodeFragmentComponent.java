package com.teapopo.life.injection.component.fragment;

import com.teapopo.life.injection.module.fragment.SignUpVertifyCodeFragmentModule;
import com.teapopo.life.injection.scope.PerActivity;
import com.teapopo.life.view.fragment.User.SignUpVertifyCodeFragment;

import dagger.Provides;
import dagger.Subcomponent;

/**
 * Created by louiszgm on 2016/5/20.
 */
@PerActivity
@Subcomponent(modules = SignUpVertifyCodeFragmentModule.class)
public interface SignUpVervifyCodeFragmentComponent {
    void inject(SignUpVertifyCodeFragment fragment);
}
