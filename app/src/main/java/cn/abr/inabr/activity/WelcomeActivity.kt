package cn.abr.inabr.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import cn.abr.inabr.R

/**
 * 应用的启动分为冷启动、热启动、温启动
 * 冷启动之前，要执行三个任务：

1.加载启动App；
2.App启动之后立即展示出一个空白的Window；
3.创建App的进程；

而这三个任务执行完毕之后会马上执行以下任务：

4.创建App对象；
5.启动Main Thread；
6.创建启动的Activity对象；
7.加载View；
8.布置屏幕；
9.进行第一次绘制；

而一旦App进程完成了第一次绘制，系统进程就会用Main Activity替换已经展示的Background Window，此时用户就可以使用App了。


Google也给出了启动加速的方向：

1.利用提前展示出来的Window，快速展示出来一个界面，给用户快速反馈的体验；
2.避免在启动时做密集沉重的初始化（Heavy app initialization）；
3.定位问题：避免I/O操作、反序列化、网络操作、布局嵌套等。

 */
class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        object : CountDownTimer(3100,1000){
            override fun onFinish() {
                startActivity(Intent(this@WelcomeActivity,MainActivity::class.java))
                finish()
            }

            override fun onTick(p0: Long) {
            }


        }.start()
    }
}
