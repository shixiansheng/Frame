package cn.abr.inabr.fragment.buyfragment

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import cn.abr.inabr.R
import cn.abr.inabr.adapter.itemdelagate.MagazineBuyItem
import cn.abr.inabr.base.BaseLazyFragment
import cn.abr.inabr.base.BaseMultiItemTypeAdapter

import cn.abr.inabr.mvp.presenter.HomePresenter
import kotlinx.android.synthetic.main.fragment_magazine.*

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [MagazineFragment.OnListFragmentInteractionListener] interface.
 */
class MagazineFragment : BaseLazyFragment<HomePresenter>() {


    override val layoutId: Int
        get() = R.layout.fragment_magazine

    override fun initView() {
            with(magazine_buy_rlv) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount).apply {
                    }
                }
                adapter = BaseMultiItemTypeAdapter<String>(activity, arrayListOf("","","","")).apply {
                    addItemViewDelegate(MagazineBuyItem())
                }
            }
        }
    override fun inject() {
    }

    override fun initData() {
    }

    override fun setListener() {
    }



    // TODO: Customize parameters
    private var columnCount = 2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }




    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
                MagazineFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_COLUMN_COUNT, columnCount)
                    }
                }
    }
}
