package cn.abr.inabr.mvp.model

import cn.abr.inabr.base.BaseEntity
import cn.abr.inabr.base.BaseModel
import cn.abr.inabr.base.ResultCallback
import cn.abr.inabr.common.Api
import cn.abr.inabr.common.Constants
import cn.abr.inabr.entity.ArticleContentEntity
import cn.abr.inabr.entity.EmptyEntity
import cn.abr.inabr.entity.SelectionsListEntity
import cn.abr.inabr.utils.GsonUtils
import okhttp3.MediaType
import okhttp3.RequestBody
import javax.inject.Inject

/**
 * .
 * Created by Administrator on 2018/5/28/028.
 */
class ColumnSortModel @Inject constructor() : BaseModel() {


    fun saveHomeTopBar(myBar: String, resultCallBack: ResultCallback<BaseEntity<List<EmptyEntity>>>) {
        val mybar = myBar()
        mybar.myBar = myBar
        getResponse(Api.SAVE_HOME_PAGE_NAVIGATION_BAR_URL,
                RequestBody.create(MediaType.parse("application/json; charset=utf-8"), GsonUtils.Instance.toJson(mybar)),
                resultCallBack)
    }

    internal inner class myBar {
        /**
         * myBar : 荐读, 新车, 专题
         */

        var myBar: String? = null
    }
}