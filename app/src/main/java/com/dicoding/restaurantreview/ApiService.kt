package com.dicoding.restaurantreview

import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
//    @Headers("Authorization: token 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")
    fun getSearchUsers(
        @Query("q") query : String
    ):Call<UserResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<UserDetailResponse>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<UserDetailResponse>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<UserDetailResponse>>

}