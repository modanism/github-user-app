package com.dicoding.restaurantreview

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.restaurantreview.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class DetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailBinding

    companion object {
        const val KEY_USER = "key_user"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)

        detailViewModel.detailUser.observe(this, { user ->
            setDetailUserData(user)
        })

        detailViewModel.isLoading.observe(this, {
            showLoading(it)
        })

        val userData = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(KEY_USER,User::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(KEY_USER)
        }

        Log.d("DetailTest", userData.toString())

        val username = userData?.login
        if (username != null) {
            detailViewModel.findDetailUser(username)
        }

//        binding.apply {
//            Glide.with(this@DetailActivity)
//                .load(userData?.avatarUrl)
//                .centerCrop()
//                .into(ivDetail)
//            tvDetailName.text = userData?.login
//        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this,username!!)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

    }

    private fun setDetailUserData(userData : UserDetailResponse) {
        binding.apply {
            tvDetailLogin.text = userData.login
            tvDetailName.text = userData.name
            Glide.with(this@DetailActivity  )
                .load(userData.avatarUrl)
                .into(ivDetail)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.detailProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}