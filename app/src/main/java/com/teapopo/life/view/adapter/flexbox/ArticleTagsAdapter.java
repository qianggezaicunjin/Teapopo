package com.teapopo.life.view.adapter.flexbox;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teapopo.life.R;
import com.teapopo.life.view.adapter.LBaseAdapter;

/**
 * Created by louiszgm on 2016/6/20.
 */
public class ArticleTagsAdapter extends LBaseAdapter<String,LBaseAdapter.BaseViewHolder>{

    public ArticleTagsAdapter(Context context) {
        super(context);
    }

    @Override
    protected BaseViewHolder createViewHolder(int position, ViewGroup parent) {
        return new BaseViewHolder(View.inflate(getContext(), R.layout.tagname,null));
    }

    @Override
    protected void bindViewHolder(BaseViewHolder holder, int position, String data) {
        TextView tv_tag = (TextView) holder.getRootView();
        tv_tag.setText(data);
    }
}
