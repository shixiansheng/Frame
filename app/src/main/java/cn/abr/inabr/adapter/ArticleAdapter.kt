package cn.abr.inabr.adapter

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import cn.abr.inabr.R
import cn.abr.inabr.adapter.holder.ArticleHolder
import cn.abr.inabr.adapter.itemdelagate.*
import cn.abr.inabr.base.BaseMultiItemTypeAdapter
import cn.abr.inabr.entity.CommentEntity
import cn.abr.inabr.entity.SelectionsListEntity
import cn.abr.inabr.utils.DateUtils
import cn.abr.inabr.utils.GlideUtils

/**
 * .
 * Created by Administrator on 2018/5/24/024.
 */
class ArticleAdapter : RecyclerView.Adapter<ArticleHolder>() {
    internal var wvParent: LinearLayout? = null
    internal var datalist: MutableList<SelectionsListEntity>? = null
    internal var commentlist: MutableList<CommentEntity>? = null
    private val WEBVIEW_TYPE: Int = 0//webview
    private val RECOMMEND_TYPE: Int = 1//推荐item
    private val COMMENTTITLE_TYPE: Int = 2//评论title
    private val COMMENT_TYPE: Int = 3//评论item

    override fun getItemViewType(position: Int): Int {
        if (position == 0)
            return WEBVIEW_TYPE
        else if (listIsNull(datalist) > 0 && position == objectIsNull(wvParent))
            return RECOMMEND_TYPE
        else if (listIsNull(commentlist) > 0 && position == (objectIsNull(wvParent) + listIsEmpty(datalist)))
            return COMMENTTITLE_TYPE
        else if (listIsNull(commentlist) > 0)
            return COMMENT_TYPE
        return -1
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ArticleHolder? {
        when (viewType) {
            WEBVIEW_TYPE -> return ArticleHolder(wvParent)
            RECOMMEND_TYPE -> return RecommendHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.article_recommend_item, parent, false))
            COMMENTTITLE_TYPE -> return ArticleHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.layout_article_item_4_hintview, parent, false))
            COMMENT_TYPE -> return CommentHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.layout_videos_secondlevel_item_3, parent, false))
            else -> return ArticleHolder(View(parent!!.context))
        }

    }

    fun objectIsNull(`object`: Any?): Int {
        return if (`object` == null) 0 else 1
    }
    fun listIsEmpty(`object`: List<Any>?): Int {
        return if (`object` == null|| `object`!!.isEmpty()) 0 else 1
    }

    fun listIsNull(`object`: List<Any>?): Int {
        return if (`object` == null) 0 else `object`.size
    }

    override fun getItemCount(): Int {
        if (wvParent == null) return 0
        var allItemCount: Int = objectIsNull(wvParent) + listIsEmpty(datalist) + listIsEmpty(commentlist) + listIsNull(commentlist)
        return allItemCount
    }

    override fun onBindViewHolder(holder: ArticleHolder?, position: Int) {
        when (getItemViewType(position)) {
            RECOMMEND_TYPE -> (holder as RecommendHolder).setData()
            COMMENT_TYPE -> {
                var index: Int = position - objectIsNull(wvParent) - listIsEmpty(datalist) - listIsEmpty(commentlist)
                if (index < 0) return
                (holder as CommentHolder).setData(commentlist!![index])
            }
        }
    }

    inner class CommentHolder(itemView: View) : ArticleHolder(itemView) {

        var item_click = itemView.findViewById<View>(R.id.videos_secondlevel_item3_click) as RelativeLayout
        var item_userclick = itemView.findViewById<View>(R.id.videos_secondlevel_item3_userclick) as RelativeLayout
        var item_head = itemView.findViewById<View>(R.id.videos_secondlevel_item_3_head) as ImageView
        var item_name = itemView.findViewById<View>(R.id.videos_secondlevel_item_3_name) as TextView
        var item_like_number = itemView.findViewById<View>(R.id.videos_secondlevel_item_3_like) as TextView
        var item_content = itemView.findViewById<View>(R.id.videos_secondlevel_item_3_content) as TextView
        var item_time = itemView.findViewById<View>(R.id.videos_secondlevel_item_3_time) as TextView
        var item_reply = itemView.findViewById<View>(R.id.videos_secondlevel_item_3_reply) as TextView

        fun setData(entity: CommentEntity) {

            GlideUtils.setThePicture(itemView.context, entity.userImg, item_head)
            item_name.text = entity.userName
            item_like_number.text = entity.likeCount
            if (1 == entity.isLike) {
                val drawable = itemView.context.resources.getDrawable(R.mipmap.comment_liked)
                drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
                item_like_number.setCompoundDrawables(drawable, null, null, null)
            } else {
                val drawable = itemView.context.resources.getDrawable(R.mipmap.unlike_comment)
                drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
                item_like_number.setCompoundDrawables(drawable, null, null, null)
            }
            item_content.text = entity.content
            item_time.text = DateUtils.formatDate(entity.createTime)
            val isZero = entity.SubCount == 0
            if (isZero) {
                item_reply.text = "回复"
            } else {
                item_reply.visibility = View.VISIBLE
                item_reply.setBackgroundResource(R.drawable.videos_secondlevel_comment_count)
                item_reply.text = "查看更多评论"
            }
        }

    }

    inner class RecommendHolder(itemView: View?) : ArticleHolder(itemView) {
        val article_recommend_rlv: RecyclerView = itemView!!.findViewById<RecyclerView>(R.id.article_recommend_rlv)

        fun setData() {
            article_recommend_rlv.apply {
                layoutManager = LinearLayoutManager(itemView.context)
                adapter = BaseMultiItemTypeAdapter<SelectionsListEntity>(itemView.context, datalist).apply {
                    addItemViewDelegate(SelectionsDefaultItem())
                    addItemViewDelegate(SelectionsMultipleItem())
                    addItemViewDelegate(SelectionsDefaultVideo())
                    addItemViewDelegate(SelectionsLargeMutipleItem())
                    addItemViewDelegate(SelectionsLargeVideo())
                    addItemViewDelegate(SelectionsSpecialItem())
                }
            }
        }
    }
}