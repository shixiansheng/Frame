package cn.abr.inabr.dagger2.component

import cn.abr.inabr.activity.ArticleActivity
import cn.abr.inabr.activity.ColumnSortActivity
import cn.abr.inabr.dagger2.module.ArticleModule
import cn.abr.inabr.dagger2.module.ColumnSortModule
import cn.abr.inabr.dagger2.module.HomeModule
import cn.abr.inabr.dagger2.module.SelectionsModule
import cn.abr.inabr.fragment.HomeFragment
import cn.abr.inabr.fragment.homefragment.SelectionsFragment
import dagger.Component
import javax.inject.Singleton

/**
 * @author 时志邦
 * @Description: ${TODO}(用一句话描述该文件做什么)
 */
@Singleton
@Component(modules = [(ColumnSortModule::class)])
interface ColumnSortComponent {
    fun inject(columnSortActivity: ColumnSortActivity)
}

@Singleton
@Component(modules = [(HomeModule::class)])
interface HomeComponent {
    fun inject(homeFragment: HomeFragment)
}

@Singleton
@Component(modules = [(SelectionsModule::class)])
interface SelectionsComponent {
    fun inject(selectionsFragment: SelectionsFragment)
}

@Singleton
@Component(modules = [(ArticleModule::class)])
interface ArticleComponent {
    fun inject(articleActivity: ArticleActivity)
}