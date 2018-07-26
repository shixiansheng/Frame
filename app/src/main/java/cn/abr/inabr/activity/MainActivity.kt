package cn.abr.inabr.activity

import android.os.Build
import android.support.v4.app.Fragment
import android.view.KeyEvent
import android.widget.LinearLayout
import cn.abr.inabr.R
import cn.abr.inabr.base.BaseActivity
import cn.abr.inabr.entity.MyCustomTabEntity
import cn.abr.inabr.fragment.EventFragment
import cn.abr.inabr.fragment.BookFragment
import cn.abr.inabr.fragment.MineFragment
import cn.abr.inabr.fragment.HomeFragment
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.shuyu.gsyvideoplayer.GSYVideoManager
import android.widget.Toast
import cn.abr.inabr.utils.StringUtils
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity(), OnTabSelectListener {
    override val initLayout: Int
        get() = R.layout.activity_main

    override fun onTabSelect(position: Int) {
        if (position == 0)
            GONE(main_tablayout.getTitleView(0))
        else {
            VISIBLE(main_tablayout.getTitleView(0))
        }
    }

    override fun onTabReselect(position: Int) {

    }


    private lateinit var customList: ArrayList<CustomTabEntity>
    private lateinit var fragmentList: ArrayList<Fragment>


    override fun initView() {
    }

    private fun initTabList() {
        customList = arrayListOf<CustomTabEntity>().apply {
            add(MyCustomTabEntity(R.mipmap.main_tab_homepage_unselect, R.mipmap.main_tab_homepage_select, "首页"))
            add(MyCustomTabEntity(R.mipmap.main_tab_activities_unselect, R.mipmap.main_tab_activities_select, "活动"))
            add(MyCustomTabEntity(R.mipmap.main_tab_books_unselect, R.mipmap.main_tab_books_select, "书刊"))
            add(MyCustomTabEntity(R.mipmap.main_tab_mine_unselect, R.mipmap.main_tab_mine_select, "我的"))
        }
    }

    override fun initListener() {
        initTab()
    }

    private fun initTab() {
        GONE( main_tablayout.getTitleView(0))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val linearLayout = main_tablayout.getChildAt(0) as LinearLayout
            var i = 0
            while (i < linearLayout.childCount) {
                linearLayout.getChildAt(i++).setBackgroundResource(R.drawable.nomaskripple)
            }
        }

        main_tablayout.setOnTabSelectListener(this)
    }

    override fun initData() {
        initTabList()
        initFragmentList()
        main_tablayout.setTabData(customList, this, R.id.main_frameLayout, fragmentList)
    }

    override fun onBackPressed() {
        if (GSYVideoManager.backFromWindowFull(this)) {
            return
        }
        super.onBackPressed()
    }

    private fun initFragmentList() {
        fragmentList = arrayListOf<Fragment>().apply {
            add(HomeFragment.Instance)
            add(EventFragment())
            add(BookFragment())
            add(MineFragment())
        }
    }



    //记录用户首次点击返回键的时间
    private var firstTime: Long = 0
    //双击退出
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() === KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - firstTime > 2000) {
                Toast.makeText(this@MainActivity, "再按一次退出程序!", Toast.LENGTH_SHORT).show()
                firstTime = System.currentTimeMillis()
            } else {
                finish()
                System.exit(0)
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

}
