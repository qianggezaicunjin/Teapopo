package com.teapopo.life.view.fragment.Home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.databinding.FragmentHomelikearticleBinding;
import com.teapopo.life.model.article.likearticle.HomeLikeArticleModel;
import com.teapopo.life.view.fragment.BaseFragment;
import com.teapopo.life.viewModel.home.HomeLikeArticleViewModel;

/**
 * Created by louiszgm on 2016/4/14 0014.
 * 喜欢的文章列表
 * 需要在登陆状态
 */
public class HomeLikeArticleFragment extends BaseFragment {


    public static HomeLikeArticleFragment newInstance() {
        return new HomeLikeArticleFragment();
    }

    @Override
    public void onCreateBinding() {

    }

    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentHomelikearticleBinding binding = FragmentHomelikearticleBinding.inflate(inflater);
        HomeLikeArticleViewModel viewModel = new HomeLikeArticleViewModel(_mActivity,new HomeLikeArticleModel(_mActivity));
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void setUpView() {

    }
}
