package com.teapopo.life.view.fragment.CommentList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.databinding.FragmentCommentlistBinding;
import com.teapopo.life.databinding.FragmentUserBinding;
import com.teapopo.life.model.commentlist.CommentListModel;
import com.teapopo.life.view.adapter.recyclerview.CommentListAdapter;
import com.teapopo.life.view.customView.RecyclerView.LinearRecyclerView;
import com.teapopo.life.view.customView.RecyclerView.OnPageListener;
import com.teapopo.life.view.fragment.SwipeBackBaseFragment;
import com.teapopo.life.viewModel.commentList.CommentListViewModel;

/**
 * Created by louiszgm on 2016/6/23.
 */
public class CommentListFragment extends SwipeBackBaseFragment implements OnPageListener {

    private FragmentCommentlistBinding mBinding;
    private CommentListViewModel mViewModel;
    private Bundle mBundle;

    public static CommentListFragment newInstance(String id,String title,String classify){
        CommentListFragment fragment = new CommentListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title",title);
        bundle.putString("id",id);
        bundle.putString("classify",classify);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreateBinding() {
        mBundle = getArguments();
    }

    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentCommentlistBinding.inflate(inflater);
        mViewModel = new CommentListViewModel(new CommentListModel(_mActivity));
        mBinding.setViewmodel(mViewModel);
        return mBinding.getRoot();
    }

    @Override
    public void setUpView() {
        setUpCommentList();

    }

    private void setUpCommentList() {

        CommentListAdapter adapter = new CommentListAdapter(_mActivity,mViewModel.data);
        mBinding.rvCommentlist.setAdapter(adapter);
        mBinding.rvCommentlist.setOnPageListener(this);
        mBinding.rvCommentlist.setOnScrollListener(new LinearRecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(mBinding.etInputcomment.isFocused()){
                    mViewModel.setSoftInputStateWhenCommentOrReply(false,false,null);
                }
            }
        });
        //请求网络数据
        mViewModel.getCommentList(mBundle.getString("id"), mBundle.getString("classify"));
    }

    public void clickAddOrReplyComment(View view){
        String content = mBinding.etInputcomment.getText().toString();
        mViewModel.addCommentOrReply(content,mBundle.getString("id"));
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mViewModel.compositeSubscription.unsubscribe();
    }

    @Override
    public void onPage() {
        mViewModel.getCommentList(mBundle.getString("id"), mBundle.getString("classify"));
    }
}
