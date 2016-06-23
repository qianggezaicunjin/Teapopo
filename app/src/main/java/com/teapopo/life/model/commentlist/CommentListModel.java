package com.teapopo.life.model.commentlist;

import android.content.Context;

import com.bluelinelabs.logansquare.LoganSquare;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.teapopo.life.model.AuthorInfo;
import com.teapopo.life.model.BaseModel;
import com.teapopo.life.model.comment.Comment;
import com.teapopo.life.model.comment.Reply;
import com.teapopo.life.model.sharedpreferences.RxSpf_UserInfoSp;
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
 * Created by louiszgm on 2016/6/23.
 */
public class CommentListModel extends BaseModel {
    public CommentListModel(Context context) {
        super(context);
    }


    public void getCommentList(String id,String classify){
        Observable<JsonObject> observable = mDataManager.getCommentList(id,classify);
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
                        modelAction.action = Action.CommentListModel_GetCommentList;
                        modelAction.t = comments;
                        mRequestView.onRequestSuccess(modelAction);
                    }

                    @Override
                    public void _onError(String s) {
                        mRequestView.onRequestErroInfo(s);
                    }
                });
    }

    //回复评论
    public void replyComment(final String id, int type, final String content){
        Observable<JsonObject> observable = mDataManager.replyComment(id,type,content);
        observable.subscribeOn(Schedulers.io())
                .observeOn(mDataManager.getScheduler())
                .compose(RxResultHelper.<JsonObject>handleResult())
                .flatMap(new Func1<JsonObject, Observable<Reply>>() {
                    @Override
                    public Observable<Reply> call(JsonObject jsonObject) {
                        RxSpf_UserInfoSp rxSpf_userInfoSp = RxSpf_UserInfoSp.create(mContext);
                        Reply reply = new Reply();
                        reply.authorInfo.nickname = rxSpf_userInfoSp.username().get();
                        reply.content = content;
                        reply.commentId = id;
                        return Observable.just(reply);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<Reply>() {
                    @Override
                    public void _onNext(Reply reply) {
                        ModelAction<Reply> action = new ModelAction<Reply>();
                        action.action = Action.CommentListModel_ReplyComment;
                        action.t = reply;
                        mRequestView.onRequestSuccess(action);
                    }

                    @Override
                    public void _onError(String s) {
                        mRequestView.onRequestErroInfo(s);
                    }
                });
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
                        action.action = Action.CommentListModel_AddComment;
                        action.t = comment;
                        mRequestView.onRequestSuccess(action);
                    }

                    @Override
                    public void _onError(String s) {
                        mRequestView.onRequestErroInfo(s);
                    }
                });
    }
    private Comment handleCommentResponseJson(JsonObject jsonObject){
        Timber.d("评论成功服务器返回的json:%s",jsonObject.toString());
        Comment comment = new Comment();
        AuthorInfo authorInfo = new AuthorInfo();
        authorInfo.avatar = jsonObject.get("avatar").getAsString();
        authorInfo.nickname = jsonObject.get("nickname").getAsString();

        comment.id = jsonObject.get("id").getAsString();
        comment.content = jsonObject.get("content").getAsString();
        comment.add_time = jsonObject.get("add_time").getAsLong();
        if(jsonObject.has("is_like")){
            comment.is_like = jsonObject.get("is_like").getAsBoolean();
        }
        comment.authorInfo = authorInfo;
        return comment;
    }
    private List<Comment> handleCommentListJson(JsonObject jsonObject)  {
        List<Comment> commentList = new ArrayList<>();
        JsonObject data = jsonObject.getAsJsonObject("data");
        JsonArray comments = data.getAsJsonArray("comments");
        JsonObject members = data.getAsJsonObject("members");
        JsonObject comment_likes = null;
        if(data.has("comment_likes")){
            comment_likes = data.getAsJsonObject("comment_likes");
        }
        JsonObject replys = null;
        if(data.has("replys")){
            replys = data.getAsJsonObject("replys");
        }
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
        return commentList;
    }
}
