package cn.abr.inabr.helper

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper

/**
 * Created by Administrator on 2018/1/26/026.
 */

class MyItemTouchCallback(private val itemTouchAdapter: ItemTouchAdapter) : ItemTouchHelper.Callback() {

    private var background: Drawable? = null
    private var bkcolor = -1

    private var onDragListener: OnDragListener? = null

    override fun isLongPressDragEnabled(): Boolean {
        return false
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }


    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        if (recyclerView.layoutManager is GridLayoutManager) {
            val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            val swipeFlags = 0
            return ItemTouchHelper.Callback.makeMovementFlags(dragFlags, swipeFlags)
        } else {
            val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
            //final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            val swipeFlags = 0
            return ItemTouchHelper.Callback.makeMovementFlags(dragFlags, swipeFlags)
        }
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        val fromPosition = viewHolder.adapterPosition//得到拖动ViewHolder的position
        val toPosition = target.adapterPosition//得到目标ViewHolder的position
        itemTouchAdapter.onMove(fromPosition, toPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        itemTouchAdapter.onSwiped(position)
    }

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            //滑动时改变Item的透明度
            val alpha = 1 - Math.abs(dX) / viewHolder.itemView.width.toFloat()
            viewHolder.itemView.alpha = alpha
            viewHolder.itemView.translationX = dX
        } else {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            if (background == null && bkcolor == -1) {
                val drawable = viewHolder!!.itemView.background
                if (drawable == null) {
                    bkcolor = 0
                } else {
                    background = drawable
                }
            }
            viewHolder!!.itemView.setBackgroundColor(Color.LTGRAY)
            //viewHolder.itemView.setBackgroundResource(R.drawable.ic_ev_column_background);
        }
        super.onSelectedChanged(viewHolder, actionState)
    }

    override fun clearView(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)

        viewHolder.itemView.alpha = 1.0f
        /*if (background != null) viewHolder.itemView.setBackgroundDrawable(background);
        if (bkcolor != -1) viewHolder.itemView.setBackgroundColor(bkcolor);*/
        viewHolder.itemView.setBackgroundColor(Color.WHITE)

        if (onDragListener != null) {
            onDragListener!!.onFinishDrag()
        }
    }

    fun setOnDragListener(onDragListener: OnDragListener): MyItemTouchCallback {
        this.onDragListener = onDragListener
        return this
    }

    interface OnDragListener {
        fun onFinishDrag()
    }

    interface ItemTouchAdapter {
        fun onMove(fromPosition: Int, toPosition: Int)

        fun onSwiped(position: Int)
    }
}
