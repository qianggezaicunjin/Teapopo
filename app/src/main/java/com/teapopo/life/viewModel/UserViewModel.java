package com.teapopo.life.viewModel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.view.View;

import com.teapopo.life.MyApplication;
import com.teapopo.life.model.event.LoginClickEvent;
import com.teapopo.life.util.sharesdk.LoginApi;
import com.teapopo.life.util.sharesdk.OnLoginListener;
import com.tencent.connect.UserInfo;
import com.tencent.tauth.Tencent;

import java.util.HashMap;

import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import timber.log.Timber;

/**
 * Created by Administrator on 2016/4/19 0019.
 */
public class UserViewModel extends BaseObservable{

}
