package com.teapopo.life.view.fragment.welfare;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.R;
import com.teapopo.life.view.fragment.BaseFragment;

/**
 * Created by louiszgm-pc on 2016/5/20.
 */
public class WelFareFragment extends BaseFragment {

    public static WelFareFragment newInstance(){
        return new WelFareFragment();
    }
    @Override
    public void onCreateBinding() {

    }

    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welfare,container,false);
        return view;
    }

    @Override
    public void setUpView() {

    }
}
