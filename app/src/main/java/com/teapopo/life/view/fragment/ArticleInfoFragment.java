package com.teapopo.life.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.teapopo.life.R;
import com.teapopo.life.data.rx.RxBus;
import com.teapopo.life.databinding.FragmentArticleinfoBinding;
import com.teapopo.life.databinding.ItemArticleinfoTopviewBinding;
import com.teapopo.life.injection.component.fragment.ArticleDetailFragmentComponent;
import com.teapopo.life.injection.module.fragment.ArticleDetailFragmentModule;
import com.teapopo.life.model.AuthorInfo;
import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.model.articleinfo.ArticleInfo;
import com.teapopo.life.model.comment.Comment;
import com.teapopo.life.util.DataUtils;
import com.teapopo.life.util.rx.RxSubscriber;
import com.teapopo.life.view.activity.ArticleDetailActivity;
import com.teapopo.life.view.adapter.recyclerview.CommentListAdapter;
import com.teapopo.life.view.adapter.viewpager.ArticleInfoImageAdapter;
import com.teapopo.life.view.customView.HackyViewPager;
import com.teapopo.life.viewModel.articleinfo.ArticleInfoViewModel;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
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
    private ArticleDetailFragmentComponent mComponent;

    private List<Comment> data;
    @Inject
    ArticleInfoViewModel mViewModel;
    @Inject
    RxBus mRxBus;

    public static ArticleInfoFragment newInstance(String articleId){
        ArticleInfoFragment fragment = new ArticleInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("articleId",articleId);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreateBinding(Bundle savedInstanceState) {
        mBinding = FragmentArticleinfoBinding.inflate(LayoutInflater.from(_mActivity));
        mComponent = ((ArticleDetailActivity)_mActivity).getComponent().articleDetailFragmentComponent(new ArticleDetailFragmentModule(mBinding));
        mComponent.inject(this);
        mViewModel.requestData(getArguments().getString("articleId"));
    }

    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding.setViewmodel(mViewModel);

        data = mViewModel.articleInfo.commentList;
        CommentListAdapter adapter = new CommentListAdapter(_mActivity,data);
        mBinding.rvArticleinfoComment.setAdapter(adapter);

        topbinding = ItemArticleinfoTopviewBinding.inflate(inflater);
        topbinding.setViewmodel(mViewModel);

        mBinding.rvArticleinfoComment.addHeader(topbinding.getRoot());
        return mBinding.getRoot();
    }

    @Override
    public void setUpView() {
        addRecyclerViewHeader();

        addComment();
    }

    private void addComment() {
        Observable<Comment> observable = mRxBus.toObserverable(Comment.class);
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<Comment>() {
                    @Override
                    public void _onNext(Comment comment) {
                        data.add(0,comment);

                        mBinding.rvArticleinfoComment.notifyDataSetChanged();
//                        mBinding.rvArticleinfoComment.scrollToPosition();
                    }

                    @Override
                    public void _onError(String s) {

                    }
                });
    }

    private void addRecyclerViewHeader() {
        Observable observable = mRxBus.toObserverable(ArticleInfo.class);
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber() {
                    @Override
                    public void _onNext(Object o) {
                        Timber.d("收到文章信息");
                        ArticleInfo articleInfo = (ArticleInfo) o;
                        //刷新评论的列表
                        data.addAll(articleInfo.commentList);
                        mBinding.rvArticleinfoComment.notifyDataSetChanged();
                        //添加轮播的图片
                        addSlideImages(articleInfo.articleImageUrls,topbinding);
                        //加入标签
                        addTags(articleInfo.tags,topbinding);
                        //添加喜欢该篇文章的会员头像
                        addFans(articleInfo.fans,topbinding);
                    }

                    @Override
                    public void _onError(String s) {

                    }
                });
    }


    private void addFans(List<AuthorInfo> member_like, ItemArticleinfoTopviewBinding binding) {
        if(member_like.size()>0){
            String text = member_like.size()+" 人喜欢了";
            binding.tvArticleinfoLikenum.setText(text);
            //相对于自身的属性
            AttributeSet attributeSet = DataUtils.getAttributeSetFromXml(_mActivity, R.layout.memberavatar);
            //相对于父控件的属性
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(_mActivity, attributeSet);
            //添加粉丝的头像
            for(AuthorInfo author:member_like){
                ImageView img = new ImageView(_mActivity,attributeSet);
                ImageLoader.getInstance().displayImage(author.getAvatarUrl(),img);
                binding.linearlayoutAddlikeimage.addView(img,params);
            }
        }
    }

    private void addTags(@NonNull List<String> tags, ItemArticleinfoTopviewBinding binding) {
        if (tags != null) {
            Timber.d("tags的个数为:%d", tags.size());
            binding.linearlayoutAddtag.removeAllViews();
            //相对于自身的属性
            AttributeSet attributeSet = DataUtils.getAttributeSetFromXml(_mActivity, R.layout.tagname);
            //相对于父控件的属性
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(_mActivity, attributeSet);
            //添加tag图标
            ImageView img_tag = new ImageView(_mActivity);
            img_tag.setBackgroundResource(R.drawable.icon_tag);
            binding.linearlayoutAddtag.addView(img_tag);
            //添加标签的文字
            for (String tag : tags) {
                TextView tv_tag = new TextView(_mActivity, attributeSet);
                tv_tag.setId(R.id.tv_tagname);
                tv_tag.setText(tag);
//                tv_tag.setOnClickListener(mBinding.getViewmodel().getOnClickListener());
                binding.linearlayoutAddtag.addView(tv_tag, params);
            }

        }
    }

    private void addSlideImages(List<String> articleImageUrls,ItemArticleinfoTopviewBinding binding) {
        //如果文章信息的articleImageUrls的大小大于0，则说明该篇文章的信息有图片轮播
        if(articleImageUrls.size()>0){
            ArticleInfoImageAdapter adapter = new ArticleInfoImageAdapter(_mActivity,articleImageUrls);
            binding.viewstubArticleinfoImage.getViewStub().inflate();
            CirclePageIndicator indicator = (CirclePageIndicator) binding.getRoot().findViewById(R.id.indicator_viewpager);
            HackyViewPager viewPager = (HackyViewPager) binding.getRoot().findViewById(R.id.viewpager);
            viewPager.setAdapter(adapter);
            indicator.setViewPager(viewPager);
        }
    }
}
