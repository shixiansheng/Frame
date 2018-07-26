package cn.abr.inabr.fragment.homefragment


import android.os.Bundle
import android.app.Fragment

import cn.abr.inabr.R
import cn.abr.inabr.base.BaseLazyFragment
import cn.abr.inabr.mvp.presenter.HomePresenter


/**
 * A simple [Fragment] subclass.
 * Use the [OtherFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OtherFragment : BaseLazyFragment<HomePresenter>() {
    override fun setListener() {

    }

    override val layoutId: Int
        get() = R.layout.fragment_other

    override fun initView() {
    }

    override fun inject() {
    }

    override fun initData() {
    }

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
        }
    }


    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OtherFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String): OtherFragment {
            val fragment = OtherFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            fragment.arguments = args
            return fragment
        }
    }

}// Required empty public constructor
