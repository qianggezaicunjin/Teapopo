package com.teapopo.life.view.fragment.User;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.R;
import com.teapopo.life.view.fragment.BaseFragment;

import butterknife.ButterKnife;

/**
 * Created by louiszgm on 2016/4/22 0022.
 */
public class BindNewAccountFragment extends BaseFragment {


    public static BindNewAccountFragment newInstance(){
        return  new BindNewAccountFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateBinding() {

    }

    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bindnewaccount,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void setUpView() {

    }


}
