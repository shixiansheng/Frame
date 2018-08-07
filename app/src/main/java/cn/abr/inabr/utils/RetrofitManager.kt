package cn.abr.inabr.utils

import android.os.Build
import android.support.v4.util.SimpleArrayMap
import android.text.TextUtils
import cn.abr.inabr.BuildConfig
import cn.abr.inabr.base.ResultCallback
import cn.abr.inabr.common.APIService
import cn.abr.inabr.common.App
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit

/**
 * .
 * Created by Administrator on 2018/4/8/008.
 */
class RetrofitManager {
    private val retrofit: Retrofit

    init {
        val okHttpClient = OkHttpClient.Builder().connectTimeout(8000, TimeUnit.SECONDS)
                .readTimeout(8000, TimeUnit.SECONDS)
                .writeTimeout(8000, TimeUnit.SECONDS)

                // 给 OKHttp 统一添加识别请求头
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                            .addHeader("user_num", "")
                            .addHeader("phone_model", Build.BRAND + " " + Build.MODEL)
                            .addHeader("phone_os", Build.VERSION.RELEASE)
                            .addHeader("app_version", ApkUtils.getVersionName(App.Instance().applicationContext))
                            .addHeader("app_channel", "_360")
                            .addHeader("app_id", "qichang")
                            .addHeader("device_id", "99000913326911")
                            .addHeader("Device", "99000913326911")
                            .addHeader("device_app_id", "")
                            .build()

                    chain.proceed(request)
                }
                .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))


        retrofit = Retrofit.Builder().baseUrl(BuildConfig.QCV_URL).client(okHttpClient.build()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
    }


    companion object {
        val Instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            RetrofitManager()
        }
    }


    private fun execute(path: String, map: SimpleArrayMap<String, String>): Observable<ResponseBody>? {
        val apiService = retrofit.create(APIService::class.java)
        val request: Observable<ResponseBody>
        if (!map.isEmpty) {
            request = apiService.request(path, map)
        } else {
            request = apiService.request(path)
        }
        return request.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    fun request(path: String, map: SimpleArrayMap<String, String>, callback: ResultCallback<*>): Unit {
        callback.onStart()
        val execute = execute(path, map)
        execute?.subscribe(
                {
                    if (it != null) {
                        val json = it.string()
                        if (!TextUtils.isEmpty(json))
                            callback.onResponse(GsonUtils.Instance.fromJson(json, callback.mType)!!, json)
                        else callback.onFailure(Throwable("服务器异常!!!"))
                    }
                }, callback::onFailure,callback::onComplete
        )
    }

    fun upload(path: String, body: RequestBody, callback: ResultCallback<*>) {
        val apiService = retrofit.create(APIService::class.java)
        apiService.upLoad(path, body).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
            if (it != null) {
                val json = it.string()
                if (!TextUtils.isEmpty(json))
                    callback.onResponse(GsonUtils.Instance.fromJson(json, callback.mType)!!, json)
                else callback.onFailure(Throwable("服务器异常!!!"))
            }
        }, callback::onFailure)
    }

    fun request(path: String, map: SimpleArrayMap<String, String>, observer: Consumer<ResponseBody>) {
        execute(path, map)?.subscribe(observer)
    }

    fun  request(path: String, map: SimpleArrayMap<String, String>, observer: Observer<ResponseBody>) {
        execute(path, map)?.subscribe(observer)
    }
}