package com.teapopo.life.injection.component.fragment;

import com.teapopo.life.injection.module.fragment.SignUpUserInfoFragmentModule;
import com.teapopo.life.injection.scope.PerActivity;
import com.teapopo.life.view.fragment.User.SignUpUserInfoFragment;


import dagger.Subcomponent;

/**
 * Created by louiszgm on 2016/5/21.
 */
@Subcomponent(modules = SignUpUserInfoFragmentModule.class)
@PerActivity
public interface SignUpUserInfoFragmentComponent {
    void inject(SignUpUserInfoFragment fragment);
}
