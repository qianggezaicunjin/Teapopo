package com.teapopo.life.view.fragment.Home;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.teapopo.life.view.activity.MainActivity;
import com.teapopo.life.view.fragment.BaseFragment;
import com.teapopo.life.viewModel.home.RecomendArticleViewModel;

import javax.inject.Inject;

import rx.Subscriber;
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
            mRxBus.toObserverable(AddHeaderEvent.class)
                    .subscribe(new Subscriber<AddHeaderEvent>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                                Timber.d(e.toString());
                        }

                        @Override
                        public void onNext(AddHeaderEvent addHeaderEvent) {
                            Timber.d("收到添加头布局的事件");
                            binding.rvRecommendarticle.addHeader(toparticleBinding.getRoot());
                            //这个是为了解决Viewpager嵌套viewpager的事件冲突
                            toparticleBinding.viewpagerToparticle.setIsCostTheEvent(true);
                            toparticleBinding.viewpagerToparticle.setCurrentItem(Integer.MAX_VALUE/2);
                            binding.rvRecommendarticle.addHeader(categoryBinding.getRoot());
                        }
                    });
        }
    }
    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Timber.d("getContentView");
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
    }
}
