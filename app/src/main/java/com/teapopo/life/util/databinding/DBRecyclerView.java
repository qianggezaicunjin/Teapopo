package com.teapopo.life.util.databinding;

import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

import com.teapopo.life.R;
import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.view.adapter.recyclerview.BaseRecyclerViewAdapter;
import com.teapopo.life.view.customView.RecyclerView.OnTopPageListener;
import com.teapopo.life.view.customView.RecyclerView.SuperRecyclerView;

import java.util.List;

import timber.log.Timber;

/**
 * Created by louiszgm-pc on 2016/5/2.
 */
public class DBRecyclerView {
    public static int SHOW_FOOTER = 0;

    public static int HIDE_FOOTER = 1;

    @BindingAdapter({"adapter"})
    public static void setAdapter(SuperRecyclerView recyclerView, BaseRecyclerViewAdapter adapter) {
        recyclerView.setAdapter(adapter);

    }
    @BindingAdapter({"data"})
    public static void bindData(SuperRecyclerView recyclerView, List<BaseEntity> data) {
        recyclerView.notifyDataSetChanged();
        recyclerView.setIsLoading(false);
    }
    @BindingAdapter({"headers"})
    public static void setHeader(SuperRecyclerView recyclerView, View headers) {
        if(headers!=null){
           recyclerView.addHeader(headers);
        }
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
