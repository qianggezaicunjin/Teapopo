package com.teapopo.life.view.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.teapopo.life.R;
import com.teapopo.life.util.sharesdk.OnLoginListener;
import com.teapopo.life.view.adapter.viewpager.TabFragmentAdapter;
import com.teapopo.life.view.fragment.User.BindAccountFragment;
import com.teapopo.life.view.fragment.User.BindNewAccountFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;


/** 此页面是用户通过第三方登录之后的绑定账号页面 */
public class ThirdRegisterActivity extends AppCompatActivity {


    private static OnLoginListener mTempRegisterListener;
    private static String mTempPlatFormName;



    private OnLoginListener mRegisterListener;
    private Platform mPlatform;


    @Bind(R.id.tabs_third_register)
     TabLayout mTabaLayout;
    @Bind(R.id.viewPager_third_register)
     ViewPager mViewPager;
   public static void setOnLoginListener(OnLoginListener login){
        mTempRegisterListener = login;
    }

    public static void setPlatform(String platName) {
        mTempPlatFormName=platName;
    }

    protected void onCreate(Bundle savedInstanceState) {
        mRegisterListener=mTempRegisterListener;
        mPlatform= ShareSDK.getPlatform(mTempPlatFormName);
        //释放引用
        mTempPlatFormName=null;
        mTempRegisterListener=null;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_register);
        ButterKnife.bind(this);
        setupTabLayout();
        initData();

    }

    private void setupTabLayout() {
       List<Fragment> fragmentList=new ArrayList<>();
        fragmentList.add(BindAccountFragment.newInstance());
        fragmentList.add(BindNewAccountFragment.newInstance());
        List<String> titleList = new ArrayList<>();
        titleList.add("绑定已有账号");
        titleList.add("创建新账号");
        TabFragmentAdapter adapter=new TabFragmentAdapter(getSupportFragmentManager(),fragmentList,titleList);
        mViewPager.setAdapter(adapter);
        mTabaLayout.setupWithViewPager(mViewPager);
    }

    /*初始化用户数据*/
    private void initData(){
        if(mPlatform != null){
            mPlatform.getDb().getUserIcon();
            mPlatform.getDb().getUserName();
            mPlatform.getDb().getUserId();
            //QQ
            mPlatform.getDb().get("city");
            mPlatform.getDb().get("province");
            //头像
            mPlatform.getDb().get("figureurl_qq_1");

        }
    }

}
