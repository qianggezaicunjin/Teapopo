package com.teapopo.life.model.welfare.GoodsDetail;

import android.content.Context;

import com.bluelinelabs.logansquare.LoganSquare;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.teapopo.life.R;
import com.teapopo.life.model.AuthorInfo;
import com.teapopo.life.model.BaseModel;
import com.teapopo.life.model.comment.Comment;
import com.teapopo.life.model.comment.Reply;
import com.teapopo.life.model.welfare.Brand;
import com.teapopo.life.model.welfare.EventGoods;
import com.teapopo.life.model.welfare.EventGoodsInfo;
import com.teapopo.life.model.welfare.Guide;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.util.rx.RxResultHelper;
import com.teapopo.life.util.rx.RxSubscriber;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by louiszgm on 2016/7/5.
 */
public class GoodsDetailModel extends BaseModel {
    String leftCommentCount;
    public GoodsDetailModel(Context context) {
        super(context);
    }

    /**
     * 获取评论列表
     * @param id
     * @param classify
     */
    public void getCommentList(String id,String classify){
            Observable<JsonObject> observable = mDataManager.getCommentList(id,classify,1);
            observable.subscribeOn(Schedulers.io())
                    .compose(RxResultHelper.<JsonObject>handleResult())
                    .flatMap(new Func1<JsonObject, Observable<List<Comment>>>() {
                        @Override
                        public Observable<List<Comment>> call(JsonObject jsonObject) {
                            List<Comment> commentList = handleCommentListJson(jsonObject);
                            return Observable.just(commentList);
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new RxSubscriber<List<Comment>>() {
                        @Override
                        public void _onNext(List<Comment> comments) {
                            ModelAction modelAction = new ModelAction();
                            modelAction.action = Action.GoodsDetailModel_GetCommentList;
                            modelAction.t = comments;
                            mRequestView.onRequestSuccess(modelAction);
                        }

                        @Override
                        public void _onError(String s) {
                            mRequestView.onRequestErroInfo(s);
                        }
                    });
    }

    private List<Comment> handleCommentListJson(JsonObject jsonObject)  {
        List<Comment> commentList = new ArrayList<>();
        JsonObject data = jsonObject.getAsJsonObject("data");
        JsonArray comments = null;
        JsonObject members = data.getAsJsonObject("members");
        JsonObject comment_likes = null;
        if(data.has("comment_likes")){
            comment_likes = data.getAsJsonObject("comment_likes");
        }
        JsonObject replys = null;
        if(data.has("replys")){
            replys = data.getAsJsonObject("replys");
        }
        if(!data.get("comments").isJsonNull()){
            comments = data.getAsJsonArray("comments");
            int count = jsonObject.get("count").getAsInt();
            leftCommentCount = String.valueOf(count - comments.size());
            for (JsonElement object:comments){
                try {
                    Comment comment = LoganSquare.parse(object.toString(),Comment.class);
                    //添加该条评论的用户基本信息
                    JsonObject member = members.getAsJsonObject(comment.member_id);
                    AuthorInfo authorInfo = LoganSquare.parse(member.toString(),AuthorInfo.class);
                    comment.authorInfo = authorInfo;
                    //该条评论是否被喜欢
                    if(comment_likes!=null){
                        if(comment_likes.has(comment.id)){
                            comment.is_like = true;
                        }
                    }
                    //添加该条评论的回复列表
                    if(replys!=null) {
                        JsonArray reply = replys.getAsJsonArray(comment.id);
                        if (reply != null) {
                            for (JsonElement jsonElement1 : reply) {
                                Reply reply1 = LoganSquare.parse(jsonElement1.toString(), Reply.class);
                                JsonObject member_reply = members.getAsJsonObject(reply1.member_id);
                                AuthorInfo reply_authorInfo = LoganSquare.parse(member_reply.toString(), AuthorInfo.class);
                                reply1.authorInfo = reply_authorInfo;
                                comment.replyList.add(reply1);
                            }
                        }
                    }
                    commentList.add(comment);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return commentList;
    }
    /**
     * 获取活动商品的收藏列表
     * @param id 商品id
     */
    public void getCollectList(String id){
        Observable<JsonObject> observable = mDataManager.getCollectList(id);
        observable.subscribeOn(Schedulers.io())
                .observeOn(mDataManager.getScheduler())
                .compose(RxResultHelper.<JsonObject>handleResult())
                .flatMap(new Func1<JsonObject, Observable<List<AuthorInfo>>>() {
                    @Override
                    public Observable<List<AuthorInfo>> call(JsonObject jsonObject) {
                        List<AuthorInfo> authorInfoList = handleCollectListJson(jsonObject);
                        return Observable.just(authorInfoList);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<List<AuthorInfo>>() {
                    @Override
                    public void _onNext(List<AuthorInfo> authorInfos) {
                        ModelAction modelAction = new ModelAction();
                        modelAction.action = Action.GoodsDetailModel_GetCollectList;
                        modelAction.t = authorInfos;
                        mRequestView.onRequestSuccess(modelAction);
                    }

                    @Override
                    public void _onError(String s) {
                        mRequestView.onRequestErroInfo(s);
                    }
                });
    }

    public List<AuthorInfo> handleCollectListJson(JsonObject jsonObject){
        JsonObject data = jsonObject.getAsJsonObject("data");
        JsonArray collects = data.getAsJsonArray("collects");
        List<AuthorInfo> authorInfoList = new ArrayList<>();
        for(JsonElement element:collects){
            try {
                AuthorInfo authorInfo = LoganSquare.parse(element.toString(),AuthorInfo.class);
                authorInfoList.add(authorInfo );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return authorInfoList;
    }
    /**
     * 获取活动商品信息
     * @param id 活动商品id
     */
    public void getGoodsDetail(String id){
        Observable<JsonObject> observable = mDataManager.getGoodsInfo(id);
        observable.subscribeOn(Schedulers.io())
                .observeOn(mDataManager.getScheduler())
                .compose(RxResultHelper.<JsonObject>handleResult())
                .flatMap(new Func1<JsonObject, Observable<EventGoodsInfo>>() {
                    @Override
                    public Observable<EventGoodsInfo> call(JsonObject jsonObject) {
                        EventGoodsInfo eventGoodsInfo = handleGoodsInfoJson(jsonObject);
                        return Observable.just(eventGoodsInfo);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<EventGoodsInfo>() {
                    @Override
                    public void _onNext(EventGoodsInfo eventGoodsInfo) {
                        ModelAction modelAction = new ModelAction();
                        modelAction.action = Action.GoodsDetailModel_GetGoodsInfo;
                        modelAction.t = eventGoodsInfo;
                        mRequestView.onRequestSuccess(modelAction);
                    }

                    @Override
                    public void _onError(String s) {
                        mRequestView.onRequestErroInfo(s);
                    }
                });
    }


    public EventGoodsInfo handleGoodsInfoJson(JsonObject jsonObject){
        EventGoodsInfo eventGoodsInfo = new EventGoodsInfo();
        JsonObject goods = jsonObject.getAsJsonObject("goods");
        JsonObject guide = null;
        JsonObject brand = null;
        JsonArray wap_images = jsonObject.getAsJsonArray("wap_images");
        if(jsonObject.has("guide")){
            guide = jsonObject.getAsJsonObject("guide");
        }
        if(jsonObject.has("brand")){
            brand = jsonObject.getAsJsonObject("brand");
        }
        try {
            //添加活动商品的基本信息
            EventGoods eventGoods = LoganSquare.parse(goods.toString(),EventGoods.class);
            eventGoodsInfo.eventGoods = eventGoods;
            //添加活动商品的导购
            if(guide!=null){
                Guide guide1 = new Guide();
                guide1.content = guide.get("content").getAsString();
                AuthorInfo authorInfo = LoganSquare.parse(guide.toString(),AuthorInfo.class);
                guide1.authorInfo = authorInfo;
                eventGoodsInfo.guide = guide1;
            }
            //该活动商品是否被收藏
            eventGoodsInfo.is_collect = jsonObject.get("is_collect").getAsBoolean();
            //添加活动商品的图片
            List<String> images = new ArrayList<>();
            for(JsonElement jsonElement:wap_images){
                images.add(jsonElement.getAsString());
            }
            //添加品牌信息
            if(brand!=null){
                Brand brand1 = LoganSquare.parse(brand.toString(),Brand.class);
                brand1.logo = setWebImageSize(R.dimen.brand_logo_width,R.dimen.brand_logo_height,brand1.logo);
                Timber.d("logo的图片为:%s",brand1.logo);
                eventGoodsInfo.brand = brand1;
            }
            eventGoodsInfo.wap_images = images;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return eventGoodsInfo;
    }
}
