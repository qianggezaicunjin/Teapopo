package com.teapopo.life.model.article;

import android.content.Context;

import com.bluelinelabs.logansquare.LoganSquare;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.teapopo.life.model.BaseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by louiszgm on 2016/5/30.
 */
public class ArticleModel extends BaseModel {
    public int mPage = 1;
    //文章总页数
    public int pageCount = 0;
    //标志位:判断是否是首次请求数据
    private boolean isNotFirstLoad;
    public ArticleModel(Context context) {
        super(context);
    }

    /**
     *
     * @param jsonObject
     * @return 返回null表示没有新文章了
     * @throws Exception
     */
    public List handleArticleJson(JsonObject jsonObject) throws Exception{
        List<Article> results = new ArrayList<>();

        //下一页的页码
        mPage = jsonObject.get("page").getAsInt();
        pageCount = jsonObject.get("pages").getAsInt();
        //下一页是否还有数据
        if(mPage<pageCount){
            //若下一页有数据，页码加1
            mPage +=1;
        }
        JsonObject data = jsonObject.getAsJsonObject("data");
        //文章
        JsonArray posts = data.getAsJsonArray("posts");
        //文章附加的属性
        JsonObject members = data.getAsJsonObject("members");
        JsonObject images = data.getAsJsonObject("images");
        JsonObject tags = data.getAsJsonObject("tags");
        JsonObject ilikes = data.getAsJsonObject("ilikes");
        //解析json,将所有属性整合到一个CategoryArticle对象里面
        for(JsonElement post:posts){
            Article article = LoganSquare.parse(post.toString(),Article.class);
            //取得文章的会员信息
            JsonObject memberInfo = members.getAsJsonObject(article.member_id);
            article.avatarUrl = memberInfo.get("avatar").getAsString();
            article.nickname = memberInfo.get("nickname").getAsString();

            //取得文章的图片url地址
            if(images!=null){
                JsonArray imageUrlsArray = images.getAsJsonArray(article.articleId);
                if(imageUrlsArray!=null){
                    List<String> imageUrls = LoganSquare.parseList(imageUrlsArray.toString(),String.class);
                    article.imageUrls = imageUrls;
                }
            }
            //取得文章的标签信息
            if(tags!=null){
                JsonArray tagsArray = tags.getAsJsonArray(article.articleId);
                if(tagsArray!=null){
                    List<String> tagNames = LoganSquare.parseList(tagsArray.toString(),String.class);
                    article.tags = tagNames;
                }
            }
            //该文章是否被喜欢
            if(ilikes!=null){
                JsonElement element = ilikes.get(article.articleId);
                if(element!=null){
                    int isLike = element.getAsInt();
                    if(isLike==1){
                        article.isLike = true;
                    }
                }
            }
            //将新封装好的article对象加进results
            results.add(article);
        }
        return results;
    }
}
