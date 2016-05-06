package com.teapopo.life.view.fragment.Home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.data.rx.RxBus;
import com.teapopo.life.databinding.FragmentRecommendarticleBinding;
import com.teapopo.life.databinding.ItemRecyclerviewHeaderBinding;
import com.teapopo.life.injection.component.RecommendArticleFragmentComponent;
import com.teapopo.life.injection.module.RecommendArticleFragmentModule;
import com.teapopo.life.model.event.AddHeaderEvent;
import com.teapopo.life.model.event.DataEvent;
import com.teapopo.life.model.toparticle.TopArticle;
import com.teapopo.life.view.activity.MainActivity;
import com.teapopo.life.view.adapter.viewpager.TabFragmentAdapter;
import com.teapopo.life.view.fragment.BaseFragment;
import com.teapopo.life.viewModel.RecomendArticleViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.observables.ConnectableObservable;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
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
    private ItemRecyclerviewHeaderBinding headerBinding;//顶部轮播图片的binding对象


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
                            binding.rvRecommendarticle.addHeader(headerBinding.getRoot());
                        }
                    else if(o instanceof AddHeaderEvent){
                            Timber.d("收到AddHeaderEvent");
                            headerBinding.indicatorCircleViewpager.setViewPager(headerBinding.viewpagerToparticle);
                            headerBinding.viewpagerToparticle.setCurrentItem(1);
                        }




                }
            });

            observable.connect();
        }
    }
    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRecommendarticleBinding.inflate(inflater);
        headerBinding = ItemRecyclerviewHeaderBinding.inflate(inflater);
        binding.setRecommendArticleViewModel(mRecomendArticleViewModel);
        headerBinding.setRecommendArticleViewModel(mRecomendArticleViewModel);

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
