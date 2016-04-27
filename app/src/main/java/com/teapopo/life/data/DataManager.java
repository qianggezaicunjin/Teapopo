package com.teapopo.life.data;

import android.content.Context;

import com.teapopo.life.MyApplication;
import com.teapopo.life.data.remote.NetWorkService;
import com.teapopo.life.data.rx.RxBus;
import com.teapopo.life.injection.component.DaggerDataManagerComponent;
import com.teapopo.life.injection.module.DataManagerModule;
import com.teapopo.life.model.PostKeyValue;
import com.teapopo.life.model.recommendarticle.Recommend;
import com.teapopo.life.model.toparticle.TopArticle;

import java.util.List;

import javax.inject.Inject;

import okhttp3.MultipartBody;
import retrofit2.Call;
import rx.Observable;
import rx.Scheduler;
import timber.log.Timber;

/**
 * Created by Administrator on 2016/4/7 0007.
 */
public class DataManager {
    @Inject
    protected NetWorkService mNetWorkService;
    @Inject
    protected Scheduler mSubscribeScheduler;

    public DataManager(Context context) {
        injectDependencies(context);
    }

    /* This constructor is provided so we can set up a DataManager with mocks from unit test.
     * At the moment this is not possible to do with Dagger because the Gradle APT plugin doesn't
     * work for the unit test variant, plus Dagger 2 doesn't provide a nice way of overriding
     * modules */
    public DataManager(NetWorkService watchTowerService,
                       Scheduler subscribeScheduler) {
        mNetWorkService = watchTowerService;
        mSubscribeScheduler = subscribeScheduler;
    }

    protected void injectDependencies(Context context) {
        DaggerDataManagerComponent.builder()
                .applicationComponent(MyApplication.get(context).getComponent())
                .dataManagerModule(new DataManagerModule())
                .build()
                .inject(this);
    }

    public Scheduler getScheduler() {
        return mSubscribeScheduler;
    }

    /**
     * 请求第几页的推荐文章列表
     * @param p 页码
     * @return
     */
   public Observable<Recommend> getRecommendArticle(int p){

       return mNetWorkService.getRecommendArticle(p);
   }

    /**
     * 获取每个模块的头部轮播的文章
     * @param classify 每个模块的标识分类 index,welfare,faxian,xinzi
     * @return
     */
    public Observable<List<TopArticle>> getTopArticle(String classify){
        return mNetWorkService.getTopArticle(classify);
    }
    /**
     * 登陆第三方社交账号之后 绑定新账号
     * @param params
     * @return
     */
    public Call<Void> bindNewAccount(List<PostKeyValue> params){
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        if(params!=null){
            for (int i=0;i<params.size();i++){
               PostKeyValue content= params.get(i);

                builder.addFormDataPart(content.getKey(), content.getValue());
            }

        }
        return mNetWorkService.bindNewAccount(builder.build());
    }
}
