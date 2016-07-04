package com.teapopo.life.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.teapopo.life.R;
import com.teapopo.life.data.rx.RxBus;
import com.teapopo.life.databinding.FragmentArticleinfoBinding;
import com.teapopo.life.databinding.ItemArticleinfoTopviewBinding;
import com.teapopo.life.injection.component.fragment.ArticleDetailFragmentComponent;
import com.teapopo.life.injection.module.fragment.ArticleDetailFragmentModule;
import com.teapopo.life.util.DataUtils;
import com.teapopo.life.view.activity.ArticleDetailActivity;
import com.teapopo.life.view.adapter.flexbox.ArticleFansAdapter;
import com.teapopo.life.view.adapter.flexbox.ArticleTagsAdapter;
import com.teapopo.life.view.adapter.recyclerview.CommentListAdapter;
import com.teapopo.life.view.adapter.viewpager.ArticleInfoImageAdapter;
import com.teapopo.life.view.customView.HackyViewPager;
import com.teapopo.life.view.customView.RecyclerView.LinearRecyclerView;
import com.teapopo.life.viewModel.articleinfo.ArticleInfoViewModel;
import com.viewpagerindicator.CirclePageIndicator;


import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by louiszgm on 2016/6/2.
 * 文章的详细信息
 * 整体布局为recyclerview
 * recyclerview的列表内容为评论列表,其余是头布局
 */
public class ArticleInfoFragment extends SwipeBackBaseFragment {
    private FragmentArticleinfoBinding mBinding;
    private ItemArticleinfoTopviewBinding topbinding;
    private CommentListAdapter mAdapter;
    private ArticleDetailFragmentComponent mComponent;

    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    private String mArticleId; //文章id
    @Inject
    ArticleInfoViewModel mViewModel;



    public static ArticleInfoFragment newInstance(String articleId){
        ArticleInfoFragment fragment = new ArticleInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("articleId",articleId);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onCreateBinding() {
        mBinding = FragmentArticleinfoBinding.inflate(LayoutInflater.from(_mActivity));
        mComponent = ((ArticleDetailActivity)_mActivity).getComponent().articleDetailFragmentComponent(new ArticleDetailFragmentModule(this));
        mComponent.inject(this);
        mArticleId = getArguments().getString("articleId");
    }

    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding.setViewmodel(mViewModel);
        return mBinding.getRoot();
    }

    @Override
    public void setUpView() {
        setUpRecyclerView();
        attachOnClickListener();
        setToolBar();
    }

    private void attachOnClickListener() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btn_articleinfo_publishcomment:
                        addCommentOrReply();
                        break;
                }
            }
        };
        mBinding.btnArticleinfoPublishcomment.setOnClickListener(listener);
    }

    /**
     * 发表评论/回复评论
     */
    private void addCommentOrReply() {
        String content = mBinding.etInputcomment.getText().toString();
        mViewModel.addCommentOrReply(content,mArticleId);
    }

    private void setUpRecyclerView() {
        //内容区，评论列表
        mAdapter = new CommentListAdapter(_mActivity,mViewModel.articleInfo.commentList);
        mBinding.rvArticleinfoComment.setAdapter(mAdapter);
        //模拟监听键盘收起,键盘收起的时候会引起RecyclerView的滚动
        mBinding.rvArticleinfoComment.setOnScrollListener(new LinearRecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                //当收起键盘时，回到发表评论的状态，setSoftInputStateWhenCommentOrReply(null)
                //只有当键盘显示时才关闭软键盘
                if(mBinding.etInputcomment.isFocused()){
                    mViewModel.setSoftInputStateWhenCommentOrReply(false,false,null);
                }
            }
        });
        //加入头布局
        topbinding = ItemArticleinfoTopviewBinding.inflate(LayoutInflater.from(_mActivity));
        topbinding.setViewmodel(mViewModel);
        mBinding.rvArticleinfoComment.addHeader(topbinding.getRoot());

        //文章的图片
        ArticleInfoImageAdapter adapter = new ArticleInfoImageAdapter(_mActivity,mViewModel.articleInfo.articleImageUrls);
        CirclePageIndicator indicator = topbinding.indicatorViewpager;
        HackyViewPager viewPager = topbinding.viewpager;
        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);
        //文章的标签
        ArticleTagsAdapter articleTagsAdapter = new ArticleTagsAdapter(_mActivity);
        articleTagsAdapter.setDataSource(mViewModel.articleInfo.tags);
        topbinding.flexboxAddtag.setAdapter(articleTagsAdapter);
        //文章的粉丝
        ArticleFansAdapter articleFansAdapter = new ArticleFansAdapter(_mActivity);
        articleFansAdapter.setDataSource(mViewModel.articleInfo.fans);
        topbinding.flexboxAddlikeimage.setAdapter(articleFansAdapter);

        //请求数据内容
        mViewModel.requestData(mArticleId);
    }

    //设置标题栏导航按钮
    public void setToolBar(){
        Toolbar toolbar=topbinding.toolbarArticleinfo;
        toolbar.setNavigationIcon(R.drawable.icon_back);
        toolbar.inflateMenu(R.menu.menu_home);
        //导航栏按钮监听
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                pop();
                return true;
            }
        });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mViewModel.compositeSubscription.unsubscribe();
        mCompositeSubscription.unsubscribe();
    }
}
