package com.teapopo.life.injection.component.fragment;

import com.teapopo.life.injection.module.fragment.UserFragmentModule;
import com.teapopo.life.injection.scope.PerActivity;
import com.teapopo.life.view.fragment.User.UserFragment;

import dagger.Subcomponent;

/**
 * Created by louiszgm on 2016/5/16.
 */
@PerActivity
@Subcomponent(modules = UserFragmentModule.class)
public interface UserFragmentComponent {
    void inject(UserFragment userFragment);
}