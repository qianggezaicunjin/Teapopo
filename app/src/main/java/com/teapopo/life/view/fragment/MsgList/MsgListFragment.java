package com.teapopo.life.view.fragment.MsgList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.teapopo.life.R;
import com.teapopo.life.view.fragment.SwipeBackBaseFragment;

/**
 * Created by louiszgm on 2016/6/17.
 */
public class MsgListFragment extends SwipeBackBaseFragment {

    public static MsgListFragment newInstance(){
        return new MsgListFragment();
    }

    @Override
    public void onCreateBinding() {

    }

    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_msglist,null);
        Button button = (Button) view.findViewById(R.id.msglist);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mActivity.showFragmentStackHierarchyView();
            }
        });
        return view;
    }

    @Override
    public void setUpView() {

    }
}
