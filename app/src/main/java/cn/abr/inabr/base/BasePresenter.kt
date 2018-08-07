package cn.abr.inabr.base

import javax.inject.Inject

/**
 * @author 时志邦
 * @Description: ${TODO}(用一句话描述该文件做什么)
 */

 interface BasePresenter<P> {

    var mView: P?

    fun detach() {
        mView = null
    }
}
