package com.teapopo.life.view.fragment.Member;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.databinding.FragmentMemberPostBinding;
import com.teapopo.life.model.article.memberArticle.MemberArticleModel;
import com.teapopo.life.view.adapter.recyclerview.MemberAdapter;
import com.teapopo.life.view.fragment.BaseFragment;
import com.teapopo.life.viewModel.Member.PostViewModel;

/**
 * Created by lhq on 2016/7/18.
 */

public class MemberPostFragment extends BaseFragment {

    private FragmentMemberPostBinding mBinding;

    PostViewModel viewModel;

    public static MemberPostFragment newInstance(){
        return new MemberPostFragment();
    }
    @Override
    public void onCreateBinding() {

    }

    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel=new PostViewModel(new MemberArticleModel(_mActivity));
        mBinding=FragmentMemberPostBinding.inflate(inflater);
        mBinding.setViewModel(viewModel);
        return mBinding.getRoot();
    }

    @Override
    public void setUpView() {
        setUpArticle();
    }

    public void setUpArticle(){
        //获取会员文章数据
        viewModel.getMemberArticle();
        MemberAdapter memberAdapter =new MemberAdapter(_mActivity,viewModel.data);
        mBinding.rvPostArticle.setAdapter(memberAdapter);

    }
}
