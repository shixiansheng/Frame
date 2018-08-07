package cn.abr.inabr.mvp.presenter

import cn.abr.inabr.base.BaseEntity
import cn.abr.inabr.base.BasePresenter
import cn.abr.inabr.base.ResultCallback
import cn.abr.inabr.entity.SelectionsListEntity
import cn.abr.inabr.entity.VideoColumnEntity
import cn.abr.inabr.mvp.model.SelectionsModel
import cn.abr.inabr.mvp.model.VideoModel
import cn.abr.inabr.mvp.view.VideoView
import javax.inject.Inject

class VideoPresenter @Inject constructor(override var mView: VideoView?) : BasePresenter<VideoView> {
    @Inject
    lateinit var model: SelectionsModel
    @Inject
    lateinit var videoModel: VideoModel

    fun getSelectionsList(mParam1: String?, page: String) {
        model.getSelectionsList(mParam1, page, object : ResultCallback<BaseEntity<List<SelectionsListEntity>>>() {
            override fun onResponse(response: BaseEntity<List<SelectionsListEntity>>, json: String) {
                if (response.Code == "200")
                    mView!!.showList(response)
                else onFailure(Throwable("请求失败!"))
            }

            override fun onFailure(t: Throwable) {
                mView!!.onFailure(t)
            }
        })
    }

    fun getVideoColumn(): Unit {
        videoModel.getVideoColumn(object :ResultCallback<BaseEntity<List<VideoColumnEntity>>>() {
            override fun onResponse(response: BaseEntity<List<VideoColumnEntity>>, json: String) {
                mView!!.showColumn(response)
            }
        })
    }
}
