package cn.abr.inabr.fragment.homefragment


import android.os.Bundle
import android.app.Fragment
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView

import cn.abr.inabr.R
import cn.abr.inabr.adapter.itemdelagate.VideoColumnItem
import cn.abr.inabr.adapter.itemdelagate.VideoFragmentItem
import cn.abr.inabr.base.BaseEntity
import cn.abr.inabr.base.BaseLazyFragment
import cn.abr.inabr.base.BaseMultiItemTypeAdapter
import cn.abr.inabr.dagger2.component.DaggerVideoComponent
import cn.abr.inabr.dagger2.module.VideoModule
import cn.abr.inabr.entity.SelectionsListEntity
import cn.abr.inabr.entity.VideoColumnEntity
import cn.abr.inabr.mvp.presenter.VideoPresenter
import cn.abr.inabr.mvp.view.VideoView
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter
import com.zhy.adapter.recyclerview.wrapper.LoadMoreWrapper
import kotlinx.android.synthetic.main.fragment_video.*
import kotlinx.android.synthetic.main.home_video_column_layout.*


/**
 * A simple [Fragment] subclass.
 * Use the [VideoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class VideoFragment : BaseLazyFragment<VideoPresenter>(), VideoView {


    override fun onFailure(t: Throwable) {

    }


    override fun showList(response: BaseEntity<List<SelectionsListEntity>>) {
        when (isLoadMore) {
            true -> isLoadMore = false
            else -> if (datalist.size > 0) datalist.clear()
        }
        if (response.Data?.size!=0) {
            datalist.addAll(response.Data!!)
            videofragment_rlv.adapter.notifyDataSetChanged()
        }else {
            isAll=true
            showToast("加载完毕")
        }
    }

    override fun showColumn(response: BaseEntity<List<VideoColumnEntity>>) {
        columnlist.addAll(response.Data!!)
        video_column_recyclerView.adapter.notifyDataSetChanged()
        mPresenter.getSelectionsList(mParam1, page.toString())
    }

    internal var mFull = false
    internal var isAll = false
    private var page: Int = 0
    private lateinit var datalist: ArrayList<SelectionsListEntity>
    private lateinit var columnlist: ArrayList<VideoColumnEntity>
    override fun setListener() {
    }

    override fun onResume() {
        super.onResume()
        GSYVideoManager.onResume()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        //如果旋转了就全屏
        mFull = newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_USER

    }


    override fun onPause() {
        super.onPause()
        GSYVideoManager.onPause()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (!isVisibleToUser) {
            GSYVideoManager.releaseAllVideos()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        GSYVideoManager.releaseAllVideos()
    }

    override val layoutId: Int
        get() = R.layout.fragment_video

    override fun initView() {
    }

    override fun inject() {
        DaggerVideoComponent.builder().videoModule(VideoModule(this)).build().inject(this)
    }

    override fun initData() {
        datalist = arrayListOf()
        columnlist = arrayListOf()
        mPresenter.getVideoColumn()
        initColumnAdapter()
        initDataAdapter()
    }

    private fun initColumnAdapter() {
        video_column_recyclerView.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = BaseMultiItemTypeAdapter(activity, columnlist).apply {
                addItemViewDelegate(VideoColumnItem().apply {
                    onItemClickListener = object : VideoColumnItem.OnItemClickListener {
                        override fun onItemClickListener(t: VideoColumnEntity) {
                            if (t.name == mParam1)
                                return
                            mParam1=if (t.name == "全部") "视频" else t.name
                            page = 0
                            mPresenter.getSelectionsList(mParam1, page.toString())
                        }

                    }
                })
            }

        }
    }

    private var isLoadMore: Boolean = false
    private fun initDataAdapter() {
        val multiItemTypeAdapter = BaseMultiItemTypeAdapter(activity, datalist).apply {
            addItemViewDelegate(VideoFragmentItem())
        }
        videofragment_rlv.apply {
            val linearLayoutManager = LinearLayoutManager(activity)
            layoutManager = linearLayoutManager
            adapter = LoadMoreWrapper<Any>(multiItemTypeAdapter).apply {
                val textView = TextView(activity)
                setLoadMoreView(textView)
                setOnLoadMoreListener {
                    if (isAll==false) {
                        if (datalist.size > 0) {
                            showToast("数据加载")
                            page++
                            isLoadMore = true
                            mPresenter.getSelectionsList(mParam1, page.toString())
                        }
                    }else {
                        showToast("加载完毕")
                    }
                }
            }
            addOnScrollListener(object : RecyclerView.OnScrollListener() {

                var firstVisibleItem: Int = 0
                var lastVisibleItem: Int = 0

                override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition()
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                    //大于0说明有播放
                    if (GSYVideoManager.instance().playPosition >= 0) {
                        //当前播放的位置
                        val position = GSYVideoManager.instance().playPosition
                        //对应的播放列表TAG
                        if (GSYVideoManager.instance().playTag == VideoFragmentItem.TAG && (position < firstVisibleItem || position > lastVisibleItem)) {

                            //如果滑出去了上面和下面就是否，和今日头条一样
                            //是否全屏
                            if (!mFull) {
                                GSYVideoManager.releaseAllVideos()
                                adapter.notifyDataSetChanged()
                            }
                        }
                    }
                }
            })
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
        fun newInstance(param1: String): VideoFragment {
            val fragment = VideoFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            fragment.arguments = args
            return fragment
        }
    }

}
