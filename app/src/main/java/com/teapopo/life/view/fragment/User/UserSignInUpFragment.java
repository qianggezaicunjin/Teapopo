package com.teapopo.life.view.fragment.User;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.MyApplication;
import com.teapopo.life.R;
import com.teapopo.life.model.event.LoginClickEvent;
import com.teapopo.life.view.adapter.viewpager.TabFragmentAdapter;
import com.teapopo.life.view.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by louiszgm on 2016/4/15 0015.
 * 用户的手机登陆以及注册
 */
public class UserSignInUpFragment extends BaseFragment {

    @Bind(R.id.tabs_sign_in_up)
    TabLayout mTabLayout;
    @Bind(R.id.viewPager_sign_in_up)
    ViewPager mViewPager;
    private View mViewContent;
    private ViewGroup parent;

    public static UserSignInUpFragment newInstances(){
        return new UserSignInUpFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View getmContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_sign_in_up,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void setUpView() {
        setupTabLayout();
    }


    private void setupTabLayout() {

        List<Fragment> fragmentList=new ArrayList<>();
        fragmentList.add(SignInFragment.newInstances());
        fragmentList.add(SignUpFragment.newInstance());
        List<String> titleList=new ArrayList<>();
        titleList.add("会员登陆");
        titleList.add("会员注册");
        TabFragmentAdapter adapter=new TabFragmentAdapter(getActivity().getSupportFragmentManager(),fragmentList,titleList);
        mViewPager.setAdapter(adapter);

        mTabLayout.setupWithViewPager(mViewPager);
    }
}
