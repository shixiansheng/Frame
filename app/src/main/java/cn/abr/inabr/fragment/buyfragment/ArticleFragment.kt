package cn.abr.inabr.fragment.buyfragment


import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager

import cn.abr.inabr.R
import cn.abr.inabr.adapter.itemdelagate.ArticleBuyItem
import cn.abr.inabr.adapter.itemdelagate.MagazineBuyItem
import cn.abr.inabr.base.BaseLazyFragment
import cn.abr.inabr.base.BaseMultiItemTypeAdapter
import cn.abr.inabr.mvp.presenter.HomePresenter
import kotlinx.android.synthetic.main.fragment_article.*
import kotlinx.android.synthetic.main.fragment_magazine.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [ArticleFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ArticleFragment : BaseLazyFragment<HomePresenter>() {
    override val layoutId: Int
        get() = R.layout.fragment_article

    override fun initView() {
        with(article_buy_rlv) {
            layoutManager =  LinearLayoutManager(context)
            adapter = BaseMultiItemTypeAdapter<String>(activity, arrayListOf("","","","")).apply {
                addItemViewDelegate(ArticleBuyItem())
            }
        }
    }

    override fun inject() {
    }

    override fun initData() {
    }

    override fun setListener() {
    }







    companion object {
        @JvmStatic
        fun newInstance() =
                ArticleFragment()

    }
}
