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
 * Created by louiszgm on 2016/4/18 0018.
 * 用户注册
 */
public class SignUpFragment extends Fragment {

    private View mViewContent;
    public static SignUpFragment newInstance(){
        return new SignUpFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(mViewContent==null){
            mViewContent=inflater.inflate(R.layout.fragment_signup,container,false);
        }
        ViewGroup parent= (ViewGroup) container.getParent();
        if (parent!=null){
            parent.removeView(mViewContent);
        }
        ButterKnife.bind(this, mViewContent);
        return mViewContent;
    }
}
