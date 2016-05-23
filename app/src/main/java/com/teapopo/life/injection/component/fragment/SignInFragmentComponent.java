package com.teapopo.life.injection.component.fragment;

import com.teapopo.life.injection.module.fragment.SignInFragmentModule;
import com.teapopo.life.injection.scope.PerActivity;
import com.teapopo.life.view.fragment.User.SignInFragment;

import dagger.Subcomponent;

/**
 * Created by louiszgm on 2016/5/23.
 */
@PerActivity
@Subcomponent(modules = SignInFragmentModule.class)
public interface SignInFragmentComponent {
    void inject(SignInFragment fragment);
}
