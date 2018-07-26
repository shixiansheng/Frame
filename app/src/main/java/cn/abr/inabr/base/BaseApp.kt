package cn.abr.inabr.base

import android.app.Application

/**
 * .
 * Created by Administrator on 2018/4/12/012.
 */

 abstract class BaseApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initLocalOptionsInMainThread()
        Thread{
            initOptionsInWorkThread()
        }.start()
    }

      private fun initOptionsInWorkThread(){
          initSDK()
          initLocalOptions()
     }

     abstract fun initLocalOptions()
     abstract fun initSDK()

    open fun initLocalOptionsInMainThread(){

    }

}
