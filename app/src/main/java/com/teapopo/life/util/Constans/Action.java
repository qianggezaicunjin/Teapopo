package com.teapopo.life.util.Constans;

import com.teapopo.life.model.comment.CommentModel;

/**
 * Created by louiszgm-pc on 2016/5/20.
 */
public enum Action {
    //for SignUpVertifyCodeModel
    SignUpVertifyCodeModel_GetVertifyCode,
    SignUpVertifyCodeModel_VertifyPhone,
    SignUpVertifyCodeModel_BindAccount,
    SignUpVertifyCodeModel_Login,
    //for SignUpUserInfoModel
    SignUpUserInfoModel_Regist,
    //for CategoryArticleModel
    CategoryArticleModel_GetArticle,
    //for RecommendArticleModel
    RecommendArticleModel_GetTopArticle,
    RecommendArticleModel_GetContents,
    RecommendArticleModel_GetHotTags,
    //for HomeLikeArticleModel
    HomeLikeArticleModel_GetArticle,
    //for ThirdPlatformModel
    CheckOpenIdModel_Check_OpenId,
    //for SignInModel
    SignInModel_Login,
    //for ArticleItemModel
    ArticleItemModel_likeArticleOrNot,
    //for ArticleInfoModel
    ArticleInfoModel_GetInfo,
    ArticleInfoModel_AddComment,
    ArticleInfoModel_ReplyComment,
    //for CommentModel
    CommentModel_ReplyComment,
    //for PublishArticleModel
    PublishArticleModel_PublishWithoutImage,
    PublishArticleModel_PublishWithImage
}
