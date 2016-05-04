package com.teapopo.life.view.fragment.Home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.R;
import com.teapopo.life.databinding.FragmentRecommendarticleBinding;
import com.teapopo.life.injection.module.RecommendArticleFragmentModule;
import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.view.activity.MainActivity;
import com.teapopo.life.view.adapter.recyclerview.RecommendArticleAdapter;
import com.teapopo.life.view.customView.RecyclerView.SuperRecyclerView;
import com.teapopo.life.view.fragment.BaseFragment;
import com.teapopo.life.viewModel.RecomendArticleViewModel;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by Administrator on 2016/4/7 0007.
 */
public class RecommendArticleFragment extends BaseFragment {
    @Bind(R.id.swipe_refresh_widget)
    SwipeRefreshLayout mSwipeRefreshWidget;

    private CompositeSubscription mSubscriptions;
    @Inject
    RecomendArticleViewModel mRecomendArticleViewModel;

    RecommendArticleAdapter mAdapter;
    public static RecommendArticleFragment newInstance() {
        return new RecommendArticleFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSubscriptions = new CompositeSubscription();
        mAdapter = new RecommendArticleAdapter(getActivity(),new ArrayList<BaseEntity>());
    }
    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommendarticle,container,false);
        ButterKnife.bind(this,view);
        return view;
    }
    @Override
    public void onCreateBinding(View contentview) {
        if(getActivity() instanceof MainActivity){
            ((MainActivity)getActivity()).getMainActivityComponent().recommendArticleFragment(new RecommendArticleFragmentModule(getActivity())).inject(this);
        }
        FragmentRecommendarticleBinding binding = FragmentRecommendarticleBinding.bind(contentview);
        binding.setRecommendArticleViewModel(mRecomendArticleViewModel);
    }
    @Override
    public void setUpView() {
        setupRecyclerView();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }

    private void setupRecyclerView() {
        //设置SwipeRefreshLayout
        // 这句话是为了，第一次进入页面的时候显示加载进度条
        mSwipeRefreshWidget.post(new Runnable() {
            @Override
            public void run() {
              mSwipeRefreshWidget.setRefreshing(true);
            }
        });
        mSwipeRefreshWidget.setColorSchemeResources(R.color.blue);
    }
}
