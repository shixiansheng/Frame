package cn.abr.inabr.fragment


import android.os.Bundle
import android.view.View
import cn.abr.inabr.*
import cn.abr.inabr.activity.ColumnSortActivity
import cn.abr.inabr.adapter.HomePageAdapter
import cn.abr.inabr.base.BaseEntity
import cn.abr.inabr.base.BaseLazyFragment
import cn.abr.inabr.dagger2.component.DaggerHomeComponent
import cn.abr.inabr.dagger2.module.HomeModule
import cn.abr.inabr.entity.HomeTopBarEntity
import cn.abr.inabr.mvp.presenter.HomePresenter
import cn.abr.inabr.mvp.view.HomeTopBarView
import kotlinx.android.synthetic.main.homefragment_main.*
import kotlinx.android.synthetic.main.layout_netword_connection_failed.*

/**
 * .
 * Created by Administrator on 2018/4/3/003.
 */

class HomeFragment : BaseLazyFragment<HomePresenter>(), HomeTopBarView, View.OnClickListener {



    private lateinit var titleArray: ArrayList<String>
    private var homePageAdapter: HomePageAdapter? = null

    companion object {
        val Instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            HomeFragment()
        }
    }

    override fun inject() {
        DaggerHomeComponent.builder().homeModule(HomeModule(this)).build().inject(this);
    }

    override val layoutId: Int
        get() = R.layout.homefragment_main

    override fun initView() {

    }

    override fun setListener() {
    }

    override var arrayOfClick: Array<out View>?
        get() = arrayOf(homepage_topbar_sort,homepage_topbar_search,loadfail_error_click_layout)
        set(value) {}

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.homepage_topbar_sort -> {
                if (titleArray.isNotEmpty()) {
                    val bundle = Bundle()
                    bundle.putStringArrayList("columnList", titleArray)
                    startActivityForResult(ColumnSortActivity::class.java, bundle, 100)
                }
            }
            R.id.homepage_topbar_search -> {
            }
            R.id.loadfail_error_click_layout -> {
                GONE(loadfail_error_click_layout)
                VISIBLE(loadfail_progressbar)
                mPresenter.getHomeTopBar()
            }

        }
    }

    fun updateList(list: ArrayList<String>) {
        titleArray.apply {
            clear()
            addAll(list)
        }
        homepage_vp.adapter = HomePageAdapter(activity!!.supportFragmentManager,titleArray)
        homepage_topbar_tablayout.notifyDataSetChanged()
    }


    override fun initData() {
        titleArray = arrayListOf()
        mPresenter.getHomeTopBar()
    }

    override fun showHomeTopBar(t: BaseEntity<List<HomeTopBarEntity>>) {
        VISIBLE(homepage_vp)
        GONE(layout_failure)
        setViewPager(t)
    }

    override fun onFailure(t: Throwable) {
        GONE(loadfail_progressbar)
        VISIBLE(loadfail_error_click_layout)
    }

    private fun setViewPager(t: BaseEntity<List<HomeTopBarEntity>>) {
        val data = t.Data!!
        for (i in data.indices) {
            val className = data[i].class_name!!
            titleArray.add(className)
        }
        homePageAdapter = HomePageAdapter(activity.supportFragmentManager, titleArray)
        homepage_vp.adapter = homePageAdapter
        homepage_topbar_tablayout.setViewPager(homepage_vp)
    }

}
