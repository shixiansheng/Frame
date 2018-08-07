package cn.abr.inabr.dagger2.module


import cn.abr.inabr.base.BaseEntity
import cn.abr.inabr.base.BaseModule
import cn.abr.inabr.entity.EmptyEntity
import cn.abr.inabr.mvp.contract.ArticleContract
import cn.abr.inabr.mvp.model.ArticleModel
import cn.abr.inabr.mvp.presenter.ArticlePresenter
import cn.abr.inabr.mvp.view.ArticleContentView
import cn.abr.inabr.mvp.view.DataView
import cn.abr.inabr.mvp.view.HomeTopBarView
import cn.abr.inabr.mvp.view.SelectionsView

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author 时志邦
 * @Description: ${TODO}(用一句话描述该文件做什么)
 */
@Module
class ColumnSortModule
constructor(view: DataView<BaseEntity<List<EmptyEntity>>>) : BaseModule<DataView<BaseEntity<List<EmptyEntity>>>>(view) {

    @Provides
    fun provideTempLateView(): DataView<BaseEntity<List<EmptyEntity>>> {
        return view
    }
}

@Module
class HomeModule
constructor(view: HomeTopBarView) : BaseModule<HomeTopBarView>(view) {

    @Provides
    fun provideTempLateView(): HomeTopBarView {
        return view
    }
}

@Module
class SelectionsModule
constructor(view: SelectionsView) : BaseModule<SelectionsView>(view) {

    @Provides
    fun provideTempLateView(): SelectionsView {
        return view
    }
}

@Module
@Singleton
class ArticleModule
constructor(view: ArticleContract.View) : BaseModule<ArticleContract.View>(view) {

    @Provides
    fun provideTempLateView(): ArticleContract.View =view

    @Provides
    fun provideModel(): ArticleModel = ArticleModel()

    @Provides
    fun providePresenter(view:ArticleContract.View,articleModel: ArticleModel)=ArticlePresenter(view,articleModel)

}