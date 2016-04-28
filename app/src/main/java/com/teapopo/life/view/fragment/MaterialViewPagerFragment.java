package com.teapopo.life.view.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.teapopo.life.R;
import com.teapopo.life.view.fragment.Home.RecommendArticleFragment;

import butterknife.Bind;
import butterknife.ButterKnife;



/**
 * Created by Administrator on 2016/4/7 0007.
 */
public class MaterialViewPagerFragment extends Fragment {
    @Bind(R.id.materialViewPager)
    MaterialViewPager materialViewPager;

    Toolbar toolbar;
       public static MaterialViewPagerFragment newInstance(){
           MaterialViewPagerFragment materialViewPagerFragment=new MaterialViewPagerFragment();


           return materialViewPagerFragment;
       }


    private  void setupToolbar() {
        toolbar=materialViewPager.getToolbar();
        if (toolbar != null) {
            ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);


            final ActionBar actionBar =((AppCompatActivity)getActivity()).getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayShowTitleEnabled(true);
                actionBar.setDisplayUseLogoEnabled(false);
                actionBar.setHomeButtonEnabled(true);
            }
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView=inflater.inflate(R.layout.fragment_viewpager,container,false);
        ButterKnife.bind(this, fragmentView);
         setupToolbar();
        setupViewPager();
        return fragmentView;
    }

    private void setupViewPager() {
        materialViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position%2){
//                    case 0:
//                        return
//                    case 1:
//                        return
                    default:
                            return RecommendArticleFragment.newInstance();
                }

            }

            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position%2){
                    case 0:
                        return "推荐";
                    case 1:
                        return "喜欢";
                }
                return "";
            }
        });

        materialViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page){
                    case 0:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.green,
                                "https://fs01.androidpit.info/a/63/0e/android-l-wallpapers-630ea6-h900.jpg");
                    case 1:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.blue,
                                "http://cdn1.tnwcdn.com/wp-content/blogs.dir/1/files/2014/06/wallpaper_51.jpg");
                }
                return null;
            }
        });

        materialViewPager.getViewPager().setOffscreenPageLimit(materialViewPager.getViewPager().getAdapter().getCount());
        materialViewPager.getPagerTitleStrip().setViewPager(materialViewPager.getViewPager());

        View logo=materialViewPager.findViewById(R.id.logo_white);
        if (logo != null)
            logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    materialViewPager.notifyHeaderChanged();
                    Toast.makeText(getActivity().getApplicationContext(), "Yes, the title is clickable", Toast.LENGTH_SHORT).show();
                }
            });
    }
}
