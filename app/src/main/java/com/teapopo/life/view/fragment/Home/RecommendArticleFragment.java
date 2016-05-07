package com.teapopo.life.view.fragment.Home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.data.rx.RxBus;
import com.teapopo.life.databinding.FragmentRecommendarticleBinding;


import com.teapopo.life.databinding.ItemHomeCategoryBinding;
import com.teapopo.life.databinding.ItemRecyclerviewToparticleBinding;
import com.teapopo.life.injection.component.RecommendArticleFragmentComponent;
import com.teapopo.life.injection.module.RecommendArticleFragmentModule;
import com.teapopo.life.model.event.AddHeaderEvent;
import com.teapopo.life.model.event.DataEvent;
import com.teapopo.life.view.activity.MainActivity;
import com.teapopo.life.view.fragment.BaseFragment;
import com.teapopo.life.viewModel.RecomendArticleViewModel;

import java.util.Timer;

import javax.inject.Inject;

import rx.functions.Action1;
import rx.observables.ConnectableObservable;
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
    private ItemRecyclerviewToparticleBinding toparticleBinding;//顶部轮播图片的binding对象
    private ItemHomeCategoryBinding categoryBinding;

    public static RecommendArticleFragment newInstance() {
        return new RecommendArticleFragment();
    }

    @Override
    public void onCreateBinding() {
        if(getActivity() instanceof MainActivity){
            mComponent = ((MainActivity)getActivity()).getMainActivityComponent().recommendArticleFragment(new RecommendArticleFragmentModule(getActivity()));
            mComponent.inject(this);
            ConnectableObservable<Object> observable = mRxBus.toObservable().publish();
            observable.subscribe(new Action1<Object>() {
                @Override
                public void call(Object o) {
                        if(o instanceof DataEvent){
                            binding.rvRecommendarticle.addHeader(toparticleBinding.getRoot());
                            toparticleBinding.viewpagerToparticle.setIsCostTheEvent(true);

                            binding.rvRecommendarticle.addHeader(categoryBinding.getRoot());

                        }
                    else if(o instanceof AddHeaderEvent){
                            Timber.d("收到AddHeaderEvent");
                            toparticleBinding.viewpagerToparticle.setCurrentItem(Integer.MAX_VALUE/2);
                        }
                }
            });

            observable.connect();
        }
    }
    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRecommendarticleBinding.inflate(inflater);
        toparticleBinding = ItemRecyclerviewToparticleBinding.inflate(inflater);
        categoryBinding = ItemHomeCategoryBinding.inflate(inflater);

        categoryBinding.rvCategory.setOrientation(RecyclerView.HORIZONTAL);

        binding.setRecommendArticleViewModel(mRecomendArticleViewModel);
        toparticleBinding.setRecommendArticleViewModel(mRecomendArticleViewModel);
        categoryBinding.setRecommendArticleViewModel(mRecomendArticleViewModel);

        return binding.getRoot();
    }
    @Override
    public void setUpView() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mComponent = null;
    }
}
