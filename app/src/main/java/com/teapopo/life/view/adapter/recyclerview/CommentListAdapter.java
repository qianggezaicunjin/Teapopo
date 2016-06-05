package com.teapopo.life.view.adapter.recyclerview;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.R;
import com.teapopo.life.databinding.ItemCommentListBinding;
import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.model.comment.Comment;
import com.teapopo.life.model.comment.CommentModel;
import com.teapopo.life.view.adapter.recyclerview.base.BaseRecyclerViewAdapter;
import com.teapopo.life.viewModel.CommentItemViewModel;

import java.util.List;

import timber.log.Timber;

/**
 * Created by louiszgm on 2016/6/3.
 */
public class CommentListAdapter extends BaseRecyclerViewAdapter<Comment,CommentListAdapter.CommentListViewHolder> {
    public CommentListAdapter(Context context, List<Comment> data) {
        super(context, data);
    }

    @Override
    public CommentListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Timber.d("onCreateViewHolder");
        ItemCommentListBinding binding = ItemCommentListBinding.inflate(layoutInflater);
        return CommentListViewHolder.createCommentListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(CommentListViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        Comment comment = (Comment) data.get(position);
        CommentItemViewModel viewModel = new CommentItemViewModel(mContext,new CommentModel(mContext));
        viewModel.comment = comment;
        holder.setViewModel(viewModel);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public static class CommentListViewHolder  extends RecyclerView.ViewHolder{

        public static CommentListViewHolder createCommentListViewHolder(ViewDataBinding binding){
            return new CommentListViewHolder(binding.getRoot(),binding);
        }
        public CommentListViewHolder(View itemView,ViewDataBinding binding) {
            super(itemView);
            itemView.setTag(binding);
        }

        public void setViewModel(CommentItemViewModel viewModel){
            ItemCommentListBinding binding = (ItemCommentListBinding) itemView.getTag();
            binding.setViewmodel(viewModel);
            binding.executePendingBindings();
        }
    }
}
