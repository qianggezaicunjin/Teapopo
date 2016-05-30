package com.teapopo.life.view.fragment.Home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.data.rx.RxBus;
import com.teapopo.life.databinding.FragmentRecommendarticleBinding;
import com.teapopo.life.injection.component.fragment.MainFragmentComponent;
import com.teapopo.life.injection.module.fragment.MainFragmentModule;
import com.teapopo.life.view.activity.MainActivity;
import com.teapopo.life.view.fragment.BaseFragment;
import com.teapopo.life.viewModel.home.RecomendArticleViewModel;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by Administrator on 2016/4/7 0007.
 */
public class RecommendArticleFragment extends BaseFragment {

    @Inject
    RecomendArticleViewModel mRecomendArticleViewModel;
    @Inject
    RxBus mRxBus;
    private MainFragmentComponent mComponent;
    private FragmentRecommendarticleBinding mBinding;//文章列表内容的binding对象

    private View mTopArticle;
    public static RecommendArticleFragment newInstance() {
        return new RecommendArticleFragment();
    }

    @Override
    public void onCreateBinding() {
        if(getActivity() instanceof MainActivity){
            mBinding = FragmentRecommendarticleBinding.inflate(LayoutInflater.from(_mActivity));
            mComponent = ((MainActivity)getActivity()).getMainActivityComponent().mainFragmentComponent(new MainFragmentModule(mBinding));
            mComponent.inject(this);
        }
    }
    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Timber.d("getContentView");
        mBinding.setRecommendArticleViewModel(mRecomendArticleViewModel);
        return mBinding.getRoot();
    }
    @Override
    public void setUpView() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
