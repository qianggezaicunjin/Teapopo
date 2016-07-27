package com.teapopo.life.model.memberLikes;

import com.teapopo.life.model.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by louiszgm on 2016/7/22.
 */
public class MemberLike extends BaseEntity {
    public String likeCounts;
    public String getLikeCounts(){
        return likeCounts+"篇";
    }

    public String date;
    public List<MemberLikeDataOverView> dataOverViewList=new ArrayList<>();

    public List<String> getImgUrl(){
        List<String> ImgUrl=new ArrayList<>();
        for(int i=0;i<dataOverViewList.size();i++){
            ImgUrl.add(dataOverViewList.get(i).cover);
        }
        return ImgUrl;
    }
}
