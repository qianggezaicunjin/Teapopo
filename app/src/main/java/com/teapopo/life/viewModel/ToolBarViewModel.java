package com.teapopo.life.viewModel;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.teapopo.life.view.fragment.User.SignUpFragment;

import me.yokeyword.fragmentation.SupportActivity;
import timber.log.Timber;

/**
 * Created by louiszgm-pc on 2016/5/18.
 */
public class ToolBarViewModel {
    private SupportActivity mContext;
    public ToolBarViewModel(Context context){
        if(context instanceof SupportActivity){
            mContext = (SupportActivity) context;
        }
    }
    public Toolbar.OnMenuItemClickListener getlis(){
        return new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Timber.d("ToolBar菜单被点击啦");
                mContext.start(SignUpFragment.newInstance());
                return true;
            }
        };
    }

    public View.OnClickListener getClick(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.showFragmentStackHierarchyView();
            }
        };
    }
}
