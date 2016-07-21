package com.teapopo.life.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.teapopo.life.R;
import com.teapopo.life.injection.component.ComponentHolder;
import com.teapopo.life.injection.component.activity.ArticleDetailActivityComponent;
import com.teapopo.life.injection.component.activity.DaggerArticleDetailActivityComponent;
import com.teapopo.life.injection.module.ActivityModule;
import com.teapopo.life.injection.module.activity.ArticleDetailActivityModule;
import com.teapopo.life.view.fragment.ArticleInfoFragment;

/**
 * Created by louiszgm on 2016/6/2.
 */
public class ArticleDetailActivity extends SwipeBackBaseActivity {
    private ArticleDetailActivityComponent mComponent;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, ArticleDetailActivity.class);
    }
    @Override
    public void onCreateBinding() {
        mComponent = DaggerArticleDetailActivityComponent.builder()
                    .applicationComponent(ComponentHolder.getAppComponent())
                    .activityModule(new ActivityModule(this))
                    .articleDetailActivityModule(new ArticleDetailActivityModule())
                    .build();
    }

    public ArticleDetailActivityComponent getComponent(){
        return this.mComponent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        loadRootFragment(R.id.framelayout_article_detail,ArticleInfoFragment.newInstance(getIntent().getStringExtra("articleId")));
    }

}
