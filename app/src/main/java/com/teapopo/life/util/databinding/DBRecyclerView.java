package com.teapopo.life.util.databinding;

import android.databinding.BindingAdapter;

import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.util.rx.RxSubscriber;
import com.teapopo.life.view.adapter.recyclerview.base.BaseRecyclerViewAdapter;
import com.teapopo.life.view.adapter.recyclerview.base.DecorateRecyclerViewAdapter;
import com.teapopo.life.view.customView.RecyclerView.SuperRecyclerView;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by louiszgm-pc on 2016/5/2.
 */
public class DBRecyclerView {
    public static int SHOW_FOOTER = 0;

    public static int HIDE_FOOTER = 1;

    @BindingAdapter({"data"})
    public static void bindData( SuperRecyclerView recyclerView, List<BaseEntity> data) {
        Timber.d("notifyDataSetChanged data");
        recyclerView.notifyDataSetChanged();
        recyclerView.setIsLoading(false);
    }
}
