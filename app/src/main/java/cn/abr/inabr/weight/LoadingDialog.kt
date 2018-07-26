package cn.abr.inabr.weight

import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.WindowManager
import android.widget.TextView

import cn.abr.inabr.R
import cn.abr.inabr.common.App
import com.google.gson.Gson


/**
 * .
 * Created by Administrator on 2018/5/10/010.
 */

class LoadingDialog : Dialog {

    private var msg = "卖力加载中..."
    private var gone: Boolean = false

     constructor(context: Context, themeResId: Int = -1) : super(context, themeResId) {
        init()
    }

    fun getMsg(): String {
        return msg
    }

    fun setMsg(msg: String): LoadingDialog {
        this.msg = msg
        return this
    }

    fun setMsgGone(gone: Boolean): LoadingDialog {
        this.gone = gone
        return this
    }

    private fun init() {
        setContentView(R.layout.dialog)
       // setCancelable(false)
        setCanceledOnTouchOutside(false)
        window!!.setBackgroundDrawableResource(android.R.color.transparent)

        /**
         * 设置幕布，也就是本dialog的背景层 dimAmount在0.0f和1.0f之间，0.0f完全不暗，即背景是可见的
         * ，1.0f时候，背景全部变黑暗。
         *
         * 如果要达到背景全部变暗的效果，需要设置
         * dialog.getWindow().addFlags(WindowManager.LayoutParams
         * .FLAG_DIM_BEHIND); ，否则，背景无效果。
         */
        val lp = window!!.attributes
        lp.dimAmount = 0.5f
        window.attributes = lp
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)

        val msg = findViewById<TextView>(R.id.id_tv_loadingmsg)
        msg.visibility = if (gone) View.GONE else View.VISIBLE
        msg.text = this.msg
    }

}

