package cn.abr.inabr.mvp.model

import cn.abr.inabr.base.BaseEntity
import cn.abr.inabr.base.BaseModel
import cn.abr.inabr.base.ResultCallback
import cn.abr.inabr.common.Api
import cn.abr.inabr.entity.BannerEntity
import cn.abr.inabr.entity.SelectionsListEntity
import javax.inject.Inject

/**
 * .
 * Created by Administrator on 2018/5/28/028.
 */
class SelectionsModel @Inject constructor() : BaseModel() {


    fun getSelectionsBanner(item: String?, callback: ResultCallback<BaseEntity<List<BannerEntity>>>) {
        getResponse(Api.GET_RECOMMEND_CAROUSEL_URL + item, callback)
    }

    fun getSelectionsList(item: String?, page: String, callback: ResultCallback<BaseEntity<List<SelectionsListEntity>>>) {
        var path: String = Api.GET_RECOMMEND_LIST_URL.replace("{item}", item!!).replace("{page}", page)
        getResponse(path, callback)
    }
}