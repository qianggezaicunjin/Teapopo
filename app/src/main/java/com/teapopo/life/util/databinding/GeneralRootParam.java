package com.teapopo.life.util.databinding;

import android.app.Activity;
import android.databinding.BindingAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.teapopo.life.R;
import com.teapopo.life.util.CustomToast;

import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by louiszgm-pc on 2016/7/20.
 */
public class GeneralRootParam {


    @BindingAdapter({"mask"})
    public static void showMaskView(View view, boolean isShow) {
            ViewGroup decorView = (ViewGroup) ((Activity)view.getContext()).getWindow().getDecorView().findViewById(android.R.id.content);;//activity的根View
            ViewGroup rootView = null;
            if(isShow){
                rootView = (ViewGroup) LayoutInflater.from(view.getContext()).inflate(R.layout.fragment_mask_loading, decorView, false);;//附加View 的 根View
                decorView.addView(rootView);
                view.setTag(rootView);
            }else {
                decorView.removeView((View) view.getTag());
            }
        }
    @BindingAdapter({"navFragment","mode"})
    public static void navigateToFragment(View view, SupportFragment fragment, int startFragMode) {
        if(fragment!=null){
            SupportActivity activity = (SupportActivity) view.getContext();
            if(startFragMode==100){
                activity.startForResult(fragment,1);
            }else {
                activity.start(fragment,startFragMode);
            }
        }
    }


    @BindingAdapter({"erroInfo"})
    public static void toastErroInfo(View view, String data) {
        if(data!=null){
            CustomToast.makeText(view.getContext(),data, Toast.LENGTH_SHORT).show();
        }

    }
}
