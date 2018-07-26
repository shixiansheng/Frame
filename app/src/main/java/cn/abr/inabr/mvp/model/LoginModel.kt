package cn.abr.inabr.mvp.model

import cn.abr.inabr.base.BaseEntity
import cn.abr.inabr.base.BaseModel
import cn.abr.inabr.base.ResultCallback
import cn.abr.inabr.common.Api
import cn.abr.inabr.common.Constants
import cn.abr.inabr.entity.ArticleContentEntity
import javax.inject.Inject

/**
 * .
 * Created by Administrator on 2018/5/28/028.
 */
class LoginModel @Inject constructor() : BaseModel() {


    fun pwdLogin(mobile: String,password: String, callback: ResultCallback<BaseEntity<List<ArticleContentEntity>>>) {
        val json=String.format(Constants.PWD_LOGIN,mobile,password)
        getResponse(Api.PWD_LOGIN_URL,json, callback)
    }


}