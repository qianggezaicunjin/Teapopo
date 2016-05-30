package com.teapopo.life.model.article;

import android.databinding.BindingConversion;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.teapopo.life.data.remote.NetWorkService;
import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.util.DataUtils;

import java.util.List;

/**
 * Created by louiszgm on 2016/5/30.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class Article extends BaseEntity {
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
        public List<String> imageUrls;//文章的图片url

        public String nickname;//文章作者的昵称

        public String avatarUrl;//文章作者头像的图片url

        public String getAvatarUrl(){
            return NetWorkService.IMAGE_ENDPOINT+avatarUrl+"_150x150"+NetWorkService.IMAGE_EXT;
        }

        public List<String> tags;//文章的标签

        public boolean isLike;//是否喜欢

        public boolean isFocus;//是否关注
}
