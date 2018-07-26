package cn.abr.inabr.utils

/**
 * .
 * Created by Administrator on 2018/6/26/026.
 * 字符串工具类
 */
object StringUtils {

    //首字母大写
    fun capitalize(str: String): String {
        val ch = str.toCharArray()
        if (ch[0] in 'a'..'z') {
            ch[0] = (ch[0].toInt() - 32).toChar()
        }else{
            throw IllegalStateException("the first letter must be English!")
        }
        return String(ch)
    }
    //首字母小写
    fun lowerCase(str: String): String {
        val ch = str.toCharArray()
        if (ch[0] in 'A'..'Z') {
            ch[0] = (ch[0].toInt() + 32).toChar()
        }else{
            throw IllegalStateException("the first letter must be English!")
        }
        return String(ch)
    }
}