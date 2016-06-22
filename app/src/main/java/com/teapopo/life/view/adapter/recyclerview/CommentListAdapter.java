package com.teapopo.life.view.adapter.recyclerview;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.teapopo.life.R;
import com.teapopo.life.databinding.ItemCommentListBinding;
import com.teapopo.life.injection.component.ComponentHolder;
import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.model.comment.Comment;
import com.teapopo.life.model.comment.CommentModel;
import com.teapopo.life.model.comment.Reply;
import com.teapopo.life.model.sharedpreferences.RxSpf_UserInfoSp;
import com.teapopo.life.util.rx.RxSubscriber;
import com.teapopo.life.view.adapter.recyclerview.base.BaseRecyclerViewAdapter;
import com.teapopo.life.viewModel.CommentItemViewModel;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by louiszgm on 2016/6/3.
 */
public class CommentListAdapter extends BaseRecyclerViewAdapter<Comment,CommentListAdapter.CommentListViewHolder> {

    public CompositeSubscription mCompositeSubscription = new CompositeSubscription();


    public CommentListAdapter(Context context, List<Comment> data) {
        super(context, data);
    }

    @Override
    public CommentListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ItemCommentListBinding mBinding = ItemCommentListBinding.inflate(layoutInflater);
        mBinding.setHandler(this);
        return CommentListViewHolder.createCommentListViewHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(CommentListViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        Timber.d("payloads为:%s",payloads.toString());
    }

    @Override
    public void onBindViewHolder(CommentListViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        Timber.d("onBindViewHolder");
        Comment comment = data.get(position);
        final CommentItemViewModel viewModel = new CommentItemViewModel(new CommentModel(mContext));
        ItemCommentListBinding binding = (ItemCommentListBinding) holder.itemView.getTag();
        binding.imgReplycomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.doReplyComment(RxSpf_UserInfoSp.create(mContext).userInfo().exists());
            }
        });
        binding.imgCommentZan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.doLikeComment();
            }
        });
        viewModel.setComment(comment);
        //添加回复列表
        List<Reply> replies = comment.replyList;
        if(replies.size()>0){
            addReply(replies,binding);
        }
        holder.setViewModel(viewModel);
    }


    private void addReply(List<Reply> replies,ItemCommentListBinding binding) {
        LinearLayout layout = binding.linearlayoutReplyComment;
        //每次在添加子View的时候，先清空布局
        layout.removeAllViews();
            for(Reply reply:replies){
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(0,8,0,0);
                String replyname = reply.authorInfo.nickname;
                String result = replyname+"回复:"+reply.content;
                TextView textView = new TextView(mContext);
                textView.setText(result);
                layout.addView(textView,params);
            }
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
