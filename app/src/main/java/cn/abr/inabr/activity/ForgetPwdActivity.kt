package cn.abr.inabr.activity

import android.view.View
import cn.abr.inabr.R
import cn.abr.inabr.base.BasePresenterActivity
import cn.abr.inabr.mvp.presenter.HomePresenter
import kotlinx.android.synthetic.main.activity_forget_pwd.*

class ForgetPwdActivity : BasePresenterActivity<HomePresenter>() {
    override fun inject() {
    }

    override fun initView() {
    }

    override fun initData() {
    }

    override fun initListener() {
    }

    override var arrayOfClick: Array<out View>?
        get() = arrayOf(forget_next)
        set(value) {}

    override val initLayout: Int
        get() = R.layout.activity_forget_pwd

    override fun onClick(v: View?) {
        when (v!!) {
            forget_next ->
                startActivity(ForgetPwdSecondActivity::class.java)
            else -> {
            }
        }
    }
}
