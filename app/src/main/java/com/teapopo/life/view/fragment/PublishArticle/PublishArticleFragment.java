package com.teapopo.life.view.fragment.PublishArticle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import com.teapopo.life.R;
import com.teapopo.life.data.rx.RxBus;
import com.teapopo.life.databinding.FragmentPublisharticleBinding;
import com.teapopo.life.injection.component.fragment.PublishArticleFragmentComponent;
import com.teapopo.life.injection.module.fragment.PublishArticleFragmentModule;
import com.teapopo.life.model.article.publisharticle.PublishArticleData;
import com.teapopo.life.util.RxUtils;
import com.teapopo.life.view.activity.PublishArticleActivity;
import com.teapopo.life.view.customView.PicassoLoader;
import com.teapopo.life.view.fragment.SwipeBackBaseFragment;
import com.teapopo.life.viewModel.publisharticle.PublishArticleViewModel;
import com.yancy.imageselector.ImageConfig;
import com.yancy.imageselector.ImageSelector;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


import me.gujun.android.taggroup.TagGroup;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by louiszgm on 2016/6/8.
 */
public class PublishArticleFragment extends SwipeBackBaseFragment {


    private FragmentPublisharticleBinding mBinding;
    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();
    private PublishArticleData publishArticleData;
    @Inject
    PublishArticleViewModel mViewModel;
    @Inject
    RxBus mRxBus;
    public static PublishArticleFragment newInstance(PublishArticleData data){
        PublishArticleFragment fragment = new PublishArticleFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("data",data);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreateBinding() {
         ((PublishArticleActivity)_mActivity).getFragmentComponent().inject(this);
         publishArticleData = getArguments().getParcelable("data");
    }

    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentPublisharticleBinding.inflate(LayoutInflater.from(_mActivity));
        mBinding.setViewModel(mViewModel);
        mBinding.setHandler(this);
        return mBinding.getRoot();
    }

    @Override
    public void setUpView() {
        //设置热门标签
        setUpHotTags();
    }



    //发布文章
    public void clickPublishArticle(View view) {
        publishArticleData.content = mBinding.etPublishcontent.getText().toString();
        publishArticleData.title = mBinding.etPublishtitle.getText().toString();
        mViewModel.publishArticle(publishArticleData);
    }



    private void setUpHotTags() {
        mBinding.tagGroup.setTags(publishArticleData.tags);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxUtils.unsubscribeIfNotNull(mCompositeSubscription);
    }
}
