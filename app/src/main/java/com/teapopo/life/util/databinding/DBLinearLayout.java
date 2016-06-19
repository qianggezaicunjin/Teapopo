package com.teapopo.life.util.databinding;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.squareup.picasso.Picasso;
import com.teapopo.life.R;
import com.teapopo.life.model.AuthorInfo;
import com.teapopo.life.util.DataUtils;
import com.teapopo.life.view.activity.ArticleDetailActivity;

import java.util.List;

import timber.log.Timber;

/**
 * Created by louiszgm-pc on 2016/6/19.
 */
public class DBLinearLayout {

    @BindingAdapter({"articleInfo"})
    public static void addArticleInfoTags(LinearLayout linearLayout, List<String> tags) {
        Timber.d("addArticleInfoFans");
        Context context = linearLayout.getContext();
        linearLayout.removeAllViews();
        //相对于自身的属性
        AttributeSet attributeSet = DataUtils.getAttributeSetFromXml(linearLayout.getContext(), R.layout.tagname);
        //相对于父控件的属性
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(linearLayout.getContext(), attributeSet);
        //添加tag图标
        ImageView img_tag = new ImageView(linearLayout.getContext());
        img_tag.setBackgroundResource(R.drawable.icon_tag);
        linearLayout.addView(img_tag);
        //添加标签的文字
        for (String tag : tags) {
            TextView tv_tag = new TextView(linearLayout.getContext(), attributeSet);
            tv_tag.setId(R.id.tv_tagname);
            tv_tag.setText(tag);
            linearLayout.addView(tv_tag, params);
        }
    }
}
