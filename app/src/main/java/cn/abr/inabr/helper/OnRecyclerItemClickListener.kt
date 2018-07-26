package cn.abr.inabr.helper

import android.support.v4.view.GestureDetectorCompat
import android.support.v7.widget.RecyclerView
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

/**
 * Created by Administrator on 2018/1/26/026.
 */

open class OnRecyclerItemClickListener<T>(private val recyclerView: RecyclerView) : RecyclerView.OnItemTouchListener {
    private val mGestureDetector: GestureDetectorCompat

    init {
        mGestureDetector = GestureDetectorCompat(recyclerView.context, ItemTouchHelperGestureListener())
    }

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        mGestureDetector.onTouchEvent(e)
        return false
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
        mGestureDetector.onTouchEvent(e)
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

    }

    private inner class ItemTouchHelperGestureListener : GestureDetector.SimpleOnGestureListener() {

        override fun onSingleTapUp(e: MotionEvent): Boolean {
            val child = recyclerView.findChildViewUnder(e.x, e.y)
            if (child != null) {
                val vh = recyclerView.getChildViewHolder(child) as T
                onItemClick(vh)
            }
            return true
        }

        override fun onLongPress(e: MotionEvent) {
            val child = recyclerView.findChildViewUnder(e.x, e.y)
            if (child != null) {
                val vh = recyclerView.getChildViewHolder(child) as T
                onLongClick(vh)
            }
        }
    }

    open fun onLongClick(vh:T) {}
    open fun onItemClick(vh: T) {}
}
