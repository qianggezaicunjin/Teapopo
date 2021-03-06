package com.teapopo.life.viewModel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.teapopo.life.BR;
import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.view.customView.RequestView;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by louiszgm on 2016/6/20.
 */
public class BaseViewModel extends BaseObservable implements RequestView<ModelAction> {
    @Bindable
    public List<BaseEntity> data = new ArrayList<>();
    @Bindable
    public String erroInfo;
    @Bindable
    public boolean isShowMasking = false;
    @Bindable
    public SupportFragment navFragment;//要启动的fragment
    @Bindable
    public int startFragmentMode = SupportFragment.STANDARD;//启动模式

    public void setShowMasking(boolean isShow){
        isShowMasking = isShow;
    }
    public void setData(List<BaseEntity> data){
        this.data = data;
    }
    public void setErroInfo(String erroInfo){
        this.erroInfo = erroInfo;
    }
    public void setNavFragment(SupportFragment fragment){
        this.navFragment = fragment;
    }
    public void setStartFragmentMode(int mode){
        startFragmentMode = mode;
    }


    public void handleNoticeInfo(String s){
        erroInfo = s;
        notifyPropertyChanged(BR.erroInfo);
    }
    public void showMaskingView(boolean isShowMasking){
        this.isShowMasking = isShowMasking;
        notifyPropertyChanged(BR.isShowMasking);
    }
    public void navToFragmentForResult(SupportFragment fragment){
        navFragment = fragment;
        startFragmentMode = 100;
        notifyPropertyChanged(BR.startFragmentMode);
        notifyPropertyChanged(BR.navFragment);
    }
    public void navToFragment(SupportFragment fragment){
        navToFragment(fragment,SupportFragment.STANDARD);
    }
    public void navToFragment(SupportFragment fragment,int mode){
        navFragment = fragment;
        startFragmentMode = mode;
        notifyPropertyChanged(BR.startFragmentMode);
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
