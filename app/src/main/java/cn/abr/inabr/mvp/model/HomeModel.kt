package cn.abr.inabr.mvp.model

import cn.abr.inabr.base.BaseEntity
import cn.abr.inabr.base.BaseModel
import cn.abr.inabr.base.ResultCallback
import cn.abr.inabr.common.Api
import cn.abr.inabr.entity.HomeTopBarEntity
import javax.inject.Inject

/**
 * .
 * Created by Administrator on 2018/5/28/028.
 */
class HomeModel @Inject constructor() : BaseModel() {

    fun getHomeTopBar(callback: ResultCallback<BaseEntity<List<HomeTopBarEntity>>>) {
        getResponse(Api.GET_HOME_PAGE_NAVIGATION_BAR_URL,callback)
    }
}