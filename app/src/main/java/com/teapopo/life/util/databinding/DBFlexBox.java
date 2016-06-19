package com.teapopo.life.util.databinding;

import android.databinding.BindingAdapter;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.flexbox.FlexboxLayout;
import com.squareup.picasso.Picasso;
import com.teapopo.life.R;
import com.teapopo.life.model.AuthorInfo;
import com.teapopo.life.util.DataUtils;
import com.teapopo.life.view.adapter.flexbox.ArticleFansAdapter;
import com.teapopo.life.view.adapter.viewpager.ArticleInfoImageAdapter;
import com.teapopo.life.view.customView.FlexBox.FlexBoxWithAdapter;
import com.teapopo.life.view.customView.HackyViewPager;

import java.util.List;

import timber.log.Timber;

/**
 * Created by louiszgm-pc on 2016/6/19.
 */
public class DBFlexBox {

    @BindingAdapter({"articleInfo"})
    public static void addArticleInfoFans(FlexBoxWithAdapter flexboxLayout, List<AuthorInfo> member_like) {
        Timber.d("addArticleInfoFans");
        ArticleFansAdapter articleFansAdapter = (ArticleFansAdapter) flexboxLayout.getAdapter();
        articleFansAdapter.setDataSource(member_like,true);
        articleFansAdapter.notifyDataSetChanged();
    }
}
