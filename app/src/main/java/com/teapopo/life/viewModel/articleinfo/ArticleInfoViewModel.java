package com.teapopo.life.viewModel.articleinfo;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jaeger.ninegridimageview.NineGridImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.teapopo.life.BR;
import com.teapopo.life.R;
import com.teapopo.life.data.remote.NetWorkService;
import com.teapopo.life.databinding.FragmentArticleinfoBinding;
import com.teapopo.life.databinding.ItemRecyclerviewArticleBinding;
import com.teapopo.life.model.AuthorInfo;
import com.teapopo.life.model.articleinfo.ArticleInfo;
import com.teapopo.life.model.articleinfo.ArticleInfoModel;
import com.teapopo.life.model.toparticle.TopArticle;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.util.DataUtils;
import com.teapopo.life.view.adapter.gridview.NineImageGridAdapter;
import com.teapopo.life.view.adapter.viewpager.ArticleInfoImageAdapter;
import com.teapopo.life.view.adapter.viewpager.TopArticleAdapter;
import com.teapopo.life.view.customView.HackyViewPager;
import com.teapopo.life.view.customView.RequestView;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Created by louiszgm on 2016/6/1.
 */
public class ArticleInfoViewModel extends BaseObservable implements RequestView<ModelAction> {
    private Context mContext;
    private ArticleInfoModel mModel;
    public String articleId;
    private FragmentArticleinfoBinding mBinding;
    @Bindable
    public ArticleInfo articleInfo;
    public ArticleInfoViewModel(Context context, ArticleInfoModel model, ViewDataBinding binding){
        mBinding = (FragmentArticleinfoBinding) binding;
        mContext = context;
        mModel = model;
        mModel.setView(this);

    }


    public void requestData(String articleId){
        mModel.getArticleInfo(articleId);
    }

    public View.OnClickListener getOnClickListener(){
        return  new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.img_likeornot:
                        break;
                }
            }
        };
    }


    @Override
    public void onRequestFinished() {

    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        Action action = data.action;
        if(action == Action.ArticleInfoModel_GetInfo){
            this.articleInfo = (ArticleInfo) data.t;
            //添加轮播的图片
            addSlideImages(articleInfo.articleImageUrls);
            //加入标签
            addTags(articleInfo.tags);
            //添加喜欢该篇文章的会员头像
            addFans(articleInfo.member_like);
            notifyPropertyChanged(BR.articleInfo);

        }
    }

    private void addFans(List<AuthorInfo> member_like) {
        if(member_like.size()>0){
            String text = member_like.size()+" 人喜欢了";
            mBinding.tvArticleinfoLikenum.setText(text);
            //相对于自身的属性
            AttributeSet attributeSet = DataUtils.getAttributeSetFromXml(mContext, R.layout.memberavatar);
            //相对于父控件的属性
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mContext, attributeSet);
            //添加粉丝的头像
            for(AuthorInfo author:member_like){
                ImageView img = new ImageView(mContext,attributeSet);
                ImageLoader.getInstance().displayImage(author.getAvatarUrl(),img);
                mBinding.linearlayoutAddlikeimage.addView(img,params);
            }
        }
    }

    private void addTags(@NonNull List<String> tags) {
        if (tags != null) {
            Timber.d("tags的个数为:%d", tags.size());
            mBinding.linearlayoutAddtag.removeAllViews();
            //相对于自身的属性
            AttributeSet attributeSet = DataUtils.getAttributeSetFromXml(mContext, R.layout.tagname);
            //相对于父控件的属性
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mContext, attributeSet);
            //添加tag图标
            ImageView img_tag = new ImageView(mContext);
            img_tag.setBackgroundResource(R.drawable.icon_tag);
            mBinding.linearlayoutAddtag.addView(img_tag);
            //添加标签的文字
            for (String tag : tags) {
                TextView tv_tag = new TextView(mContext, attributeSet);
                tv_tag.setId(R.id.tv_tagname);
                tv_tag.setText(tag);
//                tv_tag.setOnClickListener(mBinding.getViewmodel().getOnClickListener());
                mBinding.linearlayoutAddtag.addView(tv_tag, params);
            }

        }
    }

    private void addSlideImages(List<String> articleImageUrls) {
        //如果文章信息的articleImageUrls的大小大于0，则说明该篇文章的信息有图片轮播
        if(articleImageUrls.size()>0){
            ArticleInfoImageAdapter adapter = new ArticleInfoImageAdapter(mContext,articleInfo.articleImageUrls);
            mBinding.viewstubArticleinfoImage.getViewStub().inflate();
            CirclePageIndicator indicator = (CirclePageIndicator) mBinding.getRoot().findViewById(R.id.indicator_viewpager);
            HackyViewPager viewPager = (HackyViewPager) mBinding.getRoot().findViewById(R.id.viewpager);
            viewPager.setAdapter(adapter);
            indicator.setViewPager(viewPager);
        }
    }


    @Override
    public void onRequestErroInfo(String erroinfo) {

    }
}
