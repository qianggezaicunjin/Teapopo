package com.teapopo.life.view.fragment.xinzi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.teapopo.life.R;
import com.teapopo.life.databinding.FragmentXinziBinding;
import com.teapopo.life.databinding.ItemRecyclerviewToparticleBinding;
import com.teapopo.life.databinding.ItemRecyclerviewXinziToparticleBinding;
import com.teapopo.life.injection.component.fragment.MainFragmentComponent;
import com.teapopo.life.injection.module.fragment.MainFragmentModule;
import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.model.article.Article;
import com.teapopo.life.model.toparticle.TopArticle;
import com.teapopo.life.util.CustomToast;
import com.teapopo.life.view.activity.MainActivity;
import com.teapopo.life.view.adapter.recyclerview.XinZiArticleAdapter;
import com.teapopo.life.view.adapter.viewpager.TopArticleAdapter;
import com.teapopo.life.view.fragment.BaseFragment;
import com.teapopo.life.viewModel.xinzi.XinZiArticleViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by louiszgm-pc on 2016/5/20.
 */
public class XinZiFragment extends BaseFragment {

    private MainFragmentComponent mComponent;
    private XinZiArticleAdapter mAdapter;
    private List<BaseEntity> articleList = new ArrayList<>();
    private List<BaseEntity> topArticleList = new ArrayList<>();
    @Inject
    XinZiArticleViewModel mViewModel;
    private FragmentXinziBinding mBinding;
    private ItemRecyclerviewXinziToparticleBinding mTopBinding;

    public static XinZiFragment newInstance(){
        return new XinZiFragment();
    }
    @Override
    public void onCreateBinding() {
        mComponent = ((MainActivity)_mActivity).getMainActivityComponent().mainFragmentComponent(new MainFragmentModule(this));
        mComponent.inject(this);
    }

    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentXinziBinding.inflate(inflater);
        mBinding.setViewModel(mViewModel);
        return mBinding.getRoot();
    }

    @Override
    public void setUpView() {
//        setUpToolBar();
        setUpArticle();
        setUpTopArticle();
    }

    private void setUpToolBar() {
        Toolbar toolbar = mBinding.xinziToolbar.toolbar;
        TextView textView = new TextView(_mActivity);
        textView.setText("新滋");
        toolbar.addView(textView);
    }

    private void setUpArticle() {
        mAdapter = new XinZiArticleAdapter(_mActivity,articleList);
        mBinding.rvXinziarticle.setAdapter(mAdapter);
        mViewModel.requestData();
    }
    private void setUpTopArticle() {
        mTopBinding = ItemRecyclerviewXinziToparticleBinding.inflate(LayoutInflater.from(_mActivity));
        TopArticleAdapter adapter = new TopArticleAdapter(_mActivity,topArticleList);
        mTopBinding.viewpagerToparticle.setAdapter(adapter);
        mBinding.rvXinziarticle.addHeader(mTopBinding.getRoot());
        //请求数据
        mViewModel.getTopArticle();
    }
    //更新文章的内容
    public void refreshArticleContent(List<Article> articleList){
        this.articleList.addAll(articleList);
        mBinding.rvXinziarticle.notifyDataSetChanged();
    }
    //更新头部文章
    public void refreshTopArticle(List<TopArticle> topArticles){
        this.topArticleList.addAll(topArticles);
        mTopBinding.viewpagerToparticle.notifyDataSetChanged();
    }

    //处理服务器返回的错误信息
    public void handleErroMsg(String s){
        CustomToast.makeText(_mActivity,s, Toast.LENGTH_SHORT).show();
    }
}
