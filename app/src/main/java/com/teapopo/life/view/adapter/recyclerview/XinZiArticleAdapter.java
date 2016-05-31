package com.teapopo.life.view.adapter.recyclerview;

import android.content.ClipData;
import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.databinding.ItemRecyclerviewXinziArticleBinding;
import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.model.article.Article;
import com.teapopo.life.view.adapter.recyclerview.base.BaseRecyclerViewAdapter;

import java.util.List;

/**
 * Created by louiszgm on 2016/5/31.
 */
public class XinZiArticleAdapter extends BaseRecyclerViewAdapter<BaseEntity,XinZiArticleAdapter.XinZiViewHolder>{


    public XinZiArticleAdapter(Context context, List<BaseEntity> data) {
        super(context, data);
    }

    @Override
    public XinZiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemRecyclerviewXinziArticleBinding binding = ItemRecyclerviewXinziArticleBinding.inflate(layoutInflater);
        return XinZiViewHolder.CreateXinZiArticleViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(XinZiViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        Article article = (Article) data.get(position);
        holder.setArticle(article);
    }

    public static class XinZiViewHolder extends RecyclerView.ViewHolder{

        public static XinZiViewHolder CreateXinZiArticleViewHolder(ViewDataBinding binding){
            return new XinZiViewHolder(binding.getRoot(),binding);
        }
        public XinZiViewHolder(View view,ViewDataBinding binding) {
            super(view);
            itemView.setTag(binding);
        }

        public void setArticle(Article article){
            ItemRecyclerviewXinziArticleBinding binding = (ItemRecyclerviewXinziArticleBinding) itemView.getTag();
            binding.setArticle(article);
            binding.executePendingBindings();
        }
    }
}
