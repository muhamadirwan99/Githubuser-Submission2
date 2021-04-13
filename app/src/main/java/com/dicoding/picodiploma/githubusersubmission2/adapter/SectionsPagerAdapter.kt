package com.dicoding.picodiploma.githubusersubmission2.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.picodiploma.githubusersubmission2.ui.fragment.FollowersFragment
import com.dicoding.picodiploma.githubusersubmission2.ui.fragment.FollowingFragment

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    var username: String? = null

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowingFragment.newInstance(username.toString())
            1 -> fragment = FollowersFragment.newInstance(username.toString())
        }
        return fragment as Fragment
    }

}