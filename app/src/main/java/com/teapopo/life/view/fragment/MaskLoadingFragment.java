package com.teapopo.life.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.R;

/**
 * Created by louiszgm on 2016/7/20.
 */
public class MaskLoadingFragment extends BaseFragment {

    public static MaskLoadingFragment newInstance(){
        return new MaskLoadingFragment();
    }
    @Override
    public void onCreateBinding() {

    }

    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mask_loading,null);
        return view;
    }

    @Override
    public void setUpView() {

    }
}
