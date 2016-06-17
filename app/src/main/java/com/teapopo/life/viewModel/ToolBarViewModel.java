package com.teapopo.life.viewModel;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.teapopo.life.view.fragment.User.SignUpVertifyCodeFragment;

import me.yokeyword.fragmentation.SupportActivity;
import timber.log.Timber;

/**
 * Created by louiszgm-pc on 2016/5/18.
 */
public class ToolBarViewModel {
    Fragment mView;
    public ToolBarViewModel(Fragment view){
        mView = view;
    }

    //新消息
    public void requestNewMsg(){

    }
    //搜索
    public void search(){

    }
}
