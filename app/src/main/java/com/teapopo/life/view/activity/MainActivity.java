package com.teapopo.life.view.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentTabHost;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.teapopo.life.R;
import com.teapopo.life.injection.component.ComponentHolder;

import com.teapopo.life.injection.component.activity.DaggerMainActivityComponent;
import com.teapopo.life.injection.component.activity.MainActivityComponent;
import com.teapopo.life.injection.component.fragment.MainFragmentComponent;
import com.teapopo.life.injection.module.ActivityModule;
import com.teapopo.life.injection.module.activity.MainActivityModule;
import com.teapopo.life.injection.module.fragment.MainFragmentModule;
import com.teapopo.life.util.navigator.Navigator;
import com.teapopo.life.view.fragment.Home.HomeFragment;
import com.teapopo.life.view.fragment.User.UserFragment;
import com.teapopo.life.view.fragment.welfare.WelFareFragment;
import com.teapopo.life.view.fragment.xinzi.XinZiFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;
import rx.Observable;
import timber.log.Timber;


public class MainActivity extends SwipeBackBaseActivity implements View.OnClickListener {

    private MainActivityComponent mMainActivityComponent;
    @Bind(R.id.flexbox_bottomtab)
    FlexboxLayout mBottomTab;
    @DrawableRes
    private int[] tabImageNoramlArray = {R.drawable.icon_home,
            R.drawable.icon_teacup,
            R.drawable.icon_plus,
            R.drawable.icon_welfare,
            R.drawable.icon_user
    };
    @DrawableRes
    private int[] tabImageSelectedArray = {R.drawable.icon_home_press,
            R.drawable.icon_teacup_press,
            R.drawable.icon_plus,
            R.drawable.icon_welfare_press,
            R.drawable.icon_user_press
    };
    @IdRes
    private int[] tabIds = {
            R.id.tab_home,
            R.id.tab_xinzi,
            R.id.tab_publish,
            R.id.tab_welfare,
            R.id.tab_user
    };

    private String[] tabNames = {
            "首页",
            "新滋",
            "发布帖子",
            "福利社",
            "我的"
    };
    /**
     * 通过MainActivityComponent获得依赖的对象
     * @return
     */
    public MainActivityComponent getMainActivityComponent() {
        return mMainActivityComponent;
    }

    public MainFragmentComponent getMainFragmentComponent(){
        return mMainActivityComponent.mainFragmentComponent(new MainFragmentModule());
    }
    public static Intent getStartIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setUpBottomTab();
        //设置首次进入的页面是首页
        start(HomeFragment.newInstance());
        setImageSrc(0);
    }

    @Override
    protected int setContainerId() {
        return R.id.tab_content;
    }

    @Override
    public void onCreateBinding() {
        //关闭滑动返回的功能
        getSwipeBackLayout().setEnableGesture(false);
        mMainActivityComponent = DaggerMainActivityComponent.builder().applicationComponent(ComponentHolder.getAppComponent())
                .activityModule(new ActivityModule(this))
                .mainActivityModule(new MainActivityModule())
                .build();
        mMainActivityComponent.inject(this);
    }

    private void setUpBottomTab() {
       int size = tabImageNoramlArray.length;
        for(int i = 0;i<size;i++){
            View view = getTabView(i);
            mBottomTab.addView(view);
            view.setOnClickListener(this);
        }
    }
    /**
     * 返回tab的布局
     * @param index
     * @return
     */
    private View getTabView(int index) {
        @SuppressLint("InflateParams")
        View view;
        switch (index){
            case 2:
                //当Tab是发布文章的时候
                view = getLayoutInflater().inflate(R.layout.tab_publishaticle,null);
                break;
            default:
                view = getLayoutInflater().inflate(R.layout.tab_fragmenttabhost, null);
                TextView textView = (TextView) view.findViewById(R.id.tab_tv_tabname);
                textView.setText(tabNames[index]);
                break;
        }
        //设置id
        view.setId(tabIds[index]);
        //设置每个tab的图片背景
        ImageView imageView = (ImageView) view.findViewById(R.id.tab_iv_image);
        imageView.setImageResource(tabImageNoramlArray[index]);
        return view;
    }


    /**
     *
     * 设置tab的背景
     * @param index 点击tab的索引
     */
    private void setImageSrc(int index) {
        int size = tabImageNoramlArray.length;
        for(int i = 0 ; i < size;i++){
            View view = mBottomTab.getChildAt(i);
            ImageView imageView = (ImageView)view.findViewById(R.id.tab_iv_image);
            if(i == index){
                imageView.setImageResource(tabImageSelectedArray[i]);
            }else{
                imageView.setImageResource(tabImageNoramlArray[i]);
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tab_home:
                Timber.d("home");
                start(HomeFragment.newInstance(), SupportFragment.SINGLETASK);
                setImageSrc(0);
                break;
            case R.id.tab_xinzi:
                Timber.d("xinzi");
                start(XinZiFragment.newInstance(), SupportFragment.SINGLETASK);
                setImageSrc(1);
                break;
            case R.id.tab_publish:
                Navigator.getInstance().start(this,PublishArticleActivity.getStartIntent(this));
                break;
            case R.id.tab_welfare:
                Timber.d("welfare");
                start(WelFareFragment.newInstance(), SupportFragment.SINGLETASK);
                setImageSrc(3);
                break;
            case R.id.tab_user:
                Timber.d("user");
                start(UserFragment.newInstance(), SupportFragment.SINGLETASK);
                setImageSrc(4);
                break;
        }
    }
}
