package com.teapopo.life.view.fragment.Home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.data.rx.RxBus;
import com.teapopo.life.databinding.FragmentRecommendarticleBinding;
import com.teapopo.life.injection.component.fragment.RecommendArticleFragmentComponent;
import com.teapopo.life.injection.module.fragment.RecommendArticleFragmentModule;
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
    private RecommendArticleFragmentComponent mComponent;
    private FragmentRecommendarticleBinding binding;//文章列表内容的binding对象

    private View mTopArticle;
    public static RecommendArticleFragment newInstance() {
        return new RecommendArticleFragment();
    }

    @Override
    public void onCreateBinding() {
        if(getActivity() instanceof MainActivity){
            binding = FragmentRecommendarticleBinding.inflate(LayoutInflater.from(_mActivity));
            mComponent = ((MainActivity)getActivity()).getMainActivityComponent().recommendArticleFragment(new RecommendArticleFragmentModule(binding));
            mComponent.inject(this);
        }
    }
    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Timber.d("getContentView");
        binding.setRecommendArticleViewModel(mRecomendArticleViewModel);
        return binding.getRoot();
    }
    @Override
    public void setUpView() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
