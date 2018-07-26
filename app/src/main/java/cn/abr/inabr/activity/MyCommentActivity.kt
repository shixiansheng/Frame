package cn.abr.inabr.activity

import android.support.v7.widget.LinearLayoutManager
import cn.abr.inabr.R
import cn.abr.inabr.adapter.itemdelagate.MyCommentItem
import cn.abr.inabr.base.BaseMultiItemTypeAdapter
import cn.abr.inabr.base.BasePresenterActivity
import cn.abr.inabr.mvp.presenter.HomePresenter
import kotlinx.android.synthetic.main.activity_my_comment.*
import android.support.v7.widget.DividerItemDecoration



class MyCommentActivity : BasePresenterActivity<HomePresenter>() {
    override fun inject() {
    }

    override fun initView() {
        my_comment_rlv.apply {
            //添加Android自带的分割线
            addItemDecoration(DividerItemDecoration(this@MyCommentActivity, DividerItemDecoration.VERTICAL))
            layoutManager=LinearLayoutManager(this@MyCommentActivity)
            adapter=BaseMultiItemTypeAdapter(this@MyCommentActivity, arrayListOf("","","","")).apply{
                addItemViewDelegate(MyCommentItem())
            }
        }
    }

    override fun initData() {

    }

    override fun initListener() {
    }

    override val initLayout: Int
        get() = R.layout.activity_my_comment

    }
