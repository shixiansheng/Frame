package cn.abr.inabr.adapter.itemdelagate

import cn.abr.inabr.R
import com.zhy.adapter.recyclerview.base.ItemViewDelegate
import com.zhy.adapter.recyclerview.base.ViewHolder

/**
 * .
 * Created by Administrator on 2018/6/28/028.
 */
class MyCommentItem:ItemViewDelegate<String> {
    override fun getItemViewLayoutId(): Int = R.layout.fragment_item_my_comment

    override fun convert(holder: ViewHolder, t: String?, position: Int) {
    }

    override fun isForViewType(item: String?, position: Int): Boolean=true
}