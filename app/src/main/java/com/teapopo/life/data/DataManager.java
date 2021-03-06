package com.teapopo.life.data;

import android.content.Context;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.teapopo.life.data.remote.NetWorkService;
import com.teapopo.life.injection.component.ComponentHolder;
import com.teapopo.life.injection.component.DaggerDataManagerComponent;
import com.teapopo.life.injection.component.DataManagerComponent;
import com.teapopo.life.injection.module.DataManagerModule;
import com.teapopo.life.model.PostKeyValue;
import com.teapopo.life.model.address.SelectedAddress;
import com.teapopo.life.model.welfare.CartGoods;


import java.util.List;


import javax.inject.Inject;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
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
        DataManagerComponent dataManagerComponent = DaggerDataManagerComponent.builder()
                .applicationComponent(ComponentHolder.getAppComponent())
                .dataManagerModule(new DataManagerModule(context))
                .build();
        dataManagerComponent.inject(this);

    }

    public Scheduler getScheduler() {
        return mSubscribeScheduler;
    }

    /**
     * 获取热门标签
     *
     * @return
     */
    public Observable<JsonObject> getHotTags() {
        return mNetWorkService.getHotTags();
    }

    /**
     * 获取每个模块的头部轮播的文章
     *
     * @param classify 每个模块的标识分类 index,welfare,faxian,xinzi
     * @return
     */
    public Observable<JsonArray> getTopArticle(String classify) {
        return mNetWorkService.getTopArticle(classify);
    }

    /**
     * @param isVertifyCodeLogin 是否使用短信验证码登录
     * @param account            用户名/手机号/邮箱
     * @param passwd             密码/验证码
     * @return
     */
    public Observable<JsonObject> login(boolean isVertifyCodeLogin, String account, String passwd) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        builder.addFormDataPart("no_verify", "1");
        if (isVertifyCodeLogin) {
            builder.addFormDataPart("phone", account);
            builder.addFormDataPart("sms_verify", passwd);
            builder.addFormDataPart("use_sms", "1");
        } else {
            builder.addFormDataPart("login_name", account);
            builder.addFormDataPart("password", passwd);
            builder.addFormDataPart("use_sms", "0");
        }
        return mNetWorkService.login(builder.build());
    }

    /**
     * 获取用户个人信息
     *
     * @return
     */
    public Observable<JsonObject> getUserInfo() {
        return mNetWorkService.getUserInfo();
    }

    /**
     * 获取短信验证码
     *
     * @param phonenumber
     * @return
     */
    public Observable<JsonObject> getSmsVertify(String phonenumber, String temp_id) {
        return mNetWorkService.getSmsVertify(phonenumber, temp_id);
    }

    /**
     * 验证手机是否被注册过
     *
     * @param phonenum
     * @param vertifycode
     * @return
     */
    public Observable<JsonObject> vertifyPhone(String phonenum, String vertifycode) {
        return mNetWorkService.vertifyPhone(phonenum, vertifycode);
    }

    /**
     * 注册
     *
     * @param params
     * @return
     */
    public Observable<JsonObject> regist(List<PostKeyValue> params) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        if (params != null) {
            for (int i = 0; i < params.size(); i++) {
                PostKeyValue content = params.get(i);

                builder.addFormDataPart(content.getKey(), content.getValue());
            }

        }
        return mNetWorkService.regist(builder.build());
    }

    /**
     * 用来检查第三方账号是否已经被绑定过
     *
     * @param openid
     * @param platform
     * @return
     */
    public Observable<JsonObject> check_openid(String openid, String platform) {
        return mNetWorkService.check_openid(openid, platform);
    }

    /**
     * 绑定第三方账号
     *
     * @return
     */
    public Observable<JsonObject> bindAccount(String platform, String phone) {
        Platform p = ShareSDK.getPlatform(platform);
        String openid = p.getDb().getUserId();
        Timber.d("绑定第三方账号,电话号码为:%s,平台为:%s,openid为:%s", phone, platform, openid);
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        builder.addFormDataPart("login", "1");
        builder.addFormDataPart("classify", platform.toLowerCase());
        builder.addFormDataPart("phone", phone);
        builder.addFormDataPart("openid", openid);
        return mNetWorkService.bindAccount(builder.build());
    }


    /**
     * 获取category的文章
     *
     * @param category 发现/新滋的文章
     * @param page
     * @return
     */
    public Observable<JsonObject> getCategoryArticle(String category, int page) {
        return mNetWorkService.getCategoryArticle(category, page);
    }

    /**
     * 获取会员的文章
     * @param member
     * @param page
     * @return
     */
    public Observable<JsonObject> getMemberArticle(String member,int page){
        return mNetWorkService.getMemberArticle(member,page);
    }

    /**
     * 获取会员的信息
     * @param member_id
     * @return
     */
    public Observable<JsonObject> getMemberInfo(String member_id){
        return mNetWorkService.getMemberInfo(member_id);
    }

    /**
     * 文章详情
     * @param id
     * @return
     */
    public Observable<JsonObject> getArticleInfo(String id) {
        return mNetWorkService.getArticleInfo(id);
    }

    /**
     * 点赞/取消点赞文章
     *
     * @param islike
     * @param id
     * @return
     */
    public Observable<JsonObject> clickLikeArticle(boolean islike, String id) {
        if (islike) {
            return mNetWorkService.likeArticle(id);
        } else {
            return mNetWorkService.unLikeArticle(id);
        }
    }

    /**
     * 点赞评论
     * 为文章的评论或者商品的评论点赞
     *
     * @param id
     * @param type posts：文章/goods：商品
     * @return
     */
    public Observable<JsonObject> clickLikeComment(String type, String id) {
        return mNetWorkService.likeComment(id, type);
    }

    /**
     * 获取喜欢的文章列表
     *
     * @param isHomeArticle 是否在首页展示
     * @param page          在首页展示的喜欢文章包含了用户自己发布的以及点赞的文章
     *                      在个人中心展示的喜欢文章只是包括了用户自己点赞的文章
     * @return
     */
    public Observable<JsonObject> getLikeArticle(boolean isHomeArticle, int page) {
        if (isHomeArticle) {
            return mNetWorkService.getHomeLikeArticle(true, page);
        } else {
            return mNetWorkService.getUserLikeArticle(true, page);
        }
    }

    /**
     * 添加评论
     *
     * @param id
     * @param type    类型 0是posts 1是goods
     * @param content
     * @return
     */
    public Observable<JsonObject> addComment(String id, int type, String content) {
        switch (type) {
            case 0://评论文章
                return mNetWorkService.addComment(id, "posts", content);
            case 1://评论商品
                return mNetWorkService.addComment(id, "goods", content);
        }
        return null;
    }

    /**
     * 回复评论
     *
     * @param id      评论id
     * @param type    类型 0是posts 1是goods
     * @param content
     * @return
     */
    public Observable<JsonObject> replyComment(String id, int type, String content) {
        switch (type) {
            case 0://回复文章的评论
                return mNetWorkService.replyComment(id, "posts", content);
            case 1://回复商品的评论
                return mNetWorkService.replyComment(id, "goods", content);
        }
        return null;
    }

    /**
     * 发布文章
     *
     * @param title
     * @param content
     * @param coverImage
     * @param imagesID
     * @param tags
     * @return
     */
    public Observable<JsonObject> publishArticle(String title, String content, String coverImage, String tags, String[] imagesID) {
        return mNetWorkService.publishArticle(1, title, content, coverImage, tags, imagesID);
    }

    /**
     * 上传图片
     *
     * @param articleID
     * @param base64Encode
     * @return
     */
    public Call uploadImage(String articleID, String base64Encode) {
        return mNetWorkService.uploadImage(articleID, base64Encode);
    }

    /**
     * 关注会员
     *
     * @param memberId
     * @return
     */
    public Observable<JsonObject> focusmember(String memberId) {
        return mNetWorkService.focusMember(memberId);
    }

    /**
     * 获取用户的消息列表
     *
     * @param classify system/private
     * @return
     */
    public Observable<JsonObject> getMsgList(String classify) {
        return mNetWorkService.getMsgList(classify);
    }

    /**
     * 活动列表
     *
     * @return
     */
    public Observable<JsonObject> getEventList(int page) {
        return mNetWorkService.getEventList(page);
    }

    /**
     * 会员登出
     *
     * @return
     */
    public Observable<JsonObject> logOut() {
        return mNetWorkService.logOut();
    }

    /**
     * 获取评论列表
     *
     * @param id
     * @param classify
     * @return
     */
    public Observable<JsonObject> getCommentList(String id, String classify, int page) {
        return mNetWorkService.getCommentList(id, classify, page);
    }

    /**
     * @param id
     * @return
     */
    public Observable<JsonObject> getEventGoodsList(String id, int type) {
        Observable<JsonObject> observable = null;
        switch (type) {
            case 1:
                observable = mNetWorkService.getEventGoodsList(id, null, null);
                break;
            case 2:
                observable = mNetWorkService.getEventGoodsList(id, "point", null);
                break;
            case 3:
                observable = mNetWorkService.getEventGoodsList(id, null, "hot");
                break;
            case 4:
                observable = mNetWorkService.getEventGoodsList(id, null, "new");
                break;
        }
        return observable;
    }

    /**
     * 获取收货地址列表
     *
     * @return
     */
    public Observable<JsonObject> getAddressList() {
        return mNetWorkService.getAddressList();
    }


    /**
     * 下单
     *
     * @param buy_info   json格式  {活动商品ID：商品数，活动商品ID：商品数}
     * @param address_id
     * @param remark
     * @return
     */
    public Observable<JsonObject> makeOrder(String buy_info, String address_id, String remark) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        builder.addFormDataPart("buy_info", buy_info);
        builder.addFormDataPart("address_id", address_id);
        builder.addFormDataPart("message", remark);
        builder.addFormDataPart("source", String.valueOf(2));
        return mNetWorkService.makeOrder(builder.build());
    }

    /**
     * 获取订单信息
     *
     * @param orderId
     * @return
     */
    public Observable<JsonObject> getOrderInfo(String orderId) {
        return mNetWorkService.getOrderInfo(orderId);
    }

    /**
     * 获取商品的收藏列表
     *
     * @param id 商品的原始id
     * @return
     */
    public Observable<JsonObject> getCollectList(String id) {
        return mNetWorkService.getCollectList(id, "goods");
    }

    /**
     * 获取活动商品信息
     *
     * @param id
     * @return
     */
    public Observable<JsonObject> getGoodsInfo(String id) {
        return mNetWorkService.getGoodsInfo(id);
    }

    /**
     * 购物车列表
     *
     * @return
     */
    public Observable<List<CartGoods>> getCartList() {
        return mNetWorkService.getCartList();
    }


    /**
     * 添加收货地址
     *
     * @param trueName
     * @param phone
     * @param selectedAddress
     * @param detailAddress
     * @param zipcode
     * @return
     */
    public Observable<JsonObject> addAddress(String trueName, String phone, SelectedAddress selectedAddress, String detailAddress, String zipcode) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        builder.addFormDataPart("truename", trueName);
        builder.addFormDataPart("phone", phone);
        builder.addFormDataPart("province_id", selectedAddress.provinceId);
        builder.addFormDataPart("city_id", selectedAddress.cityId);
        builder.addFormDataPart("area_id", selectedAddress.areaId);
        builder.addFormDataPart("address", detailAddress);
        builder.addFormDataPart("zipcode", zipcode);
        return mNetWorkService.addAddress(builder.build());
    }

    /**
     * 个人点赞
     * @param memberId
     * @return
     */
    public Observable<JsonObject> getMemberLikes(String memberId){
        return mNetWorkService.memberLikes(memberId);
    }
}
