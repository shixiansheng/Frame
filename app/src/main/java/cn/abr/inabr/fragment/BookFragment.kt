package cn.abr.inabr.fragment

import cn.abr.inabr.mvp.presenter.HomePresenter
import cn.abr.inabr.R
import cn.abr.inabr.base.BaseLazyFragment
import kotlinx.android.synthetic.main.booksfragment_main.*


/**
 * .
 * Created by Administrator on 2018/4/3/003.
 */

class BookFragment : BaseLazyFragment<HomePresenter>() {
    override fun setListener() {

    }

    override fun inject() {
    }


    override val layoutId: Int
        get() = R.layout.booksfragment_main

    override fun initView() {

    }

    override fun notLazy(): Boolean {
        return super.notLazy()
    }

    override fun initData() {

    }
}
