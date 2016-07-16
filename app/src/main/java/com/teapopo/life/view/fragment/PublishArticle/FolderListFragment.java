package com.teapopo.life.view.fragment.PublishArticle;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.R;
import com.teapopo.life.model.imageselect.Folder;
import com.teapopo.life.view.adapter.recyclerview.ImageFolderAdapter;
import com.teapopo.life.view.customView.RecyclerView.SuperRecyclerView;
import com.teapopo.life.view.fragment.SwipeBackBaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by louiszgm on 2016/7/16.
 */
public class FolderListFragment extends SwipeBackBaseFragment {

    @Bind(R.id.rv_folderlist)
    SuperRecyclerView mRecyclerView;

    private ArrayList<Folder> folderArrayList = new ArrayList<>();
    public static FolderListFragment newInstance(ArrayList<Parcelable> folderList){
        FolderListFragment folderListFragment = new FolderListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("folderlist",folderList);

        folderListFragment.setArguments(bundle);
        return folderListFragment;
    }
    @Override
    public void onCreateBinding() {
        folderArrayList = getArguments().getParcelableArrayList("folderlist");
    }

    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_folderlist,null);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void setUpView() {
        setUpFolderList();
    }

    private void setUpFolderList() {
        ImageFolderAdapter adapter = new ImageFolderAdapter(_mActivity,folderArrayList);
        mRecyclerView.setAdapter(adapter);
    }
}
