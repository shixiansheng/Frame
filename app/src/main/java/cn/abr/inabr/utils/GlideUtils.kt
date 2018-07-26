package cn.abr.inabr.utils

import android.content.Context
import android.widget.ImageView

import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

import cn.abr.inabr.R


/**
 * @describe setImageUtil
 * @author ning
 * @Glide https://mrfu.me/2016/02/27/Glide_Getting_Started/
 */
object GlideUtils {


    /**
     * set the picture
     * @param context
     * @param picUrl
     * @param iv
     */
    fun setThePicture(context: Context, picUrl: String?, iv: ImageView) {
        var picUrl = picUrl
        if (picUrl == null) picUrl = ""
        if (picUrl.endsWith(".gif") || picUrl.endsWith(".GIF")) {
            Glide.with(context).asGif().load(picUrl)
                    .apply(RequestOptions().dontAnimate().placeholder(R.drawable.placeholder_picture_image).error(R.drawable.placeholder_picture_image))
                    //.diskCacheStrategy(DiskCacheStrategy.NONE)
                    .transition(DrawableTransitionOptions().crossFade()).into(iv)
        } else {
            Glide.with(context).load(picUrl)
                    .apply(RequestOptions().dontAnimate().placeholder(R.drawable.placeholder_picture_image).error(R.drawable.placeholder_picture_image))
                    //.diskCacheStrategy(DiskCacheStrategy.NONE)
                    .transition(DrawableTransitionOptions().crossFade()).into(iv)
        }
    }
    /**
     * set the picture
     * @param context
     * @param picUrl
     * @param iv
     */
    fun setHeadPortrait(context: Context, picUrl: String?, iv: ImageView) {
        var picUrl = picUrl
        if (picUrl == null) picUrl = ""
        if (picUrl.endsWith(".gif") || picUrl.endsWith(".GIF")) {
            Glide.with(context).asGif().load(picUrl)
                    .apply(RequestOptions().dontAnimate().placeholder(R.drawable.placeholder_picture_image).error(R.drawable.placeholder_picture_image))
                    //.diskCacheStrategy(DiskCacheStrategy.NONE)
                    .transition(DrawableTransitionOptions().crossFade()).into(iv)
        } else {
            Glide.with(context).load(picUrl)
                    .apply(RequestOptions().dontAnimate().placeholder(R.drawable.placeholder_picture_image).error(R.drawable.placeholder_picture_image))
                    //.diskCacheStrategy(DiskCacheStrategy.NONE)
                    .transition(DrawableTransitionOptions().crossFade()).into(iv)
        }
    }


    fun setNoResponseImages(context: Context, picUrl: String?, iv: ImageView) {
        var picUrl = picUrl
        if (picUrl == null) picUrl = ""
        Glide.with(context).load(picUrl).into(iv)
    }

}
