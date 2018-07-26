package cn.abr.inabr.activity

import android.content.Context
import android.os.CountDownTimer
import android.view.View
import cn.abr.inabr.R
import cn.abr.inabr.base.BasePresenterActivity
import cn.abr.inabr.entity.MyCustomTabEntity
import cn.abr.inabr.mvp.presenter.HomePresenter
import cn.abr.inabr.utils.SizeUtil
import com.flyco.tablayout.listener.OnTabSelectListener
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.common_top_titlebar.*
import android.graphics.Color
import android.os.IBinder
import android.widget.EditText
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager


class LoginActivity : BasePresenterActivity<HomePresenter>() {

    private val reserveTime: Int = 60
    private val countDownTimer: CountDownTimer = object : CountDownTimer((1000 * reserveTime + 100).toLong(), 1000) {
        override fun onFinish() {
            binding_sms.apply {
                isEnabled = true
                text = "获取验证码"
                binding_sms.setBackgroundResource(R.drawable.bg_btn_sms)
            }
        }

        override fun onTick(millisUntilFinished: Long) {
            binding_sms.text = (millisUntilFinished / 1000).toString()
        }
    }


    override fun inject() {
    }

    override fun initView() {
        initTopBar()
    }

    private fun initTopBar() {
        login_tab.apply {
            setTabData(arrayListOf(MyCustomTabEntity(0, 0, "账号登录")
                    , MyCustomTabEntity(0, 0, "动态码登录")))
            currentTab = 0
            indicatorWidth = SizeUtil.px2dip(this@LoginActivity, SizeUtil.getTextWidth(this@LoginActivity,
                    login_tab.getTitleView(0).text.toString(), SizeUtil.px2dip(this@LoginActivity, login_tab.getTitleView(0).textSize))).toFloat()
            setOnTabSelectListener(object : OnTabSelectListener {
                override fun onTabSelect(position: Int) {
                    when (position) {
                        0 -> {
                            binding_inputpwd.hint = "请输入密码"
                            GONE(binding_sms)
                            VISIBLE(login_forget)
                            indicatorWidth = SizeUtil.px2dip(this@LoginActivity, SizeUtil.getTextWidth(this@LoginActivity,
                                    login_tab.getTitleView(0).text.toString(), SizeUtil.px2dip(this@LoginActivity, login_tab.getTitleView(0).textSize))).toFloat()
                        }
                        1 -> {
                            binding_inputpwd.hint = "请输入验证码"
                            VISIBLE(binding_sms)
                            GONE(login_forget)
                            indicatorWidth = SizeUtil.px2dip(this@LoginActivity, SizeUtil.getTextWidth(this@LoginActivity,
                                    login_tab.getTitleView(1).text.toString(), SizeUtil.px2dip(this@LoginActivity, login_tab.getTitleView(1).textSize))).toFloat()
                        }
                    }
                }

                override fun onTabReselect(position: Int) {

                }

            })
        }



        GONE(titlebar_more)
    }

    override fun initData() {
    }

    override fun initListener() {

    }

    override var arrayOfClick: Array<out View>?
        get() = arrayOf(binding_sms, login_button, login_register)
        set(value) {}

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.binding_sms -> {
                binding_sms.isEnabled = false
                binding_sms.setBackgroundColor(Color.parseColor("#cccccc"))
                countDownTimer.start()
            }
            R.id.login_button -> {
                showToast("跳转")
            }
            R.id.login_register -> {
                startActivity(RegisterActivity::class.java)
            }
        }
    }


    override val initLayout: Int
        get() = R.layout.activity_login

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer.cancel()
    }

    /**
     * 点击外边关闭输入法
     */
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (isShouldHideKeyboard(v, ev)) {
                val res = hideKeyboard(v!!.windowToken)
                if (res) {
                    //隐藏了输入法，则不再分发事件
                    return true
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private fun isShouldHideKeyboard(v: View?, event: MotionEvent): Boolean {
        if (v != null && v is EditText) {
            val l = intArrayOf(0, 0)
            v.getLocationInWindow(l)
            val left = l[0]
            val top = l[1]
            val bottom = top + v.height
            val right = left + v.width
            return !(event.x > left && event.x < right
                    && event.y > top && event.y < bottom)
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     * @param token
     */
    private fun hideKeyboard(token: IBinder?): Boolean {
        if (token != null) {
            val im = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            return im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS)
        }
        return false
    }
}
