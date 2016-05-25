package com.teapopo.life.model.recommendarticle;

import android.databinding.BindingConversion;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.teapopo.life.data.remote.NetWorkService;
import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.util.DataUtils;

import java.util.List;

/**
 * Created by louiszgm on 2016/4/23.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class RecommendArticle extends BaseEntity{

    public String browse_num;

    public String collect_num;

    public String comment_num;

    public String cover;

    public String excerpt;//文章内容

    public String getExcerpt(){
        return  excerpt.replaceAll("\t|\n", "");
    }
    @JsonField(name = "id")
    public String articleId;

    public String like_num;

    public String member_id;

    public long publish_time;

    @BindingConversion
    public static String getTime(long time){
        return DataUtils.getStrTime(String.valueOf(time));
    }

    public String title;

   //下面是增加的属性
   public ArticleImage articleImage;//文章的图片url

    public String nickname;//文章作者的昵称

    public String avatarUrl;//文章作者头像的图片url

    public String getAvatarUrl(){
        return NetWorkService.IMAGE_ENDPOINT+avatarUrl+"_70x70"+NetWorkService.IMAGE_EXT;
    }
    @Override
    public String toString() {
        return "文章的图片为:"+articleImage.articleImageUrls.size()+"作者昵称为:"+nickname+"头像url为:"+avatarUrl+"文章ID为:"+articleId;
    }
}
