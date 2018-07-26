package cn.abr.inabr.dagger2.module


import cn.abr.inabr.base.BaseEntity
import cn.abr.inabr.base.BaseModule
import cn.abr.inabr.entity.EmptyEntity
import cn.abr.inabr.mvp.view.ArticleContentView
import cn.abr.inabr.mvp.view.DataView
import cn.abr.inabr.mvp.view.HomeTopBarView
import cn.abr.inabr.mvp.view.SelectionsView
import javax.inject.Inject

import dagger.Module
import dagger.Provides

/**
 * @author 时志邦
 * @Description: ${TODO}(用一句话描述该文件做什么)
 */
@Module
class ColumnSortModule @Inject
constructor(view: DataView<BaseEntity<List<EmptyEntity>>>) : BaseModule<DataView<BaseEntity<List<EmptyEntity>>>>(view) {

    @Provides
    fun provideTempLateView(): DataView<BaseEntity<List<EmptyEntity>>> {
        return view
    }
}

@Module
class HomeModule @Inject
constructor(view: HomeTopBarView) : BaseModule<HomeTopBarView>(view) {

    @Provides
    fun provideTempLateView(): HomeTopBarView {
        return view
    }
}

@Module
class SelectionsModule @Inject
constructor(view: SelectionsView) : BaseModule<SelectionsView>(view) {

    @Provides
    fun provideTempLateView(): SelectionsView {
        return view
    }
}

@Module
class ArticleModule @Inject
constructor(view: ArticleContentView) : BaseModule<ArticleContentView>(view) {

    @Provides
    fun provideTempLateView(): ArticleContentView =view
}