package com.teapopo.life.view.fragment.xinzi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.R;
import com.teapopo.life.databinding.FragmentXinziBinding;
import com.teapopo.life.injection.component.fragment.MainFragmentComponent;
import com.teapopo.life.injection.module.fragment.MainFragmentModule;
import com.teapopo.life.view.activity.MainActivity;
import com.teapopo.life.view.fragment.BaseFragment;
import com.teapopo.life.viewModel.xinzi.XinZiArticleViewModel;

import javax.inject.Inject;

/**
 * Created by louiszgm-pc on 2016/5/20.
 */
public class XinZiFragment extends BaseFragment {

    private MainFragmentComponent mComponent;
    @Inject
    XinZiArticleViewModel mViewModel;
    public static XinZiFragment newInstance(){
        return new XinZiFragment();
    }
    @Override
    public void onCreateBinding() {
        mComponent = ((MainActivity)_mActivity).getMainActivityComponent().mainFragmentComponent(new MainFragmentModule());
        mComponent.inject(this);
    }

    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentXinziBinding binding = FragmentXinziBinding.inflate(inflater);
        binding.setViewModel(mViewModel);
        return binding.getRoot();
    }

    @Override
    public void setUpView() {

    }
}
