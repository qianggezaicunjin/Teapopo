package com.teapopo.life.viewModel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.teapopo.life.BR;
import com.teapopo.life.R;
import com.teapopo.life.model.article.Article;
import com.teapopo.life.model.article.ArticleItemModel;
import com.teapopo.life.model.article.likearticle.HomeLikeArticle;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.util.CustomToast;
import com.teapopo.life.util.ViewUtils;
import com.teapopo.life.view.adapter.gridview.NineImageGridAdapter;
import com.teapopo.life.view.customView.RequestView;

import timber.log.Timber;

/**
 * Created by louiszgm on 2016/5/27.
 */
public class ArticleItemViewModel extends BaseObservable implements RequestView<ModelAction> {
    private Context mContext;
    private ArticleItemModel mModel;
    @Bindable
    public Article article;


    public ArticleItemViewModel(Context context, ArticleItemModel model){
        mContext = context;
        mModel = model;
        mModel.setView(this);
    }

    public View.OnClickListener getOnClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               switch (v.getId()){
                   case R.id.tv_tagname:
                       String tagname = (String) ((TextView) v).getText();
                       Timber.d("标签%s",tagname);
                       showTagArticle(tagname);
                       break;
                   case R.id.btn_focus:
                       Timber.d("关注");
                       doFocusAction();
                       break;
                   case R.id.img_likeornot:
                       Timber.d("点赞");
                       doLikeArticle();
                       break;
                   case R.id.img_comment:
                       Timber.d("评论");
                       break;
               }
            }
        };
    }
    //点赞/取消点赞文章
    private void doLikeArticle() {
        String articleId = article.articleId;
        mModel.likeArticleOrNot(!article.isLike,articleId);
    }
    //关注文章
    private void doFocusAction() {
    }

    //根据标签名字显示对应文章
    private void showTagArticle(String tagname) {
    }

    public NineGridImageViewAdapter getNineImageAdapter(){
        return new NineImageGridAdapter<String>();
    }

    @Override
    public void onRequestFinished() {

    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        Action action = data.action;
        if(action == Action.ArticleItemModel_likeArticleOrNot){
            Timber.d("点赞");
           article.isLike = !article.isLike;
           int likenum = Integer.parseInt(article.like_num);
            if(article.isLike){
                likenum+=1;
            }else {
                likenum-=1;
            }
            article.like_num = String.valueOf(likenum);
            notifyPropertyChanged(BR.article);
        }
    }

    @Override
    public void onRequestErroInfo(String erroinfo) {
        CustomToast.makeText(mContext,erroinfo, Toast.LENGTH_SHORT).show();
    }
}
