package com.teapopo.life.view.adapter.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.databinding.ItemRecyclerviewArticleBinding;
import com.teapopo.life.databinding.ItemRecyclerviewHeaderBinding;
import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.model.recommendarticle.ArticleImage;
import com.teapopo.life.model.recommendarticle.RecommendArticle;
import com.teapopo.life.model.recommendarticle.RecommendData;
import com.teapopo.life.view.activity.ImagePagerActivity;
import com.teapopo.life.view.adapter.gridview.NoScrollGridAdapter;
import com.teapopo.life.viewModel.RecomendArticleViewModel;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;



/**
 * Created by Administrator on 2016/4/7 0007.
 */
public class RecommendArticleAdapter extends BaseRecyclerViewAdapter<BaseEntity, RecommendArticleAdapter.RecommendArticleViewHolder> {

    private List<ArticleImage> mImages;//一篇文章对应的图片的列表
    private List<String> mImageUrls;

    public RecommendArticleAdapter(Context context,List<BaseEntity> data) {
        super(context,data);
        this.mImages = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public RecommendArticleAdapter.RecommendArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return RecommendArticleViewHolder.createViewHolder(ItemRecyclerviewArticleBinding.inflate(layoutInflater));
    }


    @Override
    public void onBindViewHolder(RecommendArticleAdapter.RecommendArticleViewHolder holder, int position) {
        super.onBindViewHolder(holder,position);
        RecommendArticle post= (RecommendArticle) data.get(position);
        mImageUrls=post.articleImage.articleImageUrls;
        holder.setArticle(post);
        ItemRecyclerviewArticleBinding binding=(ItemRecyclerviewArticleBinding)holder.mBinding;
        if(mImageUrls!=null){
            NoScrollGridAdapter adapter=new NoScrollGridAdapter(mContext,mImageUrls);
            binding.gvArticleImage.setAdapter(adapter);
        }else {
            binding.gvArticleImage.setVisibility(View.GONE);
        }
    }
    public static class RecommendArticleViewHolder extends RecyclerView.ViewHolder{
          ViewDataBinding mBinding;
        public static RecommendArticleViewHolder createViewHolder(ViewDataBinding binding) {
            return new RecommendArticleViewHolder(binding.getRoot(), binding);
        }

        public RecommendArticleViewHolder(View view, ViewDataBinding binding) {
            super(view);
            itemView.setTag(binding);
        }
        public void setArticle(RecommendArticle recommendArticle){
            ItemRecyclerviewArticleBinding binding= (ItemRecyclerviewArticleBinding) itemView.getTag();
            mBinding = binding;
            binding.setRecommendArticle(recommendArticle);
            binding.executePendingBindings();
        }
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



}
