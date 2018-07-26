package cn.abr.inabr.base


import javax.inject.Inject

import dagger.Module
import dagger.Provides

/**
 * @author 时志邦
 * @Description: ${TODO}(用一句话描述该文件做什么)
 */


open class BaseModule<T> constructor(var view: T)