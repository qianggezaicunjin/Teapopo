package com.teapopo.life.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.teapopo.life.R;
import com.teapopo.life.model.user.LoginModel;
import com.teapopo.life.model.user.UserInfoModel;
import com.teapopo.life.view.customView.swipeback.SwipeBackLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by louiszgm on 2016/5/10.
 */
public class LogInAndOutActivity extends SwipeBackBaseActivity implements Toolbar.OnMenuItemClickListener, View.OnClickListener {
    @Bind(R.id.toolbar_sign_in_out)
    Toolbar mToolbar;
    @Bind(R.id.btn_login)
    Button mBtn_login;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, LogInAndOutActivity.class);
    }
    @Override
    public void onCreateBinding() {
        setContentView(R.layout.activity_sign_in_out);
        ButterKnife.bind(this);
        setUpAppBar();
        mBtn_login.setOnClickListener(this);
    }

    private void setUpAppBar() {
        this.setSupportActionBar(mToolbar);
        mToolbar.setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_fast_signup,menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        LoginModel loginModel = new LoginModel(this);
        loginModel.login();
    }
}
