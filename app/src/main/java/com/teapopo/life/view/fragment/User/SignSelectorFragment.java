package com.teapopo.life.view.fragment.User;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.teapopo.life.R;
import com.teapopo.life.databinding.FragmentSignSelectorBinding;
import com.teapopo.life.view.fragment.BaseFragment;

import butterknife.Bind;

/**
 * Created by louiszgm on 2016/4/18 0018.
 */
public class SignSelectorFragment extends BaseFragment {
    private View mContentView;
    private FragmentSignSelectorBinding mFragmentSignSelectorBinding;
    @Bind(R.id.btn_sign_selector)
    Button mButton;

    public static SignSelectorFragment newInstance() {
        return new SignSelectorFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateBinding() {

    }

    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_selector, container, false);
        return view;
    }

    @Override
    public void setUpView() {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
    }

}
