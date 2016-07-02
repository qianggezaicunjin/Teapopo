package com.teapopo.life.view.fragment.User;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.databinding.FragmentUserBinding;
import com.teapopo.life.injection.component.ComponentHolder;
import com.teapopo.life.injection.component.fragment.MainFragmentComponent;
import com.teapopo.life.injection.module.fragment.MainFragmentModule;
import com.teapopo.life.model.event.LogOutEvent;
import com.teapopo.life.util.DialogFactory;
import com.teapopo.life.util.navigator.Navigator;
import com.teapopo.life.view.activity.MainActivity;
import com.teapopo.life.view.activity.SignInAndUpActivity;
import com.teapopo.life.view.fragment.BaseFragment;
import com.teapopo.life.viewModel.userCenter.UserViewModel;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by louiszgm on 2016/4/18 0018.
 */
public class UserFragment extends BaseFragment {
    @Inject
     UserViewModel mViewModel;
    public static UserFragment newInstance(){
        return new UserFragment();
    }
    @Override
    public void onCreateBinding() {
        Timber.d("Oncreate");
        if(getActivity() instanceof MainActivity){
             ((MainActivity)getActivity()).getMainFragmentComponent().inject(this);
            }
    }

    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentUserBinding binding = FragmentUserBinding.inflate(inflater);
        binding.setUserViewModel(mViewModel);
        binding.setHandler(this);
        return binding.getRoot();
    }

    @Override
    public void setUpView() {}
    //点击用户头像
    public void clickUserAvatar(View view){
        Navigator.getInstance().start(_mActivity,SignInAndUpActivity.getStartIntent(_mActivity));
    }
    //用户注销
    public void clickLogOut(View view){
        DialogFactory.createSureOrNotDialog(_mActivity, "确定退出吗?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case AlertDialog.BUTTON_POSITIVE:
                        //用户注销
                        mViewModel.logOut();
                        break;
                }
            }
        }).show();
    }
    @Override
    public void onResume() {
        super.onResume();
        mViewModel.requestData();
    }
}
