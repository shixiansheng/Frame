package cn.abr.inabr.mvp.presenter

import cn.abr.inabr.base.BaseEntity
import cn.abr.inabr.base.BasePresenter
import cn.abr.inabr.base.ResultCallback
import cn.abr.inabr.common.Api
import cn.abr.inabr.entity.ArticleContentEntity
import cn.abr.inabr.entity.CommentEntity
import cn.abr.inabr.entity.EmptyEntity
import cn.abr.inabr.entity.SelectionsListEntity
import cn.abr.inabr.mvp.contract.ArticleContract
import cn.abr.inabr.mvp.model.ArticleModel
import cn.abr.inabr.mvp.view.ArticleContentView
import javax.inject.Inject

/**
 * .
 * Created by Administrator on 2018/4/24/024.
 */
class ArticlePresenter
constructor(override var mView: ArticleContract.View?, private var articleModel: ArticleModel) : ArticleContract.Presenter {


    override fun getArticleContent(newsID: String) {

        articleModel.getResponse(Api.GET_NEWS_DETAILS_URL + newsID, object : ResultCallback<BaseEntity<List<ArticleContentEntity>>>() {

            override fun onComplete() {
                super.onComplete()
            }

            override fun onStart() {
                super.onStart()
            }

            override fun onResponse(response: BaseEntity<List<ArticleContentEntity>>, json: String) {
                mView!!.showArticle(response)
            }

            override fun onFailure(t: Throwable) {
                super.onFailure(t)
            }
        })

    }

    override fun getSelectionsList(tags: String, contentID: String) {
        articleModel.getSelectionsList(tags, contentID, object : ResultCallback<BaseEntity<List<SelectionsListEntity>>>() {
            override fun onResponse(response: BaseEntity<List<SelectionsListEntity>>, json: String) {
                mView!!.showAbout(response)
            }
        })
    }

    override fun getCommentList(newsId: String, page: String) {
        articleModel.getCommentList(newsId, page, object : ResultCallback<BaseEntity<List<CommentEntity>>>() {
            override fun onResponse(response: BaseEntity<List<CommentEntity>>, json: String) {
                mView!!.showComment(response)
            }
        })
    }

    override fun sendComment(content: String, userId: String, parentId: String, target_user_id: String) {
        articleModel.sendComment(content, userId, parentId, target_user_id, object : ResultCallback<BaseEntity<List<EmptyEntity>>>() {
            override fun onResponse(response: BaseEntity<List<EmptyEntity>>, json: String) {
                mView!!.sendComment(response)
            }

        })
    }

    override fun like(newsId: String, page: String) {

    }

    override fun collect(newsId: String, page: String) {

    }

}
