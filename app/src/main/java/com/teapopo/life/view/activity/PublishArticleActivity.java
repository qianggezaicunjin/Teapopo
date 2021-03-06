package com.teapopo.life.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.teapopo.life.R;
import com.teapopo.life.injection.component.ComponentHolder;
import com.teapopo.life.injection.component.activity.DaggerPublishArticleActivityComponent;
import com.teapopo.life.injection.component.activity.PublishArticleActivityComponent;
import com.teapopo.life.injection.component.fragment.PublishArticleFragmentComponent;
import com.teapopo.life.injection.module.ActivityModule;
import com.teapopo.life.injection.module.activity.PublishAriticleActivityModule;
import com.teapopo.life.injection.module.fragment.PublishArticleFragmentModule;
import com.teapopo.life.model.imageselect.ImageConfig;
import com.teapopo.life.view.fragment.PublishArticle.ImageSelectorFragment;
import com.teapopo.life.view.fragment.PublishArticle.PublishArticleFragment;


import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by louiszgm on 2016/6/8.
 */
public class PublishArticleActivity extends SwipeBackBaseActivity{

    PublishArticleActivityComponent mComponent;

    //打开图片选择器的请求码
    private static final int REQUEST_CODE = 1000;

    @Inject
    ImageConfig.Builder builder;

    ArrayList<String> pathList = new ArrayList<>();
    public static Intent getStartIntent(Context context) {
        return new Intent(context, PublishArticleActivity.class);
    }

    @Override
    public void onCreateBinding() {
        mComponent = DaggerPublishArticleActivityComponent.builder()
                .applicationComponent(ComponentHolder.getAppComponent())
                .activityModule(new ActivityModule(this))
                .publishAriticleActivityModule(new PublishAriticleActivityModule())
                .build();
        mComponent.inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_article);
        loadRootFragment(R.id.framelayout_publish_article,ImageSelectorFragment.newInstance(builder));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }



    public PublishArticleActivityComponent getComponent(){
        return mComponent;
    }

    public PublishArticleFragmentComponent getFragmentComponent(){
        return mComponent.publishArticleFragmentComponent(new PublishArticleFragmentModule());
    }
}
