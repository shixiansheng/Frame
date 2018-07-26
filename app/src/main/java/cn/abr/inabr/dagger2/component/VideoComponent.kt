package cn.abr.inabr.dagger2.component

import cn.abr.inabr.dagger2.module.SelectionsModule
import cn.abr.inabr.dagger2.module.VideoModule
import cn.abr.inabr.fragment.homefragment.VideoFragment
import cn.abr.inabr.mvp.model.SelectionsModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(VideoModule::class)])
interface VideoComponent {
    fun inject(videoFragment: VideoFragment)
}
