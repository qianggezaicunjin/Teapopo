package com.teapopo.life.view.fragment.Member;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.R;
import com.teapopo.life.databinding.FragmentMemberBinding;
import com.teapopo.life.model.AuthorInfo;
import com.teapopo.life.view.adapter.viewpager.HomeTabFragmentAdapter;
import com.teapopo.life.view.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lhq on 2016/7/18.
 */

public class MemberFragment extends BaseFragment {

    private FragmentMemberBinding mBinding;

    public static AuthorInfo memberInfo;

    public static MemberFragment newInstance(AuthorInfo authorInfo){
        memberInfo=authorInfo;
        return new MemberFragment();
    }
    @Override
    public void onCreateBinding() {

    }

    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding=FragmentMemberBinding.inflate(inflater);
        mBinding.setView(this);
        return mBinding.getRoot();
    }

    @Override
    public void setUpView() {
        setAppBar();
        setUpToolbar();
    }

    public void setAppBar(){
        //设置滑动标签页
        List<String> titles=new ArrayList<>();

        titles.add("帖子");
        titles.add("喜欢");
        mBinding.tabsPersonalHomepage.setTabMode(TabLayout.MODE_FIXED);
        mBinding.tabsPersonalHomepage.addTab(mBinding.tabsPersonalHomepage.newTab().setText(titles.get(0)));
        mBinding.tabsPersonalHomepage.addTab(mBinding.tabsPersonalHomepage.newTab().setText(titles.get(1)));

        List<Fragment> fragmentList = new ArrayList<>();
        MemberPostFragment memberPostFragment = MemberPostFragment.newInstance();
        final MemberLikeFragment memberLikeFragment = MemberLikeFragment.newInstance();
        fragmentList.add(memberPostFragment);
        fragmentList.add(memberLikeFragment);

        //如果在fragment里面嵌套的viewpager里面再嵌套fragment,则需要getChildFragmentManager()
        HomeTabFragmentAdapter tabAdapter=new HomeTabFragmentAdapter(getChildFragmentManager(),fragmentList,titles);
        mBinding.viewPagerHomepage.setAdapter(tabAdapter);
        mBinding.tabsPersonalHomepage.setupWithViewPager(mBinding.viewPagerHomepage);
    }

    public void setUpToolbar(){
        mBinding.toolbarPersonal.setNavigationIcon(R.drawable.icon_back);
        mBinding.toolbarPersonal.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
    }
}
