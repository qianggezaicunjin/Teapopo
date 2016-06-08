package com.teapopo.life.view.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentTabHost;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.teapopo.life.R;
import com.teapopo.life.injection.component.ComponentHolder;

import com.teapopo.life.injection.component.activity.DaggerMainActivityComponent;
import com.teapopo.life.injection.component.activity.MainActivityComponent;
import com.teapopo.life.injection.module.ActivityModule;
import com.teapopo.life.injection.module.activity.MainActivityModule;
import com.teapopo.life.view.fragment.Home.HomeFragment;
import com.teapopo.life.view.fragment.User.UserFragment;
import com.teapopo.life.view.fragment.welfare.WelFareFragment;
import com.teapopo.life.view.fragment.xinzi.XinZiFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;


public class MainActivity extends SwipeBackBaseActivity {

    private MainActivityComponent mMainActivityComponent;
    @Bind(android.R.id.tabhost)
    FragmentTabHost mTabHost;

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
    /**
     * 通过MainActivityComponent获得依赖的对象
     * @return
     */
    public MainActivityComponent getMainActivityComponent() {
        return mMainActivityComponent;
    }

    public static Intent getStartIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setUpTabHost();
    }

    @Override
    protected int setContainerId() {
        return 0;
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


    private void setUpTabHost() {
        mTabHost.setup(this, getSupportFragmentManager(), R.id.tab_content);
        mTabHost.getTabWidget().setDividerDrawable(null); // 去掉分割线

        TabHost.TabSpec tabSpec1 = mTabHost.newTabSpec("首页").setIndicator(getTabView(0,"首页" ));
        mTabHost.addTab(tabSpec1, HomeFragment.class, null);

        mTabHost.getTabWidget().getChildTabViewAt(0).setOnClickListener(new TabOnClickListener(mTabHost,0));

        TabHost.TabSpec tabSpec2 = mTabHost.newTabSpec("新滋").setIndicator(getTabView(1,"新滋"));
        mTabHost.addTab(tabSpec2, XinZiFragment.class, null);
        mTabHost.getTabWidget().getChildTabViewAt(1).setOnClickListener(new TabOnClickListener(mTabHost,1));


        TabHost.TabSpec tabSpec3 = mTabHost.newTabSpec("发布帖子").setIndicator(getTabView(2,"发布帖子"));
        mTabHost.addTab(tabSpec3,WelFareFragment.class, null);
        mTabHost.getTabWidget().getChildTabViewAt(2).setOnClickListener(new TabOnClickListener(mTabHost,2));


        TabHost.TabSpec tabSpec4 = mTabHost.newTabSpec("福利社").setIndicator(getTabView(3,"福利社"));
        mTabHost.addTab(tabSpec4, WelFareFragment.class, null);
        mTabHost.getTabWidget().getChildTabViewAt(3).setOnClickListener(new TabOnClickListener(mTabHost,3));

        TabHost.TabSpec tabSpec5 = mTabHost.newTabSpec("我的").setIndicator(getTabView(4,"我的"));
        mTabHost.addTab(tabSpec5, UserFragment.class, null);
        mTabHost.getTabWidget().getChildTabViewAt(4).setOnClickListener(new TabOnClickListener(mTabHost,4));

    }

    /**
     * 返回tab的布局
     * @param index
     * @param tabName
     * @return
     */
    private View getTabView(int index, String tabName) {
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
                textView.setText(tabName);
                break;
        }
        //设置id
        view.setId(tabIds[index]);
        //设置每个tab的图片背景
        ImageView imageView = (ImageView) view.findViewById(R.id.tab_iv_image);
        imageView.setImageResource(tabImageNoramlArray[index]);
        return view;
    }

    class TabOnClickListener implements View.OnClickListener{

        private FragmentTabHost fragmentTabHost;
        private int index;
        public TabOnClickListener(FragmentTabHost fragmentTabHost,int index){
            this.fragmentTabHost = fragmentTabHost;
            this.index = index;
        }

        @Override
        public void onClick(View v) {
           setImageSrc(fragmentTabHost,index);
            if(index==2){
                Timber.d("发布帖子");
            }else {
                fragmentTabHost.setCurrentTab(index);
            }
        }
    }

    /**
     *
     * 设置tab点击时候的背景
     * @param fragmentTabHost
     * @param index 点击tab的索引
     */
    private void setImageSrc(FragmentTabHost fragmentTabHost, int index) {
        for(int i = 0 ; i < fragmentTabHost.getTabWidget().getTabCount();i++){
            View view = fragmentTabHost.getTabWidget().getChildAt(i);
            ImageView imageView = (ImageView)view.findViewById(R.id.tab_iv_image);
            if(i == index){
                if(index!=2){
                    TextView textView = (TextView) view.findViewById(R.id.tab_tv_tabname);
                }
                imageView.setImageResource(tabImageSelectedArray[i]);
            }else{
                imageView.setImageResource(tabImageNoramlArray[i]);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
        return super.onOptionsItemSelected(item);
    }
}
