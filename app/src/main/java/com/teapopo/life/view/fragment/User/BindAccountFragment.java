package com.teapopo.life.view.fragment.User;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.R;

import butterknife.ButterKnife;

/**
 * Created by louiszgm on 2016/4/22 0022.
 */
public class BindAccountFragment extends Fragment {

    private View mContentView;

    public static BindAccountFragment newInstance(){
        return  new BindAccountFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(mContentView==null){
            mContentView=inflater.inflate(R.layout.fragment_bindaccount,container,false);
        }
        ViewGroup parent= (ViewGroup) container.getParent();
        if(parent!=null){
            parent.removeView(mContentView);
        }
        ButterKnife.bind(this,mContentView);
        return mContentView;
    }
}
