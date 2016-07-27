package com.teapopo.life.view.adapter.recyclerview;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.databinding.ItemRecyclerviewMemberlikeBinding;
import com.teapopo.life.databinding.ItemRecyclerviewMemberpostBinding;
import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.model.article.Article;
import com.teapopo.life.model.article.memberArticle.MemberArticleModel;
import com.teapopo.life.model.memberLikes.MemberLike;
import com.teapopo.life.model.memberLikes.MemberLikesModel;
import com.teapopo.life.view.adapter.gridview.NineImageGridAdapter;
import com.teapopo.life.view.adapter.recyclerview.base.BaseRecyclerViewAdapter;
import com.teapopo.life.viewModel.Member.MemberLikeItemViewModel;
import com.teapopo.life.viewModel.Member.MemberPostItemViewModel;

import java.util.List;

import timber.log.Timber;

/**
 * Created by lhq on 2016/7/19.
 */

public class MemberLikeAdapter extends BaseRecyclerViewAdapter<BaseEntity,MemberLikeAdapter.LikeMemberViewHolder> {

    public MemberLikeAdapter(Context context, List<BaseEntity> data) {
        super(context, data);
    }

    @Override
    public LikeMemberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return LikeMemberViewHolder.CreateLikeMemberViewHolder(ItemRecyclerviewMemberlikeBinding.inflate(layoutInflater));
    }

    @Override
    public void onBindViewHolder(LikeMemberViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ItemRecyclerviewMemberlikeBinding binding=(ItemRecyclerviewMemberlikeBinding) holder.itemView.getTag();
        MemberLikeItemViewModel viewModel=new MemberLikeItemViewModel(new MemberLikesModel(mContext));
        MemberLike memberLike= (MemberLike) data.get(position);
        viewModel.memberLike= memberLike;
        holder.setViewModel(viewModel);
        //设置九宫格的图片显示
        NineImageGridAdapter adapter = new NineImageGridAdapter();
        binding.gvNineimage.setAdapter(adapter);
        binding.gvNineimage.setImagesData(memberLike.getImgUrl());
    }

    public static class LikeMemberViewHolder extends RecyclerView.ViewHolder{

        public static LikeMemberViewHolder CreateLikeMemberViewHolder(ViewDataBinding binding){
            return new LikeMemberViewHolder(binding.getRoot(),binding);
        }

        public LikeMemberViewHolder(View view, ViewDataBinding binding) {
            super(view);
            itemView.setTag(binding);
        }
        public void setViewModel(MemberLikeItemViewModel viewModel){
            ItemRecyclerviewMemberlikeBinding binding=(ItemRecyclerviewMemberlikeBinding) itemView.getTag();
            binding.setViewModel(viewModel);
            binding.executePendingBindings();
        }
    }
}
