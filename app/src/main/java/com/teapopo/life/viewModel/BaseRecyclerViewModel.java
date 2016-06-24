package com.teapopo.life.viewModel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.util.databinding.DBRecyclerView;
import com.teapopo.life.BR;
import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Created by louiszgm-pc on 2016/5/2.
 */
public class BaseRecyclerViewModel<T> extends BaseViewModel {
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

    public void stopLoading(){
        Timber.d("stopLoading");
        loading = false;
        notifyPropertyChanged(BR.loading);
    }

    public void startLoading(){
        Timber.d("startLoading");
        loading = true;
        notifyPropertyChanged(BR.loading);
    }

}
