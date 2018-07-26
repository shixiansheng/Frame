package cn.abr.inabr.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.KeyEvent
import android.view.View
import cn.abr.inabr.R
import cn.abr.inabr.adapter.ColumnSortAdapter
import cn.abr.inabr.base.BaseEntity
import cn.abr.inabr.base.BasePresenterActivity
import cn.abr.inabr.common.Api
import cn.abr.inabr.dagger2.component.DaggerColumnSortComponent
import cn.abr.inabr.dagger2.module.ColumnSortModule
import cn.abr.inabr.entity.EmptyEntity
import cn.abr.inabr.fragment.HomeFragment
import cn.abr.inabr.helper.MyItemTouchCallback
import cn.abr.inabr.helper.OnRecyclerItemClickListener
import cn.abr.inabr.mvp.presenter.ColumnSortPresenter
import cn.abr.inabr.mvp.view.DataView
import kotlinx.android.synthetic.main.activity_column_sort.*
import kotlinx.android.synthetic.main.common_top_titlebar.*
import java.util.ArrayList

class ColumnSortActivity : BasePresenterActivity<ColumnSortPresenter>(), MyItemTouchCallback.OnDragListener, DataView<BaseEntity<List<EmptyEntity>>> {
    override fun showData(t: BaseEntity<List<EmptyEntity>>) {
        if (t.Code!! == "200") {
            HomeFragment.Instance.updateList(columnList!!)
        }
    }


    private var columnList: ArrayList<String>? = null
    override fun inject() {
        DaggerColumnSortComponent.builder().columnSortModule(ColumnSortModule(this)).build().inject(this)
    }

    override fun initView() {
        titlebar_more.visibility = View.GONE
        titlebar_title.text = "编辑频道"
        column_sort_rlv.setHasFixedSize(true)
        column_sort_rlv.layoutManager = LinearLayoutManager(this)
        column_sort_rlv.itemAnimator = DefaultItemAnimator()
    }

    override fun initData() {
        columnList = intent.getStringArrayListExtra("columnList")
    }

    override fun initListener() {
        initRlv()
    }

    private fun initRlv() {
        val columnSortAdapter = ColumnSortAdapter(this, columnList!!)
        column_sort_rlv.adapter = columnSortAdapter
        val itemTouchHelper = ItemTouchHelper(MyItemTouchCallback(columnSortAdapter).setOnDragListener(this))
        itemTouchHelper.attachToRecyclerView(column_sort_rlv)
        column_sort_rlv.addOnItemTouchListener(object : OnRecyclerItemClickListener<RecyclerView.ViewHolder>(column_sort_rlv) {
            override fun onLongClick(vh: RecyclerView.ViewHolder) {
                if (vh.layoutPosition != 0) {
                    itemTouchHelper.startDrag(vh)
                    // VibratorUtil.Vibrate(this@EHColumnActivity, 10)   震动

                }
            }

            override fun onItemClick(vh: RecyclerView.ViewHolder) {}
        })
    }

    override val initLayout: Int
        get() = R.layout.activity_column_sort

    private var isChange: Boolean = false

    override fun onFinishDrag() {
        if (!isChange) isChange = true
    }


    override fun back(v: View) {
        if (isChange)
            saveColumnState()
        super.back(v)
    }

    override fun onBackPressed() {
        if (isChange)
            saveColumnState()
        super.onBackPressed()
    }

    private fun saveColumnState() {
        mPresenter.saveHomeTopBar(columnList.toString().replace("[", "").replace("]", "").replace(" ", ""))
    }
}
