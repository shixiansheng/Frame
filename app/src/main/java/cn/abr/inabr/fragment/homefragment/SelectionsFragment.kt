package cn.abr.inabr.fragment.homefragment


import android.os.Bundle
import android.app.Fragment
import android.content.pm.ActivityInfo.*
import android.content.res.Configuration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import cn.abr.inabr.R
import cn.abr.inabr.activity.MainActivity
import cn.abr.inabr.adapter.itemdelagate.*
import cn.abr.inabr.base.BaseEntity
import cn.abr.inabr.base.BaseLazyFragment
import cn.abr.inabr.base.BaseMultiItemTypeAdapter
import cn.abr.inabr.common.Api
import cn.abr.inabr.dagger2.component.DaggerSelectionsComponent
import cn.abr.inabr.dagger2.module.SelectionsModule
import cn.abr.inabr.entity.BannerEntity
import cn.abr.inabr.entity.SelectionsListEntity
import cn.abr.inabr.mvp.presenter.SelectionsPresenter
import cn.abr.inabr.mvp.view.SelectionsView
import cn.abr.inabr.utils.GlideUtils
import cn.abr.inabr.utils.ToastUtil
import cn.abr.inabr.weight.xbanner.XBanner
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper
import com.zhy.adapter.recyclerview.wrapper.LoadMoreWrapper
import kotlinx.android.synthetic.main.fragment_selections.*


/**
 * A simple [Fragment] subclass.
 * Use the [SelectionsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SelectionsFragment : BaseLazyFragment<SelectionsPresenter>(), SelectionsView {

    private var page: Int = 0
    private val count: Int = 10
    private var selections_xbanner: XBanner? = null
    internal var mFull = false
    internal var isAll = false

    private lateinit var datalist: MutableList<SelectionsListEntity>
    private var isLoadMore: Boolean = false
    override fun showList(response: BaseEntity<List<SelectionsListEntity>>) {
        when (isLoadMore) {
            true -> isLoadMore = false
            else -> if (datalist.size > 0) datalist.clear()
        }
        if (response.Data?.size!=0) {
            datalist.addAll(response.Data!!)
            selections_recyclerView.adapter.notifyDataSetChanged()
        }else
        {
            isAll=true
            showToast("加载完毕")
        }
    }

    override fun showBanner(t: BaseEntity<List<BannerEntity>>) {
        val data = t.Data!!
        if (!data.isEmpty()) {
            selections_xbanner!!.apply {
                visibility = View.VISIBLE
                setData(data.toMutableList(), changeList(data.toMutableList()))
            }

        }
    }

    override fun setListener() {
        selections_xbanner!!.setmAdapter { _, model, view, _ ->
            val bannerEntity = model as BannerEntity
            GlideUtils.setThePicture(activity, bannerEntity.imageUrl, view as ImageView)
        }
    }

    private fun changeList(list: MutableList<BannerEntity>): MutableList<String>? {
        return list.flatMap {
            listOf<String>(it.title!! + "\t")
        }.toMutableList()
    }

    override fun onResume() {
        super.onResume()
        selections_xbanner!!.startAutoPlay()
        GSYVideoManager.onResume()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (!isVisibleToUser) {
            GSYVideoManager.releaseAllVideos()
        }
        if (selections_xbanner != null)
            when (isVisibleToUser) {
                false -> {
                    selections_xbanner!!.stopAutoPlay()
                }
                true -> {
                    selections_xbanner!!.startAutoPlay()
                }
            }
    }


    override fun onStop() {
        super.onStop()
        selections_xbanner!!.stopAutoPlay()
    }

    override val layoutId: Int
        get() = R.layout.fragment_selections

    lateinit var inflate: View
    override fun initView() {
        inflate = LayoutInflater.from(activity).inflate(R.layout.xbanner, null, false)
        selections_xbanner = inflate.findViewById<XBanner>(R.id.selections_xbanner)
        selections_xbanner!!.visibility = View.GONE
    }


    override fun inject() {
        DaggerSelectionsComponent.builder().selectionsModule(SelectionsModule(this)).build().inject(this)
    }

    override fun initData() {
        datalist = arrayListOf()
        requestData()
        initAdapter()
    }

    private fun initAdapter() {
        val multiItemTypeAdapter = BaseMultiItemTypeAdapter(activity, datalist).apply {
            addItemViewDelegate(SelectionsDefaultItem())
            addItemViewDelegate(SelectionsMultipleItem())
            addItemViewDelegate(SelectionsDefaultVideo())
            addItemViewDelegate(SelectionsLargeMutipleItem())
            addItemViewDelegate(SelectionsLargeVideo())
            addItemViewDelegate(SelectionsSpecialItem())
        }
        val linearLayoutManager = LinearLayoutManager(activity)
        selections_recyclerView
                .apply {
                    layoutManager = linearLayoutManager

                }.addOnScrollListener(object : RecyclerView.OnScrollListener() {

                    internal var firstVisibleItem: Int = 0
                    internal var lastVisibleItem: Int = 0

                    override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                        super.onScrollStateChanged(recyclerView, newState)
                    }

                    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition()
                        lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                        //大于0说明有播放
                        if (GSYVideoManager.instance().playPosition >= 0) {
                            //当前播放的位置
                            val position = GSYVideoManager.instance().playPosition
                            //对应的播放列表TAG
                            if (GSYVideoManager.instance().playTag == SelectionsLargeVideo.TAG && (position < firstVisibleItem || position > lastVisibleItem)) {

                                //如果滑出去了上面和下面就是否，和今日头条一样
                                //是否全屏
                                if (!mFull) {
                                    GSYVideoManager.releaseAllVideos()
                                    multiItemTypeAdapter.notifyDataSetChanged()
                                }
                            }
                        }
                    }
                })


        selections_recyclerView.adapter = LoadMoreWrapper<Any>(HeaderAndFooterWrapper<Any>(multiItemTypeAdapter).apply {
            addHeaderView(inflate)
        }).apply {
            val textView = TextView(activity)
            setLoadMoreView(textView)
            setOnLoadMoreListener {
                if (!isAll) {
                    if (datalist.size > 0) {
                        showToast("数据加载")
                        page++
                        isLoadMore = true
                        mPresenter.getSelectionsList(mParam1, page.toString())
                    }
                }else{
                    showToast("加载完毕")
                }
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        //如果旋转了就全屏
        mFull = newConfig.orientation == SCREEN_ORIENTATION_USER

    }


    override fun onPause() {
        super.onPause()
        GSYVideoManager.onPause()
    }


    override fun onDestroy() {
        super.onDestroy()
        GSYVideoManager.releaseAllVideos()
    }

    private fun requestData() {
        if (!mParam1.isNullOrEmpty()) {
            mPresenter.getSelectionsBanner(mParam1)
            mPresenter.getSelectionsList(mParam1, page.toString())
        }
    }

    private var mParam1: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
        }
    }

    companion object {
        private val ARG_PARAM1 = "param1"
        fun newInstance(param1: String): SelectionsFragment {
            val fragment = SelectionsFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            fragment.arguments = args
            return fragment
        }
    }

}
