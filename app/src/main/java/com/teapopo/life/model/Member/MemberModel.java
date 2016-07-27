package com.teapopo.life.model.Member;

import android.content.Context;

import com.google.gson.JsonObject;
import com.teapopo.life.model.BaseModel;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.util.rx.RxResultHelper;
import com.teapopo.life.util.rx.RxSubscriber;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lhq on 2016/7/26.
 */

public class MemberModel extends BaseModel {
    public MemberModel(Context context) {
        super(context);
    }
    /**
     * 关注会员
     * @param memberId
     */
    public void focusMember( String memberId){
        Observable<JsonObject> observable = mDataManager.focusmember(memberId);
        observable.subscribeOn(Schedulers.io())
                .compose(RxResultHelper.<JsonObject>handleResult())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<JsonObject>() {
                    @Override
                    public void _onNext(JsonObject jsonObject) {
                        ModelAction modelAction = new ModelAction();
                        modelAction.action = Action.MemberModel_focusMember;
                        mRequestView.onRequestSuccess(modelAction);
                    }

                    @Override
                    public void _onError(String s) {

                    }
                });
    }
}
