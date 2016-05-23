package com.teapopo.life.util.sharesdk;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;


import com.teapopo.life.model.user.CheckOpenIdModel;
import com.teapopo.life.util.navigator.Navigator;
import com.teapopo.life.view.activity.SignInAndUpActivity;
import com.teapopo.life.view.activity.ThirdRegisterActivity;
import com.teapopo.life.view.customView.RequestView;
import com.teapopo.life.view.fragment.User.SignUpVertifyCodeFragment;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import me.yokeyword.fragmentation.SupportActivity;
import timber.log.Timber;

public class LoginApi implements Callback, RequestView {
	private static final int MSG_AUTH_CANCEL = 1;
	private static final int MSG_AUTH_ERROR= 2;
	private static final int MSG_AUTH_COMPLETE = 3;

	private OnLoginListener loginListener;
	private String platform;
	private Context context;

	private Handler handler;

	public LoginApi() {
		handler = new Handler(Looper.getMainLooper(), this);
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public void setOnLoginListener(OnLoginListener login){
		this.loginListener=login;
	}

	public void login(Context context) {
		this.context = context.getApplicationContext();
		if (platform == null) {
			return;
		}

		//初始化SDK
		ShareSDK.initSDK(context);
		Platform plat = ShareSDK.getPlatform(platform);
		if (plat == null) {
			return;
		}

		if (plat.isAuthValid()) {
			plat.removeAccount(true);
			return;
		}

		//使用SSO授权，通过客户单授权
		plat.SSOSetting(false);
		plat.setPlatformActionListener(new PlatformActionListener() {
			public void onComplete(Platform plat, int action,HashMap<String, Object> res) {
				if (action == Platform.ACTION_USER_INFOR) {
					Message msg = new Message();
					msg.what = MSG_AUTH_COMPLETE;
					msg.arg2 = action;
					msg.obj =  new Object[] {plat.getName(), res};
					handler.sendMessage(msg);
				}
			}

			public void onError(Platform plat, int action, Throwable t) {
				if (action == Platform.ACTION_USER_INFOR) {
					Message msg = new Message();
					msg.what = MSG_AUTH_ERROR;
					msg.arg2 = action;
					msg.obj = t;
					handler.sendMessage(msg);
				}
				t.printStackTrace();
			}

			public void onCancel(Platform plat, int action) {
				if (action == Platform.ACTION_USER_INFOR) {
					Message msg = new Message();
					msg.what = MSG_AUTH_CANCEL;
					msg.arg2 = action;
					msg.obj = plat;
					handler.sendMessage(msg);
				}
			}
		});
		plat.showUser(null);
	}

	/**处理操作结果*/
	public boolean handleMessage(Message msg) {
		switch(msg.what) {
			case MSG_AUTH_CANCEL: {
				// 取消
				Toast.makeText(context, "canceled", Toast.LENGTH_SHORT).show();
			} break;
			case MSG_AUTH_ERROR: {
				// 失败
				Throwable t = (Throwable) msg.obj;
				String text = "caught error: " + t.getMessage();
				Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
				t.printStackTrace();
			} break;
			case MSG_AUTH_COMPLETE: {
				// 第三方账号登录成功，本地需要校验该第三方账号是否已经被绑定
				Object[] objs = (Object[]) msg.obj;
				String plat = (String) objs[0];
				//用户的基本信息
				@SuppressWarnings("unchecked")
				HashMap<String, Object> res = (HashMap<String, Object>) objs[1];
				Timber.d("第三方登录成功返回的信息为:%s",res.toString());
				CheckOpenIdModel model = new CheckOpenIdModel(context);
				model.setView(this);
				model.check_openid(plat);
				//如果已经被绑定，则直接登录

				//如果未绑定,则调用注册流程
			} break;
		}
		return false;
	}

	@Override
	public void onRequestFinished() {

	}

	@Override
	public void onRequestSuccess(Object data) {

	}

	@Override
	public void onRequestErroInfo(String erroinfo) {

	}
}
