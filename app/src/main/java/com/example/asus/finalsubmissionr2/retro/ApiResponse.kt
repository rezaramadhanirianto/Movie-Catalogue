package com.example.asus.finalsubmissionr2.retro

import com.example.asus.finalsubmissionr2.model.MovieAlarm
import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("results")
    val result: List<MovieAlarm>
)