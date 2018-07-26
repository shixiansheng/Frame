package cn.abr.inabr.adapter.itemdelagate

import android.text.TextUtils
import cn.abr.inabr.R
import cn.abr.inabr.R.id.*
import cn.abr.inabr.entity.SelectionsListEntity
import cn.abr.inabr.utils.GlideUtils
import com.zhy.adapter.recyclerview.base.ItemViewDelegate
import com.zhy.adapter.recyclerview.base.ViewHolder
import com.zhy.autolayout.utils.AutoUtils

/**
 * .
 * Created by Administrator on 2018/4/27/027.
 */
class SelectionsDefaultVideo :ItemViewDelegate<SelectionsListEntity> {

    private val ITEM_DEFAULT_VIDEO = 5

    override fun getItemViewLayoutId(): Int = R.layout.home_list_default_video_item

    override fun convert(holder: ViewHolder, t: SelectionsListEntity, position: Int) {
        if ( t.imageUrl!!.isNotEmpty())
        GlideUtils.setThePicture(holder.itemView.context,t.imageUrl!![0],holder.getView(home_list_default_video_img))
        holder.apply {
            if (TextUtils.isEmpty(t.leftLabel))
            {
                setText(home_list_default_video_label,t.centerLabel)
                setText(home_list_default_video_comment,"")
            }else{
                setText(home_list_default_video_label,t.leftLabel)
                setText(home_list_default_video_comment,t.centerLabel)
            }
            setText(home_list_default_video_time,t.rightLabel)
            setText(home_list_default_video_title,t.title)
            setText(home_list_default_video_duration,t.videoTime)
        }

    }

    override fun isForViewType(item: SelectionsListEntity?, position: Int): Boolean =item!!.type==ITEM_DEFAULT_VIDEO
}