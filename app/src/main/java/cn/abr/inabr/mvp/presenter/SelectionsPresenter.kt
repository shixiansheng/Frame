package cn.abr.inabr.mvp.presenter

import cn.abr.inabr.base.BaseEntity
import cn.abr.inabr.base.BasePresenter
import cn.abr.inabr.base.ResultCallback
import cn.abr.inabr.entity.BannerEntity
import cn.abr.inabr.entity.SelectionsListEntity
import cn.abr.inabr.mvp.model.SelectionsModel
import cn.abr.inabr.mvp.view.SelectionsView
import javax.inject.Inject

/**
 * .
 * Created by Administrator on 2018/4/24/024.
 */
class SelectionsPresenter @Inject
constructor(override var mView: SelectionsView?) : BasePresenter<SelectionsView>{

    @Inject
    lateinit var selectionsModel: SelectionsModel


    fun getSelectionsBanner(mParam1: String?) {
        selectionsModel.getSelectionsBanner(mParam1,object : ResultCallback<BaseEntity<List<BannerEntity>>>() {
            override fun onResponse(response: BaseEntity<List<BannerEntity>>, json: String) {
                mView!!.showBanner(response)
            }
        })
    }

    fun getSelectionsList(mParam1: String?, page: String) {
        selectionsModel.getSelectionsList(mParam1,page,object : ResultCallback<BaseEntity<List<SelectionsListEntity>>>() {
            override fun onResponse(response: BaseEntity<List<SelectionsListEntity>>, json: String) {
                mView!!.showList(response)
            }
        })
    }
}
