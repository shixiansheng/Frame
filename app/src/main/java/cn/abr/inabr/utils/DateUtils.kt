package cn.abr.inabr.utils

import android.text.TextUtils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date

/**
 * Created by Administrator on 2017/8/28.
 * @author Msquirrel
 */

object DateUtils {

    private var sf: SimpleDateFormat? = null

    /**
     * 获取系统时间 格式为："yyyy（年）MM（月）dd HH（时）:mm（分）:ss（秒）
     *
     * @return
     */
    fun CurrentDate(): String {
        val d = Date(System.currentTimeMillis())
        sf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return sf!!.format(d)
    }

    /**
     * 时间戳转换成字符窜
     * @param time
     * @return
     */
    fun DateToString(time: Long): String {
        val d = Date(time)
        sf = SimpleDateFormat("yyyy年MM月dd日")
        return sf!!.format(d)
    }

    /**
     * 将字符串转为时间戳
     * @param time
     * @return
     */
    fun StringToDate(time: String): Long {
        sf = SimpleDateFormat("yyyy年MM月dd日")
        var date = Date()
        try {
            date = sf!!.parse(time)
        } catch (e: ParseException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

        return date.time
    }

    /**
     * 将时间戳转换为秒
     * @param str
     * @return
     */
    fun dateTimeMs(str: String): String {
        val simpleDateFormat = SimpleDateFormat("yyyyMMddHHmmss")
        var msTime: Long = -1
        try {
            msTime = simpleDateFormat.parse(str).time
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return (msTime / 1000).toString()
    }

    /**
     * 距离当前时间进行汉字转化
     * @param stringDate
     * @return
     */
    fun formatDate(stringDate: String?): String? {
        try {
            if (TextUtils.isEmpty(stringDate)) return null
            val dateformat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
            val date = dateformat.parse(stringDate)
            val curDate = Date(System.currentTimeMillis())
            val time = curDate.time - date.time

            val seconds = time / 1000
            val minutes = seconds / 60
            val hours = minutes / 60
            if (seconds <= 0)
                return "刚刚"
            else if (minutes <= 0)
                return "刚刚"
            else if (hours <= 0)
                return minutes.toString() + "分钟前"
            else if (hours in 1..23)
                return hours.toString() + "小时前"
            else {
                val format = SimpleDateFormat("yyyy-MM-dd")
                return format.format(date)
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return ""
    }
}