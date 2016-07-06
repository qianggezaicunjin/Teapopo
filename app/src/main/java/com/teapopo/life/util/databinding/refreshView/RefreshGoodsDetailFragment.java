package com.teapopo.life.util.databinding.refreshView;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.teapopo.life.R;
import com.teapopo.life.data.remote.NetWorkService;
import com.teapopo.life.view.adapter.viewpager.ArticleInfoImageAdapter;
import com.teapopo.life.view.customView.HackyViewPager;
import com.teapopo.life.view.customView.HtmlTextView.HtmlTextView;

import java.util.List;

import timber.log.Timber;

/**
 * Created by louiszgm on 2016/7/5.
 */
public class RefreshGoodsDetailFragment {

    @BindingAdapter({"goodsInfo"})
    public static void setGuideContent(HtmlTextView textView, String html){
        Timber.d("加载的文章信息为:%s",html);
        if (html==null){
            textView.setHtmlFromString("正在加载....",false);
        }else {
            textView.setHtmlFromString(html,false);
        }

    }

    @BindingAdapter({"goodsInfo"})
    public static void bindGoodsImage(HackyViewPager viewPager, List<String> data) {
        Timber.d("addArticleInfoImages");
        if(data!=null){
            for(String url:data){
                if (!((ArticleInfoImageAdapter)viewPager.getAdapter()).imageUrls.contains(url)){
                    ((ArticleInfoImageAdapter)viewPager.getAdapter()).imageUrls.add(url);
                }
            }
        }

        viewPager.notifyDataSetChanged();
    }
}
