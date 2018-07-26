package cn.abr.inabr.utils

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import cn.abr.inabr.utils.ApkUtils.getVersionName

import java.io.File

/**
 * Created by Administrator on 2017/8/1.
 *
 * @author ngj
 * @describe 获取app相关信息的工具类
 */

object ApkUtils {

    /**
     * 获取应用程序名称
     */
    fun getAppName(context: Context): String? {
        try {
            val packageManager = context.packageManager
            val packageInfo = packageManager.getPackageInfo(context.packageName, 0)
            val labelRes = packageInfo.applicationInfo.labelRes
            return context.resources.getString(labelRes)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return null
    }

    /**
     * 获取应用程序版本名称信息
     *
     * @param context
     * @return 当前应用的版本名称
     */
    fun getVersionName(context: Context): String {
        try {
            // 获取 PackageManager 实例
            val mPackageManager = context.packageManager
            // getPackageName() 是你当前类的包名，0代表是获取版本信息
            val packInfo = mPackageManager.getPackageInfo(context.packageName, 0)
            return packInfo.versionName
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }

    }

    /**
     * 获取应用程序版本号信息
     *
     * @param context
     * @return 当前应用程序版本号
     */
    fun getVersionCode(context: Context): Int {
        try {
            val mPackageManager = context.packageManager
            val packInfo = mPackageManager.getPackageInfo(context.packageName, 0)
            return packInfo.versionCode
        } catch (e: Exception) {
            e.printStackTrace()
            return 0
        }

    }

    /**
     * 得到安装的intent
     *
     * @param apkFile
     * @return
     */
    fun getInstallIntent(apkFile: File): Intent {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.setDataAndType(Uri.fromFile(File(apkFile.absolutePath)),
                "application/vnd.android.package-archive")
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        return intent
    }

    /**
     * 获取系统信息
     *
     * @param context
     * @return
     */
    fun getHandSetInfo(context: Context): String {
        return "[手机型号:" + Build.MODEL +
                ", 手机品牌:" + Build.BRAND +
                ", SDK版本:" + Build.VERSION.SDK_INT +
                ", 系统版本:" + Build.VERSION.RELEASE +
                ", 软件版本:" + getVersionName(context) + "]"
    }

    /**
     * 获取渠道名
     *
     * @param ctx 此处习惯性的设置为activity，实际上context就可以
     * @return 如果没有获取成功，那么返回值为空
     */
    fun getChannelName(ctx: Context?): String? {
        if (ctx == null) {
            return null
        }
        var channelName: String? = null
        try {
            val packageManager = ctx.packageManager
            if (packageManager != null) {
                // 注意此处为ApplicationInfo 而不是 ActivityInfo,因为友盟设置的meta-data是在application标签中，而不是某activity标签中，所以用ApplicationInfo
                val applicationInfo = packageManager.getApplicationInfo(ctx.packageName, PackageManager.GET_META_DATA)
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        // key换成说需要的key
                        channelName = applicationInfo.metaData.getString("UMENG_CHANNEL")
                    }
                }

            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return channelName
    }
}
