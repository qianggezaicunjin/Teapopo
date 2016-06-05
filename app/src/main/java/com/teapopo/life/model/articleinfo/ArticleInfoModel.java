package com.teapopo.life.model.articleinfo;

import android.content.Context;

import com.bluelinelabs.logansquare.LoganSquare;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.teapopo.life.model.AuthorInfo;
import com.teapopo.life.model.BaseModel;
import com.teapopo.life.model.comment.Comment;
import com.teapopo.life.model.comment.Reply;
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

/**
 * Created by louiszgm-pc on 2016/5/31.
 */
public class ArticleInfoModel extends BaseModel {
    public ArticleInfoModel(Context context) {
        super(context);
    }

    //添加评论
    public void addComment(String id,int type,String content){
        Observable<JsonObject> observable = mDataManager.addComment(id,type,content);
        observable.subscribeOn(Schedulers.io())
                .observeOn(mDataManager.getScheduler())
                .compose(RxResultHelper.<JsonObject>handleResult())
                .flatMap(new Func1<JsonObject, Observable<Comment>>() {
                    @Override
                    public Observable<Comment> call(JsonObject jsonObject) {
                        Comment comment = handleCommentResponseJson(jsonObject);
                        return Observable.just(comment);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<Comment>() {
                    @Override
                    public void _onNext(Comment comment) {
                        ModelAction<Comment> action = new ModelAction<Comment>();
                        action.action = Action.ArticleInfoModel_AddComment;
                        action.t = comment;
                        mRequestView.onRequestSuccess(action);
                    }

                    @Override
                    public void _onError(String s) {

                    }
                });
    }
    private Comment handleCommentResponseJson(JsonObject jsonObject){
        Comment comment = new Comment();
        AuthorInfo authorInfo = new AuthorInfo();
        authorInfo.avatar = jsonObject.get("avatar").getAsString();
        authorInfo.nickname = jsonObject.get("nickname").getAsString();

        comment.id = jsonObject.get("id").getAsString();
        comment.content = jsonObject.get("content").getAsString();
        comment.add_time = jsonObject.get("add_time").getAsLong();
        comment.authorInfo = authorInfo;
        return comment;
    }
    //获取文章信息
    public void getArticleInfo(String articleId){
        Observable<JsonObject> observable = mDataManager.getArticleInfo(articleId);
        observable.subscribeOn(Schedulers.io())
                .observeOn(mDataManager.getScheduler())
                .compose(RxResultHelper.<JsonObject>handleResult())
                .flatMap(new Func1<JsonObject, Observable<ArticleInfo>>() {
                    @Override
                    public Observable<ArticleInfo> call(JsonObject jsonObject) {
                        ArticleInfo articleInfo = new ArticleInfo();
                        try {
                            articleInfo = handleArticleInfoJson(jsonObject);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return Observable.just(articleInfo);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<ArticleInfo>() {
                    @Override
                    public void _onNext(ArticleInfo articleInfo) {
                        ModelAction<ArticleInfo> action = new ModelAction<ArticleInfo>();
                        action.action = Action.ArticleInfoModel_GetInfo;
                        action.t = articleInfo;
                        mRequestView.onRequestSuccess(action);
                    }

                    @Override
                    public void _onError(String s) {
                        mRequestView.onRequestErroInfo(s);
                    }
                });

    }

    private ArticleInfo handleArticleInfoJson(JsonObject jsonObject) throws IOException {
        JsonObject post = jsonObject.getAsJsonObject("posts");
        JsonObject members = jsonObject.getAsJsonObject("members");

        ArticleInfo articleInfo = LoganSquare.parse(post.toString(), ArticleInfo.class);

        //添加文章的轮播图片
        JsonArray images = jsonObject.getAsJsonArray("images");
        if(images.size()>0){
            for(JsonElement image:images){
                String url = ((JsonObject)image).get("filename").getAsString();
                articleInfo.articleImageUrls.add(url);
            }
        }
        //文章作者的个人信息
        JsonObject member = members.getAsJsonObject(articleInfo.member_id);
        AuthorInfo authorInfo = LoganSquare.parse(member.toString(),AuthorInfo.class);
        articleInfo.authorInfo = authorInfo;
        //文章的标签
        for(JsonElement tag:jsonObject.getAsJsonArray("tags")){
            articleInfo.tags.add(tag.getAsString());
        }
        //添加喜欢的人的头像
        JsonArray likes = jsonObject.getAsJsonArray("likes");
        if(likes.size()>0){
            for(JsonElement jsonElement:likes){
                String member_id = jsonElement.getAsJsonObject().get("member_id").getAsString();
                JsonObject member_fans = members.getAsJsonObject(member_id);
                AuthorInfo like_authorInfo = LoganSquare.parse(member_fans.toString(),AuthorInfo.class);
                articleInfo.fans.add(like_authorInfo);
            }
        }
        //添加评论列表
        JsonArray comments = jsonObject.getAsJsonArray("comments");
        if(comments.size()>0){
            for (JsonElement jsonElement:comments){
                Comment comment = LoganSquare.parse(jsonElement.toString(),Comment.class);
                JsonObject member_comment = members.getAsJsonObject(comment.member_id);
                AuthorInfo comment_authorInfo = LoganSquare.parse(member_comment.toString(),AuthorInfo.class);
                comment.authorInfo = comment_authorInfo;
                //添加评论的回复列表
                JsonObject replys = jsonObject.getAsJsonObject("replys");
                if(replys!=null){
                    //一个评论有一条或者多条回复
                    JsonArray reply = replys.getAsJsonArray(comment.id);
                    if(reply!=null){
                        for (JsonElement jsonElement1:reply){
                            Reply reply1 = LoganSquare.parse(jsonElement1.toString(),Reply.class);
                            JsonObject member_reply = members.getAsJsonObject(reply1.member_id);
                            AuthorInfo reply_authorInfo = LoganSquare.parse(member_reply.toString(),AuthorInfo.class);
                            reply1.authorInfo = reply_authorInfo;
                            comment.replyList.add(reply1);
                        }
                    }
                }
                articleInfo.commentList.add(comment);
            }
        }
        return articleInfo;
    }


}
