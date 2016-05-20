package com.teapopo.life.view.activity;

import android.content.Context;
import android.content.Intent;

import com.teapopo.life.R;
import com.teapopo.life.view.fragment.User.SignInFragment;

import butterknife.ButterKnife;

/**
 * Created by louiszgm on 2016/5/10.
 */
public class SignInAndUpActivity extends SwipeBackBaseActivity  {


    public static Intent getStartIntent(Context context) {
        return new Intent(context, SignInAndUpActivity.class);
    }
    @Override
    public void onCreateBinding() {
        setContentView(R.layout.activity_sign_in_out);
        ButterKnife.bind(this);
        start(SignInFragment.newInstances());
    }


    @Override
    protected int setContainerId() {
        return R.id.framelayout_sign_in_out;
    }
}
