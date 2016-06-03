package com.teapopo.life.view.adapter.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.R;
import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.view.adapter.recyclerview.base.BaseRecyclerViewAdapter;

import java.util.List;

import timber.log.Timber;

/**
 * Created by louiszgm on 2016/6/3.
 */
public class CommentListAdapter extends BaseRecyclerViewAdapter<BaseEntity,CommentListAdapter.CommentListViewHolder> {
    public CommentListAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    public CommentListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Timber.d("onCreateViewHolder");
        View view = layoutInflater.inflate(R.layout.item_comment_list,null);
        return new CommentListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentListViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

    }

    @Override
    public int getItemCount() {
        return 10;
    }


    public static class CommentListViewHolder  extends RecyclerView.ViewHolder{

        public CommentListViewHolder(View itemView) {
            super(itemView);
        }
    }
}
