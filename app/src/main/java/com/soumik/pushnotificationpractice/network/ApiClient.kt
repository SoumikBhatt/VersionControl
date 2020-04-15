package com.soumik.pushnotificationpractice.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

var apiService = ApiClient().apiServices

class ApiClient {
    var retrofit : Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://fcm.googleapis.com/")
        .build()

    var apiServices: ApiServices = retrofit.create(ApiServices::class.java)
}