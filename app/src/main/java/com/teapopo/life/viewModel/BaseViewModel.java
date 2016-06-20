package com.teapopo.life.viewModel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.teapopo.life.BR;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.view.customView.RequestView;

/**
 * Created by louiszgm on 2016/6/20.
 */
public class BaseViewModel extends BaseObservable implements RequestView<ModelAction> {
    @Bindable
    public String erroInfo;

    public void setErroInfo(String erroInfo){
        this.erroInfo = erroInfo;
    }

    @Override
    public void onRequestFinished() {

    }

    @Override
    public void onRequestSuccess(ModelAction data) {

    }

    @Override
    public void onRequestErroInfo(String erroinfo) {
        this.erroInfo = erroinfo;
        notifyPropertyChanged(BR.erroInfo);
    }
}
