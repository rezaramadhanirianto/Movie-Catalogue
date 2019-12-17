package com.example.asus.finalsubmissionr2.retro

import android.app.Application
import android.content.Context
import com.example.asus.finalsubmissionr2.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiMain : Application() {
    private val client = OkHttpClient().newBuilder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        })
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://api.themoviedb.org/3/discover/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val services: ApiService = retrofit.create(ApiService::class.java)
}
