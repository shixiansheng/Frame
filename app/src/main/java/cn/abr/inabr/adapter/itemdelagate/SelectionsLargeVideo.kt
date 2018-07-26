package cn.abr.inabr.adapter.itemdelagate

import android.content.Context
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import cn.abr.inabr.R
import cn.abr.inabr.R.id.*
import cn.abr.inabr.entity.SelectionsListEntity
import cn.abr.inabr.utils.GlideUtils
import cn.abr.inabr.weight.videoplayer.SampleCoverVideo
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.zhy.adapter.recyclerview.base.ItemViewDelegate
import com.zhy.adapter.recyclerview.base.ViewHolder
import com.zhy.autolayout.utils.AutoUtils

/**
 * .
 * Created by Administrator on 2018/4/27/027.
 */
class SelectionsLargeVideo : ItemViewDelegate<SelectionsListEntity> {

    private val ITEM_DEFAULT = 4

    companion object {
        val TAG: String? = "SelectionsLargeVideo"
    }

    override fun getItemViewLayoutId(): Int = R.layout.home_list_large_video_item


    private lateinit var gsyVideoOptionBuilder: GSYVideoOptionBuilder

    private lateinit var context: Context

    override fun convert(holder: ViewHolder, t: SelectionsListEntity, position: Int) {
        context = holder.itemView.context
        gsyVideoOptionBuilder = GSYVideoOptionBuilder()
        val sampleCoverVideo = holder.getView<SampleCoverVideo>(home_list_large_video_detail)
        onBind(position, t, sampleCoverVideo)
        holder.apply {
            if (TextUtils.isEmpty(t.leftLabel)) {
                setText(home_list_large_video_label, t.videoColumn)
                setText(home_list_large_video_comment, "")
            } else {
                setText(home_list_large_video_label, t.videoColumn)
                setText(home_list_large_video_comment, t.centerLabel)
            }
            setText(home_list_large_video_title, t.title)
            setText(home_list_large_video_time, t.rightLabel)
        }

    }


    override fun isForViewType(item: SelectionsListEntity?, position: Int): Boolean = item!!.type == ITEM_DEFAULT


    fun onBind(position: Int, videoModel: SelectionsListEntity, sampleCoverVideo: SampleCoverVideo) {


        sampleCoverVideo.loadCoverImage(if (videoModel.imageUrl != null && videoModel.imageUrl!!.isNotEmpty())
            videoModel.imageUrl!![0] else  "",R.drawable.placeholder_picture_image)

        //val url: String? = videoModel.videoUrl
        val url: String? = "http://yun.it7090.com/video/XHLaunchAd/video03.mp4"
        val title: String? = videoModel.title

        sampleCoverVideo.apply {
            initUIState()
            //增加title
            titleTextView.visibility = View.GONE
            findViewById<LinearLayout>(R.id.layout_top).visibility=View.GONE
            //设置返回键
            backButton.visibility = View.GONE

            //设置全屏按键功能
            fullscreenButton.visibility = View.GONE
        }
        //防止错位，离开释放
        gsyVideoOptionBuilder.apply {
            setIsTouchWiget(false)//是否可以滑动界面改变进度，声音等
           // setThumbImageView(imageView)
            setUrl(url)
            setSetUpLazy(true)//lazy可以防止滑动卡顿
            setVideoTitle(title)//标题
            setCacheWithPlay(true)//是否边缓存，m3u8等无效
            setRotateViewAuto(false)//是否开启自动旋转
            setLockLand(false)//一全屏就锁屏横屏，默认false竖屏，可配合setRotateViewAuto使用
            setPlayTag(TAG)//播放tag防止错误，因为普通的url也可能重复
            setShowFullAnimation(false)//是否使用全屏动画效果
            setNeedLockFull(false)//是否需要全屏锁定屏幕功能
            setPlayPosition(position)//设置播放位置防止错位
            setVideoAllCallBack(object : GSYSampleCallBack() {
                override fun onPrepared(url: String?, vararg objects: Any) {
                    super.onPrepared(url, *objects)
                    if (!sampleCoverVideo.isIfCurrentIsFullscreen) {
                        //静音
                       // GSYVideoManager.instance().isNeedMute = true
                    }
                }

                override fun onAutoComplete(url: String?, vararg objects: Any?) {
                    sampleCoverVideo.initUIState()
                }

                override fun onQuitFullscreen(url: String?, vararg objects: Any) {
                    super.onQuitFullscreen(url, *objects)
                    //全屏不静音
                    GSYVideoManager.instance().isNeedMute = true
                }

                override fun onEnterFullscreen(url: String?, vararg objects: Any) {
                    super.onEnterFullscreen(url, *objects)
                    GSYVideoManager.instance().isNeedMute = false
                    sampleCoverVideo.currentPlayer.titleTextView.text = objects[0] as String
                }
            }).build(sampleCoverVideo)
        }

    }


}