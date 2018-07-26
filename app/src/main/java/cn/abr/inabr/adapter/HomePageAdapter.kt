package cn.abr.inabr.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.ViewGroup
import cn.abr.inabr.fragment.homefragment.OtherFragment
import cn.abr.inabr.fragment.homefragment.SelectionsFragment
import cn.abr.inabr.fragment.homefragment.VideoFragment

/**
 * .
 * Created by Administrator on 2018/4/23/023.
 */
class HomePageAdapter(fragmentManager: FragmentManager, var titleList: List<String>) : FragmentStatePagerAdapter(fragmentManager) {



    override fun getItem(position: Int): Fragment {
       return  getFragment(titleList[position])
    }

    private fun getFragment(class_name: String): Fragment {
        when (class_name) {
            "直播" ->
                return SelectionsFragment.newInstance(class_name)
            "视频" ->
                return VideoFragment.newInstance(class_name)
            else ->
                return SelectionsFragment.newInstance(class_name)
        }
    }

    override fun getCount(): Int {
        return titleList.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return titleList[position]
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        //super.destroyItem(container, position, `object`)
    }
}