package com.teapopo.life.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.teapopo.life.R;
import com.teapopo.life.model.user.LoginModel;
import com.teapopo.life.view.fragment.User.SignInFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by louiszgm on 2016/5/10.
 */
public class SignInAndOutActivity extends SwipeBackBaseActivity  {


    public static Intent getStartIntent(Context context) {
        return new Intent(context, SignInAndOutActivity.class);
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
