package cn.abr.inabr.mvp.presenter


import cn.abr.inabr.base.BaseEntity
import cn.abr.inabr.base.BasePresenter
import cn.abr.inabr.base.ResultCallback
import cn.abr.inabr.entity.EmptyEntity
import cn.abr.inabr.mvp.model.ColumnSortModel
import cn.abr.inabr.mvp.view.DataView
import javax.inject.Inject

/**
 * @author 时志邦
 * @Description: ${TODO}(用一句话描述该文件做什么)
 */

class ColumnSortPresenter @Inject
constructor(mView: DataView<BaseEntity<List<EmptyEntity>>>) : BasePresenter<DataView<BaseEntity<List<EmptyEntity>>>>(mView) {

    @Inject
    lateinit var columnSortModel: ColumnSortModel


    fun saveHomeTopBar(myBar: String) {

        columnSortModel.saveHomeTopBar(myBar, object : ResultCallback<BaseEntity<List<EmptyEntity>>>() {
            override fun onResponse(response: BaseEntity<List<EmptyEntity>>, json: String) {
                mView!!.showData(response)
            }
        })
    }


}
