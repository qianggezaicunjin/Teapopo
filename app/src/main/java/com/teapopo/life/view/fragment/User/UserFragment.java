package com.teapopo.life.view.fragment.User;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.databinding.FragmentUserBinding;
import com.teapopo.life.injection.component.fragment.UserFragmentComponent;
import com.teapopo.life.injection.module.fragment.UserFragmentModule;
import com.teapopo.life.view.activity.MainActivity;
import com.teapopo.life.view.fragment.BaseFragment;
import com.teapopo.life.viewModel.userCenter.UserViewModel;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by louiszgm on 2016/4/18 0018.
 */
public class UserFragment extends BaseFragment {

    private UserFragmentComponent mComponent;

    @Inject
     UserViewModel mViewModel;
    @Override
    public void onCreateBinding() {
        Timber.d("Oncreate");
        if(getActivity() instanceof MainActivity){
            mComponent = ((MainActivity)getActivity()).getMainActivityComponent().userFragment(new UserFragmentModule());
            mComponent.inject(this);}
    }

    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentUserBinding binding = FragmentUserBinding.inflate(inflater);
        binding.setUserViewModel(mViewModel);
        return binding.getRoot();
    }

    @Override
    public void setUpView() {

    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.requestData();
    }
}
