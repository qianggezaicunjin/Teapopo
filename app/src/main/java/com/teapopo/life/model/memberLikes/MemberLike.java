package com.teapopo.life.model.memberLikes;

import com.teapopo.life.model.BaseEntity;

import java.util.List;

/**
 * Created by louiszgm on 2016/7/22.
 */
public class MemberLike extends BaseEntity {
    public String likeCounts;
    public String date;
    public List<MemberLikeDataOverView> dataOverViewList;
}
