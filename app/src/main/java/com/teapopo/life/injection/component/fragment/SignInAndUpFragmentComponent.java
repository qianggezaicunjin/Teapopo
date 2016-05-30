package com.teapopo.life.injection.component.fragment;

import com.teapopo.life.injection.module.fragment.SignInAndUpFragmentModule;
import com.teapopo.life.injection.scope.PerActivity;
import com.teapopo.life.view.fragment.User.SignInFragment;
import com.teapopo.life.view.fragment.User.SignUpUserInfoFragment;
import com.teapopo.life.view.fragment.User.SignUpVertifyCodeFragment;

import dagger.Module;
import dagger.Subcomponent;

/**
 * Created by louiszgm on 2016/5/30.
 */
@Subcomponent(modules = SignInAndUpFragmentModule.class)
@PerActivity
public interface SignInAndUpFragmentComponent {
    void inject(SignInFragment fragment);

    void inject(SignUpUserInfoFragment fragment);

    void inject(SignUpVertifyCodeFragment fragment);
    
}
