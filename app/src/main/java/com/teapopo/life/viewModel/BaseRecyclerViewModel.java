package com.teapopo.life.viewModel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.model.recommendarticle.RecommendArticle;
import com.teapopo.life.model.toparticle.TopArticle;
import com.teapopo.life.util.Constans.ViewModelAction;
import com.teapopo.life.util.databinding.DBRecyclerView;
import com.teapopo.life.BR;
import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Created by louiszgm-pc on 2016/5/2.
 */
public class BaseRecyclerViewModel<T> extends BaseObservable {
    @Bindable
    public List<T> data = new ArrayList<>();

    @Bindable
    public int footerStatus = DBRecyclerView.SHOW_FOOTER;

    @Bindable
    public boolean loading = false;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getFooterStatus() {
        return footerStatus;
    }

    public void setFooterStatus(int footerStatus) {
        this.footerStatus = footerStatus;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    public boolean isLoading() {
        return loading;
    }

    public void requestData() {
        loading = true;
        notifyPropertyChanged(BR.loading);
    }

    public void onRequestFinished() {

    }

    public void onRequestSuccess(ViewModelAction data) {

    }
}
