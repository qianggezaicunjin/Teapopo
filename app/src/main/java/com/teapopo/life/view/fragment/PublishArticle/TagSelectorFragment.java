package com.teapopo.life.view.fragment.PublishArticle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.R;
import com.teapopo.life.model.article.publisharticle.PublishArticleData;
import com.teapopo.life.view.fragment.SwipeBackBaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.gujun.android.taggroup.TagGroup;

/**
 * Created by louiszgm on 2016/7/20.
 */
public class TagSelectorFragment extends SwipeBackBaseFragment {

    @Bind(R.id.hottag_group)
    TagGroup mHotTags;
    @Bind(R.id.tag_group)
    TagGroup mTagGroup;
    @Bind(R.id.toolbar_tag_selector)
    Toolbar mToolBar;

    private PublishArticleData publishArticleData;
    public static TagSelectorFragment newInstance(PublishArticleData publishArticleData){
        TagSelectorFragment tagSelectorFragment = new TagSelectorFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("publishArticleData",publishArticleData);
        tagSelectorFragment.setArguments(bundle);
        return tagSelectorFragment;
    }
    @Override
    public void onCreateBinding() {
        publishArticleData = getArguments().getParcelable("publishArticleData");
    }

    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_tag,null);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void setUpView() {
        setUpToolBar();
        setUpHotTags();
    }

    private void setUpToolBar() {
        mToolBar.setTitle("选择标签");
        mToolBar.inflateMenu(R.menu.menu_next_step);
        mToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                publishArticleData.tags = mTagGroup.getTags();
                start(PublishArticleFragment.newInstance(publishArticleData));
                return true;
            }
        });
    }

    private void setUpHotTags() {
        mHotTags.setTags(new String[]{"评测"});
    }
}
