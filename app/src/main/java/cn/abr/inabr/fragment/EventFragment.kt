package cn.abr.inabr.fragment

import cn.abr.inabr.mvp.presenter.HomePresenter
import cn.abr.inabr.R
import cn.abr.inabr.base.BaseLazyFragment


/**
 * .
 * Created by Administrator on 2018/4/3/003.
 */

class EventFragment : BaseLazyFragment<HomePresenter>() {
    override fun setListener() {

    }

    override fun inject() {
    }


    override val layoutId: Int
        get() = R.layout.activitiesfragment_main

    override fun initView() {
    }

    override fun initData() {

    }
}
