package cn.abr.inabr.adapter

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import cn.abr.inabr.R
import cn.abr.inabr.R.id.column_sort_item_img
import cn.abr.inabr.R.id.column_sort_item_title
import cn.abr.inabr.helper.MyItemTouchCallback
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder
import java.util.*

/**
 * .
 * Created by Administrator on 2018/4/23/023.
 */
class ColumnSortAdapter(context: Context,list:List<String>): CommonAdapter<String>(context, R.layout.columnsort_rlv_item,list), MyItemTouchCallback.ItemTouchAdapter {
    override fun onMove(fromPosition: Int, toPosition: Int) {
        if (fromPosition == 0 || toPosition == 0) {
            return
        }
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(datas, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(datas, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onSwiped(position: Int) {
        datas.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun convert(holder: ViewHolder, t: String?, position: Int) {
        if (position==0)
        {
            holder.run {
                getView<TextView>(column_sort_item_title).setTextColor(Color.parseColor("#929191"))
                getView<ImageView>(column_sort_item_img).visibility=View.GONE
            }
        }
        holder.setText(column_sort_item_title,t)
    }


}