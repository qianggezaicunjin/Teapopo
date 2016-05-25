package com.teapopo.life.data.remote;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/4/7 0007.
 */
public interface NetWorkService {
    String ENDPOINT = "http://www.teapopo.com/api/";
    String IMAGE_ENDPOINT="http://img.teapopo.com/";
    String IMAGE_EXT=".jpg";
    /**
     * 返回推荐的文章.
     * 参数
     * p:  页码 默认1
     * r:  每页行数 默认8
     */
    @GET("posts/recommend?app=1")
    Observable<JsonObject> getRecommendArticle(@Query("p") int page );

    /**
     *
     * 返回推荐文章列表页面的类别标签
     * 参数
     * p: 页码
     * r: 行数
     * 不带参数时，默认返回8条记录
     */
    @GET("terms/list")
    Observable<JsonObject> getCategory();


    /**
     * 返回每个版块头部轮播的文章
     * 参数
     * classify:  分类 index,welfare,faxian,xinzi
     */
    @GET("category/slide")
    Observable<JsonArray> getTopArticle(@Query("classify") String classify );

    /**
     * 登录
     * @param requestBody
     * @return
     */
    @POST("members/login")
    Observable<JsonObject> login(@Body RequestBody requestBody);

    /**
     * 注册
     * @param requestBody
     * @return
     */
    @POST("members/register")
    Observable<JsonObject> regist(@Body RequestBody requestBody);

    @GET("members/self")
    Observable<JsonObject> getUserInfo();


    /**
     * 绑定原有帐号
     * api/members/bind
     * POST
     * classify  weixin,qq,weibo
     * login  是否建立登录状态 1是0否
     * openid  openid or uid
     * phone 手机号
     */
    @POST("members/bind")
    Observable<JsonObject> bindAccount(@Body RequestBody requestBody);

    @GET("members/verify_sms?no_verify=1")
    Observable<JsonObject> getSmsVertify(@Query("phone") String phonenumber,@Query("temp_id") String forwhat);

    @FormUrlEncoded
    @POST("members/check_phone")
    Observable<JsonObject> vertifyPhone(@Field("phone") String phonenum,@Field("verify")String vertifycode);

    /**
     * 检查第三方账号是否已经被绑定
     * @param openid
     * @param platform qq,weibo,weixin
     * @return
     */
    @FormUrlEncoded
    @POST("members/check_openid")
    Observable<JsonObject> check_openid(@Field("openid") String openid,@Field("classify") String platform);

    @GET("posts/list")
    Call<JsonObject> getArticle(@Query("category")String category);
}
