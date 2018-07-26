package cn.abr.inabr.fragment

import android.view.View
import cn.abr.inabr.mvp.presenter.HomePresenter
import cn.abr.inabr.R
import cn.abr.inabr.activity.LoginActivity
import cn.abr.inabr.activity.SettingsActivity
import cn.abr.inabr.base.BaseLazyFragment
import kotlinx.android.synthetic.main.minefragment_main.*
import kotlinx.android.synthetic.main.video_layout_cover.*


/**
 * .
 * Created by Administrator on 2018/4/3/003.
 */

class MineFragment : BaseLazyFragment<HomePresenter>(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v) {
            minefragment_name -> {
                startActivity(LoginActivity::class.java)
            }
            minefragment_icon -> {
                startActivity(LoginActivity::class.java)
            }
            minefragment_settings ->{
                startActivity(SettingsActivity::class.java)
            }
        }
    }

    override fun setListener() {
    }

    override var arrayOfClick: Array<out View>?
        get() = arrayOf(minefragment_name,minefragment_icon,minefragment_settings)
        set(value) {}


    override fun inject() {
    }


    override val layoutId: Int
        get() = R.layout.minefragment_main

    override fun initView() {

    }

    override fun notLazy(): Boolean {
        return super.notLazy()
    }

    override fun initData() {

    }
}
