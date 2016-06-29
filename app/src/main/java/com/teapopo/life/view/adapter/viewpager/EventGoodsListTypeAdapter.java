package com.teapopo.life.view.adapter.viewpager;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.teapopo.life.model.welfare.Event;
import com.teapopo.life.model.welfare.EventGoods;
import com.teapopo.life.view.fragment.welfare.EventGoodsListFragment;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Created by louiszgm on 2016/6/28.
 */
public class EventGoodsListTypeAdapter extends FragmentStatePagerAdapter {

    private List<String> mTitles;
    private String eventId;

    public EventGoodsListTypeAdapter(FragmentManager fm, String eventId, List<String> titles) {
        super(fm);
        mTitles = titles;
        this.eventId = eventId;
    }

    @Override
    public Fragment getItem(int position) {
        Timber.d("getItem  %d",position);
        return  EventGoodsListFragment.newInstance(eventId, position+1);
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
