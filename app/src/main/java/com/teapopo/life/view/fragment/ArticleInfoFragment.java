package com.teapopo.life.view.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;
import com.teapopo.life.R;
import com.teapopo.life.data.rx.RxBus;
import com.teapopo.life.databinding.FragmentArticleinfoBinding;
import com.teapopo.life.databinding.ItemArticleinfoTopviewBinding;
import com.teapopo.life.injection.component.fragment.ArticleDetailFragmentComponent;
import com.teapopo.life.injection.module.fragment.ArticleDetailFragmentModule;
import com.teapopo.life.model.AuthorInfo;
import com.teapopo.life.model.articleinfo.ArticleInfo;
import com.teapopo.life.model.comment.Comment;
import com.teapopo.life.util.CustomToast;
import com.teapopo.life.util.DataUtils;
import com.teapopo.life.util.rx.RxSubscriber;
import com.teapopo.life.view.activity.ArticleDetailActivity;
import com.teapopo.life.view.adapter.flexbox.ArticleFansAdapter;
import com.teapopo.life.view.adapter.recyclerview.CommentListAdapter;
import com.teapopo.life.view.adapter.viewpager.ArticleInfoImageAdapter;
import com.teapopo.life.view.customView.HackyViewPager;
import com.teapopo.life.view.customView.RecyclerView.LinearRecyclerView;
import com.teapopo.life.viewModel.articleinfo.ArticleInfoViewModel;
import com.viewpagerindicator.CirclePageIndicator;


import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

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
    private List<Comment> data;//评论列表的数据
    private String mArticleId; //文章id
    private Comment mReplyComment;//要回复的评论
    @Inject
    ArticleInfoViewModel mViewModel;
    @Inject
    RxBus mRxBus;
    private ArticleFansAdapter articleFansAdapter;

    public static ArticleInfoFragment newInstance(String articleId){
        ArticleInfoFragment fragment = new ArticleInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("articleId",articleId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        articleFansAdapter = new ArticleFansAdapter(_mActivity);
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
        //标识发出回复评论的动作
        tagWhenDoReplyAction();
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
        if(TextUtils.isEmpty(content)){
            CustomToast.makeText(_mActivity,"输入内容不能为空", Toast.LENGTH_SHORT).show();
        }else {
            //回复评论  or  发表评论
            if(mReplyComment!=null){
                //回复评论
                mViewModel.replyComment(mReplyComment.id,0,content);
            }else {
                //发表评论d
                mViewModel.addComment(mArticleId,0,content);
            }
        }

    }
    //添加评论
    public void refreshAddComment(Comment comment){
        //将最新的评论加在第一个位置
        data.add(0,comment);
        mBinding.rvArticleinfoComment.notifyDataSetChanged();
        //关闭软键盘
        DataUtils.closeSoftInput(_mActivity,mBinding.linearlayoutInputComment);
        mBinding.etInputcomment.setText("");
        CustomToast.makeText(_mActivity,"发表评论成功!", Toast.LENGTH_SHORT);
    }

    //回复成功时收起键盘
    public void refreshWhenReplyDone(){
        //收起软键盘
        DataUtils.closeSoftInput(_mActivity,mBinding.linearlayoutInputComment);
        mBinding.etInputcomment.setText("发表评论");
        mReplyComment = null;
    }
    private void setUpRecyclerView() {
        //内容区，评论列表
        data = mViewModel.articleInfo.commentList;
        mAdapter = new CommentListAdapter(_mActivity,mViewModel.getData());
        mBinding.rvArticleinfoComment.setAdapter(mAdapter);
        //监听键盘收起,当键盘收起的时候回触发RecycerView的滚动
        mBinding.rvArticleinfoComment.setOnScrollListener(new LinearRecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                //当收起键盘时，回到发表评论的状态，清除RxSpf_ReplyCommentSp
                DataUtils.closeSoftInput(_mActivity,mBinding.linearlayoutInputComment);
                mBinding.etInputcomment.setHint("发表评论");
               mReplyComment = null;
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
        //文章的粉丝

        articleFansAdapter.setDataSource(mViewModel.articleInfo.fans);
        topbinding.flexboxAddlikeimage.setAdapter(articleFansAdapter);

        //请求数据内容
        mViewModel.requestData(mArticleId);
    }


    /**
     * 处理返回的评论内容
     * 回复评论
     */
    private void tagWhenDoReplyAction() {
        Observable<Comment> observable = mRxBus.toObserverable(Comment.class);
        mCompositeSubscription.add( observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<Comment>() {
                    @Override
                    public void _onNext(Comment comment) {
                        //如果不包含该comment，则代表发表的是评论
                        if(data.contains(comment)){
                            DataUtils.showSoftInput(_mActivity,mBinding.linearlayoutInputComment);
                            mBinding.etInputcomment.setHint("回复"+comment.authorInfo.nickname);
                            mReplyComment = comment;
                        }
                    }

                    @Override
                    public void _onError(String s) {

                    }
                }));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdapter.mCompositeSubscription.unsubscribe();
        mCompositeSubscription.unsubscribe();
    }
}
