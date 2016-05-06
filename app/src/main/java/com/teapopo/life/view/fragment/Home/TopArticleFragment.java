package com.teapopo.life.view.fragment.Home;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.teapopo.life.R;
import com.teapopo.life.data.remote.NetWorkService;
import com.teapopo.life.databinding.FragmentToparticleBinding;
import com.teapopo.life.model.toparticle.TopArticle;
import com.teapopo.life.view.fragment.BaseFragment;

import timber.log.Timber;

/**
 * Created by louiszgm on 2016/5/5.
 */
public class TopArticleFragment extends BaseFragment {
    public static final String TOP_ARTICLE = "TOP_ARTICLE";
    private TopArticle mTopArticle;
    DisplayImageOptions mOptions;

    public static TopArticleFragment newInstance(TopArticle topArticle){
        TopArticleFragment fragment = new TopArticleFragment();
        Bundle args = new Bundle();
        args.putParcelable(TOP_ARTICLE, topArticle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreateBinding() {
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(TOP_ARTICLE)) {
            mTopArticle = bundle.getParcelable(TOP_ARTICLE);
        }
        mOptions = new DisplayImageOptions.Builder()//
                .cacheInMemory(true)//
                .cacheOnDisk(true)//
                .bitmapConfig(Bitmap.Config.RGB_565)//
                .build();
    }

    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Timber.d("TopArticleFragment");
        FragmentToparticleBinding binding = FragmentToparticleBinding.inflate(inflater);
        String url= NetWorkService.IMAGE_ENDPOINT+mTopArticle.topImageUrl+NetWorkService.IMAGE_EXT;
        ImageLoader.getInstance().displayImage(url, binding.ivPic, mOptions);
        return binding.getRoot();
    }

    @Override
    public void setUpView() {

    }
}
