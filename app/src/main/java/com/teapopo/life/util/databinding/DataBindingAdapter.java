package com.teapopo.life.util.databinding;

import android.databinding.BindingAdapter;
import android.support.v4.widget.SwipeRefreshLayout;

import com.teapopo.life.view.customView.RecyclerView.SuperRecyclerView;

/**
 * Created by louiszgm on 2016/5/4.
 */
public class DataBindingAdapter {
    @BindingAdapter({"isLoading"})
    public static void isLoading(SwipeRefreshLayout swipeRefreshLayout, boolean isLoading) {
        swipeRefreshLayout.setRefreshing(isLoading);
    }
}
