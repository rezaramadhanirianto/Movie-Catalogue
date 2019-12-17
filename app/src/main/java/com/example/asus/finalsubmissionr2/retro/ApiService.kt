package com.example.asus.finalsubmissionr2.retro

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("movie?api_key=6e4227ca7e0d74b4abb39cf05b473946")

    fun getToday(

        @Query("primary_release_date.gte") dateGte: String,
        @Query("primary_release_date.lte") dateLte: String
    ): Call<ApiResponse>
}