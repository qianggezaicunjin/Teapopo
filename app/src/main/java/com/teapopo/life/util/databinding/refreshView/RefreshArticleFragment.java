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

import timber.log.Timber;

/**
 * Created by louiszgm on 2016/6/20.
 */
public class RefreshArticleFragment {

    @BindingAdapter({"articleInfo"})
    public static void addArticleInfoComments(SuperRecyclerView recyclerView, List<Comment> data) {
        Timber.d("addArticleInfoComments");
        CommentListAdapter commentListAdapter = (CommentListAdapter) recyclerView.getBookendsAdapter().getWrappedAdapter();
        for(Comment comment:data){
            if(!commentListAdapter.data.contains(comment)){
                commentListAdapter.data.add(0,comment);
            }
        }
        recyclerView.notifyDataSetChanged();
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
            DataUtils.toggleSoftInput(editText.getContext(),editText);
        }else {
            editText.clearFocus();
            DataUtils.closeSoftInput(editText.getContext(),editText);
        }



    }
}
