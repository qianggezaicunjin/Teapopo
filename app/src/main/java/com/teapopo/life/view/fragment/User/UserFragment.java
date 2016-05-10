package com.teapopo.life.view.fragment.User;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.MyApplication;
import com.teapopo.life.R;
import com.teapopo.life.data.rx.RxBus;
import com.teapopo.life.model.event.LoginClickEvent;
import com.teapopo.life.view.adapter.viewpager.TabFragmentAdapter;
import com.teapopo.life.view.customView.HackyViewPager;
import com.teapopo.life.view.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action1;
import rx.observables.ConnectableObservable;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by louiszgm on 2016/4/18 0018.
 */
public class UserFragment extends BaseFragment {
    @Bind(R.id.toolbar_user)
    Toolbar mToolBar;
    @Bind(R.id.viewPager_user)
    HackyViewPager mViewPager;
    @Bind(R.id.appbar_user)
    AppBarLayout mAppBar;
    private View mViewContent;
    private RxBus mRxBus;
    private CompositeSubscription mSubscriptions;
    public static UserFragment newInstance(){
        return new UserFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.d("UserFragment onCreate");
        mRxBus= MyApplication.get(getActivity()).getComponent().rxbus();
        mSubscriptions=new CompositeSubscription();

        ConnectableObservable<Object> msgcontentEvent= mRxBus.toObservable().publish();
        mSubscriptions.add(msgcontentEvent.subscribe(new Action1<Object>() {
            @Override
            public void call(Object object) {
                if(object instanceof LoginClickEvent){
                    Timber.d("收到 loginClickEvent啦");
                    mViewPager.setCurrentItem(1);
                }
            }
        }));
        mSubscriptions.add(msgcontentEvent.connect());
    }

    @Override
    public void onCreateBinding() {

    }

    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_user,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void setUpView() {
        setupToolBar();
        setupViewPager();
    }

    private void setupViewPager() {

        //禁止viewpager滑动
        mViewPager.setScrollble(true);
        List<Fragment> fragmentList=new ArrayList<>();
        fragmentList.add(new SignSelectorFragment());
        fragmentList.add(new UserSignInUpFragment());

        List<String> titlesList=new ArrayList<>();
        titlesList.add("SignSelectorFragment");
        titlesList.add("SignInUpFragment");
        TabFragmentAdapter tabFragmentAdapter=new TabFragmentAdapter(getChildFragmentManager(),fragmentList,titlesList);

        mViewPager.setAdapter(tabFragmentAdapter);
    }

    private void setupToolBar() {
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolBar);
    }

}
