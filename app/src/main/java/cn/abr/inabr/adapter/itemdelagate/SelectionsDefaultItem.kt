package cn.abr.inabr.adapter.itemdelagate

import android.content.Intent
import android.graphics.Color
import android.text.TextUtils
import android.widget.TextView
import cn.abr.inabr.R
import cn.abr.inabr.R.id.*
import cn.abr.inabr.activity.ArticleActivity
import cn.abr.inabr.entity.SelectionsListEntity
import cn.abr.inabr.utils.GlideUtils
import com.zhy.adapter.recyclerview.base.ItemViewDelegate
import com.zhy.adapter.recyclerview.base.ViewHolder
import com.zhy.autolayout.utils.AutoUtils

/**
 * .
 * Created by Administrator on 2018/4/27/027.
 */
class SelectionsDefaultItem :ItemViewDelegate<SelectionsListEntity> {

    private val ITEM_DEFAULT = 1
    private val ITEM_AD = 7

    override fun getItemViewLayoutId(): Int = R.layout.home_list_default_item

    override fun convert(holder: ViewHolder, t: SelectionsListEntity, position: Int) {
            holder.getView<TextView>(R.id.home_list_default_browse).setTextColor( if(t.type==ITEM_AD)Color.parseColor("#3BA5EC") else Color.parseColor("#868686"))

        if ( t.imageUrl!!.isNotEmpty())
        GlideUtils.setThePicture(holder.itemView.context,t.imageUrl!![0],holder.getView(home_list_default_img))
        holder.apply {
            if (TextUtils.isEmpty(t.leftLabel))
            {
                setText(home_list_default_browse,t.centerLabel)
                setText(home_list_default_comment,"")
            }else{
                setText(home_list_default_browse, t.leftLabel)
                setText(home_list_default_comment, t.centerLabel)
            }
            setText(home_list_default_title,t.title)
            setText(home_list_default_time, t.rightLabel)
        }.itemView.setOnClickListener {
            if (t.type==ITEM_DEFAULT) {
                it.context.startActivity(Intent(it.context, ArticleActivity::class.java).apply {
                    putExtra("NewsId", t.newsID)
                })
            }
        }

    }


    override fun isForViewType(item: SelectionsListEntity?, position: Int): Boolean =item!!.type==ITEM_DEFAULT||item!!.type==ITEM_AD
}