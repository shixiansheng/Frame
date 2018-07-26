package cn.abr.inabr.adapter.itemdelagate

import android.widget.ImageView
import cn.abr.inabr.R
import cn.abr.inabr.entity.VideoColumnEntity
import cn.abr.inabr.utils.GlideUtils
import com.zhy.adapter.recyclerview.base.ItemViewDelegate
import com.zhy.adapter.recyclerview.base.ViewHolder

/**
 * .
 * Created by Administrator on 2018/7/9/009.
 */
class VideoColumnItem :ItemViewDelegate<VideoColumnEntity> {
     var onItemClickListener:OnItemClickListener?=null
    override fun getItemViewLayoutId(): Int = R.layout.home_video_column_item

    override fun convert(holder: ViewHolder, t: VideoColumnEntity, position: Int) {
            GlideUtils.setNoResponseImages(holder.itemView.context,t.picUrl,holder.getView<ImageView>(R.id.video_column_img))
            holder.setText(R.id.video_column_text,t.name)
        holder.setOnClickListener(R.id.video_column_item_cardView) {
            onItemClickListener?.onItemClickListener(t)
        }
    }

    override fun isForViewType(item: VideoColumnEntity?, position: Int): Boolean =true

    interface OnItemClickListener
    {
        fun onItemClickListener(t: VideoColumnEntity)
    }
}