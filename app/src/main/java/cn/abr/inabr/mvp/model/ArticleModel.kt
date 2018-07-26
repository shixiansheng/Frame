package cn.abr.inabr.mvp.model

import cn.abr.inabr.base.BaseEntity
import cn.abr.inabr.base.BaseModel
import cn.abr.inabr.base.ResultCallback
import cn.abr.inabr.common.Api
import cn.abr.inabr.common.Constants
import cn.abr.inabr.entity.ArticleContentEntity
import cn.abr.inabr.entity.CommentEntity
import cn.abr.inabr.entity.EmptyEntity
import cn.abr.inabr.entity.SelectionsListEntity
import okhttp3.MediaType
import okhttp3.RequestBody
import javax.inject.Inject

/**
 * .
 * Created by Administrator on 2018/5/28/028.
 */
class ArticleModel @Inject constructor() : BaseModel() {


    fun getArticleContent(newsID: String, callback: ResultCallback<BaseEntity<List<ArticleContentEntity>>>) {
        getResponse(Api.GET_NEWS_DETAILS_URL.replace("{id}", newsID), callback)
    }

    fun getSelectionsList(tags: String, contentID: String, callback: ResultCallback<BaseEntity<List<SelectionsListEntity>>>) {
        val json = String.format(Constants.About_Raw, tags, contentID)
        getResponse(Api.GET_ABOUT_RECOMMEND_URL, json, callback)
    }

    fun getCommentList(newsId: String, page: String, callback: ResultCallback<BaseEntity<List<CommentEntity>>>) {

        //TODO("需要获取保存的用户id")
        val json = String.format(Constants.ARTICLE_COMMENT, newsId, page + "", "10", "")
        getResponse(Api.GET_ARTICLE_COMMENT_URL, json, callback)
    }

    fun sendComment(content: String, userId: String, parentId: String, target_user_id: String, callback: ResultCallback<BaseEntity<List<EmptyEntity>>>) {
        val json = String.format(Constants.SEND_COMMENT, content, userId, parentId, target_user_id, "文章")
        getResponse(Api.SAVE_ARTICLE_COMMENT_URL, json, callback)
    }
}