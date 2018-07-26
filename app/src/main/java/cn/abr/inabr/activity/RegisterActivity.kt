package cn.abr.inabr.activity

import android.view.View
import cn.abr.inabr.R
import cn.abr.inabr.base.BasePresenterActivity
import cn.abr.inabr.mvp.presenter.HomePresenter
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BasePresenterActivity<HomePresenter>() {
    override fun inject() {

    }

    override fun initView() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initData() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initListener() {
       // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        register_next.setOnClickListener(this)
    }

    override var arrayOfClick: Array<out View>?
        get() = arrayOf(register_next)
        set(value) {}

    override val initLayout: Int
        get() =R.layout.activity_register

    override fun onClick(v: View?) {
        when (v!!) {
            register_next -> {
                startActivity(ForgetPwdActivity::class.java)
            }
            else -> {
            }
        }
    }
}
