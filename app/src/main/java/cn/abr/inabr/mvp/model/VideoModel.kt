package cn.abr.inabr.mvp.model

import cn.abr.inabr.base.BaseEntity
import cn.abr.inabr.base.BaseModel
import cn.abr.inabr.base.ResultCallback
import cn.abr.inabr.common.Api
import cn.abr.inabr.common.Constants
import cn.abr.inabr.entity.*
import okhttp3.MediaType
import okhttp3.RequestBody
import javax.inject.Inject

/**
 * .
 * Created by Administrator on 2018/5/28/028.
 */
class VideoModel @Inject constructor() : BaseModel() {


    fun getVideoColumn( callback: ResultCallback<BaseEntity<List<VideoColumnEntity>>>) {
        getResponse(Api.GET_VIDEO_COLUMNS_URL, callback)
    }
}