package com.teapopo.life.view.adapter.recyclerview;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.databinding.ItemRecyclerviewMemberpostBinding;
import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.model.article.Article;
import com.teapopo.life.model.article.memberArticle.MemberArticleModel;
import com.teapopo.life.view.adapter.gridview.NineImageGridAdapter;
import com.teapopo.life.view.adapter.recyclerview.base.BaseRecyclerViewAdapter;
import com.teapopo.life.viewModel.Member.MemberPostItemViewModel;

import java.util.List;

/**
 * Created by lhq on 2016/7/19.
 */

public class MemberAdapter extends BaseRecyclerViewAdapter<BaseEntity,MemberAdapter.HomePageViewHolder> {

    public MemberAdapter(Context context, List<BaseEntity> data) {
        super(context, data);
    }

    @Override
    public HomePageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return HomePageViewHolder.CreateHomepagerViewHolder(ItemRecyclerviewMemberpostBinding.inflate(layoutInflater));
    }

    @Override
    public void onBindViewHolder(HomePageViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ItemRecyclerviewMemberpostBinding binding=(ItemRecyclerviewMemberpostBinding) holder.itemView.getTag();
        MemberPostItemViewModel viewModel=new MemberPostItemViewModel(new MemberArticleModel(mContext));
        Article article= (Article) data.get(position);
        viewModel.memberArticle= article;
        holder.setViewModel(viewModel);
        //设置九宫格的图片显示
        NineImageGridAdapter adapter = new NineImageGridAdapter();
        binding.gvNineimage.setAdapter(adapter);
        binding.gvNineimage.setImagesData(article.imageUrls);
    }

    public static class HomePageViewHolder extends RecyclerView.ViewHolder{

        public static HomePageViewHolder CreateHomepagerViewHolder(ViewDataBinding binding){
            return new HomePageViewHolder(binding.getRoot(),binding);
        }

        public HomePageViewHolder(View view, ViewDataBinding binding) {
            super(view);
            itemView.setTag(binding);
        }
        public void setViewModel(MemberPostItemViewModel viewModel){
            ItemRecyclerviewMemberpostBinding binding=(ItemRecyclerviewMemberpostBinding) itemView.getTag();
            binding.setViewModel(viewModel);
            binding.executePendingBindings();
        }
    }
}
