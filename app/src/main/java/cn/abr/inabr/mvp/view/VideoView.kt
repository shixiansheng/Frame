package cn.abr.inabr.mvp.view

import cn.abr.inabr.base.BaseEntity
import cn.abr.inabr.entity.SelectionsListEntity
import cn.abr.inabr.entity.VideoColumnEntity

interface VideoView :FailureView{
    fun showList(response: BaseEntity<List<SelectionsListEntity>>)
    fun showColumn(response: BaseEntity<List<VideoColumnEntity>>)
}
