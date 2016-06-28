package com.teapopo.life.view.adapter.viewpager;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.teapopo.life.model.welfare.Event;
import com.teapopo.life.model.welfare.EventGoods;
import com.teapopo.life.view.fragment.welfare.EventGoodsListFragment;

import java.util.List;

/**
 * Created by louiszgm on 2016/6/28.
 */
public class EventGoodsListTypeAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mFragments;
    private List<String> mTitles;
    private Event event;
    public EventGoodsListTypeAdapter(FragmentManager fm, Event event, List<String> titles) {
        super(fm);
        mTitles = titles;
        this.event = event;
    }

    @Override
    public Fragment getItem(int position) {
        return EventGoodsListFragment.newInstance(event);
    }


    @Override
    public int getCount() {
        return mTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return mTitles.get(position);
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}
