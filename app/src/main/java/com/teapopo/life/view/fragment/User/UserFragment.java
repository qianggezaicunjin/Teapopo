package com.teapopo.life.view.fragment.User;

import android.content.Context;
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
import com.teapopo.life.databinding.FragmentUserBinding;
import com.teapopo.life.injection.component.UserFragmentComponent;
import com.teapopo.life.injection.module.RecommendArticleFragmentModule;
import com.teapopo.life.injection.module.UserFragmentModule;
import com.teapopo.life.model.event.LoginClickEvent;
import com.teapopo.life.model.user.UserInfoModel;
import com.teapopo.life.view.activity.MainActivity;
import com.teapopo.life.view.adapter.viewpager.TabFragmentAdapter;
import com.teapopo.life.view.customView.HackyViewPager;
import com.teapopo.life.view.fragment.BaseFragment;
import com.teapopo.life.viewModel.UserViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

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

    private UserFragmentComponent mComponent;

    @Inject
     UserViewModel mViewModel;
    @Override
    public void onCreateBinding() {
        Timber.d("Oncreate");
        if(getActivity() instanceof MainActivity){
            mComponent = ((MainActivity)getActivity()).getMainActivityComponent().userFragment(new UserFragmentModule(getActivity()));
            mComponent.inject(this);}
    }

    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentUserBinding binding = FragmentUserBinding.inflate(inflater);
        binding.setUserViewModel(mViewModel);
        return binding.getRoot();
    }

    @Override
    public void setUpView() {

    }

    @Override
    public void onResume() {
        super.onResume();
        Timber.d("OnResume");
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Timber.d("onAttach");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Timber.d("onDeatach");
    }

}
