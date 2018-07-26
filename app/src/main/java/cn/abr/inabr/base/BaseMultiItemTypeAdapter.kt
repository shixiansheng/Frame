package cn.abr.inabr.base

import android.content.Context
import android.view.View
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder
import com.zhy.autolayout.utils.AutoUtils

/**
 * .
 * Created by Administrator on 2018/5/12/012.
 */
open class BaseMultiItemTypeAdapter<T>(context: Context?, datas: MutableList<T>?) : MultiItemTypeAdapter<T>(context, datas) {
    override fun onViewHolderCreated(holder: ViewHolder?, itemView: View?) {
        AutoUtils.autoSize(holder!!.itemView)
    }}