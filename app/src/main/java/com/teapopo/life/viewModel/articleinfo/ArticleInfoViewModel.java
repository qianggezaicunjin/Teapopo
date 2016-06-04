package com.teapopo.life.viewModel.articleinfo;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
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
import com.teapopo.life.databinding.ItemArticleinfoTopviewBinding;
import com.teapopo.life.databinding.ItemRecyclerviewArticleBinding;
import com.teapopo.life.injection.component.ComponentHolder;
import com.teapopo.life.model.AuthorInfo;
import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.model.articleinfo.ArticleInfo;
import com.teapopo.life.model.articleinfo.ArticleInfoModel;
import com.teapopo.life.model.toparticle.TopArticle;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.util.DataUtils;
import com.teapopo.life.view.adapter.gridview.NineImageGridAdapter;
import com.teapopo.life.view.adapter.recyclerview.CommentListAdapter;
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
    public ArticleInfo articleInfo =new ArticleInfo();


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
            Timber.d("评论的个数为:%d",articleInfo.commentList.size());
            ComponentHolder.getAppComponent().rxbus().post(this.articleInfo);
            notifyPropertyChanged(BR.articleInfo);
        }
    }
    @Override
    public void onRequestErroInfo(String erroinfo) {

    }
}
