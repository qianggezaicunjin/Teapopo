package com.teapopo.life.util.databinding;

import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.Toast;

import com.teapopo.life.util.CustomToast;
import com.teapopo.life.view.adapter.viewpager.ArticleInfoImageAdapter;
import com.teapopo.life.view.customView.HackyViewPager;

import java.util.List;

import timber.log.Timber;

/**
 * Created by louiszgm on 2016/6/20.
 */
public class ToastErroInfo {
    @BindingAdapter({"erroInfo"})
    public static void toastErroInfo(View view, String data) {
        if(data!=null){
            CustomToast.makeText(view.getContext(),data, Toast.LENGTH_SHORT).show();
        }

    }
}
