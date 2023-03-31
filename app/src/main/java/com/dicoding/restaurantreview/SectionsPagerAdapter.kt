package com.dicoding.restaurantreview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: AppCompatActivity, name: String) : FragmentStateAdapter(activity) {
    var username: String = name
    override fun createFragment(position: Int): Fragment {
        return FollowFragment.newInstance(position + 1, username)
    }
    override fun getItemCount(): Int {
        return 2
    }
}