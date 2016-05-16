package com.teapopo.life.view.customView;

import com.teapopo.life.model.BaseEntity;

import java.util.List;

/**
 * Created by louiszgm-pc on 2016/5/3.
 */
public interface RequestView<T> {

    void onRequestFinished();

    void onRequestSuccess(T data);


    void onRequestErroInfo(String erroinfo);
}
