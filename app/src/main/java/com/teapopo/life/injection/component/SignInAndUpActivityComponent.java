package com.teapopo.life.injection.component;

import com.teapopo.life.injection.component.fragment.SignUpVervifyCodeFragmentComponent;
import com.teapopo.life.injection.module.ActivityModule;
import com.teapopo.life.injection.module.SignInAndUpActivityModule;
import com.teapopo.life.injection.module.fragment.SignUpVertifyCodeFragmentModule;
import com.teapopo.life.injection.scope.PerActivity;
import com.teapopo.life.view.activity.SignInAndUpActivity;

import dagger.Component;

/**
 * Created by louiszgm on 2016/5/20.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class,modules = {ActivityModule.class,SignInAndUpActivityModule.class})
public interface SignInAndUpActivityComponent extends ActivityComponent{

    void inject(SignInAndUpActivity activity);

    SignUpVervifyCodeFragmentComponent signUpVervifyCodeFragmentComponent(SignUpVertifyCodeFragmentModule module);

}