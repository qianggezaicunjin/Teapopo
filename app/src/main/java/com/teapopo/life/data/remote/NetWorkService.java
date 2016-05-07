package com.teapopo.life.data.remote;


import com.teapopo.life.model.category.Category;
import com.teapopo.life.model.category.CategoryList;
import com.teapopo.life.model.recommendarticle.Recommend;
import com.teapopo.life.model.toparticle.TopArticle;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
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
    Observable<Recommend> getRecommendArticle(@Query("p") int page );

    /**
     *
     * 返回推荐文章列表页面的类别标签
     * 参数
     * p: 页码
     * r: 行数
     * 不带参数时，默认返回8条记录
     */
    @GET("terms/list")
    Observable<CategoryList> getCategory();


    /**
     * 返回每个版块头部轮播的文章
     * 参数
     * classify:  分类 index,welfare,faxian,xinzi
     */
    @GET("category/slide")
    Observable<List<TopArticle>> getTopArticle(@Query("classify") String classify );
    /**
     * 绑定社交账号
     * 建立新帐号
     * api/members/register
     * POST
     * classify   weixin,qq,weibo
     * openid   openid or uid
     * nickname 昵称
     * sex 1男2女0未知
     * province 省份名
     * city 城市名
     * headimgurl 头像链接
     * phone 手机号
     * password 密码
     * confirm 确认密码
     * verify 短信验证码
     * @param requestBody
     * @return
     */
    @POST("members/register")
    Call<Void> bindNewAccount(@Body RequestBody requestBody);

    /**
     * 绑定原有帐号
     * api/members/bind
     * POST
     * classify  weixin,qq,weibo
     * login  是否建立登录状态 1是0否
     * openid  openid or uid
     * phone 手机号
     * password 密码
     */
    @POST("members/bind")
    Call<Void> bindAccount(@Body RequestBody requestBody);


}
