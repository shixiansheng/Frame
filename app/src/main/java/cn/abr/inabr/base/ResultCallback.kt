package cn.abr.inabr.base

import com.google.gson.internal.`$Gson$Types`

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * @author 时志邦
 * @CreateDate 2017/11/28 20:09
 * @Description: ${TODO}(用一句话描述该文件做什么)
 */

abstract class ResultCallback<in T> {
    var mType: Type? = null

    init {
        mType = getSuperclassTypeParameter(javaClass)
    }

    abstract fun onResponse(response: T, json: String)
    open fun onFailure(t: Throwable) {

    }
    internal fun getSuperclassTypeParameter(subclass: Class<*>): Type? {
            //通过反射得到泛型参数
            //Type是 Java 编程语言中所有类型的公共高级接口。它们包括原始类型、参数化类型、数组类型、类型变量和基本类型。
            val superclass = subclass.genericSuperclass
            if (superclass is Class<*>) {
                throw RuntimeException("Missing type parameter.")
            }
            //ParameterizedType参数化类型，即泛型
            val parameterized = superclass as ParameterizedType
            //getActualTypeArguments获取参数化类型的数组，泛型可能有多个
            //将Java 中的Type实现,转化为自己内部的数据实现,得到gson解析需要的泛型
            return `$Gson$Types`.canonicalize(parameterized.actualTypeArguments[0])
        }



}
