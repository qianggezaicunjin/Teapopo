package com.teapopo.life.view.adapter.flexbox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.teapopo.life.R;
import com.teapopo.life.model.AuthorInfo;
import com.teapopo.life.view.adapter.LBaseAdapter;

import java.util.List;

/**
 * Created by louiszgm-pc on 2016/6/20.
 */
public class ArticleFansAdapter extends LBaseAdapter<AuthorInfo,LBaseAdapter.BaseViewHolder> {

    public ArticleFansAdapter(Context context) {
        super(context);
    }

    @Override
    protected BaseViewHolder createViewHolder(int position, ViewGroup parent) {
        return new BaseViewHolder(View.inflate(getContext(), R.layout.memberavatar,null));
    }

    @Override
    protected void bindViewHolder(BaseViewHolder holder, int position, AuthorInfo data) {
        ImageView imageView = (ImageView) holder.getRootView();
        String url = data.getAvatarUrl();
        Picasso.with(getContext()).load(url).into(imageView);
    }
}
