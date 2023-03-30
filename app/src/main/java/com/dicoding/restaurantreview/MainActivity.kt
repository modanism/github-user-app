package com.dicoding.restaurantreview

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.restaurantreview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        supportActionBar?.hide()

        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

//        mainViewModel.restaurant.observe(this, { restaurant ->
//            setRestaurantData(restaurant)
//        })

        val layoutManager = LinearLayoutManager(this)
        binding.rvReview.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvReview.addItemDecoration(itemDecoration)

        mainViewModel.listReview.observe(this, { consumerReviews ->
            setReviewData(consumerReviews)
        })
        mainViewModel.isLoading.observe(this, {
            showLoading(it)
        })

//        mainViewModel.snackbarText.observe(this, {
//            it.getContentIfNotHandled()?.let { snackBarText ->
//                Snackbar.make(
//                    window.decorView.rootView,
//                    snackBarText,
//                    Snackbar.LENGTH_SHORT
//                ).show()
//            }
//        })

//        binding.btnSend.setOnClickListener { view ->
//            mainViewModel.postReview(binding.edReview.text.toString())
//            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//            imm.hideSoftInputFromWindow(view.windowToken, 0)
//        }
    }

//    private fun setRestaurantData(restaurant: Restaurant) {
//        binding.tvTitle.text = restaurant.name
//        binding.tvDescription.text = restaurant.description
//        Glide.with(this)
//            .load("https://restaurant-api.dicoding.dev/images/large/${restaurant.pictureId}")
//            .into(binding.ivPicture)
//    }

    private fun setReviewData(consumerReviews: List<User>) {
        val adapter = ReviewAdapter(consumerReviews)
        binding.rvReview.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                mainViewModel.findUser(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isBlank()) {
                    mainViewModel.findUser("a")
                }
                return false
            }
        })
        return true
    }
}