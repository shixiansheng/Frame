package cn.abr.inabr.fragment


import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.View
import android.view.ViewGroup
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
import com.wf.statusmanager.OnStatusChildClickListener
import com.wf.statusmanager.StatusViewManager
import kotlinx.android.synthetic.main.homefragment_main.*

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
        statusViewManager = StatusViewManager.Builder(homepage_vp)
                .setOnStatusChildClickListener(object :OnStatusChildClickListener{
                    override fun onEmptyChildClick(view: View?) {

                    }

                    override fun onErrorChildClick(view: View?) {
                        statusViewManager.showErrorLayout()
                        mPresenter.getHomeTopBar()
                    }

                    override fun onCustomerChildClick(view: View?) {
                    }

                })
                .build()
        //statusViewManager.showLoadingLayout()
    }


    private lateinit var statusViewManager: StatusViewManager

    override fun setListener() {
    }

    override var arrayOfClick: Array<out View>?
        get() = arrayOf(homepage_topbar_sort,homepage_topbar_search)
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
        setViewPager(t)
        statusViewManager.showSuccessLayout()
    }

    override fun onFailure(t: Throwable) {
        statusViewManager.showErrorLayout()
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
