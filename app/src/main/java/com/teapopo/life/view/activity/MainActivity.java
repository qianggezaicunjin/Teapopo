package com.teapopo.life.view.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v4.app.FragmentTabHost;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;

import com.teapopo.life.R;
import com.teapopo.life.data.remote.cookie.PersistentCookieStore;
import com.teapopo.life.injection.component.DaggerMainActivityComponent;
import com.teapopo.life.injection.component.MainActivityComponent;
import com.teapopo.life.injection.module.ActivityModule;
import com.teapopo.life.injection.module.MainActivityModule;
import com.teapopo.life.view.fragment.Home.HomeFragment;
import com.teapopo.life.view.fragment.User.UserFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;


public class MainActivity extends BaseActivity {

    private MainActivityComponent mMainActivityComponent;
    @Bind(android.R.id.tabhost)
    FragmentTabHost mTabHost;
    @DrawableRes
    private int[] mImages = {R.drawable.selector_home,
            R.drawable.icon_teacup,
            R.drawable.icon_welfare,
            R.drawable.icon_user
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
    public void onCreateBinding() {
        mMainActivityComponent = DaggerMainActivityComponent.builder().applicationComponent(getAppComponent())
                .activityModule(new ActivityModule(this))
                .mainActivityModule(new MainActivityModule())
                .build();
        mMainActivityComponent.inject(this);
    }


    private void setUpTabHost() {
        mTabHost.setup(this, getSupportFragmentManager(), R.id.tab_content);
        mTabHost.getTabWidget().setDividerDrawable(null); // 去掉分割线

        for (int i = 0; i < mImages.length; i++) {
            switch (mImages[i]) {
                case R.drawable.selector_home:
                    // Tab按钮添加文字和图片
                    TabHost.TabSpec tabSpec = mTabHost.newTabSpec("首页").setIndicator(getImageView(i));
                    // 添加Fragment
                    mTabHost.addTab(tabSpec, HomeFragment.class, null);
                    break;
                case R.drawable.icon_teacup:
                    // Tab按钮添加文字和图片
                    TabHost.TabSpec tabSpec1 = mTabHost.newTabSpec("新滋").setIndicator(getImageView(i));
                    // 添加Fragment
                    mTabHost.addTab(tabSpec1, HomeFragment.class, null);
                    break;
                case R.drawable.icon_welfare:
                    // Tab按钮添加文字和图片
                    TabHost.TabSpec tabSpec2 = mTabHost.newTabSpec("福利社").setIndicator(getImageView(i));
                    // 添加Fragment
                    mTabHost.addTab(tabSpec2, HomeFragment.class, null);
                    break;
                case R.drawable.icon_user:

                    // Tab按钮添加文字和图片
                    TabHost.TabSpec tabSpec3 = mTabHost.newTabSpec("我的").setIndicator(getImageView(i));
                    // 添加Fragment
                    mTabHost.addTab(tabSpec3, UserFragment.class, null);

                    break;
            }
            // 设置Tab按钮的背景
//            mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.color.blue);
        }
    }

    // 获得图片资源
    private View getImageView(int index) {
        @SuppressLint("InflateParams")
        View view = getLayoutInflater().inflate(R.layout.tab_fragmenttabhost, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.tab_iv_image);
        imageView.setImageResource(mImages[index]);
        return view;
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
