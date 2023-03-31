package com.dicoding.restaurantreview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    private val _detailUser = MutableLiveData<UserDetailResponse>()
    val detailUser: LiveData<UserDetailResponse> = _detailUser

    private val _listFollow = MutableLiveData<List<UserDetailResponse>>()
    val listFollow: LiveData<List<UserDetailResponse>> = _listFollow

    private val _listFollowing = MutableLiveData<List<User>>()
    val listFollowing: LiveData<List<User>> = _listFollowing

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "DetailViewModel"
    }

    fun findDetailUser(username : String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<UserDetailResponse> {
            override fun onResponse(
                call: Call<UserDetailResponse>,
                response: Response<UserDetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detailUser.value = response.body()
                    Log.d("DetailUser", detailUser.toString())
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })

    }

    fun findFollow(opt: String, username: String) {
        Log.d("Username",username)
        _isLoading.value = true
        val client = if (opt == "follower") {
            ApiConfig.getApiService().getFollowers(username)
        } else {
           ApiConfig.getApiService().getFollowing(username)
        }
        client.enqueue(object : Callback<List<UserDetailResponse>> {
            override fun onResponse(call: Call<List<UserDetailResponse>>, response: Response<List<UserDetailResponse>>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    Log.d("Testing",response.body().toString())
                    _listFollow.value = response.body()
                } else {
                    Log.e(TAG,"onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserDetailResponse>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })


    }

}