package cn.abr.inabr.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import cn.abr.inabr.R
import cn.abr.inabr.base.BasePresenterActivity
import cn.abr.inabr.fragment.buyfragment.ArticleFragment
import cn.abr.inabr.fragment.buyfragment.MagazineFragment
import cn.abr.inabr.mvp.presenter.HomePresenter
import kotlinx.android.synthetic.main.activity_my_buy.*

class MyBuyActivity : BasePresenterActivity<HomePresenter>() {

    override fun inject() {
    }

    override fun initView() {
    }

    override fun initData() {
        initTab()
    }

    private fun initTab() {
        my_buy_tab.setViewPager(my_buy_vp, arrayOf("杂志","文章"),this, arrayListOf(MagazineFragment.newInstance(2), ArticleFragment.newInstance()))
    }

    override fun initListener() {
    }

    override val initLayout: Int
        get() = R.layout.activity_my_buy


}
