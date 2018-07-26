package cn.abr.inabr.utils

import android.content.Context
import android.text.TextPaint



/**
 * .
 * Created by Administrator on 2018/5/3/003.
 */

object SizeUtil {

    fun px2dip(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    //获取TextView中文字的宽度
    fun getTextWidth(context: Context, text: String, textSize: Int): Float {
        val paint = TextPaint()
        val scaledDensity = context.resources.displayMetrics.scaledDensity
        paint.textSize = scaledDensity * textSize
        return paint.measureText(text)
    }
}
