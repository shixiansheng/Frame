package cn.abr.inabr.utils

import com.google.gson.Gson

/**
 * @author 时志邦
 * @Description: ${TODO}(用一句话描述该文件做什么)
 */
class GsonUtils private constructor(){

    //双重锁写法
    companion object {
        val Instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED){
            Gson()
        }
    }

  /*  //静态内部类写法
    companion object {
        val Instance=Holder.gson
    }

    private object Holder
    {
        val gson=Gson()
    }*/
}