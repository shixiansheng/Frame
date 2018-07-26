package cn.abr.inabr.adapter.itemdelagate

import android.widget.ImageView
import cn.abr.inabr.R
import cn.abr.inabr.R.id.*
import cn.abr.inabr.entity.SelectionsListEntity
import cn.abr.inabr.utils.GlideUtils
import cn.abr.inabr.utils.ToastUtil
import com.zhy.adapter.recyclerview.base.ItemViewDelegate
import com.zhy.adapter.recyclerview.base.ViewHolder

/**
 * .
 * Created by Administrator on 2018/6/27/027.
 */
class SelectionsSpecialItem: ItemViewDelegate<SelectionsListEntity> {

    private val ITEM_SPECIAL = 8

    override fun getItemViewLayoutId(): Int = R.layout.home_list_large_special_item

    override fun convert(holder: ViewHolder, t: SelectionsListEntity, position: Int) {
        if (t.imageUrl!=null&& t.imageUrl!!.isNotEmpty())
       GlideUtils.setThePicture(holder.itemView.context, t!!.imageUrl!![0],holder.getView(home_list_large_special_img))
        holder.apply {
            setText(home_list_large_special_title,t.title)
            setText(home_list_large_special_intro,t.rightLabel)
        }.itemView.setOnClickListener{
            ToastUtil.show(it.context,"专题！")
        }
    }

    override fun isForViewType(item: SelectionsListEntity?, position: Int): Boolean = item!!.type == ITEM_SPECIAL


}