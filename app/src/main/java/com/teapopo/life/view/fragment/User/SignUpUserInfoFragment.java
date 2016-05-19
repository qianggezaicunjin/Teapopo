package com.teapopo.life.view.fragment.User;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.databinding.FragmentSignupUserinfoBinding;
import com.teapopo.life.view.fragment.SwipeBackBaseFragment;

/**
 * Created by louiszgm on 2016/5/19.
 */
public class SignUpUserInfoFragment extends SwipeBackBaseFragment {

    private FragmentSignupUserinfoBinding binding;

    @Override
    public void onCreateBinding(Bundle savedInstanceState) {

    }

    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSignupUserinfoBinding.inflate(inflater);

        return binding.getRoot();
    }

    @Override
    public void setUpView() {

    }
}
