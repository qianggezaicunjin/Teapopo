package com.teapopo.life.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.teapopo.life.R;
import com.teapopo.life.injection.component.ComponentHolder;
import com.teapopo.life.injection.component.DaggerSignInAndUpActivityComponent;
import com.teapopo.life.injection.component.SignInAndUpActivityComponent;
import com.teapopo.life.injection.component.fragment.SignUpVervifyCodeFragmentComponent;
import com.teapopo.life.injection.module.ActivityModule;
import com.teapopo.life.injection.module.SignInAndUpActivityModule;
import com.teapopo.life.view.fragment.User.SignInFragment;

import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by louiszgm on 2016/5/10.
 */
public class SignInAndUpActivity extends SwipeBackBaseActivity  {

    private SignInAndUpActivityComponent mComponent;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, SignInAndUpActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_out);
        ButterKnife.bind(this);
        start(SignInFragment.newInstances());
    }

    @Override
    public void onCreateBinding() {
       mComponent = DaggerSignInAndUpActivityComponent.builder().
               applicationComponent(ComponentHolder.getAppComponent()).
               activityModule(new ActivityModule(this)).
               signInAndUpActivityModule(new SignInAndUpActivityModule())
               .build();
        mComponent.inject(this);
    }

    public SignInAndUpActivityComponent getSignInAndUpActivityComponent(){
        return mComponent;
    }

    @Override
    protected int setContainerId() {
        return R.id.framelayout_sign_in_out;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Timber.d("SignInAndUpActivity 的 onSaveInstanceState被调用啦");
    }
}
