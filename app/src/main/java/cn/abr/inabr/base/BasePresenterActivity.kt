package cn.abr.inabr.base

import android.content.Intent
import android.os.Bundle
import com.zhy.autolayout.AutoLayoutActivity


import javax.inject.Inject
import android.view.View
import android.view.View.*
import cn.abr.inabr.R
import cn.abr.inabr.common.App
import cn.abr.inabr.utils.ToastUtil
import cn.abr.inabr.weight.LoadingDialog
import com.gyf.barlibrary.ImmersionBar


abstract class BasePresenterActivity<P : BasePresenter<*>> : AutoLayoutActivity(), View.OnClickListener {

    @Inject
    lateinit var mPresenter: P
    private lateinit var loadingDialog: LoadingDialog

    abstract fun inject()
    abstract fun initView()
    abstract fun initData()
    abstract fun initListener()
    abstract val initLayout: Int
    open var arrayOfClick: Array<out View>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(initLayout)
        loadingDialog = LoadingDialog(this, R.style.progress_dialog)
        initOptions()
        if (arrayOfClick!=null) {
            setOnclickListener(*arrayOfClick!!)
        }
    }
    private fun initOptions() {
        initStatusBar(ImmersionBar.with(this))
        inject()
        initView()
        initData()
        initListener()
    }

    fun showLoadingProgress(): Unit {
        if (!loadingDialog.isShowing)
            loadingDialog.show()
    }

    fun hideLoadingProgress(): Unit {
        if (loadingDialog.isShowing)
            loadingDialog.dismiss()
    }

    private fun initStatusBar(immersionBar: ImmersionBar) {
        immersionBar.init()
    }


    private fun setOnclickListener(vararg v: View){
        v.forEach {
            it.setOnClickListener(this)
        }
    }

    open fun back(v: View): Unit {
        finish()
    }
    open fun share(v: View): Unit {
    }

    open fun more(v: View): Unit {
    }

    fun GONE(vararg v: View): Unit {
        v.forEach {
            it.visibility= GONE
        }
    }
    fun VISIBLE(vararg v: View): Unit {
        v.forEach {
            it.visibility= VISIBLE
        }
    }
    fun INVISIBLE(vararg v: View): Unit {
        v.forEach {
            it.visibility= INVISIBLE
        }
    }

    override fun onClick(v: View?) {

    }

    /**
     * 无参跳转
     * @param clz
     */
    fun startActivity(clz: Class<*>) {
        val intent = Intent(this, clz)
        startActivity(intent)
    }

    /**
     * 显示toast
     * @param msg
     */
    fun showToast(msg: String) {
        ToastUtil.show(this,msg)
    }

    /**
     * 有参跳转
     * @param clz
     * @param bundle
     */
    fun startActivity(clz: Class<*>, bundle: Bundle) {
        val intent = Intent(this, clz)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    fun startActivityForResult(clz: Class<*>, bundle: Bundle, requestCode: Int) {
        val intent = Intent(this, clz)
        intent.putExtras(bundle)
        startActivityForResult(intent, requestCode)
    }

    override fun onDestroy() {
        super.onDestroy()
            mPresenter?.detach()
        ImmersionBar.with(this).destroy()
        App.Instance().getRefWatcher()
                .apply {
                    watch(mPresenter)
                    watch(this@BasePresenterActivity)
                }
    }
}
