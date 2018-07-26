package cn.abr.inabr.common

import android.support.v4.util.SimpleArrayMap
import io.reactivex.Observable
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

/**
 * @author 时志邦
 * @Description: ${TODO}(用一句话描述该文件做什么)
 */

interface APIService {

    @FormUrlEncoded
    @POST
    fun request(@Url path: String, @FieldMap map: SimpleArrayMap<String, String>): Observable<ResponseBody>

    @POST
    fun upLoad(@Url path: String, @Body requestBody: RequestBody): Observable<ResponseBody>

    @GET
    fun request(@Url path: String): Observable<ResponseBody>
}
