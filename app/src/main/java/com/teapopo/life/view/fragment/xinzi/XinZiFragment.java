package com.teapopo.life.view.fragment.xinzi;

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
public class XinZiFragment extends BaseFragment {

    public static XinZiFragment newInstance(){
        return new XinZiFragment();
    }
    @Override
    public void onCreateBinding() {

    }

    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_xinzi,container,false);
        return view;
    }

    @Override
    public void setUpView() {

    }
}
