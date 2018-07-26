package cn.abr.inabr.common

import cn.abr.inabr.BuildConfig
import cn.abr.inabr.base.BaseApp
import com.orhanobut.logger.*
import com.orhanobut.logger.PrettyFormatStrategy
import com.squareup.leakcanary.LeakCanary
import com.zhy.autolayout.config.AutoLayoutConifg
import com.squareup.leakcanary.RefWatcher

/**
 *　　　　　　　 ┏┓       ┏┓+ +
 *　　　　　　　┏┛┻━━━━━━━┛┻┓ + +
 *　　　　　　　┃　　　　　　 ┃
 *　　　　　　　┃　　　━　　　┃ ++ + + +
 *　　　　　　 █████━█████  ┃+
 *　　　　　　　┃　　　　　　 ┃ +
 *　　　　　　　┃　　　┻　　　┃
 *　　　　　　　┃　　　　　　 ┃ + +
 *　　　　　　　┗━━┓　　　 ┏━┛
 *               ┃　　  ┃
 *　　　　　　　　　┃　　  ┃ + + + +
 *　　　　　　　　　┃　　　┃　Code is far away from     bug with the animal protecting
 *　　　　　　　　　┃　　　┃ + 　　　　         神兽保佑,代码无bug
 *　　　　　　　　　┃　　　┃
 *　　　　　　　　　┃　　　┃　　+
 *　　　　　　　　　┃　 　 ┗━━━┓ + +
 *　　　　　　　　　┃ 　　　　　┣┓
 *　　　　　　　　　┃ 　　　　　┏┛
 *　　　　　　　　　┗┓┓┏━━━┳┓┏┛ + + + +
 *　　　　　　　　　 ┃┫┫　 ┃┫┫
 *　　　　　　　　　 ┗┻┛　 ┗┻┛+ + + +
 */
//
//                            _ooOoo_
//                           o8888888o
//                           88" . "88
//                           (| -_- |)
//                           O\  =  /O
//                        ____/`---'\____
//                      .'  \\|     |//  `.
//                     /  \\|||  :  |||//  \
//                    /  _||||| -:- |||||-  \
//                    |   | \\\  -  /// |   |
//                    | \_|  ''\---/''  |   |
//                    \  .-\__  `-`  ___/-. /
//                  ___`. .'  /--.--\  `. . __
//               ."" '<  `.___\_<|>_/___.'  >'"".
//              | | :  `- \`.;`\ _ /`;.`/ - ` : | |
//              \  \ `-.   \_ __\ /__ _/   .-` /  /
//         ======`-.____`-.___\_____/___.-`____.-'======
//                            `=---='
//        ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
//                      Buddha Bless, No Bug !
//        佛曰:
//                写字楼里写字间，写字间里程序员；
//                程序人员写程序，又拿程序换酒钱。
//                酒醒只在网上坐，酒醉还来网下眠；
//                酒醉酒醒日复日，网上网下年复年。
//                但愿老死电脑间，不愿鞠躬老板前；
//                奔驰宝马贵者趣，公交自行程序员。
//                别人笑我忒疯癫，我笑自己命太贱；
//                不见满街漂亮妹，哪个归得程序员？
//
//
/**
 *                      江城子 . 程序员之歌
 *
 *                  十年生死两茫茫，写程序，到天亮。
 *                      千行代码，Bug何处藏。
 *                  纵使上线又怎样，朝令改，夕断肠。
 *
 *                  领导每天新想法，天天改，日日忙。
 *                      相顾无言，惟有泪千行。
 *                  每晚灯火阑珊处，夜难寐，加班狂。
 */
class App : BaseApp() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    override fun initLocalOptions() {
        initLogger()
    }

    override fun initLocalOptionsInMainThread() {
        AutoLayoutConifg.getInstance().useDeviceSize()
    }

    override fun initSDK() {
        refWatcher = setupLeakCanary()
    }

    private fun setupLeakCanary(): RefWatcher {
        return if (LeakCanary.isInAnalyzerProcess(this)) {
            RefWatcher.DISABLED
        } else LeakCanary.install(this)
    }

    fun getRefWatcher(): RefWatcher {
        return this.refWatcher
    }
    private fun initLogger() {
        val formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)  // 是否打印线程号,默认true
                .methodCount(0)         // 展示几个方法数,默认2
                .methodOffset(5)        // (Optional) Hides internal method calls up to offset. Default 5
                //                .logStrategy(customLog) //是否更换打印输出,默认为logcat
                .tag("QiChang_Debug")   // 全局的tag
                .build()
        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }
    private lateinit var refWatcher: RefWatcher

    companion object {
        private lateinit var instance: App
        open fun Instance() = instance
    }
}