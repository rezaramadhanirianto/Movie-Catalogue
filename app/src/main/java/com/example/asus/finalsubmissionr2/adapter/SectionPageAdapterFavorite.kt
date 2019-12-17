package com.example.asus.finalsubmissionr2.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.asus.finalsubmissionr2.R
import com.example.asus.finalsubmissionr2.fragment.MovieFavorite
import com.example.asus.finalsubmissionr2.fragment.MovieFragment
import com.example.asus.finalsubmissionr2.fragment.TvShowFavorite
import com.example.asus.finalsubmissionr2.fragment.TvShowFragment

class SectionPageAdapterFavorite (private val mContext: Context, fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
    private val TAB_TITLES = intArrayOf(R.string.tab_movie, R.string.tab_tv)
    override fun getItem(position: Int): Fragment {
        var fragment:Fragment?=null
        when(position){
            0-> fragment = MovieFavorite()
            1-> fragment = TvShowFavorite()
        }
        return fragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return 2
    }

}