package com.teapopo.life.util.sharesdk;

import android.content.Context;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;


import com.teapopo.life.model.sharedpreferences.RxSpf_ThirdLoginSp;
import com.teapopo.life.model.user.ThirdPlatformModel;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.view.customView.RequestView;
import com.teapopo.life.view.fragment.User.SignUpVertifyCodeFragment;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import me.yokeyword.fragmentation.SupportActivity;
import timber.log.Timber;

public class LoginApi implements Callback, RequestView<ModelAction> {
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
//		this.context = context.getApplicationContext();
		this.context = context;
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
//				Toast.makeText(context, "canceled", Toast.LENGTH_SHORT).show();
			} break;
			case MSG_AUTH_ERROR: {
				// 失败
				Throwable t = (Throwable) msg.obj;
				String text = "caught error: " + t.getMessage();
//				Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
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
				//将第三方登录成功的平台名字和openid保存在sharedpreference
				RxSpf_ThirdLoginSp spf_thirdLogin = RxSpf_ThirdLoginSp.create(context);
				spf_thirdLogin.edit()
						.platform()
						.put(plat)
						.apply();
				ThirdPlatformModel model = new ThirdPlatformModel(context);
				model.setView(this);
				model.check_openid(plat);
			} break;
		}
		return false;
	}

	@Override
	public void onRequestFinished() {

	}

	@Override
	public void onRequestSuccess(ModelAction data) {
		if(data.action == Action.CheckOpenIdModel_Check_OpenId){
			//如果已经被绑定，则直接登录
			//如果未绑定,则调用绑定流程
			if((Boolean) data.t){
				((SupportActivity)context).finish();
			}else {
				((SupportActivity)context).start(SignUpVertifyCodeFragment.newInstance());
			}
		}
	}

	@Override
	public void onRequestErroInfo(String erroinfo) {

	}
}
