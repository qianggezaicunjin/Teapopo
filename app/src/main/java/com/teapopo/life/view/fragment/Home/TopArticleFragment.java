package com.teapopo.life.view.fragment.Home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.R;
import com.teapopo.life.databinding.FragmentToparticleBinding;
import com.teapopo.life.model.toparticle.TopArticle;
import com.teapopo.life.view.fragment.BaseFragment;

/**
 * Created by louiszgm on 2016/5/5.
 */
public class TopArticleFragment extends BaseFragment {
    public static final String TOP_ARTICLE = "TOP_ARTICLE";

    public TopArticleFragment newInstance(TopArticle topArticle){
        TopArticleFragment fragment = new TopArticleFragment();
        Bundle args = new Bundle();
        args.putParcelable(TOP_ARTICLE, topArticle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreateBinding() {

    }

    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentToparticleBinding binding = FragmentToparticleBinding.inflate(inflater);

        return binding.getRoot();
    }

    @Override
    public void setUpView() {

    }
}
