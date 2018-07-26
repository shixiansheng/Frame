package cn.abr.inabr.dagger2.module

import cn.abr.inabr.base.BaseModule
import cn.abr.inabr.mvp.view.VideoView
import dagger.Module
import dagger.Provides
import javax.inject.Inject

@Module
class VideoModule @Inject constructor(view: VideoView) : BaseModule<VideoView>(view) {
    @Provides
    fun provideTempLateView(): VideoView = view
}
