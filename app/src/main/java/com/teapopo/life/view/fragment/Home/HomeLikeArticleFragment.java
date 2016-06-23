package com.teapopo.life.view.fragment.Home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.databinding.FragmentHomelikearticleBinding;
import com.teapopo.life.injection.component.fragment.MainFragmentComponent;
import com.teapopo.life.injection.module.fragment.MainFragmentModule;
import com.teapopo.life.model.article.likearticle.HomeLikeArticleModel;
import com.teapopo.life.view.activity.MainActivity;
import com.teapopo.life.view.adapter.recyclerview.RecommendArticleAdapter;
import com.teapopo.life.view.customView.RecyclerView.OnPageListener;
import com.teapopo.life.view.fragment.BaseFragment;
import com.teapopo.life.viewModel.home.HomeLikeArticleViewModel;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by louiszgm on 2016/4/14 0014.
 * 喜欢的文章列表
 * 需要在登陆状态
 */
public class HomeLikeArticleFragment extends BaseFragment implements OnPageListener, SwipeRefreshLayout.OnRefreshListener {


    private FragmentHomelikearticleBinding mBinding;

    @Inject
    HomeLikeArticleViewModel mViewModel;
    public static HomeLikeArticleFragment newInstance() {
        return new HomeLikeArticleFragment();
    }

    @Override
    public void onCreateBinding() {
       ((MainActivity)_mActivity).getMainFragmentComponent().inject(this);

    }

    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentHomelikearticleBinding.inflate(inflater);
        mBinding.setViewModel(mViewModel);
        return mBinding.getRoot();
    }

    @Override
    public void setUpView() {
        setUpArticle();
    }

    private void setUpArticle() {
        RecommendArticleAdapter articleAdapter = new RecommendArticleAdapter(_mActivity,mViewModel.data);
        mBinding.rvHomelikearticle.setAdapter(articleAdapter);
        mBinding.rvHomelikearticle.setOnPageListener(this);
        mBinding.swipeRefreshWidget.setOnRefreshListener(this);
    }

    public void loadData(){
        mViewModel.requestData();
    }
    @Override
    public void onPage() {
        mViewModel.requestData();
    }

    @Override
    public void onRefresh() {
        mViewModel.data.clear();
        mViewModel.requestData();
    }
}
