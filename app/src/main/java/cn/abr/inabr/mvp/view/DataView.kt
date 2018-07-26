package cn.abr.inabr.mvp.view

import cn.abr.inabr.base.BaseEntity
import cn.abr.inabr.entity.*

/**
 * .
 * Created by Administrator on 2018/4/23/023.
 */
interface DataView<T> {
    fun showData(t:T)
}

interface FailureView{
    fun onFailure(t: Throwable){

    }
}

interface HomeTopBarView :FailureView{
    fun showHomeTopBar(t: BaseEntity<List<HomeTopBarEntity>>)
}


interface SelectionsView {
    fun showBanner(response: BaseEntity<List<BannerEntity>>)
    fun showList(response: BaseEntity<List<SelectionsListEntity>>)
}

interface ArticleContentView {
    fun showArticle(response: BaseEntity<List<ArticleContentEntity>>)
    fun showAbout(response: BaseEntity<List<SelectionsListEntity>>)
    fun showComment(response: BaseEntity<List<CommentEntity>>)
    fun sendComment(response: BaseEntity<List<EmptyEntity>>)
}