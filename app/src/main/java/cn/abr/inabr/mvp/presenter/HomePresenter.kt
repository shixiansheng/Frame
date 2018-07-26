package cn.abr.inabr.mvp.presenter


import cn.abr.inabr.mvp.view.HomeTopBarView
import cn.abr.inabr.base.BaseEntity
import cn.abr.inabr.base.BasePresenter
import cn.abr.inabr.base.ResultCallback
import cn.abr.inabr.entity.HomeTopBarEntity
import cn.abr.inabr.mvp.model.HomeModel
import io.reactivex.Observable
import javax.inject.Inject

/**
 * @author 时志邦
 * @Description: ${TODO}(用一句话描述该文件做什么)
 */

class HomePresenter @Inject
constructor(mView: HomeTopBarView) : BasePresenter<HomeTopBarView>(mView) {

    @Inject
     lateinit var homeModel: HomeModel


    fun getHomeTopBar() {
        homeModel.getHomeTopBar( object : ResultCallback<BaseEntity<List<HomeTopBarEntity>>>() {
            override fun onResponse(response: BaseEntity<List<HomeTopBarEntity>>, json: String) {
                mView!!.showHomeTopBar(response)
            }

            override fun onFailure(t: Throwable) {
                mView!!.onFailure(t)
            }
        })
    }
}
