package com.teapopo.life.util.databinding.refreshView;

import android.databinding.BindingAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.teapopo.life.model.AuthorInfo;
import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.model.comment.Comment;
import com.teapopo.life.util.CustomToast;
import com.teapopo.life.util.DataUtils;
import com.teapopo.life.view.adapter.flexbox.ArticleFansAdapter;
import com.teapopo.life.view.adapter.flexbox.ArticleTagsAdapter;
import com.teapopo.life.view.adapter.recyclerview.CommentListAdapter;
import com.teapopo.life.view.adapter.recyclerview.base.BaseRecyclerViewAdapter;
import com.teapopo.life.view.adapter.viewpager.ArticleInfoImageAdapter;
import com.teapopo.life.view.customView.FlexBox.FlexBoxWithAdapter;
import com.teapopo.life.view.customView.HackyViewPager;
import com.teapopo.life.view.customView.RecyclerView.SuperRecyclerView;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import timber.log.Timber;

/**
 * Created by louiszgm on 2016/6/20.
 */
public class RefreshArticleFragment {

    @BindingAdapter({"articleInfo"})
    public static void addArticleInfoComments(final SuperRecyclerView recyclerView, List<Comment> data) {
        Timber.d("addArticleInfoComments");
        final CommentListAdapter commentListAdapter = (CommentListAdapter) recyclerView.getBookendsAdapter().getWrappedAdapter();
        if(commentListAdapter.data.size()==0){
            commentListAdapter.data.addAll(data);
            recyclerView.notifyDataSetChanged();
        }else {
            Observable.from(data)
                    .filter(new Func1<Comment, Boolean>() {
                        @Override
                        public Boolean call(Comment comment) {
                            //如果包含此评论，则有可能是回复评论
                            //如果不包含，则是添加评论
                            if(!commentListAdapter.data.contains(comment)){
                                return true;
                            } else {
                                //通过comment的replyPosition参数来判断回复的评论列表的位置
                                if(comment.replyPosition!=null){
                                    Timber.d("添加回复的评论的位置是:%s",comment.replyPosition);
                                    recyclerView.notifyItemChanged(Integer.parseInt(comment.replyPosition)+1);
                                    comment.replyPosition = null;
                                }
                                return false;
                            }

                        }
                    })
                    .subscribe(new Action1<Comment>() {
                        @Override
                        public void call(Comment comment) {
                            //添加评论
                            commentListAdapter.data.add(0,comment);
                            recyclerView.notifyItemInserted(0);
                        }
                    });
        }

        recyclerView.setIsLoading(false);
    }
    @BindingAdapter({"articleInfo"})
    public static void addArticleInfoFans(FlexBoxWithAdapter flexboxLayout, List<AuthorInfo> member_like) {
        Timber.d("addArticleInfoFans");
        ArticleFansAdapter articleFansAdapter = (ArticleFansAdapter) flexboxLayout.getAdapter();
        articleFansAdapter.setDataSource(member_like,true);
        articleFansAdapter.notifyDataSetChanged();
    }
    @BindingAdapter({"articleInfo"})
    public static void addArticleInfoTags(FlexBoxWithAdapter flexboxLayout, List<String> tags) {
        Timber.d("addArticleInfoTags");
        ArticleTagsAdapter articleTagsAdapter = (ArticleTagsAdapter) flexboxLayout.getAdapter();
        articleTagsAdapter.setDataSource(tags,true);
        articleTagsAdapter.notifyDataSetChanged();
    }
    @BindingAdapter({"articleInfo"})
    public static void bindImage(HackyViewPager viewPager, List<String> data) {
        Timber.d("addArticleInfoImages");
        for(String url:data){
            if (!((ArticleInfoImageAdapter)viewPager.getAdapter()).imageUrls.contains(url)){
                ((ArticleInfoImageAdapter)viewPager.getAdapter()).imageUrls.add(url);
            }
        }
        viewPager.notifyDataSetChanged();
    }

    @BindingAdapter({"android:hint","showSoftInput"})
    public static void refreshWhenAddCommentDone(EditText editText,String hint,boolean isShow){
        Timber.d("refreshWhenAddCommentDone");
        editText.setHint(hint);
        editText.setText(null);
        if(isShow){
            editText.requestFocus();
            DataUtils.showSoftInput(editText.getContext(),editText);
        }else {
            editText.clearFocus();
            DataUtils.closeSoftInput(editText.getContext(),editText);
        }
    }
}
