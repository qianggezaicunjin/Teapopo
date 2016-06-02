package com.teapopo.life.model;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.teapopo.life.data.remote.NetWorkService;

/**
 * Created by louiszgm on 2016/6/2.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class AuthorInfo extends BaseEntity {
    public String avatar;

    public String getAvatarUrl(){
        return NetWorkService.IMAGE_ENDPOINT+avatar+"_100x100"+NetWorkService.IMAGE_EXT;
    }
    public String nickname;

    public String fans_num;

    public String signature;
    @JsonField(name = "subscribe")
    public boolean isSubscribe;
}
