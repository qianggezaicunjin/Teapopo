package com.teapopo.life.view.fragment.User;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.teapopo.life.R;
import com.teapopo.life.databinding.FragmentSignSelectorBinding;
import com.teapopo.life.viewModel.UserViewModel;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by louiszgm on 2016/4/18 0018.
 */
public class SignSelectorFragment extends Fragment {
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        if (mContentView == null) {
            mContentView = inflater.inflate(R.layout.fragment_sign_selector, container, false);
            mFragmentSignSelectorBinding = FragmentSignSelectorBinding.bind(mContentView);
            mFragmentSignSelectorBinding.setUserViewModel(new UserViewModel(getActivity()));
        }
        ViewGroup parent = (ViewGroup) container.getParent();
        if (parent != null) {
            parent.removeView(mContentView);
        }
        ButterKnife.bind(this, mContentView);
        return mContentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
    }

}
