package cn.abr.inabr.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.util.Log
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import cn.abr.inabr.R
import cn.abr.inabr.common.App
import example.wf.com.statuslayoutmanager.StatusLayoutManager
import kotlinx.android.synthetic.main.base_layout.*
import javax.inject.Inject

abstract class BaseLazyFragment<P : BasePresenter<*>> : Fragment(), View.OnClickListener {
    override fun onClick(v: View?) {

    }

    private val pageName: String = javaClass.simpleName

    private var visible = false//当前是否可见
    private var isInitView = false//是否与View建立起映射关系
    private var isFirstLoad = true//是否是第一次加载数据
    lateinit var convertView: View
    @Inject
    lateinit var mPresenter: P
    /**
     * 加载页面布局文件
     * @return
     */
    protected abstract val layoutId: Int
    open var arrayOfClick: Array<out View>? = null
    private var mViews: SparseArray<View>? = null
    protected  var statusLayoutManager: StatusLayoutManager?=null
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.e("Fragment", "   " + this.javaClass.simpleName)
        convertView = LayoutInflater.from(activity).inflate(layoutId, container, false)
        mViews = SparseArray()
        isInitView = true
        return convertView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("Fragment", "   " + this.javaClass.simpleName)
    }


    /**
     * 让布局中的view与fragment中的变量建立起映射
     */
    protected abstract fun initView()

    /**
     * 注入
     */
    abstract fun inject()

    /**
     * 加载要显示的数据
     */
    protected abstract fun initData()

    protected abstract fun setListener()
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


    open fun notLazy(): Boolean {
        return false
    }


    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detach()

        App.Instance().getRefWatcher()
                .apply {
                    watch(mPresenter)
                    watch(this@BaseLazyFragment)
                }

    }

    private fun setNull(`object`: Any?) {
        if (`object` != null)
            `object` == null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        inject()
        initView()
        if (notLazy()) initData() else lazyLoadData()
        setListener()
        if (arrayOfClick != null)
            setOnclickListener(*arrayOfClick!!)
    }

    open fun initLayoutManager(@LayoutRes contentViewId:Int) {
        statusLayoutManager = StatusLayoutManager.newBuilder(activity)
                .contentView(contentViewId)
                .loadingView(R.layout.loading)
                .errorView(R.layout.error)
                .build()
        (convertView as ViewGroup).addView(statusLayoutManager?.rootLayout)
    }

    private fun setOnclickListener(vararg v: View) {
        v.forEach {
            it.setOnClickListener(this)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        Log.e("Fragment", "context" + "   " + this.javaClass.simpleName)
    }


    override fun onResume() {
        super.onResume()
        if (userVisibleHint) {
            onVisibilityChangedToUser(true, false)
        }
    }

    override fun onPause() {
        super.onPause()
        if (userVisibleHint) {
            onVisibilityChangedToUser(false, false)
        }
    }

    /**
     * 无参跳转
     * @param clz
     */
    fun startActivity(clz: Class<*>) {
        val intent = Intent(activity, clz)
        startActivity(intent)
    }

    /**
     * 显示toast
     * @param msg
     */
    fun showToast(msg: String) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
    }

    /**
     * 有参跳转
     * @param clz
     * @param bundle
     */
    fun startActivity(clz: Class<*>, bundle: Bundle) {
        val intent = Intent(activity, clz)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    fun startActivityForResult(clz: Class<*>, bundle: Bundle, requestCode: Int) {
        val intent = Intent(activity, clz)
        intent.putExtras(bundle)
        startActivityForResult(intent, requestCode)
    }
    /* @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        setUserVisibleHint(true);
    }*/

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        Log.e("Fragment", "isVisibleToUser " + isVisibleToUser + "   " + this.javaClass.simpleName)
        if (isVisibleToUser) {
            visible = true
            lazyLoadData()
        } else {
            visible = false
        }
        if (isResumed) {
            onVisibilityChangedToUser(isVisibleToUser, true)
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        userVisibleHint = !hidden
    }

    private fun lazyLoadData() {
        if (isFirstLoad) {
            Log.e("Fragment", "第一次加载 " + " isInitView  " + isInitView + "  visible  " + visible + "   " + this.javaClass.simpleName)
        } else {
            Log.e("Fragment", "不是第一次加载" + " isInitView  " + isInitView + "  visible  " + visible + "   " + this.javaClass.simpleName)
        }
        if (!isFirstLoad || !visible || !isInitView) {
            Log.e("Fragment", "不加载" + "   " + this.javaClass.simpleName)
            return
        }

        Log.e("Fragment", "完成数据第一次加载")
        initData()
        isFirstLoad = false
    }

    protected fun <E : View> findView(viewId: Int): E? {
        if (convertView != null) {
            var view: View? = mViews!!.get(viewId)
            if (view == null) {
                view = convertView!!.findViewById<View>(viewId) as E
                mViews!!.put(viewId, view)
            }
            return view as E
        }
        return null
    }

    /**
     * 当Fragment对用户的可见性发生了改变的时候就会回调此方法
     * @param isVisibleToUser true：用户能看见当前Fragment；false：用户看不见当前Fragment
     * @param isHappenedInSetUserVisibleHintMethod true：本次回调发生在setUserVisibleHintMethod方法里；false：发生在onResume或onPause方法里
     */
    fun onVisibilityChangedToUser(isVisibleToUser: Boolean, isHappenedInSetUserVisibleHintMethod: Boolean) {
        if (isVisibleToUser) {
            if (pageName != null) {
                // MobclickAgent.onPageStart(pageName);
                //                LogUtil.i(getClass(), pageName + " - display - " + (isHappenedInSetUserVisibleHintMethod ? "setUserVisibleHint" : "onResume"));
            }
        } else {
            if (pageName != null) {
                // MobclickAgent.onPageEnd(pageName);
                //                LogUtil.i(getClass(), pageName + " - hidden - " + (isHappenedInSetUserVisibleHintMethod ? "setUserVisibleHint" : "onPause"));
            }
        }
    }
}
