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

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by louiszgm on 2016/4/15 0015.
 * 用户的手机登陆以及注册
 */
public class UserSignInUpFragment extends Fragment {

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mViewContent == null) {
            mViewContent = inflater.inflate(R.layout.fragment_user_sign_in_up, container, false);
        }
        // 缓存View判断是否含有parent, 如果有需要从parent删除, 否则发生已有parent的错误.
        parent = (ViewGroup) mViewContent.getParent();
        if (parent != null) {
            parent.removeView(mViewContent);
        }
        ButterKnife.bind(this, mViewContent);
        setupTabLayout();
        return mViewContent;
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
