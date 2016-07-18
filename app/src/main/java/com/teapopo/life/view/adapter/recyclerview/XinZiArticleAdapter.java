package com.teapopo.life.view.adapter.recyclerview;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import com.teapopo.life.R;

import com.teapopo.life.databinding.ItemRecyclerviewXinziArticleBinding;
import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.model.article.Article;
import com.teapopo.life.model.article.ArticleItemModel;
import com.teapopo.life.view.adapter.recyclerview.base.BaseRecyclerViewAdapter;
import com.teapopo.life.view.fragment.CommentList.CommentListFragment;
import com.teapopo.life.viewModel.ArticleItemViewModel;

import me.yokeyword.fragmentation.SupportActivity;
import timber.log.Timber;

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
        return XinZiViewHolder.CreateXinZiArticleViewHolder(ItemRecyclerviewXinziArticleBinding.inflate(layoutInflater));
    }

    @Override
    public void onBindViewHolder(XinZiViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ItemRecyclerviewXinziArticleBinding binding = (ItemRecyclerviewXinziArticleBinding) holder.itemView.getTag();
        ArticleItemViewModel viewModel = new ArticleItemViewModel(new ArticleItemModel(mContext));
        Article article = (Article) data.get(position);
        viewModel.article = article;
        holder.setViewModel(viewModel);
        //设置点击事件
        XinZiArticleItemClickListener xinZiArticleItemClickListener =new XinZiArticleItemClickListener(viewModel);
        binding.btnFocus.setOnClickListener(xinZiArticleItemClickListener);
        binding.imgUser.setOnClickListener(xinZiArticleItemClickListener);
        binding.imgComment.setOnClickListener(xinZiArticleItemClickListener);
        binding.imgLikeornot.setOnClickListener(xinZiArticleItemClickListener);
    }

    class XinZiArticleItemClickListener implements View.OnClickListener{
        private ArticleItemViewModel mViewModel;
        public XinZiArticleItemClickListener(ArticleItemViewModel viewModel){
            mViewModel = viewModel;
        }
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_focus:
                    Timber.d("关注");
                    mViewModel.doFocusAction();
                    break;
                case R.id.img_likeornot:
                    Timber.d("点赞");
                    mViewModel.doLikeArticle();
                    break;
                case R.id.img_comment:
                    Timber.d("评论");
                    ((SupportActivity)mContext).start(CommentListFragment.newInstance(mViewModel.article.articleId,mViewModel.article.title,"posts"));
                    break;
            }
        }
    }

    public static class XinZiViewHolder extends RecyclerView.ViewHolder{

        public static XinZiViewHolder CreateXinZiArticleViewHolder(ViewDataBinding binding){
            return new XinZiViewHolder(binding.getRoot(),binding);
        }
        public XinZiViewHolder(View view,ViewDataBinding binding) {
            super(view);
            itemView.setTag(binding);
        }

        public void setViewModel(ArticleItemViewModel viewModel){
            ItemRecyclerviewXinziArticleBinding binding = (ItemRecyclerviewXinziArticleBinding) itemView.getTag();
            binding.setViewmodel(viewModel);
            binding.executePendingBindings();
        }
    }
}
