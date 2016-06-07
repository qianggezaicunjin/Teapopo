package com.teapopo.life.viewModel.articleinfo;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.ninegridimageview.NineGridImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.utils.L;
import com.teapopo.life.BR;
import com.teapopo.life.MyApplication;
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
import com.teapopo.life.model.comment.Comment;
import com.teapopo.life.model.comment.Reply;
import com.teapopo.life.model.sharedpreferences.RxSpf_ReplyCommentSp;
import com.teapopo.life.model.toparticle.TopArticle;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.util.CustomToast;
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
        this.articleId = articleId;
        mModel.getArticleInfo(articleId);
    }

    public View.OnClickListener getOnClickListener(){
        return  new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.img_likeornot:
                        break;
                    case R.id.btn_articleinfo_publishcomment:
                        //添加评论
                        addComment();
                        break;
                }
            }
        };
    }

    private void addComment() {
        String content = mBinding.etInputcomment.getText().toString();
        if(TextUtils.isEmpty(content)){
            CustomToast.makeText(mContext,"输入内容不能为空", Toast.LENGTH_SHORT).show();
        }else {
            //回复评论  or  发表评论
            RxSpf_ReplyCommentSp rxSpf_replyCommentSp = RxSpf_ReplyCommentSp.create(mContext);
            if(rxSpf_replyCommentSp.commentId().exists()){
                //回复评论
                mModel.replyComment(rxSpf_replyCommentSp.commentId().get(),0,content);
            }else {
                //发表评论d
                mModel.addComment(articleId,0,content);
            }
        }

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
        }else if(action == Action.ArticleInfoModel_AddComment){
            Comment comment = (Comment) data.t;
            ComponentHolder.getAppComponent().rxbus().post(comment);
        }else if(action == Action.ArticleInfoModel_ReplyComment){
            Timber.d("回复成功，发送通知更新界面");
            Reply reply = (Reply) data.t;
            ComponentHolder.getAppComponent().rxbus().post(reply);
            //收起软键盘
            DataUtils.closeSoftInput(mContext,mBinding.linearlayoutInputComment);
        }
    }
    @Override
    public void onRequestErroInfo(String erroinfo) {

    }
}
