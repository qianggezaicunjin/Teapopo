package com.teapopo.life.view.fragment.welfare;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import com.teapopo.life.R;
import com.teapopo.life.model.articleinfo.ArticleInfoModel;

import com.teapopo.life.view.customView.HtmlTextView.HtmlTextView;
import com.teapopo.life.view.customView.RequestView;
import com.teapopo.life.view.fragment.BaseFragment;


import timber.log.Timber;

/**
 * Created by louiszgm-pc on 2016/5/20.
 */
public class WelFareFragment extends BaseFragment implements RequestView {

    HtmlTextView tv;
    public static WelFareFragment newInstance(){
        return new WelFareFragment();
    }
    @Override
    public void onCreateBinding() {

    }

    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welfare,container,false);

        tv = (HtmlTextView) view.findViewById(R.id.tv_html);
        ArticleInfoModel model = new ArticleInfoModel(_mActivity);
        model.getArticleInfo("10245");
        model.setView(this);
        return view;
    }

    @Override
    public void setUpView() {

    }

    @Override
    public void onRequestFinished() {

    }

    @Override
    public void onRequestSuccess(Object data) {
    }

    @Override
    public void onRequestErroInfo(String erroinfo) {

    }


}
