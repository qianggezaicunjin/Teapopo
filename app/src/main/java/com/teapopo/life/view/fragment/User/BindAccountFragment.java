package com.teapopo.life.view.fragment.User;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.R;
import com.teapopo.life.view.fragment.BaseFragment;

import butterknife.ButterKnife;

/**
 * Created by louiszgm on 2016/4/22 0022.
 */
public class BindAccountFragment extends BaseFragment {


    public static BindAccountFragment newInstance(){
        return  new BindAccountFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View getmContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bindaccount,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void setUpView() {

    }


}
