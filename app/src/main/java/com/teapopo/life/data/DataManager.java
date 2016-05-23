package com.teapopo.life.data;

import android.content.Context;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.teapopo.life.MyApplication;
import com.teapopo.life.data.remote.NetWorkService;
import com.teapopo.life.injection.component.ComponentHolder;
import com.teapopo.life.injection.component.DaggerDataManagerComponent;
import com.teapopo.life.injection.component.DataManagerComponent;
import com.teapopo.life.injection.module.DataManagerModule;
import com.teapopo.life.model.erroinfo.ErroInfo;
import com.teapopo.life.model.PostKeyValue;
import com.teapopo.life.model.category.CategoryList;
import com.teapopo.life.model.recommendarticle.Recommend;
import com.teapopo.life.model.toparticle.TopArticle;



import java.util.List;


import javax.inject.Inject;

import okhttp3.MultipartBody;
import retrofit2.Call;
import rx.Observable;
import rx.Scheduler;

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
         DataManagerComponent dataManagerComponent = DaggerDataManagerComponent.builder()
                .applicationComponent(ComponentHolder.getAppComponent())
                .dataManagerModule(new DataManagerModule(context))
                .build();
               dataManagerComponent .inject(this);

    }

    public Scheduler getScheduler() {
        return mSubscribeScheduler;
    }

    /**
     * 请求第几页的推荐文章列表
     * @param p 页码
     * @return
     */
   public Observable<JsonObject> getRecommendArticle(int p){

       return mNetWorkService.getRecommendArticle(p);
   }

    /**
     * 返回推荐文章列表页面的类别标签
     * @return
     */
    public Observable<JsonObject> getCategorys(){
        return mNetWorkService.getCategory();
    }
    /**
     * 获取每个模块的头部轮播的文章
     * @param classify 每个模块的标识分类 index,welfare,faxian,xinzi
     * @return
     */
    public Observable<JsonArray> getTopArticle(String classify){
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
    /**
     * 登录接口
     * @param params
     * @return
     */
    public Observable<JsonObject> login(List<PostKeyValue> params){
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        if(params!=null){
            for (int i=0;i<params.size();i++){
                PostKeyValue content= params.get(i);

                builder.addFormDataPart(content.getKey(), content.getValue());
            }

        }
        return mNetWorkService.login(builder.build());
    }

    /**
     * 获取用户个人信息
     * @return
     */
    public Observable<JsonObject> getUserInfo(){
        return mNetWorkService.getUserInfo();
    }

    /**
     * 获取短信验证码
     * @param phonenumber
     * @return
     */
    public Observable<JsonObject> getSmsVertify(String phonenumber,String temp_id){
            return mNetWorkService.getSmsVertify(phonenumber,temp_id);
    }

    /**
     * 验证手机是否被注册过
     * @param phonenum
     * @param vertifycode
     * @return
     */
    public Observable<JsonObject> vertifyPhone(String phonenum,String vertifycode){
        return mNetWorkService.vertifyPhone(phonenum,vertifycode);
    }

    /**
     * 注册
     * @param params
     * @return
     */
    public Observable<JsonObject> regist(List<PostKeyValue> params){
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        if(params!=null){
            for (int i=0;i<params.size();i++){
                PostKeyValue content= params.get(i);

                builder.addFormDataPart(content.getKey(), content.getValue());
            }

        }
        return mNetWorkService.regist(builder.build());
    }

    public Observable<JsonObject> check_openid(String openid,String platform){
            return mNetWorkService.check_openid(openid,platform);
    }
}
