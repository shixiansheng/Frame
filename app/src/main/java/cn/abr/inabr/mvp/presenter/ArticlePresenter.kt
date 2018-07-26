package cn.abr.inabr.mvp.presenter

import cn.abr.inabr.base.BaseEntity
import cn.abr.inabr.base.BasePresenter
import cn.abr.inabr.base.ResultCallback
import cn.abr.inabr.entity.ArticleContentEntity
import cn.abr.inabr.entity.CommentEntity
import cn.abr.inabr.entity.EmptyEntity
import cn.abr.inabr.entity.SelectionsListEntity
import cn.abr.inabr.mvp.model.ArticleModel
import cn.abr.inabr.mvp.view.ArticleContentView
import javax.inject.Inject

/**
 * .
 * Created by Administrator on 2018/4/24/024.
 */
class ArticlePresenter @Inject
constructor(mView: ArticleContentView) : BasePresenter<ArticleContentView>(mView) {


    @Inject
    lateinit var articleModel: ArticleModel


    fun getArticleContent(newsID: String) {

        articleModel.getArticleContent(newsID, object : ResultCallback<BaseEntity<List<ArticleContentEntity>>>() {
            override fun onResponse(response: BaseEntity<List<ArticleContentEntity>>, json: String) {
                mView!!.showArticle(response)
            }
        })
    }

    fun getSelectionsList(tags: String, contentID: String) {
        articleModel.getSelectionsList(tags, contentID, object : ResultCallback<BaseEntity<List<SelectionsListEntity>>>() {
            override fun onResponse(response: BaseEntity<List<SelectionsListEntity>>, json: String) {
                mView!!.showAbout(response)
            }
        })
    }

    fun getCommentList(newsId: String, page: String) {
        articleModel.getCommentList(newsId, page, object : ResultCallback<BaseEntity<List<CommentEntity>>>() {
            override fun onResponse(response: BaseEntity<List<CommentEntity>>, json: String) {
                mView!!.showComment(response)
            }
        })
    }

    fun sendComment(content: String, userId: String, parentId: String, target_user_id: String) {
        articleModel.sendComment(content,userId,parentId,target_user_id,object :ResultCallback<BaseEntity<List<EmptyEntity>>>(){
            override fun onResponse(response: BaseEntity<List<EmptyEntity>>, json: String) {
                mView!!.sendComment(response)
            }

        })
    }

    fun like(newsId: String, page: String) {

    }

    fun collect(newsId: String, page: String) {

    }

}
