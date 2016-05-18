package com.teapopo.life.view.fragment.Home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.R;
import com.teapopo.life.view.adapter.viewpager.TabFragmentAdapter;
import com.teapopo.life.view.fragment.BaseFragment;
import com.teapopo.life.viewModel.ToolBarViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by louiszgm on 2016/4/12 0012.
 * 首页
 */
public class HomeFragment extends BaseFragment {
    @Bind(R.id.toolbar_home)
    Toolbar mToolbar;
    @Bind(R.id.tabs)
    TabLayout mTabLayout;
    @Bind(R.id.viewPager_home)
    ViewPager mViewPager;


    public static HomeFragment newInstance() {
        return new HomeFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateBinding() {

    }

    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void setUpView() {
        setupAppBar();
    }

    /**
     * 设置首页的顶部
     */
    private void setupAppBar() {
        mToolbar.setNavigationIcon(R.drawable.icon_search);

        //设置滑动标签页
        List<String> titles=new ArrayList<>();

        titles.add(getResources().getString(R.string.recommend_article));
        titles.add(getResources().getString(R.string.like_article));
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(1)));

        List<Fragment> fragmentList = new ArrayList<>();
        RecommendArticleFragment recommendArticleFragment= RecommendArticleFragment.newInstance();
        LikeArticleFragment likeArticleFragment=LikeArticleFragment.newInstance();
        fragmentList.add(recommendArticleFragment);
        fragmentList.add(likeArticleFragment);

        //如果在fragment里面嵌套的viewpager里面再嵌套fragment,则需要getChildFragmentManager()
        TabFragmentAdapter tabAdapter=new TabFragmentAdapter(getChildFragmentManager(),fragmentList,titles);
        mViewPager.setAdapter(tabAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

}
