package com.teapopo.life.view.fragment.xinzi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
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
import com.teapopo.life.util.DataUtils;
import com.teapopo.life.util.ViewUtils;
import com.teapopo.life.util.navigator.Navigator;
import com.teapopo.life.view.activity.ArticleDetailActivity;
import com.teapopo.life.view.activity.MainActivity;
import com.teapopo.life.view.adapter.recyclerview.XinZiArticleAdapter;
import com.teapopo.life.view.adapter.recyclerview.base.BaseRecyclerViewAdapter;
import com.teapopo.life.view.adapter.viewpager.TopArticleAdapter;
import com.teapopo.life.view.customView.RecyclerView.OnPageListener;
import com.teapopo.life.view.fragment.BaseFragment;
import com.teapopo.life.view.fragment.SwipeBackBaseFragment;
import com.teapopo.life.viewModel.xinzi.XinZiArticleViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by louiszgm-pc on 2016/5/20.
 */
public class XinZiFragment extends BaseFragment implements BaseRecyclerViewAdapter.OnItemClickListener, OnPageListener {

    private XinZiArticleAdapter mAdapter;

    @Inject
    XinZiArticleViewModel mViewModel;
    private FragmentXinziBinding mBinding;
    private ItemRecyclerviewXinziToparticleBinding mTopBinding;

    public static XinZiFragment newInstance(){
        return new XinZiFragment();
    }
    @Override
    public void onCreateBinding() {
         ((MainActivity)_mActivity).getMainFragmentComponent().inject(this);
    }

    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentXinziBinding.inflate(inflater);
        mBinding.setViewModel(mViewModel);
        return mBinding.getRoot();
    }

    @Override
    public void setUpView() {
        setUpToolBar();
        setUpArticle();
        setUpTopArticle();
    }

    private void setUpToolBar() {
        Toolbar toolbar = mBinding.xinziToolbar.toolbar;
        AttributeSet attributeSet = DataUtils.getAttributeSetFromXml(_mActivity,R.layout.toolbartitle);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(_mActivity,attributeSet);
        TextView textView = new TextView(_mActivity,attributeSet);
        textView.setText("新滋");
        toolbar.addView(textView,params);
        setUpToolBar(toolbar);
    }

    private void setUpArticle() {
        mAdapter = new XinZiArticleAdapter(_mActivity,mViewModel.data);
        mAdapter.setOnItemClickListener(this);
        mBinding.rvXinziarticle.setAdapter(mAdapter);
        mBinding.rvXinziarticle.setOnPageListener(this);
        mViewModel.requestData();
    }
    private void setUpTopArticle() {
        mTopBinding = ItemRecyclerviewXinziToparticleBinding.inflate(LayoutInflater.from(_mActivity));
        TopArticleAdapter adapter = new TopArticleAdapter(_mActivity,mViewModel.xinzi_topArticleList);
        mTopBinding.viewpagerToparticle.setAdapter(adapter);
        mBinding.rvXinziarticle.addHeader(mTopBinding.getRoot());
        //请求数据
        mViewModel.getTopArticle();
    }

    @Override
    public void onItemClick(View view, int position) {
        Article article = (Article) mViewModel.data.get(position);
        //跳转到文章详情页
        Intent intent = ArticleDetailActivity.getStartIntent(_mActivity);
        intent.putExtra("articleId",article.articleId);
        Navigator.getInstance().start(_mActivity,intent);
    }

    @Override
    public void onPage() {
        mViewModel.requestData();
    }
}
