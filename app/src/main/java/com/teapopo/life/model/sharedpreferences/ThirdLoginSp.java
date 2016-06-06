package com.teapopo.life.model.sharedpreferences;

import me.yokeyword.rxapi.Spf;

/**
 * Created by louiszgm on 2016/5/24.
 * 将第三方登录成功的平台名字和openid保存在sharedpreference
 *
 * 用途:在登录注册的时候，用来判断是否是通过第三方平台登录  例如:QQ 微博等
 *
 */
@Spf
public class ThirdLoginSp {
    String platform;
    String openid;
}
