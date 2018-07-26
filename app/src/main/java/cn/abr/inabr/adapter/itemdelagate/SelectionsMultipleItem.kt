package cn.abr.inabr.adapter.itemdelagate

import android.content.Intent
import android.text.TextUtils
import cn.abr.inabr.R
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
class SelectionsMultipleItem : ItemViewDelegate<SelectionsListEntity> {

    private val ITEM_DEFAULT_MULTIPLE = 3

    override fun getItemViewLayoutId(): Int = R.layout.home_list_default_multiple_item

    override fun convert(holder: ViewHolder, t: SelectionsListEntity, position: Int) {
        if (t.imageUrl!!.isNotEmpty())
            t.imageUrl!!.size.also {
                if (it > 0) GlideUtils.setThePicture(holder.itemView.context, t.imageUrl!![0], holder.getView(R.id.home_list_default_multiple_left_img))
                    if (it > 1)GlideUtils.setThePicture(holder.itemView.context, t.imageUrl!![1], holder.getView(R.id.home_list_default_multiple_center_img))
                        if (it > 2) GlideUtils.setThePicture(holder.itemView.context, t.imageUrl!![2], holder.getView(R.id.home_list_default_multiple_right_img))
            }
        holder.apply {
            if (TextUtils.isEmpty(t.leftLabel)) {
                setText(R.id.home_list_default_multiple_browse, t.centerLabel)
                setText(R.id.home_list_default_multiple_comment, "")
            } else {
                setText(R.id.home_list_default_multiple_browse, t.leftLabel)
                setText(R.id.home_list_default_multiple_comment, t.centerLabel)
            }
            setText(R.id.home_list_default_multiple_time, t.rightLabel)
            setText(R.id.home_list_default_multiple_title, t.title)
        }.itemView.setOnClickListener {
            it.context.startActivity(Intent(it.context, ArticleActivity::class.java).apply {
                putExtra("NewsId", t.newsID)
            })
        }

    }

    override fun isForViewType(item: SelectionsListEntity?, position: Int): Boolean = item!!.type == ITEM_DEFAULT_MULTIPLE
}