package com.teapopo.life.model.user;

import android.content.Context;

import com.teapopo.life.model.BaseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by louiszgm on 2016/5/11.
 */
public class UserInfoModel extends BaseModel {
    
    public UserInfoModel(Context context) {
        super(context);
    }

    public void getUserInfo(){
        Call<Void> call = mDataManager.getUserInfo();
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}
