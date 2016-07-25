package com.teapopo.life.view.fragment.Member;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.databinding.FragmentMemberLikeBinding;
import com.teapopo.life.view.fragment.BaseFragment;

/**
 * Created by lhq on 2016/7/18.
 */

public class MemberLikeFragment extends BaseFragment {

    private FragmentMemberLikeBinding mBinding;

    public static MemberLikeFragment newInstance(){
        return new MemberLikeFragment();
    }
    @Override
    public void onCreateBinding() {

    }

    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding=FragmentMemberLikeBinding.inflate(inflater);
        return mBinding.getRoot();
    }

    @Override
    public void setUpView() {
    }

}
