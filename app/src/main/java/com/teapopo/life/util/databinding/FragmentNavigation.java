package com.teapopo.life.util.databinding;

import android.databinding.BindingAdapter;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.teapopo.life.util.CustomToast;

import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by louiszgm on 2016/7/16.
 */
public class FragmentNavigation {

    @BindingAdapter({"navFragment"})
    public static void navigateToFragment(View view, SupportFragment fragment) {
        SupportActivity activity = (SupportActivity) view.getContext();
        activity.start(fragment);
    }
}
