package com.teapopo.life.util.databinding;

import android.databinding.BindingAdapter;

import com.teapopo.life.R;
import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.view.adapter.recyclerview.BaseRecyclerViewAdapter;
import com.teapopo.life.view.customView.RecyclerView.SuperRecyclerView;

import java.util.List;

import timber.log.Timber;

/**
 * Created by louiszgm-pc on 2016/5/2.
 */
public class DBRecyclerView {
    public static int SHOW_FOOTER = 0;

    public static int HIDE_FOOTER = 1;

    @BindingAdapter({"bind:adapter"})
    public static void bindAdapter(SuperRecyclerView recyclerView, BaseRecyclerViewAdapter adapter) {
        Timber.d("设置recyclerview的adapter");
        recyclerView.setAdapter(adapter);
        recyclerView.setPageFooter(R.layout.layout_loading_footer);
    }

    @BindingAdapter({"bind:data"})
    public static void bindData(SuperRecyclerView recyclerView, List<BaseEntity> data) {
        Timber.d("recyclerview的数据集改变");
        recyclerView.notifyDataSetChanged();
        recyclerView.setIsLoading(false);
    }

    @BindingAdapter({"isLoading"})
    public static void isLoading(SuperRecyclerView recyclerView, boolean isLoading) {
        recyclerView.setIsLoading(isLoading);
    }

    @BindingAdapter({"footerStatus"})
    public static void footerStatus(SuperRecyclerView recyclerView, int footerStatus) {
        if (footerStatus == SHOW_FOOTER) {
            recyclerView.setPageEnable(true);
            recyclerView.showLoadingFooter();
        } else {
            recyclerView.setPageEnable(false);
            recyclerView.removePageFooter();
        }
    }
}
