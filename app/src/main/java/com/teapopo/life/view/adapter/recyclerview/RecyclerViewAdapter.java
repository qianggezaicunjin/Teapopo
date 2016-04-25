package com.teapopo.life.view.adapter.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.databinding.ItemRecyclerviewArticleBinding;
import com.teapopo.life.databinding.ItemRecyclerviewHeaderBinding;
import com.teapopo.life.R;
import com.teapopo.life.model.recommendarticle.ArticleImage;
import com.teapopo.life.model.recommendarticle.RecommendArticle;
import com.teapopo.life.model.recommendarticle.RecommendData;
import com.teapopo.life.view.activity.ImagePagerActivity;
import com.teapopo.life.view.adapter.gridview.NoScrollGridAdapter;
import com.teapopo.life.viewModel.PostViewModel;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;



/**
 * Created by Administrator on 2016/4/7 0007.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private RecommendData mContents;
    private List<RecommendArticle> mPosts;//文章列表
    private List<ArticleImage> mImages;//一篇文章对应的图片的列表
    private List<String> mImageUrls;
    private Context mContext;
    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;
    static final int TYPE_FOOTER=2;

    public RecyclerViewAdapter(Context context) {
        this.mContext=context;
        this.mPosts = new ArrayList<>();
        this.mImages = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_HEADER;
            default:
                return TYPE_CELL;
        }
    }


    public void setItems(RecommendData contents){
        if(contents!=null){
            this.mContents =contents;
            mPosts =contents.recommendArticles;
        }else {
            mPosts=new ArrayList<>();
            mImages = new ArrayList<>();
        }

    }

    public void addItem(RecommendData contents){
        if(contents!=null){
            mContents=contents;
            for (int i=0;i<contents.recommendArticles.size();i++){
                RecommendArticle post=contents.recommendArticles.get(i);
                ArticleImage image=contents.articleImages.get(i);
                if(!mPosts.contains(post)){
                    mPosts.add(post);
                    mImages.add(image);
                    notifyItemInserted(mPosts.size());
                }else {
                    mPosts.set(mPosts.indexOf(post),post);
                    mImages.set(mImages.indexOf(image),image);
                    notifyItemChanged(mPosts.indexOf(post));
                }
            }
        }
    }
    @Override
    public int getItemCount() {
        //返回的item总数=文章列表个数+头布局;
        return mPosts.size()+1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        switch (viewType) {
            case TYPE_HEADER: {

                ItemRecyclerviewHeaderBinding headerBinding=DataBindingUtil.inflate(
                        LayoutInflater.from(parent.getContext()),
                        R.layout.item_recyclerview_header,
                        parent,
                        false
                );
                return new HeaderBindingHolder(headerBinding);
            }
            case TYPE_CELL: {
                ItemRecyclerviewArticleBinding articleBinding= DataBindingUtil.inflate(
                        LayoutInflater.from(parent.getContext()),
                        R.layout.item_recyclerview_article,
                        parent,
                        false
                );

                return new ArticleBindingHolder(articleBinding);
            }
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                ItemRecyclerviewHeaderBinding headerBinding=  ((HeaderBindingHolder)holder).binding;

                break;
            case TYPE_CELL:
                ItemRecyclerviewArticleBinding postBinding = ((ArticleBindingHolder)holder).binding;

                if (mContents!=null){
                      Timber.d("onBindViewHolder的position为:%d",position);
                     //这里实际的文章所在的位置=position-1,减去头布局
                      RecommendArticle post=mPosts.get(position-1);



                    //设置文章列表的图片
                      String postId = post.articleId;
                      mImageUrls=getArticleImageUrls(mImages, postId);
                      if(mImageUrls!=null){
                          NoScrollGridAdapter adapter=new NoScrollGridAdapter(mContext,mImageUrls);
                          postBinding.gvArticleImage.setAdapter(adapter);
                      }else {
                          postBinding.gvArticleImage.setVisibility(View.GONE);
                      }
                    postBinding.setViewModel(new PostViewModel(mContext, mPosts.get(position-1),mContents));
                  }


                break;
        }
    }

    /**
     * 返回推荐文章列表的单条文章的图片地址
     * @param images
     * @param postId
     */
    private List<String> getArticleImageUrls(List<ArticleImage> images, String postId) {
        ArrayList<String> imageUrls;
        for (int i=0;i<images.size();i++){
            String id=images.get(i).articleId;
            if (id.equals(postId)){
                imageUrls= (ArrayList<String>) images.get(i).articleImageUrls;
                return imageUrls;
            }
        }
        return null;
    }

    /**
     * 打开图片查看器
     *
     * @param position
     * @param urls2
     */
    protected void imageBrower(int position, ArrayList<String> urls2) {
        Intent intent = new Intent(mContext, ImagePagerActivity.class);
        // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls2);
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
        mContext.startActivity(intent);
    }

    public static class ArticleBindingHolder extends RecyclerView.ViewHolder{
        private ItemRecyclerviewArticleBinding binding;

        public ArticleBindingHolder(ItemRecyclerviewArticleBinding binding){
            super(binding.cardViewArticle);
            this.binding=binding;
        }
    }

    public static class HeaderBindingHolder extends RecyclerView.ViewHolder{
        private ItemRecyclerviewHeaderBinding binding;

        public HeaderBindingHolder(ItemRecyclerviewHeaderBinding binding){
            super(binding.cardViewHeader);
            this.binding=binding;
        }
    }
}
