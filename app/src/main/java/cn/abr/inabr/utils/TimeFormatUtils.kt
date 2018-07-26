package cn.abr.inabr.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * .
 * Created by Administrator on 2018/5/2/002.
 */
class TimeFormatUtils {

    //时间分段
    fun slicing(label: String): String {
        try {
            if (label == null) return ""
            val dateformat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
            val date = dateformat.parse(label)
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