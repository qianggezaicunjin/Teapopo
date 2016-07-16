package com.teapopo.life.viewModel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.teapopo.life.BR;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.view.customView.RequestView;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by louiszgm on 2016/6/20.
 */
public class BaseViewModel extends BaseObservable implements RequestView<ModelAction> {
    @Bindable
    public String erroInfo;

    @Bindable
    public SupportFragment navFragment;
    public void setErroInfo(String erroInfo){
        this.erroInfo = erroInfo;
    }

    public void setNavFragment(SupportFragment fragment){
        this.navFragment = fragment;
    }
    public void handleNoticeInfo(String s){
        erroInfo = s;
        notifyPropertyChanged(BR.erroInfo);
    }
    public void navToFragment(SupportFragment fragment){
        navFragment = fragment;
        notifyPropertyChanged(BR.navFragment);
    }
    @Override
    public void onRequestFinished() {

    }

    @Override
    public void onRequestSuccess(ModelAction data) {

    }

    @Override
    public void onRequestErroInfo(String erroinfo) {
        handleNoticeInfo(erroinfo);
    }
}
