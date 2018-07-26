package cn.abr.inabr.adapter.itemdelagate

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import cn.abr.inabr.R
import cn.abr.inabr.R.id.*
import cn.abr.inabr.entity.SelectionsListEntity
import cn.abr.inabr.utils.GlideUtils
import cn.abr.inabr.weight.videoplayer.SampleCoverVideo
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.zhy.adapter.recyclerview.base.ItemViewDelegate
import com.zhy.adapter.recyclerview.base.ViewHolder

/**
 * .
 * Created by Administrator on 2018/4/27/027.
 */
class VideoFragmentItem : ItemViewDelegate<SelectionsListEntity> {


    companion object {
        val TAG: String? = this.javaClass.simpleName
    }

    override fun getItemViewLayoutId(): Int = R.layout.videofragment_list_video_item


    private lateinit var gsyVideoOptionBuilder: GSYVideoOptionBuilder

    private lateinit var context: Context

    override fun convert(holder: ViewHolder, t: SelectionsListEntity, position: Int) {
        context = holder.itemView.context
        gsyVideoOptionBuilder = GSYVideoOptionBuilder()
        val sampleCoverVideo = holder.getView<SampleCoverVideo>(vdieofragment_list_samplevideo)
        onBind(position, t, sampleCoverVideo,holder)
        holder.apply {
            setText(vdieofragment_list_column_text, t.videoColumn)
            setText(vdieofragment_list_commentcount, t.centerLabel)
            setText(vdieofragment_list_time, t.rightLabel)
            setText(vdieofragment_list_title,t.title)
            setText(vdieofragment_list_duration,t.videoTime)
            GlideUtils.setThePicture(this.itemView.context,t.videoColumnImage,getView<ImageView>(vdieofragment_list_column_img))
        }

    }


    override fun isForViewType(item: SelectionsListEntity?, position: Int): Boolean = true


    fun onBind(position: Int, videoModel: SelectionsListEntity, sampleCoverVideo: SampleCoverVideo, holder: ViewHolder) {

        sampleCoverVideo.loadCoverImage(if (videoModel.imageUrl != null && videoModel.imageUrl!!.isNotEmpty())
            videoModel.imageUrl!![0] else  "",R.drawable.placeholder_picture_image)

        //val url: String? = videoModel.videoUrl
        val url: String? = "http://cache.m.iqiyi.com/mus/19863336609/6b7b7b77d6f4681f8d2498603d883101/afbe8fd3d73448c9//20180712/7f/15/0519fc3e47c32eaef52fbb0f2cd3c6bc.m3u8?qd_originate=tmts_py&tvid=19863336609&bossStatus=0&qd_vip=0&px=&qd_src=&prv=&previewType=&previewTime=&from=&qd_time=1531454675319&qd_p=7f000001&qd_asc=2863b8c22a425e4608f8cd34e5aec02f&qypid=19863336609_04000000001000000000_1&qd_k=26e398123f105a45716e58f9a527cb1b&isdol=0&code=2&iswb=0&vf=bf54f6b5f874ff70a765f7e76935c33e&np_tag=nginx_part_tag&v=513799574&qypid=19863336609_-107233"
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

                    if (!sampleCoverVideo.isIfCurrentIsFullscreen) {
                        //静音
                       // GSYVideoManager.instance().isNeedMute = true
                    }
                }

                override fun onStartPrepared(url: String?, vararg objects: Any?) {
                    holder.getView<TextView>(R.id.vdieofragment_list_duration).visibility=View.GONE
                }
                override fun onAutoComplete(url: String?, vararg objects: Any?) {
                    sampleCoverVideo.initUIState()
                    holder.getView<TextView>(R.id.vdieofragment_list_duration).visibility=View.VISIBLE
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