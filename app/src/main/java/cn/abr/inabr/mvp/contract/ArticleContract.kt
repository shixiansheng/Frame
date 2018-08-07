package cn.abr.inabr.mvp.contract

import cn.abr.inabr.base.*
import cn.abr.inabr.entity.ArticleContentEntity
import cn.abr.inabr.entity.CommentEntity
import cn.abr.inabr.entity.EmptyEntity
import cn.abr.inabr.entity.SelectionsListEntity

/**
 * .
 * Created by Administrator on 2018/8/7/007.
 */
interface ArticleContract {


    abstract class Model : BaseModel() {
        abstract fun getSelectionsList(tags: String, contentID: String, callback: ResultCallback<BaseEntity<List<SelectionsListEntity>>>)
        abstract fun getCommentList(newsId: String, page: String, callback: ResultCallback<BaseEntity<List<CommentEntity>>>)
        abstract fun sendComment(content: String, userId: String, parentId: String, target_user_id: String, callback: ResultCallback<BaseEntity<List<EmptyEntity>>>)
    }

    interface View : BaseView {
        fun showArticle(response: BaseEntity<List<ArticleContentEntity>>)
        fun showAbout(response: BaseEntity<List<SelectionsListEntity>>)
        fun showComment(response: BaseEntity<List<CommentEntity>>)
        fun sendComment(response: BaseEntity<List<EmptyEntity>>)
    }

    interface Presenter : BasePresenter<View> {
        fun getArticleContent(newsID: String)
        fun getSelectionsList(tags: String, contentID: String)
        fun getCommentList(newsId: String, page: String)
        fun sendComment(content: String, userId: String, parentId: String, target_user_id: String)
        fun like(newsId: String, page: String)
        fun collect(newsId: String, page: String)
    }
}
