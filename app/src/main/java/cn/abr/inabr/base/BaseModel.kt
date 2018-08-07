package cn.abr.inabr.base


import android.support.v4.util.SimpleArrayMap
import cn.abr.inabr.utils.RetrofitManager
import okhttp3.MediaType
import okhttp3.RequestBody
import java.util.HashMap

import javax.inject.Inject


/**
 * @author 时志邦
 * @Description: ${TODO}(用一句话描述该文件做什么)
 */

open class BaseModel {

    private val map = SimpleArrayMap<String, String>()


    fun setKeyMap(key: String, value: String): BaseModel {
        map.put(key,value);
        return this
    }

    fun clearMap(): BaseModel {
        if (map.size() > 0)
            map.clear()
        return this
    }

    fun getResponse(path: String, resultCallBack: ResultCallback<*>) {
        RetrofitManager.Instance.request(path, map, resultCallBack)
    }
    fun getResponse(path: String, requestBody: RequestBody, resultCallBack: ResultCallback<*>) {
        RetrofitManager.Instance.upload(path, requestBody, resultCallBack)
    }

    fun getResponse(path: String, json: String, resultCallBack: ResultCallback<*>) {
        RetrofitManager.Instance.upload(path, RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                json), resultCallBack)
    }
}
