package com.teapopo.life.model.sharedpreferences;

import me.yokeyword.rxapi.Spf;

/**
 * Created by louiszgm on 2016/5/24.
 * 将第三方登录成功的平台名字和openid保存在sharedpreference
 */
@Spf
public class ThirdLogin {
    String platform;
    String openid;
}
