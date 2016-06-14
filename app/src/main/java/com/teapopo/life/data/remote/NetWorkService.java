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
    String IMAGE_ENDPOINT = "http://img.teapopo.com/";
    String IMAGE_EXT = ".jpg";

    /**
     * 返回推荐文章列表页面的类别标签
     * 参数
     * p: 页码
     * r: 行数
     * 不带参数时，默认返回8条记录
     */
    @GET("terms/list")
    Observable<JsonObject> getHotTags();


    /**
     * 返回每个版块头部轮播的文章
     * 参数
     * classify:  分类 index,welfare,faxian,xinzi
     */
    @GET("category/slide")
    Observable<JsonArray> getTopArticle(@Query("classify") String classify);

    /**
     * 登录
     *
     * @param requestBody
     * @return
     */
    @POST("members/login")
    Observable<JsonObject> login(@Body RequestBody requestBody);

    /**
     * 注册
     *
     * @param requestBody
     * @return
     */
    @POST("members/register")
    Observable<JsonObject> regist(@Body RequestBody requestBody);

    /**
     * 获取个人信息
     *
     * @return
     */
    @GET("members/self")
    Observable<JsonObject> getUserInfo();


    /**
     * 绑定
     * api/members/bind
     * POST
     * classify  weixin,qq,weibo
     * login  是否建立登录状态 1是0否
     * openid  openid or uid
     * phone 手机号
     */
    @POST("members/bind")
    Observable<JsonObject> bindAccount(@Body RequestBody requestBody);

    /**
     * 发送短信验证码
     *
     * @param phonenumber
     * @param forwhat
     * @return
     */
    @GET("members/verify_sms?no_verify=1")
    Observable<JsonObject> getSmsVertify(@Query("phone") String phonenumber, @Query("temp_id") String forwhat);

    /**
     * 验证手机号
     *
     * @param phonenum
     * @param vertifycode
     * @return
     */
    @FormUrlEncoded
    @POST("members/check_phone")
    Observable<JsonObject> vertifyPhone(@Field("phone") String phonenum, @Field("verify") String vertifycode);

    /**
     * 验证openid
     *
     * @param openid
     * @param platform qq,weibo,weixin
     * @return
     */
    @FormUrlEncoded
    @POST("members/check_openid")
    Observable<JsonObject> check_openid(@Field("openid") String openid, @Field("classify") String platform);

    /**
     * 文章列表
     * 获取首页的推荐文章列表，以及新滋的文章列表
     *
     * @param category 参数为: 发现/新滋
     * @return
     */
    @GET("posts/list?all=1")
    Observable<JsonObject> getCategoryArticle(@Query("category") String category, @Query("p") int page);

    /**
     * 文章列表
     * 获取个人点赞以及发布的文章,首页的喜欢文章列表
     *
     * @param ilike
     * @return
     */
    @GET("posts/list?all=1")
    Observable<JsonObject> getHomeLikeArticle(@Query("ilikes") boolean ilike, @Query("p") int page);

    /**
     * 文章列表
     * 获取个人点赞的文章,个人中心里的赞过的文章
     *
     * @param like
     * @return
     */
    @GET("posts/list?all=1")
    Observable<JsonObject> getUserLikeArticle(@Query("likes") boolean like, @Query("p") int page);

    /**
     * 文章列表
     * 获取个人发布的文章
     *
     * @param memberId
     * @return
     */
    @GET("posts/list?all=1")
    Observable<JsonObject> getUserPublishArticle(@Query("member_id") String memberId);

    /**
     * 文章信息
     *
     * @param articleId 通过文章id获得文章详情
     * @return
     */
    @GET("posts/info")
    Observable<JsonObject> getArticleInfo(@Query("id") String articleId);

    /**
     * 添加点赞
     *
     * @return
     */
    @GET("likes/add")
    Observable<JsonObject> likeArticle(@Query("id") String articleId);

    /**
     * 取消点赞
     *
     * @return
     */
    @GET("likes/delete")
    Observable<JsonObject> unLikeArticle(@Query("id") String articleId);

    /**
     * 添加评论
     *
     * @param id
     * @param type    分类 posts:文章  goods：商品
     * @param content
     * @return
     */
    @POST("comments/add")
    @FormUrlEncoded
    Observable<JsonObject> addComment(@Query("id") String id, @Query("type") String type, @Field("content") String content);

    /**
     * 回复评论
     *
     * @param id
     * @param type
     * @param content
     * @return
     */
    @POST("comments/reply")
    @FormUrlEncoded
    Observable<JsonObject> replyComment(@Query("id") String id, @Query("type") String type, @Field("content") String content);


    /**
     * @param title      文章标题
     * @param content    文章内容
     * @param coverImage 文章封面
     * @param images     文章图片
     * @return
     */
    @POST("posts/add")
    @FormUrlEncoded
    Observable<JsonObject> publishArticle(@Field("no_verify")int no_vertify,@Field("title") String title, @Field("content") String content, @Field("cover") String coverImage, @Field("images") String[] images,@Field("tags")String tags);

    @POST("posts/images")
    @FormUrlEncoded
    Observable<JsonObject> uploadImage(@Field("post_id") String articleId, @Field("base64") String base64Encode);


    @GET("test")
    Call<JsonObject> test();
}
