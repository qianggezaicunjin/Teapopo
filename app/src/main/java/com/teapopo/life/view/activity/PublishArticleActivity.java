package com.teapopo.life.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.teapopo.life.R;
import com.teapopo.life.injection.component.ComponentHolder;
import com.teapopo.life.injection.component.activity.DaggerPublishArticleActivityComponent;
import com.teapopo.life.injection.component.activity.PublishArticleActivityComponent;
import com.teapopo.life.injection.module.ActivityModule;
import com.teapopo.life.injection.module.activity.PublishAriticleActivityModule;
import com.teapopo.life.view.fragment.PublishArticle.PublishArticleFragment;

/**
 * Created by louiszgm on 2016/6/8.
 */
public class PublishArticleActivity extends SwipeBackBaseActivity{

    PublishArticleActivityComponent mComponent;

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
        start(PublishArticleFragment.newInstance());
    }

    @Override
    protected int setContainerId() {
        return R.id.framelayout_publish_article;
    }

    public PublishArticleActivityComponent getComponent(){
        return mComponent;
    }
}
