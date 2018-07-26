package cn.abr.inabr.base

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.zhy.autolayout.AutoLayoutActivity


import android.view.View
import cn.abr.inabr.R
import cn.abr.inabr.common.App
import cn.abr.inabr.weight.LoadingDialog
import com.gyf.barlibrary.ImmersionBar


abstract class BaseActivity : AutoLayoutActivity(), View.OnClickListener {
    override fun onClick(v: View?) {

    }


    abstract fun initView()
    abstract fun initData()
    abstract fun initListener()
    abstract val initLayout: Int
    open var arrayOfClick: Array<out View>? = null
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(initLayout)
        loadingDialog = LoadingDialog(this, R.style.progress_dialog)
        initStatusBar(ImmersionBar.with(this))
        initView()
        initData()
        initListener()
        if (arrayOfClick!=null)
            setOnclickListener(*arrayOfClick!!)
    }

    fun showLoadingProgress(): Unit {
        if (!loadingDialog?.isShowing)
            loadingDialog?.show()
    }

    fun hideLoadingProgress(): Unit {
        if (loadingDialog!!.isShowing)
            loadingDialog!!.dismiss()
    }
    private fun setOnclickListener(vararg v: View) {
        v.forEach {
            it.setOnClickListener(this)
        }
    }

    fun GONE(vararg v: View): Unit {
        v.forEach {
            it.visibility = View.GONE
        }
    }

    fun VISIBLE(vararg v: View): Unit {
        v.forEach {
            it.visibility = View.VISIBLE
        }
    }

    fun INVISIBLE(vararg v: View): Unit {
        v.forEach {
            it.visibility = View.INVISIBLE
        }
    }

    open fun initStatusBar(immersionBar: ImmersionBar) {
        immersionBar.init()
    }


    override fun onDestroy() {
        super.onDestroy()
        ImmersionBar.with(this).destroy()
        App.Instance().getRefWatcher().watch(this@BaseActivity)
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
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
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
}
