package cn.abr.inabr.common

import cn.abr.inabr.BuildConfig


/**
 * @author 时志邦
 * @Description: ${TODO}(用一句话描述该文件做什么)
 */

object Api {

    val GET_RECOMMEND_CAROUSEL_URL =BuildConfig.QCV_URL + "home/gettop/"//顶部轮播图

    val GET_RECOMMEND_LIST_URL = BuildConfig.QCV_URL +"home/getlist/{item}/{page}/10"//页面数据  https://api.qichangv.com//home/getlist/荐读/0/10

    val GET_HOME_PAGE_NAVIGATION_BAR_URL =BuildConfig.QCV_URL + "home/NavigationBar"//顶部导航栏

    val SAVE_HOME_PAGE_NAVIGATION_BAR_URL = BuildConfig.QCV_URL +"home/myNBar"//保存导航栏修改后状态

    val GET_NEWS_DETAILS_URL = BuildConfig.QCV_URL +"Content/getNews/{id}"//文章详情

    val GET_VIDEO_COLUMNS_URL = BuildConfig.QCV_URL +"column/3" //视频栏目

    val GET_VIDEO_COLUMNSINFO_URL =BuildConfig.QCV_URL + "columninfo/" //视频栏目详情

    val GET_VIDEO_SHARE_URL = "http://www.infoacq.com/magazine/shareV.html?newsID=" //视频分享页链接

    val GET_ARTICLE_SHARE_URL = "http://www.infoacq.com/magazine/news/shareArt/index.html?newsID=" //文章分享页链接

    val GET_VIDEO_DETAILS_URL =BuildConfig.QCV_URL + "Content/getVideo/" //视频详情页面

    val GET_SPECIAL_COLUMNS_URL = BuildConfig.QCV_URL +"Special/getlist/{page}/{count}" //专题栏目

    val GET_SPECIAL_CONTENT_URL = BuildConfig.QCV_URL +"Special/content/{special_id}" //专题栏目详情

    val GET_ABOUT_RECOMMEND_URL = BuildConfig.QCV_URL +"Content/getAbout" //相关推荐

    val GET_ARTICLE_COMMENT_URL = BuildConfig.QCV_URL + "Comment" //文章评论

    val GET_SECOND_COMMENT_URL = BuildConfig.QCV_URL + "Comment/content" //二级评论

    val SAVE_ARTICLE_COMMENT_URL = BuildConfig.QCV_URL + "Comment/savemycomm" //发表评论

    val SAVE_COMMENT_LIKE_URL = BuildConfig.QCV_URL + "Comment/islike" //对评论点赞

    val SMS_CODE_URL = BuildConfig.SMS_URL + "sms/code" //短信验证码

    val PWD_LOGIN_URL = BuildConfig.USER_URL + "v1/user/login/mobile" //密码登录

    val PHONE_REGISTER_URL = BuildConfig.USER_URL + "/v1/user/register/mobile" //手机号注册

    val THIRD_LOGIN_URL = BuildConfig.USER_URL + "v1/user/third" //三方登录

    val SMS_LOGIN_URL = BuildConfig.USER_URL + "v1/user/mobilecode" //短信登录

    val PWD_BACK_URL = BuildConfig.USER_URL + "v1/user/back" //找回密码
}
