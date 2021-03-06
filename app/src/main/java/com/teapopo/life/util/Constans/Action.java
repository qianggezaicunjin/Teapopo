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
    ArticleItemModel_focusMember,
    ArticleItemModel_GetMemberInfo,
    //for ArticleInfoModel
    ArticleInfoModel_GetInfo,
    ArticleInfoModel_AddComment,
    ArticleInfoModel_ReplyComment,
    //for CommentModel
    CommentModel_ReplyComment,
    CommentModel_ClickLikeComment,
    //for PublishArticleModel
    PublishArticleModel_PublishArticle,
    //for XinZiArticleModel
    XinZiArticleModel_GetTopArticle,
    //for MemberArticleModel
    MemberArticleModel_GetArticle,
    //for EventListModel
    EventListModel_GetEventList,
    EventListModel_GetTopAriticle,
    //for UserInfoModel
    UserInfoModel_GetUserInfo,
    UserInfoModel_LogOut,
    //for CommentListModel
    CommentListModel_GetCommentList,
    CommentListModel_ReplyComment,
    CommentListModel_AddComment,
    //for EventGoodsListModel
    EventGoodsListModel_GetGoodsList,
    //for AddressManageModel
    AddressManageModel_GetAddressLsit,
    //for MakeOrderModel
    GoodsSettleMentModel_MakeOrder,
    //for OrderSettleMentModel
    OrderSettleMentModel_GetOrderInfo,
    //for GoodsDetailModel
    GoodsDetailModel_GetGoodsInfo,
    GoodsDetailModel_GetCollectList,
    GoodsDetailModel_GetCommentList,
    //for CartListModel
    CartListModel_GetCartList,
    //for EditAddressModel
    EditAddressModel_GetDistrictPickerData,
    EditAddressModel_AddAdress,
    //for ImageSelectModel
    ImageSelectModel_GetImageData,
    ImageSelectModel_GetFolderList,
    ImageSelectModel_UploadImage,
    ImageSelectModel_UploadMutiImage,
    //for MemberLikesModel
    MemberLikesModel_GetMemberLikes,
    //for MemberModel
    MemberModel_focusMember
}
