package com.teapopo.life.view.adapter.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.teapopo.life.R;
import com.teapopo.life.databinding.ItemRecyclerviewArticleBinding;
import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.model.article.Article;
import com.teapopo.life.model.article.ArticleItemModel;
import com.teapopo.life.util.DataUtils;
import com.teapopo.life.view.activity.ImagePagerActivity;
import com.teapopo.life.view.adapter.recyclerview.base.BaseRecyclerViewAdapter;
import com.teapopo.life.viewModel.ArticleItemViewModel;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;


/**
 * Created by Administrator on 2016/4/7 0007.
 */
public class RecommendArticleAdapter extends BaseRecyclerViewAdapter<BaseEntity, RecommendArticleAdapter.RecommendArticleViewHolder> {
    public RecommendArticleAdapter(Context context,List<BaseEntity> data) {
        super(context,data);
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
        ArticleItemViewModel mViewModel = new ArticleItemViewModel(mContext,new ArticleItemModel(mContext));
        Article post= (Article) data.get(position);
        mViewModel.article =  post;
        holder.setViewModel(mViewModel);
        //添加tag
        ItemRecyclerviewArticleBinding binding = (ItemRecyclerviewArticleBinding) holder.itemView.getTag();
        addTags(post.tags,binding);
    }
    //添加每篇文章的tag
    private void addTags(@NonNull List<String> tags, ItemRecyclerviewArticleBinding binding) {
        if(tags!=null){
            Timber.d("tags的个数为:%d",tags.size());
            binding.llRvItemTag.removeAllViews();
            //相对于自身的属性
            AttributeSet attributeSet=DataUtils.getAttributeSetFromXml(mContext, R.layout.tagname);
            //相对于父控件的属性
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(mContext,attributeSet);
            //添加tag图标
            ImageView img_tag = new ImageView(mContext);
            img_tag.setBackgroundResource(R.drawable.icon_tag);
            binding.llRvItemTag.addView(img_tag);
            //添加标签的文字
            for(String tag:tags){
                TextView tv_tag = new TextView(mContext,attributeSet);
                tv_tag.setId(R.id.tv_tagname);
                tv_tag.setText(tag);
                tv_tag.setOnClickListener(binding.getViewmodel().getOnClickListener());
                binding.llRvItemTag.addView(tv_tag,params);
            }
        }

    }

    public static class RecommendArticleViewHolder extends RecyclerView.ViewHolder{
        public static RecommendArticleViewHolder createViewHolder(ViewDataBinding binding) {
            return new RecommendArticleViewHolder(binding.getRoot(), binding);
        }

        public RecommendArticleViewHolder(View view, ViewDataBinding binding) {
            super(view);
            itemView.setTag(binding);
        }
        public void setViewModel(ArticleItemViewModel viewModel){
            ItemRecyclerviewArticleBinding binding= (ItemRecyclerviewArticleBinding) itemView.getTag();
            binding.setViewmodel(viewModel);
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
