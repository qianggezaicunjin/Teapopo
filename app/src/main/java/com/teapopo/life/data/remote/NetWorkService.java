package com.teapopo.life.data.remote;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.teapopo.life.model.welfare.CartGoods;


import java.util.List;

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
     * @param phonenumber
     * @param forwhat
     * @return
     */
    @GET("members/verify_sms?no_verify=1")
    Observable<JsonObject> getSmsVertify(@Query("phone") String phonenumber, @Query("temp_id") String forwhat);

    /**
     * 验证手机号
     * @param phonenum
     * @param vertifycode
     * @return
     */
    @FormUrlEncoded
    @POST("members/check_phone")
    Observable<JsonObject> vertifyPhone(@Field("phone") String phonenum, @Field("verify") String vertifycode);

    /**
     * 验证openid
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
     * 获取会员的文章列表
     *
     * @param member_id
     * @return
     */
    @GET("posts/list?all=1")
    Observable<JsonObject> getMemberArticle(@Query("member_id") String member_id, @Query("p") int page);
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
     * 点赞评论
     * 为文章的评论或者商品的评论点赞
     * @param commentId
     * @param type  posts：文章/goods：商品
     * @return
     */
    @GET("comments/like")
    Observable<JsonObject> likeComment(@Query("id") String commentId,@Query("type")String type);


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

    /**
     * 上传图片
     * @param articleId
     * @param base64Encode
     * @return
     */
    @POST("posts/images")
    @FormUrlEncoded
    Call<JsonObject> uploadImage(@Field("post_id") String articleId, @Field("base64") String base64Encode);

    /**
     * 关注会员
     * @param memberId
     * @return
     */
    @GET("focus/member")
    Observable<JsonObject>  focusMember(@Query("id")String memberId);

    /**
     * 消息列表
     * @param classify
     * @return
     */
    @GET("members/messages")
    Observable<JsonObject>  getMsgList(@Query("classify")String classify);

    /**
     * 活动列表
     * @return
     */
    @GET("events/list")
    Observable<JsonObject> getEventList(@Query("p")int page);

    /**
     * 会员登出
     * @return
     */
    @GET("members/logout")
    Observable<JsonObject> logOut();

    /**
     * 获取评论列表
     * @param id
     * @param type 分类 goods 或者 posts
     * @return
     */
    @GET("comments/list")
    Observable<JsonObject> getCommentList(@Query("id")String id,@Query("type")String type,@Query("p")int page);

    /**
     * 活动商品列表
     * 每种类型的商品列表通过本地进行筛选，节省网络资源
     * @param id
     * @return
     */
    @GET("events/goods")
    Observable<JsonObject> getEventGoodsList(@Query("id")String id,@Query("price")String price,@Query("order")String order);

    /**
     * 获取收货地址列表
     * @return
     */
    @GET("address/list")
    Observable<JsonObject> getAddressList();


    /**
     * 下单
     * @return
     */
    @POST("orders/create")
    Observable<JsonObject>  makeOrder(@Body RequestBody requestBody);

    /**
     * 获取订单信息
     * @param orderId
     * @return
     */
    @GET("orders/info")
    Observable<JsonObject> getOrderInfo(@Query("id")String orderId);

    /**
     * 收藏列表
     * @param id
     * @param type goods or posts
     * @return
     */
    @GET("collects/list")
    Observable<JsonObject> getCollectList(@Query("id")String id,@Query("type")String type);

    /**
     * 活动商品信息
     * @param id 活动商品id
     * @return
     */
    @GET("events/goods_info")
    Observable<JsonObject> getGoodsInfo(@Query("id")String id);

    /**
     * 购物车列表
     * @return
     */
    @GET("cart/list")
    Observable<List<CartGoods>> getCartList();

    @POST("address/add")
    Observable<JsonObject> addAddress(@Body RequestBody requestBody);
    @GET("test")
    Call<JsonObject> test();
}
